package com.unist.npc.queuing;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.APIErrorResult;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.auth.SessionCallback;
import com.kakao.kakaotalk.KakaoTalkHttpResponseHandler;
import com.kakao.kakaotalk.KakaoTalkProfile;
import com.kakao.kakaotalk.KakaoTalkService;
import com.kakao.usermgmt.LoginButton;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;


public class LoginActivity extends Activity {

    private LinearLayout login;
    private LoginButton kakaoLogin;
    private final SessionCallback mySessionCallback = new MySessionStatusCallback();
    private Session session;
    private BackPressCloseHandler backPressCloseHandler;

    String nickName;
    String profileImageURL ;
    String thumbnailURL ;
    String countryISO ;

    TextView owner_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.e("onCreate", "POP");
        backPressCloseHandler = new BackPressCloseHandler(this);

        //Session.initialize(getApplicationContext(), AuthType.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN);//MAKE ONLY POSSIBLE FOR KAKAOLOGIN
        Session.initialize(this);
        kakaoLogin = (LoginButton) findViewById(R.id.com_kakao_login);
        login = (LinearLayout) findViewById(R.id.login);
        session = Session.getCurrentSession();
        session.addCallback(mySessionCallback);

        Log.d("SESSON_STATUS", "++ session.isClosed() : " + session.isClosed());
        if (session.isClosed()){
            kakaoLogin.setVisibility(View.VISIBLE);
        } else {
            kakaoLogin.setVisibility(View.GONE);
            if (session.implicitOpen()) {
                kakaoLogin.setVisibility(View.GONE);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.error_message_for_service_unavailable), Toast.LENGTH_SHORT).show();
                finish();
            }
        }

       /* login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
    }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            session.removeCallback(mySessionCallback);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
                return;
            }

            super.onActivityResult(requestCode, resultCode, data);
        }

        private class MySessionStatusCallback implements SessionCallback {
            @Override
            public void onSessionOpened() {
                // 프로그레스바 종료
                Log.d("OPEND", "OPEN");
                // 세션 오픈후 보일 페이지로 이동
                readProfile();
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("com.unist.npc.queuing.", Context.MODE_PRIVATE);
                if(!prefs.contains("IsLogin"))
                    prefs.edit().putBoolean("IsLogin",true).apply();
                else{
                    prefs.edit().remove("IsLogin").apply();
                    prefs.edit().putBoolean("IsLogin",true).apply();
                }

                final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onSessionClosed(final KakaoException exception) {
                // 프로그레스바 종료
                // 세션 오픈을 못했으니 다시 로그인 버튼 노출.
                Log.d("LOGINFAIL","FAIL " +exception);
                kakaoLogin.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSessionOpening() {
                //프로그레스바 시작
            }
        }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
        Session.initialize(this);
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

            }
        });

    }



    private abstract class MyTalkHttpResponseHandler<T> extends KakaoTalkHttpResponseHandler<T> {
        @Override
        public void onHttpSessionClosedFailure(final APIErrorResult errorResult) {
            redirectLoginActivity();
        }

        @Override
        public void onNotKakaoTalkUser(){
            Toast.makeText(getApplicationContext(), "not a KakaoTalk user", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(final APIErrorResult errorResult) {
            Toast.makeText(getApplicationContext(), "failed : " + errorResult, Toast.LENGTH_SHORT).show();
        }
    }
    private void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}