package com.example.choi.cracker.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.choi.cracker.R;
import com.example.choi.cracker.Data.User;
import com.google.gson.Gson;

/**
 * Created by choi on 2017. 7. 15..
 */

public class AddCardInfo extends AppCompatActivity {
    EditText cardNickname, cardNumber;
    TextView cardUsername;
    String userName;
    Button addBtn;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcard);
        loadNowData();
        setCustomActionBar();

        cardNickname = (EditText) findViewById(R.id.card_nickname);
        cardNumber = (EditText) findViewById(R.id.card_num);
        cardUsername = (TextView) findViewById(R.id.card_user_name);
        addBtn = (Button) findViewById(R.id.add);

        cardUsername.setText(""+ user.getUserName());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setCardName(cardNickname.getText().toString());
                int cardNum = Integer.parseInt(cardNumber.getText().toString());
                user.setCardNum(cardNum);
                String json_ = new Gson().toJson(user);
                Log.d("asd",json_);
                saveNowData();
                finish();
            }
        });

    }

    void loadNowData() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        userName = pref.getString("add_card_name", null);
        user = new Gson().fromJson(userName, User.class);
        Log.d("asdasd",""+user);
    }
    void saveNowData() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String json = new Gson().toJson(user);
        editor.putString("add_card_name", json);
        editor.apply();
    }

    public void setCustomActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.addcard_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        View mCustomView = LayoutInflater.from(this).inflate(R.layout.actionbar_addcard,null);
        actionBar.setCustomView(mCustomView);

        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0,0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView,params);
    }
}
