package com.example.choi.cracker.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.choi.cracker.Data.User;
import com.example.choi.cracker.Network.NetworkHelper;
import com.example.choi.cracker.R;
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

/**
 * Created by choi on 2017. 7. 17..
 */

public class LoginActivity extends AppCompatActivity {
    User user;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
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
                                user = new User(user_.getFacebook_ID(),user_.getCardName(),user_.getUserName()
                                        ,user_.getCardNum(),user_.getMoney(),user_.getPaied(),user_.getTransfer(),user_.getEmpty(),user_.getCardIn());
                                String json = new Gson().toJson(user);
                                Log.d("user",json);
                                saveNowData();
                                finish();
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
    }
    void saveNowData() { //items 안의 내용이 저장됨
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String json = new Gson().toJson(user);
        editor.putString("add_card_name", json);
        editor.putBoolean("isLogin",true);
        editor.commit();
    }

    void loadNowData() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String User_name = pref.getString("add_card_name", null);
        user = new Gson().fromJson(User_name, User.class);
        Boolean noCard = pref.getBoolean("noCard",true);
        Log.d("asdasd",""+user);
    }
}
