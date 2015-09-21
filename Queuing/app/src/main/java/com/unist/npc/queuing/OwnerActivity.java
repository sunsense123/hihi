package com.unist.npc.queuing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class OwnerActivity extends AppCompatActivity {
    String owner_name="outback_sinchon";

    JSONArray jarray;
    JSONObject jobj;
    ArrayList<CusListItem> items;
    CusListAdapter adapter;
    ListView cus_list;
    ReservDialog reservDialog;
    TextView adduser_btn;

    String user_regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        adduser_btn = (TextView) findViewById(R.id.adduser_btn);
        items = new ArrayList<>();
        adapter = new CusListAdapter(getApplicationContext(),R.layout.activity_owner_item,items);
        cus_list = (ListView) findViewById(R.id.cus_list);
        cus_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("pass","regid: "+items.get(position).cus_regid);
                new HttpPostRequest_2().execute("out", items.get(position).cus_priority, owner_name,"",items.get(position).cus_name,items.get(position).cus_method,items.get(position).cus_regid);
                items.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        new HttpPostRequest().execute();
        reservDialog = new ReservDialog(this);
        reservDialog.setTitle("Your Information");
        reservDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });

        reservDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (items.size() == 0) {
                    items.add(new CusListItem("1", reservDialog._name, reservDialog._number, "13:00", "OFFLINE",""));

                } else {
                    items.add(new CusListItem(String.valueOf(Integer.parseInt(items.get(items.size() - 1).cus_priority) + 1), reservDialog._name, reservDialog._number, "13:00", "OFFLINE",""));

                }
                adapter.notifyDataSetChanged();


                new HttpPostRequest_2().execute("in", String.valueOf(Integer.parseInt(items.get(items.size() - 1).cus_priority) + 1), owner_name, reservDialog._number, reservDialog._name, "OFFLINE","");

            }
        });
        adduser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservDialog.show();

            }
        });


    }

    private class HttpPostRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... info) {
            String sResult = "Error";

            try {
                URL url = new URL("http://52.69.163.43/queuing/line_parse.php?name="+owner_name);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");



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
            try {
                jarray = new JSONArray(result);
                for(int i=0;i<jarray.length();i++){
                    jobj = jarray.getJSONObject(i);
                    items.add(new CusListItem(jobj.getString("pid"),jobj.getString("name"),jobj.getString("party"),jobj.getString("time"),jobj.getString("method"),""));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            cus_list.setAdapter(adapter);

        }
    }
    private class HttpPostRequest_2 extends AsyncTask<String, Void, String> {

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
                        +"party=" + info[3] + "&"
                        +"name=" + info[4] + "&"
                        +"method=" + info[5] + "&"
                        +"regid=" +info[6]+ "&"
                        +"location=tablet";

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

        }
    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String name = intent.getStringExtra("name");
            String num = intent.getStringExtra("num");
            user_regid = intent.getStringExtra("regid");
            int find_index = 0;
            Log.d("MSG_RECEIVED", "message : " + name + " " + num + " " + user_regid.charAt(0));
            if(num.length()==4) { //9999 for delete
                for (int i = 0; i < items.size(); i++) {
                    if (name.equals(items.get(i).getCus_name())) {
                        find_index = i;
                        break;
                    }
                }
                if (cus_list.getChildAt(find_index) == null) {
                    items.remove(find_index);
                    adapter.notifyDataSetChanged();
                } else {
                    LinearLayout cus_item = (LinearLayout) cus_list.getChildAt(find_index).findViewById(R.id.cuslist_item);
                    items.remove(find_index);
                    adapter.notifyDataSetChanged();
                   /* ex_Ani = new ExpandAnimation(cus_item, 500);
                    final int i = find_index;
                    ex_Ani.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            items.remove(i);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    cus_item.startAnimation(ex_Ani);*/
                }
            }
            else{
                if (items.size() == 0) {
                    items.add(new CusListItem("1", name, num, "13:00", "App",user_regid));

                } else {
                    items.add(new CusListItem(String.valueOf(Integer.parseInt(items.get(items.size() - 1).cus_priority) + 1), name, num, "13:00", "App",user_regid));

                }
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mReceiver,new IntentFilter("key"));

    }

    @Override
    public void onPause(){
        super.onPause();

        getApplicationContext().unregisterReceiver(mReceiver);
    }
}
