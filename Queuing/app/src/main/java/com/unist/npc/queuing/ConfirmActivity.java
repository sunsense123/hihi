package com.unist.npc.queuing;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.APIErrorResult;
import com.kakao.usermgmt.MeResponseCallback;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.UserProfile;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by npc on 2015. 8. 6..
 */
public class ConfirmActivity extends Activity {

    private TextView select1, select2, select3, select4, select5, select6, confirm_btn;
    private int party_num;
    String username;
    String resname;
    String dummy_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Intent intent = getIntent();
        username=intent.getExtras().getString("username");
        resname =intent.getExtras().getString("resname");
        dummy_name = intent.getExtras().getString("dummy_name");

        select1 = (TextView) findViewById(R.id.selection_1);
        select2 = (TextView) findViewById(R.id.selection_2);
        select3 = (TextView) findViewById(R.id.selection_3);
        select4 = (TextView) findViewById(R.id.selection_4);
        select5 = (TextView) findViewById(R.id.selection_5);
        select6 = (TextView) findViewById(R.id.selection_6);
        confirm_btn = (TextView) findViewById(R.id.confirm_btn);

        select1.setOnClickListener(mOnClick);
        select2.setOnClickListener(mOnClick);
        select3.setOnClickListener(mOnClick);
        select4.setOnClickListener(mOnClick);
        select5.setOnClickListener(mOnClick);
        select6.setOnClickListener(mOnClick);
        confirm_btn.setOnClickListener(mOnClick);



    }

    private View.OnClickListener mOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.selection_1: {
                    party_num = 1;
                    select1.setBackgroundColor(Color.parseColor("#B3E5FC"));
                    select2.setBackgroundResource(0);
                    select3.setBackgroundResource(0);
                    select4.setBackgroundResource(0);
                    select5.setBackgroundResource(0);
                    select6.setBackgroundResource(0);

                    break;
                }
                case R.id.selection_2: {
                    party_num = 2;
                    select1.setBackgroundResource(0);
                    select2.setBackgroundColor(Color.parseColor("#B3E5FC"));
                    select3.setBackgroundResource(0);
                    select4.setBackgroundResource(0);
                    select5.setBackgroundResource(0);
                    select6.setBackgroundResource(0);
                    break;
                }
                case R.id.selection_3: {
                    party_num = 3;
                    select1.setBackgroundResource(0);
                    select2.setBackgroundResource(0);
                    select3.setBackgroundColor(Color.parseColor("#B3E5FC"));
                    select4.setBackgroundResource(0);
                    select5.setBackgroundResource(0);
                    select6.setBackgroundResource(0);
                    break;
                }
                case R.id.selection_4: {
                    party_num = 4;
                    select1.setBackgroundResource(0);
                    select2.setBackgroundResource(0);
                    select3.setBackgroundResource(0);
                    select4.setBackgroundColor(Color.parseColor("#B3E5FC"));
                    select5.setBackgroundResource(0);
                    select6.setBackgroundResource(0);
                    break;
                }
                case R.id.selection_5: {
                    party_num = 5;
                    select1.setBackgroundResource(0);
                    select2.setBackgroundResource(0);
                    select3.setBackgroundResource(0);
                    select4.setBackgroundResource(0);
                    select5.setBackgroundColor(Color.parseColor("#B3E5FC"));
                    select6.setBackgroundResource(0);
                    break;
                }
                case R.id.selection_6: {
                    party_num = 6;
                    select1.setBackgroundResource(0);
                    select2.setBackgroundResource(0);
                    select3.setBackgroundResource(0);
                    select4.setBackgroundResource(0);
                    select5.setBackgroundResource(0);
                    select6.setBackgroundColor(Color.parseColor("#B3E5FC"));
                    break;
                }
                case R.id.confirm_btn: {
                    DBManager_reserv manager = new DBManager_reserv(getApplicationContext(), "reserv_info.db", null, 1);
                    if(manager.returnName().equals("nothing")) new HttpPostRequest().execute("in",username,String.valueOf(party_num),"App",dummy_name,"APA91bGy1gqUVGGApf8FB3jIwyxf_M9E6neq2DL3fMTKihht6t2CBpuL2qKdQUDkk-knqHXkKCRSK2h0l6ANvVA-yAteAO9gw7A8FmA6gkqvccPqFQOAOcAnZKz_uavR715ty6Q1J47V");
                    else Toast.makeText(getApplicationContext(),"You already queue!",Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    };

    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onSuccess(final UserProfile userProfile) {
                Log.d("SUCCESS", "UserProfile : " + userProfile + " & party num : " + party_num);
                userProfile.saveUserToCache();
            }

            @Override
            public void onNotSignedUp() {
                //showSignup();
            }

            @Override
            public void onSessionClosedFailure(final APIErrorResult errorResult) {
            }

            @Override
            public void onFailure(final APIErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Log.d("FAIL", message);

                if (errorResult.getErrorCodeInt() == -777) {
                    Toast.makeText(getApplicationContext(), "SERVICE_UNAVAILABLE", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {

                }
            }
        });
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
                        +"name=" + info[1] + "&"
                        +"party=" + info[2] + "&"
                        +"method=" + info[3] + "&"
                        +"resname=" + info[4]+ "&"
                        +"regid=" + info[5];

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
                Log.e("CHECK", sResult);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sResult;
        }

        @Override
        protected void onPostExecute(String result){
            Toast.makeText(getApplicationContext(), "Queuing complete!", Toast.LENGTH_SHORT).show();
            DBManager_reserv manager = new DBManager_reserv(getApplicationContext(), "reserv_info.db", null, 1);
            manager.insert("insert into RESERV_INFO values (" + Integer.getInteger(result) + ",'" + resname + "','3','" +dummy_name+ "')");
            Log.e("CONFIRM",":"+manager.returnPid()+" "+manager.returnName()+" "+manager.returnParty());
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

}