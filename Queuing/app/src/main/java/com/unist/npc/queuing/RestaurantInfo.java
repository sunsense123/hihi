package com.unist.npc.queuing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;


public class RestaurantInfo extends AppCompatActivity {
    private static final int CALL_REQUEST = 456;


    Toolbar toolbar;
    FrameLayout res_confirm;

    String name = null;
    String cuisine = null;
    String img_large = null;
    String timing = null;
    String location = null;
    Double x_coordinate = null;
    Double y_coordinate = null;
    String phone_num = null;
    String dummyname;

    ImageView resinfo_image;
    TextView resinfo_name;
    TextView resinfo_cuisine;
    TextView resinfo_cuisine2;
    TextView resinfo_timing;
    TextView resinfo_location;
    TextView resinfo_phone_num;
    TextView resinfo_webpage;
    LinearLayout frame;
    CustomscrollViewformap scroll;

    int width_image;
    int height_image;

    String username;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_info);
        Intent intent = getIntent();
        img_large = intent.getExtras().getString("img_large");
        name = intent.getExtras().getString("name");
        cuisine = intent.getExtras().getString("cuisine");
        timing = intent.getExtras().getString("timing");
        location = intent.getExtras().getString("location");
        phone_num = intent.getExtras().getString("phone_num");
        x_coordinate = intent.getExtras().getDouble("x_coordinate");
        y_coordinate = intent.getExtras().getDouble("y_coordinate");
        username = intent.getExtras().getString("username");
        dummyname = intent.getExtras().getString("dummy_name");
        this.setResult(Activity.RESULT_OK);


        resinfo_image = (ImageView) findViewById(R.id.resinfo_image);
        resinfo_name = (TextView) findViewById(R.id.resinfo_name);
        resinfo_cuisine = (TextView) findViewById(R.id.resinfo_cuisine);
        resinfo_cuisine2= (TextView) findViewById(R.id.resinfo_cuisine2);
        resinfo_timing = (TextView) findViewById(R.id.resinfo_timing);
        resinfo_location = (TextView) findViewById(R.id.resinfo_location);
        resinfo_phone_num = (TextView) findViewById(R.id.resinfo_phone_num);
        resinfo_webpage = (TextView) findViewById(R.id.resinfo_webpage);
        frame = (LinearLayout) findViewById(R.id.resinfo_frame);


        resinfo_name.setText(name);
        resinfo_cuisine.setText(cuisine);
        resinfo_cuisine2.setText(cuisine);
        resinfo_timing.setText(timing);
        resinfo_location.setText(location);
        resinfo_phone_num.setText(phone_num);
        resinfo_webpage.setText("www.test.com");

        res_confirm = (FrameLayout)findViewById(R.id.res_confirm);
        res_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager_reserv manager = new DBManager_reserv(getApplicationContext(), "reserv_info.db", null, 1);
                if(manager.returnName().equals("nothing")){
                    Intent intent = new Intent(RestaurantInfo.this, ConfirmActivity.class);
                    intent.putExtra("username",username);
                    intent.putExtra("resname",name);
                    intent.putExtra("dummy_name",dummyname);
                    startActivityForResult(intent, CALL_REQUEST);
                }else Toast.makeText(getApplicationContext(), "You already queue!", Toast.LENGTH_LONG).show();

            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar_rest_info);

        /*final MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("6f34a566bab64437f455521185842b3f");
        ViewGroup mapViewContainer = (ViewGroup)findViewById(R.id.resinfo_map);
        mapViewContainer.addView(mapView);
        MapView.setMapTilePersistentCacheEnabled(true);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        mapView.setHDMapTileEnabled(true);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(x_coordinate, y_coordinate), true);
        mapView.setZoomLevel(2, true);
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    return true;
                }
                return false;
            }
        });
        addMarker(mapView);*/

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurantinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        height_image = frame.getHeight();
        width_image = frame.getWidth();
        Picasso.with(getApplicationContext()).load(img_large).resize(width_image, height_image).centerCrop().into(resinfo_image);
    }

    public void addMarker(MapView mapView){
        MapPOIItem marker = new MapPOIItem();
        marker.setShowCalloutBalloonOnTouch(true);
        marker.setShowDisclosureButtonOnCalloutBalloon(false);
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(x_coordinate, y_coordinate));
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);


        mapView.addPOIItem(marker);
    }



}

