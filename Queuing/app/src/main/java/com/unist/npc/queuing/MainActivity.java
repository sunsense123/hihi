package com.unist.npc.queuing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.kakao.auth.APIErrorResult;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.MeResponseCallback;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.UserProfile;
import com.kakao.util.helper.log.Logger;

import org.w3c.dom.Text;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;

    // Declaring Your View and Variables
    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private DrawerLayout mDrawerLayout;
    private FrameLayout rightDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    FrameLayout map_btn;
    private TextView mypage_btn;

    DBManager_reserv manager;


    private int[] imageResId = {
            R.drawable.ic_tab_shop,
            R.drawable.ic_tab_reserv_info,
            R.drawable.ic_tab_favorites,
    };
    //CharSequence Titles[]={"Home","Events"};
    int Numboftabs =3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences prefs = getApplicationContext().getSharedPreferences("com.unist.npc.queuing.", Context.MODE_PRIVATE);
        Boolean IsLogined= prefs.getBoolean("IsLogin",false);
        Log.d("CREATE", "CREATE, is login? : " + IsLogined);
        if(IsLogined) {
            setContentView(R.layout.activity_main);
            backPressCloseHandler = new BackPressCloseHandler(this);
            // Creating The Toolbar and setting it as the Toolbar for the activity
            toolbar = (Toolbar) findViewById(R.id.toolbar_main);
            mypage_btn = (TextView) findViewById(R.id.main2mypage);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
            adapter = new ViewPagerAdapter(getSupportFragmentManager(), imageResId, Numboftabs, getApplicationContext());
            // Assigning ViewPager View and setting the adapter
            pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            rightDrawer = (FrameLayout) findViewById(R.id.rDrawer);
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
                /**
                 * Called when a drawer has settled in a completely closed state.
                 */
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                }

                /**
                 * Called when a drawer has settled in a completely open state.
                 */
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };
            mDrawerLayout.setDrawerListener(mDrawerToggle);

            // Assiging the Sliding Tab Layout View
            tabs = (SlidingTabLayout) findViewById(R.id.tabs);
//        tabs.setDistributeEvenly(false); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

            // Setting Custom Color for the Scroll bar indicator of the Tab View
            tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    return getResources().getColor(R.color.tabsScrollColor);
                }
            });

            // Setting the ViewPager For the SlidingTabsLayout
            tabs.setViewPager(pager);


            mypage_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                    startActivity(intent);
                }
            });
            map_btn = (FrameLayout)findViewById(R.id.map_btn);
            map_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            //setContentView(R.layout.activity_login);
            redirectLoginActivity();
        }
    }

    protected void showSignup() {
        Logger.d("KAKAO", "not registered user");
        redirectLoginActivity();
    }
    protected void redirectLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_navigation_drawer);
        Bitmap new_icon = resizeBitmapImageFn(icon, 40);
        Drawable d = new BitmapDrawable(getResources(),new_icon);
        menu.getItem(0).setIcon(d);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //item.setIcon(R.drawable.)
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        item.getIcon();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            } else {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private Bitmap resizeBitmapImageFn(
            Bitmap bmpSource, int maxResolution){
        int iWidth = bmpSource.getWidth();      //비트맵이미지의 넓이
        int iHeight = bmpSource.getHeight();     //비트맵이미지의 높이
        int newWidth = iWidth ;
        int newHeight = iHeight ;
        float rate = 0.0f;

        //이미지의 가로 세로 비율에 맞게 조절
        if(iWidth > iHeight ){
            if(maxResolution < iWidth ){
                rate = maxResolution / (float) iWidth ;
                newHeight = (int) (iHeight * rate);
                newWidth = maxResolution;
            }
        }else{
            if(maxResolution < iHeight ){
                rate = maxResolution / (float) iHeight ;
                newWidth = (int) (iWidth * rate);
                newHeight = maxResolution;
            }
        }

        return Bitmap.createScaledBitmap(
                bmpSource, newWidth, newHeight, true);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            manager = new DBManager_reserv(context, "reserv_info.db", null, 1);
            Log.e("CHECK", "onReceive");
            manager.delete("delete from RESERV_INFO");
            adapter.notifyDataSetChanged();
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        Log.e("CHECK", "main onResume");

        getApplicationContext().registerReceiver(mReceiver, new IntentFilter("cus"));

    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e("CHECK", "main onPause");
        getApplicationContext().unregisterReceiver(mReceiver);
    }

}
