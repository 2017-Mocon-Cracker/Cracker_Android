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

import com.example.choi.cracker.Network.NetworkHelper;
import com.example.choi.cracker.R;
import com.example.choi.cracker.Data.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs = null;
    TextView textView;
    String User_name;
    User user;
    Button login;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        checkFirstRun();
        loadNowData();
        callbackManager = CallbackManager.Factory.create();
        setCustomActionBar();



        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.e("asdf", loginResult.getAccessToken().getToken());
                Call<User> access = NetworkHelper.getNetworkInstance().facebookLogin(loginResult.getAccessToken().getToken());
                access.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("asd",response.code()+"");
                        switch (response.code()) {
                            case 200:
                                Log.e("asdf", response.body().getUserName());
                                User user_ = response.body();
//                                String email, String cardName, String userName, String cardNum, int money, int paied, Boolean isTransfer
                                user = new User(user_.getEmail(),user_.getCardName(),user_.getUserName()
                                        ,user_.getCardNum(),user_.getMoney(),user_.getPaied(),user_.getTransfer(),user_.getEmpty());
                                String user__ = new Gson().toJson(user);
                                Log.d("main_user","user"+user__);
                                saveNowData();
                                break;
                            case 400:
                                Log.d("fail","ff");
                                break;
                        }
                    }


                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("fail","fail");
                        Log.e("asdf", t.getMessage());
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr", error.toString());
            }
        });

        textView = (TextView) findViewById(R.id.add_card);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView.getText().toString().equals("카드 추가하기")) {
                    saveNowData();
                    Intent intent = new Intent(MainActivity.this, AddCardInfo.class);
                    startActivityForResult(intent, 333);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode==333){
                loadNowData();
                if(!user.getCardNum().toString().equals(""))
                    textView.setText("정보 확인하기");
            }
        }
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
