package com.unist.npc.queuing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kakao.kakaotalk.KakaoTalkProfile;
import com.kakao.kakaotalk.KakaoTalkService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab2_reservation_info extends Fragment {
    Boolean isQueue = false;
    TextView resrv_name;
    TextView resrv_party;
    TextView time_left;
    TextView people_left;
    TextView cancel_btn;
    Context mContext;

    DBManager_reserv manager;
    DBManager_update manager_update;

    String nickName;
    String profileImageURL ;
    String thumbnailURL ;
    String countryISO ;

    LayoutInflater inflater_g;
    ViewGroup container_g;
    Bundle savedInstanceState_g;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = null;
        mContext = container.getContext();
        inflater_g=inflater;
        container_g=container;
        savedInstanceState_g=savedInstanceState;

        manager = new DBManager_reserv(mContext, "reserv_info.db", null, 1);
        Log.e("info"," "+manager.returnName());
        if(manager.returnName().equals("nothing")) isQueue = false;
        else isQueue = true;
        if(isQueue){
            v = inflater.inflate(R.layout.tab2_reservation_info,container,false);
            resrv_name = (TextView) v.findViewById(R.id.resrv_name);
            resrv_party = (TextView) v.findViewById(R.id.resrv_party);
            time_left = (TextView) v.findViewById(R.id.time_left);
            people_left = (TextView) v.findViewById(R.id.people_left);
            cancel_btn = (TextView) v.findViewById(R.id.cancel_btn);



            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manager = new DBManager_reserv(mContext, "reserv_info.db", null, 1);
                    Log.e("pass", manager.returnPid());
                    new HttpPostRequest().execute("out", manager.returnPid(), manager.returnDummyname(), "APA91bHn451utRiGTPGlBH4VAgUb99C5fUHYgMqLhDK5R0JyPjT1yVYA0AoR7wiJHtiIKJlO_L4pe0P9xe_sRkojM7a2z2rWVp_WjNnFdgz_Qsmr7CuU-dT1C-C5y6TEFBjMUPdnf7qs", "민태원");
                    manager.delete("delete from RESERV_INFO");
                    Intent intent = new Intent(mContext,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    //onCreate(savedInstanceState);
                }
            });

        }else{
            v = inflater.inflate(R.layout.tab2_reservation_info_blank,container,false);



        }
        return v;
    }
    private class HttpPostRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... info) {
            String sResult = "Error";

            try {
                URL url = new URL("http://52.69.163.43/queuing/line_manage.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                String body = "in_out=" + info[0] +"&"
                        +"priority=" + info[1] + "&"
                        +"resname=" + info[2] + "&"
                        +"regid=" + info[3] + "&"
                        +"method=App" + "&"
                        +"name=" + info[4] + "&"
                        +"location=phone";

                Log.e("value",info[0]+" "+info[1]+" "+info[2]);

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
                Log.e("pass", sResult);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sResult;
        }

        @Override
        protected void onPostExecute(String result){
            Toast.makeText(mContext, "Cancel complete!", Toast.LENGTH_SHORT).show();
            //manager.delete("delete from RESERV_INFO");
        }
    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            manager_update = new DBManager_update(mContext, "update_info2.db", null, 1);
            Log.e("CHECK", manager_update.returnPid());
            manager = new DBManager_reserv(mContext, "reserv_info.db", null, 1);
            Log.e("info"," "+manager.returnName());
            if(manager.returnName().equals("nothing")) isQueue = false;
            else isQueue = true;
            if(isQueue){
                if(manager_update.returnPid().equals("nothing")){
                }else if(manager_update.returnPid().equals("1000")){
                    people_left.setText(String.valueOf(Integer.parseInt(people_left.getText().toString())-1));
                }else{
                    people_left.setText(String.valueOf(Integer.parseInt(manager_update.returnPid()) - 1));
                }
                manager_update.delete("delete from UPDATE_INFO");
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();

        mContext.registerReceiver(mReceiver, new IntentFilter("up"));
        manager_update = new DBManager_update(mContext, "update_info2.db", null, 1);
        Log.e("CHECK", manager_update.returnPid());

        manager = new DBManager_reserv(mContext, "reserv_info.db", null, 1);
        Log.e("info"," "+manager.returnName());
        if(manager.returnName().equals("nothing")) isQueue = false;
        else isQueue = true;
        if(isQueue){
            if(manager_update.returnPid().equals("nothing")){
            }else if(manager_update.returnPid().equals("1000")){
                people_left.setText(String.valueOf(Integer.parseInt(people_left.getText().toString())-1));
            }else{
                people_left.setText(String.valueOf(Integer.parseInt(manager_update.returnPid()) - 1));
            }
            manager_update.delete("delete from UPDATE_INFO");
        }


    }

    @Override
    public void onPause(){
        super.onPause();
        mContext.unregisterReceiver(mReceiver);
    }



}
