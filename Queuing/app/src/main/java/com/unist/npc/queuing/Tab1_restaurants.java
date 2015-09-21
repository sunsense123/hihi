package com.unist.npc.queuing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kakao.auth.APIErrorResult;
import com.kakao.kakaotalk.KakaoTalkHttpResponseHandler;
import com.kakao.kakaotalk.KakaoTalkProfile;
import com.kakao.kakaotalk.KakaoTalkService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab1_restaurants extends Fragment {

    private static final int CALL_REQUEST = 123;

    Context mContext;
    ListView res_listview;
    ArrayList<ResListItem> items;
    ResListAdapter adapter;
    RelativeLayout layout_img;
    boolean lastItemVisibleFlag;

    String name = null;
    String cuisine = null;
    int waiting_people = 0;
    String img_large = null;
    String timing = null;
    String location = null;
    Double x_coordinate = null;
    Double y_coordinate = null;
    String phone_num = null;
    String dummyname = null;


    String nickName;
    String profileImageURL ;
    String thumbnailURL ;
    String countryISO ;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        readProfile();
        new HttpPostRequest2().execute("APA91bGy1gqUVGGApf8FB3jIwyxf_M9E6neq2DL3fMTKihht6t2CBpuL2qKdQUDkk-knqHXkKCRSK2h0l6ANvVA-yAteAO9gw7A8FmA6gkqvccPqFQOAOcAnZKz_uavR715ty6Q1J47V",nickName);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab1_restaurants, container, false);

        if(container!=null){
            mContext = container.getContext();

            res_listview = (ListView) v.findViewById(R.id.res_list);
       // layout_img = (RelativeLayout) v.findViewById(R.id.layout_large_img);
        items = new ArrayList<ResListItem>();
        adapter = new ResListAdapter(mContext,R.layout.res_list_item,items);
            res_listview.setEnabled(false);
        new HttpPostRequest().execute("");

        res_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, RestaurantInfo.class);
                intent.putExtra("name",items.get(position).res_name);
                intent.putExtra("cuisine",items.get(position).res_cuisine);
                intent.putExtra("timing",items.get(position).res_timing);
                intent.putExtra("img_large",items.get(position).res_imgurl);
                intent.putExtra("location",items.get(position).res_location);
                intent.putExtra("phone_num",items.get(position).res_phone_num);
                intent.putExtra("x_coordinate",items.get(position).res_x_coordinate);
                intent.putExtra("y_coordinate",items.get(position).res_y_coordinate);
                intent.putExtra("username",nickName);
                intent.putExtra("dummy_name",items.get(position).res_dummyname);

                startActivityForResult(intent,CALL_REQUEST);
            }
        });
        lastItemVisibleFlag = false;
        /*res_listview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("onScroll","YES");
                //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
                lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
                if(lastItemVisibleFlag){
                    Log.e("T&F","TRUE");
                    for(int i=0;i<3;i++) items.add(new ResListItem(0,null,null,null,null,null));
                    adapter.notifyDataSetChanged();
                }
                else Log.e("T&F","FALSE");
            }
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) Log.e("STATE","TRUE");
                else Log.e("STATE","FALSE");
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag) {
                    //TODO 화면이 바닦에 닿을때 처리

                }
            }

        });*/
        }
        return v;

    }
    public class HttpPostRequest extends AsyncTask<String,Void,String>{
        String sResult="error";
        @Override
        protected String doInBackground(String... info) {
            URL url = null;
            try {
                url = new URL("http://52.69.163.43/queuing/get_all_rest_info.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                String post_value = "";

                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                osw.write(post_value);
                osw.flush();

                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    builder.append(str);
                }
                sResult = builder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }


            return sResult;
        }
        @Override
        protected void onPostExecute(String result){
            Log.e("RESULT",result);
            String jsonall = result;
            JSONArray jArray = null;

            String distance = null;
            try{
                jArray = new JSONArray(jsonall);
                JSONObject json_data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    name = json_data.getString("name");
                    img_large = json_data.getString("img_large");
                    cuisine = json_data.getString("cuisine");
                    waiting_people = json_data.getInt("waiting_people");
                    x_coordinate = json_data.getDouble("x_coordinate");
                    y_coordinate =json_data.getDouble("y_coordinate");
                    location = json_data.getString("location");
                    phone_num = json_data.getString("phone_num");
                    timing = json_data.getString("timing");
                    dummyname = json_data.getString("dummy_name");
                    distance = String.valueOf((int)calDistance(37.557627, 126.936976,x_coordinate,y_coordinate));
                    items.add(new ResListItem(img_large, name, cuisine, distance, String.valueOf(waiting_people * 5),x_coordinate,y_coordinate,location,timing,phone_num,dummyname));
                    Log.e("PROFILE",":"+i);

                }
            }catch(Exception e){
                e.printStackTrace();
            }
            res_listview.setAdapter(adapter);





        }
    }

    public double calDistance(double lat1, double lon1, double lat2, double lon2){

        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
        dist = dist * 1000.0;      // 단위  km 에서 m 로 변환

        return dist;
    }

    // 주어진 도(degree) 값을 라디언으로 변환
    private double deg2rad(double deg){
        return (double)(deg * Math.PI / (double)180d);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private double rad2deg(double rad){
        return (double)(rad * (double)180d / Math.PI);
    }

    public void readProfile() {
        KakaoTalkService.requestProfile(new MyTalkHttpResponseHandler<KakaoTalkProfile>() {
            @Override
            public void onHttpSuccess(final KakaoTalkProfile talkProfile) {
                nickName = talkProfile.getNickName();
                profileImageURL = talkProfile.getProfileImageURL();
                thumbnailURL = talkProfile.getThumbnailURL();
                countryISO = talkProfile.getCountryISO();
                // display
                Log.d("OPEND", "onHttpSuccess " + nickName);
                res_listview.setEnabled(true);

            }
        });

    }



    private abstract class MyTalkHttpResponseHandler<T> extends KakaoTalkHttpResponseHandler<T> {
        @Override
        public void onHttpSessionClosedFailure(final APIErrorResult errorResult) {
            //redirectLoginActivity();
        }

        @Override
        public void onNotKakaoTalkUser(){
            Toast.makeText(mContext, "not a KakaoTalk user", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(final APIErrorResult errorResult) {
            Toast.makeText(mContext, "failed : " + errorResult, Toast.LENGTH_SHORT).show();
        }
    }

    private class HttpPostRequest2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... info) {
            String sResult = "Error";

            try {
                URL url = new URL("http://52.69.163.43/queuing/user_enroll.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");

                String body = "regid=" + info[0] +"&"
                        +"name=" + info[1];

                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                osw.write(body);
                osw.flush();


                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                String str;

                while ((str = reader.readLine()) != null) {
                    builder.append(str);
                }
                sResult     = builder.toString();
                Log.e("enroll", sResult);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sResult;
        }

        @Override
        protected void onPostExecute(String result){

        }

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause(){
        super.onPause();
    }

}
