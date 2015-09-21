package com.unist.npc.queuing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.APIErrorResult;
import com.kakao.usermgmt.LogoutResponseCallback;
import com.kakao.usermgmt.UserManagement;

/**
 * Created by mark_mac on 2015. 8. 9..
 */
public class MypageActivity extends Activity{

    private TextView logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        logout_btn = (TextView) findViewById(R.id.logout_btn);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onSuccess(final long userId) {
                        redirectLoginActivity();
                    }

                    @Override
                    public void onFailure(final APIErrorResult apiErrorResult) {
                        //redirectLoginActivity();
                        Log.e("LOGOUTFAIL", "ERROR : " + apiErrorResult);
                    }
                });
            }

        });

    }
    protected void redirectMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void redirectLoginActivity() {

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("com.unist.npc.queuing.", Context.MODE_PRIVATE);
        prefs.edit().remove("IsLogin").apply();
        prefs.edit().putBoolean("IsLogin",false).apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
