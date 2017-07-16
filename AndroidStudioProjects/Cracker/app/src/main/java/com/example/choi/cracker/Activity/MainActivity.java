package com.example.choi.cracker.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.choi.cracker.R;
import com.example.choi.cracker.Data.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs = null;
    TextView textView;
    String User_name;
    User user;
    Button login;
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        checkFirstRun();
//        login = (Button) findViewById(R.id.facebook_login);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url ="http://soylatte.kr:8888/facebook/token";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//            }
//        });
//    }
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        loadNowData();

        callbackManager = CallbackManager.Factory.create();
        setCustomActionBar();



//        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(final LoginResult loginResult) {
//                Log.e("asdf", loginResult.getAccessToken().getToken());
//                Call<User> access = NetworkHelper.getNetworkInstance().facebookLogin(loginResult.getAccessToken().getToken());
//                access.enqueue(new Callback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//                        switch (response.code()) {
//                            case 200:
//                                Log.e("asdf", response.body().getUserName());
//                                User user_ = response.body();
//                                user = new User(user_.getEmail(),user_.getCardName(),user_.getUserName(),user_.getMoney(),user_.getCardNum());
//                                Log.d("main_user",""+user);
//                                saveNowData();
//                                break;
//                            case 400:
//                                break;
//                        }
//                    }
//
//
//                    @Override
//                    public void onFailure(Call<User> call, Throwable t) {
//                        Log.e("asdf", t.getMessage());
//                    }
//                });
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.e("LoginErr", error.toString());
//            }
//        });

        textView = (TextView) findViewById(R.id.add_card);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNowData();
                Intent intent = new Intent(MainActivity.this, AddCardInfo.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void checkFirstRun() {
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            Intent newIntent = new Intent(MainActivity.this, GuideActivity.class);
            startActivity(newIntent);
            prefs.edit().putBoolean("isFirstRun", false).apply();
        }
    }
    void saveNowData() { //items 안의 내용이 저장됨
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String json = new Gson().toJson(user);
        editor.putString("add_card_name", json);
        editor.commit();
    }

    void loadNowData() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        User_name = pref.getString("add_card_name", null);
        user = new Gson().fromJson(User_name, User.class);
        Log.d("asdasd",""+user);
    }

    public void setCustomActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        View mCustomView = LayoutInflater.from(this).inflate(R.layout.actionbar_main,null);
        actionBar.setCustomView(mCustomView);

        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0,0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView,params);
    }
}
