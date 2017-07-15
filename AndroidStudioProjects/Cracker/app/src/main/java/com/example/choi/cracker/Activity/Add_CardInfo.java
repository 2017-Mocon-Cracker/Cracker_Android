package com.example.choi.cracker.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.choi.cracker.R;
import com.example.choi.cracker.data.User;
import com.google.gson.Gson;

/**
 * Created by choi on 2017. 7. 15..
 */

public class Add_CardInfo extends AppCompatActivity {
    EditText card_Nickname, card_Number;
    TextView card_Username;
    String User_name;
    Button add_Btn;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcard);
        loadNowData();

        card_Nickname = (EditText) findViewById(R.id.card_nickname);
        card_Number = (EditText) findViewById(R.id.card_num);
        card_Username = (TextView) findViewById(R.id.card_user_name);
        add_Btn = (Button) findViewById(R.id.add);

        card_Username.setText(""+ user.getUserName());

        add_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setCardName(card_Nickname.getText().toString());
                int cardNum = Integer.parseInt(card_Number.getText().toString());
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
        User_name = pref.getString("add_card_name", null);
        user = new Gson().fromJson(User_name, User.class);
        Log.d("asdasd",""+user);
    }
    void saveNowData() { //items 안의 내용이 저장됨
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String json = new Gson().toJson(user);
        editor.putString("add_card_name", json);
        editor.commit();
    }
}
