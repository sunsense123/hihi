/**
 * Copyright 2014 Daum Kakao Corp.
 *
 * Redistribution and modification in source or binary forms are not permitted without specific prior written permission. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.unist.npc.queuing;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.kakao.auth.APIErrorResult;
import com.kakao.auth.Session;
import com.kakao.usermgmt.MeResponseCallback;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.UserProfile;
import com.kakao.util.helper.log.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * 유효한 세션이 있다는 검증 후
 * me를 호출하여 가입 여부에 따라 가입 페이지를 그리던지 Main 페이지로 이동 시킨다.
 */

public class TableActivity extends Activity {

    private Context mcontext;
    private TextView table1;
    private TextView table2;
    private TextView table3;
    private TextView table4;
    private TextView table5;
    private TextView table6;
    private TextView table7;
    private TextView table8;
    private TextView table9;
    private TextView table10;
    private TextView table11;
    private TextView table12;
    private TextView table13;
    private TextView table14;
    private TextView table15;

// 그냥 state는 가게단에서 하는 state변경값들 저장
    static Boolean state1 = false;
    static Boolean state2 = false;
    static Boolean state3 = false;
    static Boolean state4 = false;
    static Boolean state5 = false;
    static Boolean state6 = false;
    static Boolean state7 = false;
    static Boolean state8 = false;
    static Boolean state9 = false;
    static Boolean state10 = false;
    static Boolean state11 = false;
    static Boolean state12 = false;
    static Boolean state13 = false;
    static Boolean state14 = false;
    static Boolean state15 = false;


// _2 state는 서버에서 가져와 맨처음 앱 실행시 사용
    static Boolean state1_2 = false;
    static Boolean state2_2 = false;
    static Boolean state3_2 = false;
    static Boolean state4_2 = false;
    static Boolean state5_2 = false;
    static Boolean state6_2 = false;
    static Boolean state7_2 = false;
    static Boolean state8_2 = false;
    static Boolean state9_2 = false;
    static Boolean state10_2 = false;
    static Boolean state11_2 = false;
    static Boolean state12_2 = false;
    static Boolean state13_2 = false;
    static Boolean state14_2 = false;
    static Boolean state15_2 = false;
    static Boolean state_check = false;


    long table1_start =0;
    long table1_end = 0;
    long table2_start =0;
    long table2_end = 0;
    long table3_start =0;
    long table3_end = 0;
    long table4_start =0;
    long table4_end = 0;
    long table5_start =0;
    long table5_end = 0;
    long table6_start =0;
    long table6_end = 0;
    long table7_start =0;
    long table7_end = 0;
    long table8_start =0;
    long table8_end = 0;
    long table9_start =0;
    long table9_end = 0;
    long table10_start =0;
    long table10_end = 0;
    long table11_start =0;
    long table11_end = 0;
    long table12_start =0;
    long table12_end = 0;
    long table13_start =0;
    long table13_end = 0;
    long table14_start =0;
    long table14_end = 0;
    long table15_start =0;
    long table15_end = 0;


    static Button check;
    static ArrayList<Integer> table_state;
    Check_system mSystem;
    DBManager_table_manager table_manager;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.table_grandmother);

        try {
            table_manager = new DBManager_table_manager(getApplicationContext(), "table_manager9.db", null, 1);
            table_manager.mcontext = this.mcontext;
        }
        catch (Exception e){
            Log.e(e.toString(), e.toString());
        }
        table_state = new ArrayList<Integer>();
        try {
            table_state = new ArrayList<Integer>(table_manager.returnState());
        }
        catch (Exception e){
            Log.e("kjkdsabbbbbbbbbbbbbjas",e.toString());
        }
   //     table_state = new ArrayList<Integer>();
   //     table_state.add(1);
   //     setContentView(R.layout.table_management);

        table1 = (TextView) findViewById(R.id.tb1);
        table2 = (TextView) findViewById(R.id.tb2);
        table3 = (TextView) findViewById(R.id.tb3);
        table4 = (TextView) findViewById(R.id.tb4);
        table5 = (TextView) findViewById(R.id.tb5);
        table6 = (TextView) findViewById(R.id.tb6);
        table7 = (TextView) findViewById(R.id.tb7);
        table8 = (TextView) findViewById(R.id.tb8);
        table9 = (TextView) findViewById(R.id.tb9);
        table10 = (TextView) findViewById(R.id.tb10);
        table11 = (TextView) findViewById(R.id.tb11);
        table12 = (TextView) findViewById(R.id.tb12);
        table13 = (TextView) findViewById(R.id.tb13);
        table14 = (TextView) findViewById(R.id.tb14);
        table15 = (TextView) findViewById(R.id.tb15);

        table1.setBackgroundResource(android.R.color.white);
        table2.setBackgroundResource(android.R.color.white);
        table3.setBackgroundResource(android.R.color.white);
        table4.setBackgroundResource(android.R.color.white);
        table5.setBackgroundResource(android.R.color.white);
        table6.setBackgroundResource(android.R.color.white);
        table7.setBackgroundResource(android.R.color.white);
        table8.setBackgroundResource(android.R.color.white);
        table9.setBackgroundResource(android.R.color.white);
        table10.setBackgroundResource(android.R.color.white);
        table11.setBackgroundResource(android.R.color.white);
        table12.setBackgroundResource(android.R.color.white);
        table13.setBackgroundResource(android.R.color.white);
        table14.setBackgroundResource(android.R.color.white);
        table15.setBackgroundResource(android.R.color.white);

        try {
            if (table_state.get(0) == 1)
                table1.setBackgroundResource(android.R.color.black);
            if (table_state.get(1) == 1)
                table2.setBackgroundResource(android.R.color.black);
            if (table_state.get(2) == 1)
                table3.setBackgroundResource(android.R.color.black);
            if (table_state.get(3) == 1)
                table4.setBackgroundResource(android.R.color.black);
            if (table_state.get(4) == 1)
                table5.setBackgroundResource(android.R.color.black);
            if (table_state.get(5) == 1)
                table6.setBackgroundResource(android.R.color.black);
            if (table_state.get(6) == 1)
                table7.setBackgroundResource(android.R.color.black);
            if (table_state.get(7) == 1)
                table8.setBackgroundResource(android.R.color.black);
            if (table_state.get(8) == 1)
                table9.setBackgroundResource(android.R.color.black);
            if (table_state.get(9) == 1)
                table10.setBackgroundResource(android.R.color.black);
            if (table_state.get(10) == 1)
                table11.setBackgroundResource(android.R.color.black);
            if (table_state.get(11) == 1)
                table12.setBackgroundResource(android.R.color.black);
            if (table_state.get(12) == 1)
                table13.setBackgroundResource(android.R.color.black);
            if (table_state.get(13) == 1)
                table14.setBackgroundResource(android.R.color.black);
            if (table_state.get(14) == 1)
                table15.setBackgroundResource(android.R.color.black);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),String.valueOf(table_state.size()),Toast.LENGTH_LONG).show();
 //           Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }
try {
    state1 = (table_state.get(0) == 1);
    state2 = (table_state.get(1) == 1);
    state3 = (table_state.get(2) == 1);
    state4 = (table_state.get(3) == 1);
    state5 = (table_state.get(4) == 1);
    state6 = (table_state.get(5) == 1);
    state7 = (table_state.get(6) == 1);
    state8 = (table_state.get(7) == 1);
    state9 = (table_state.get(8) == 1);
    state10 = (table_state.get(9) == 1);
    state11 = (table_state.get(10) == 1);
    state12 = (table_state.get(11) == 1);
    state13 = (table_state.get(12) == 1);
    state14 = (table_state.get(13) == 1);
    state15 = (table_state.get(14) == 1);
}
catch (Exception e){
    Log.e("하 시발",table_state.toString());
}
        /*
        state1 = state1_2;
        state2 = state2_2;
        state3 = state3_2;
        state4 = state4_2;
        state5 = state5_2;
        state6 = state6_2;
        state7 = state7_2;
        state8 = state8_2;
        state9 = state9_2;
        state10= state10_2;
        state11= state11_2;
        state12= state12_2;
        state13= state13_2;
        state14= state14_2;
        state15= state15_2;
        
*/
        table1.setOnClickListener(new Click(1));
        table2.setOnClickListener(new Click(2));
        table3.setOnClickListener(new Click(3));
        table4.setOnClickListener(new Click(4));
        table5.setOnClickListener(new Click(5));
        table6.setOnClickListener(new Click(6));
        table7.setOnClickListener(new Click(7));
        table8.setOnClickListener(new Click(8));
        table9.setOnClickListener(new Click(9));
        table10.setOnClickListener(new Click(10));
        table11.setOnClickListener(new Click(11));
        table12.setOnClickListener(new Click(12));
        table13.setOnClickListener(new Click(13));
        table14.setOnClickListener(new Click(14));
        table15.setOnClickListener(new Click(15));

        check = (Button) findViewById(R.id.checkBtn);
        check.setOnClickListener(new Executing());

        mSystem = new Check_system();


    }
    public class Check_system{

        public void Checking(){
                if(       state1 != (table_state.get(0)==1)
                        ||state2 != (table_state.get(1)==1)
                        ||state3 != (table_state.get(2)==1)
                        ||state4 != (table_state.get(3)==1)
                        ||state5 != (table_state.get(4)==1)
                        ||state6 != (table_state.get(5)==1)
                        ||state7 != (table_state.get(6)==1)
                        ||state8 != (table_state.get(7)==1)
                        ||state9 != (table_state.get(8)==1)
                        ||state10 != (table_state.get(9)==1)
                        ||state11 != (table_state.get(10)==1)
                        ||state12 != (table_state.get(11)==1)
                        ||state13 != (table_state.get(12)==1)
                        ||state14 != (table_state.get(13)==1)
                        ||state15 != (table_state.get(14)==1)){

                        check.setBackgroundColor(Color.parseColor("#ffff2121"));
                        state_check = true;
                    }
                    else{
                        check.setBackgroundColor(Color.parseColor("#44ff2121"));
                        state_check = false;
                    }

        }
    }
    public class Click implements OnClickListener{

        int mtype;

        Click(int type){

            mtype = type;
        }

        @Override
        public void onClick(View v) {
            switch(mtype){

                case 1:
                    if(!state1) {
                        table1_start = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.black);
                        state1 = true;
                        mSystem.Checking();
                    }
                    else{
                        table1_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state1 = false;
                        mSystem.Checking();
                    }
                    break;
                case 2:
                    if(!state2) {
                        table2_start = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.black);
                        state2 = true;
                        mSystem.Checking();
                    }
                    else{
                        table2_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state2 = false;
                        mSystem.Checking();

                    }

                    break;
                case 3:
                    if(!state3) {
                        table3_start = System.currentTimeMillis();

                        v.setBackgroundResource(android.R.color.black);
                        state3 = true;
                        mSystem.Checking();
                    }
                    else{
                        table3_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state3 = false;
                        mSystem.Checking();

                    }

                    break;
                case 4:
                    if(!state4) {
                        table4_start = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.black);
                        state4 = true;
                        mSystem.Checking();
                    }
                    else{
                        table4_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state4 = false;
                        mSystem.Checking();

                    }

                    break;
                case 5:
                    if(!state5) {
                        table5_start = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.black);
                        state5 = true;
                        mSystem.Checking();
                    }
                    else{
                        table5_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state5 = false;
                        mSystem.Checking();
                    }
                    break;
                case 6:
                    if(!state6) {
                        table6_start = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.black);
                        state6 = true;
                        mSystem.Checking();
                    }
                    else{
                        table6_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state6 = false;
                        mSystem.Checking();

                    }

                    break;
                case 7:
                    if(!state7) {
                        table7_start = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.black);
                        state7 = true;
                        mSystem.Checking();
                    }
                    else{
                        table7_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state7 = false;
                        mSystem.Checking();

                    }

                    break;
                case 8:
                    if(!state8) {
                        table8_start = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.black);
                        state8 = true;
                        mSystem.Checking();
                    }
                    else{
                        table8_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state8 = false;
                        mSystem.Checking();
                    }

                    break;
                case 9:
                    if(!state9) {
                        table9_start = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.black);
                        state9 = true;
                        mSystem.Checking();
                    }
                    else{
                        table9_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state9 = false;
                        mSystem.Checking();
                    }
                    break;
                case 10:
                    if(!state10) {
                        table10_start = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.black);
                        state10 = true;
                        mSystem.Checking();
                    }
                    else{
                        table10_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state10 = false;
                        mSystem.Checking();

                    }

                    break;
                case 11:
                    if(!state11) {
                        table11_start = System.currentTimeMillis();

                        v.setBackgroundResource(android.R.color.black);
                        state11 = true;
                        mSystem.Checking();
                    }
                    else{
                        table11_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state11 = false;
                        mSystem.Checking();

                    }

                    break;
                case 12:
                    if(!state12) {
                        table12_start = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.black);
                        state12 = true;
                        mSystem.Checking();
                    }
                    else{
                        table12_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state12 = false;
                        mSystem.Checking();

                    }

                    break;
                case 13:
                    if(!state13) {
                        table1_start = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.black);
                        state13 = true;
                        mSystem.Checking();
                    }
                    else{
                        table13_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state13 = false;
                        mSystem.Checking();
                    }
                    break;
                case 14:
                    if(!state14) {
                        table14_start = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.black);
                        state14 = true;
                        mSystem.Checking();
                    }
                    else{
                        table14_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state14 = false;
                        mSystem.Checking();

                    }

                    break;
                case 15:
                    if(!state15) {
                        table15_start = System.currentTimeMillis();

                        v.setBackgroundResource(android.R.color.black);
                        state15 = true;
                        mSystem.Checking();
                    }
                    else{
                        table15_end = System.currentTimeMillis();
                        v.setBackgroundResource(android.R.color.white);
                        state15 = false;
                        mSystem.Checking();

                    }

                    break;
            }

        }
    }
    /*
    private class HttpPostRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... info) {
            String sResult = "Error";

            try {
                URL url = new URL("http://52.69.163.43/queuing/table_manager.php");
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
*/
    private class Executing implements OnClickListener {
        @Override
        public void onClick(View v) {
            if(state_check){
                ArrayList<Integer> Flag = new ArrayList<Integer>();
                if(state1 != (table_state.get(0)==1) && state1 == true){
                    //dB 에 start_time을 넣는 작업
                    try {
                        table_manager.update("update table_manager set State = 1 where tableID = 1");
                        table_manager.update("update table_manager set startTime = " + table1_start + " where tableID = 1");
                        table_state.set(1,1);
                    }
                    catch (Exception e){
                        Toast.makeText(mcontext,e.toString(),Toast.LENGTH_LONG);
                        finish();
                    }
                }
                else if(state1 != (table_state.get(0)==1) && state1 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                    try{
                    table_manager.update("update table_manager set State = 0 where tableID = 1");

                    }
                    catch (Exception e){
                        Toast.makeText(mcontext,e.toString(),Toast.LENGTH_LONG);
                    }

                    }
                if(state2 != (table_state.get(0)==1) && state2 == true){
                    //dB 에 start_time을 넣는 작업

                }
                else if(state2 != (table_state.get(0)==1) && state2 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state3 != (table_state.get(1)==1) && state3 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state3 != (table_state.get(1)==1) && state3 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state4 != (table_state.get(2)==1) && state4 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state4 != (table_state.get(2)==1) && state4 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state5 != (table_state.get(3)==1) && state5 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state5 != (table_state.get(3)==1) && state5 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state6 != (table_state.get(4)==1) && state6 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state6 != (table_state.get(4)==1) && state6 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state7 != (table_state.get(5)==1) && state7 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state7 != (table_state.get(5)==1) && state7 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state8 != (table_state.get(6)==1) && state8 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state8 != (table_state.get(6)==1) && state8 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state9 != (table_state.get(7)==1) && state9 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state9 != (table_state.get(7)==1) && state9 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state10 != (table_state.get(8)==1) && state10 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state10 != (table_state.get(8)==1) && state10 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state11 != (table_state.get(9)==1) && state11 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state11 != (table_state.get(9)==1) && state11 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state12 != (table_state.get(10)==1) && state12 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state12 != (table_state.get(10)==1) && state12 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state13 != (table_state.get(11)==1) && state13 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state13 != (table_state.get(11)==1) && state13 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state14 != (table_state.get(12)==1) && state14 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state14 != (table_state.get(12)==1) && state14 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state15 != (table_state.get(13)==1) && state15 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state15 != (table_state.get(13)==1) && state15 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }
                if(state15 != (table_state.get(14)==1) && state15 == true){
                    //dB 에 start_time을 넣는 작업
                }
                else if(state15 != (table_state.get(14)==1) && state15 == false){
                    //서버로 시간차를 보내는 작업
                    //dB에 table 상태를 초기화 하는 작업
                }

                mSystem.Checking();
            }

        }
    }
}
