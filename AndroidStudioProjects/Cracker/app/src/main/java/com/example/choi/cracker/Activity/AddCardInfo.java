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
import android.widget.Toast;

import com.example.choi.cracker.Network.NetworkHelper;
import com.example.choi.cracker.R;
import com.example.choi.cracker.Data.User;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by choi on 2017. 7. 15..
 */

public class AddCardInfo extends AppCompatActivity {
    EditText cardNickname, cardNumber1, cardNumber2, cardNumber3, cardNumber4;
    String userName;
    TextView addBtn;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcard);
        loadNowData();
        setCustomActionBar();

        cardNickname = (EditText) findViewById(R.id.card_nickname);
        cardNumber1 = (EditText) findViewById(R.id.card_num_1);
        cardNumber2 = (EditText) findViewById(R.id.card_num_2);
        cardNumber3 = (EditText) findViewById(R.id.card_num_3);
        cardNumber4 = (EditText) findViewById(R.id.card_num_4);
        addBtn = (TextView) findViewById(R.id.add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("null?", cardNumber1.getText() + "");
                if (cardNumber1.getText().toString().equals("") || cardNumber2.getText().toString().equals("")
                        || cardNumber3.getText().toString().equals("") || cardNumber4.getText().toString().equals("")
                        || cardNickname.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "카드번호 또는 이름을 확인해주세요", Toast.LENGTH_SHORT).show();
                } else if (cardNumber1.length() < 4
                        || cardNumber2.length() < 4 || cardNumber3.length() < 4
                        || cardNumber4.length() < 4) {
                    Toast.makeText(getApplicationContext(), "카드번호 길이를 확인해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    String cardNum = cardNum();
                    Log.d("asd",cardNickname.getText().toString());
                    user.setCardName(cardNickname.getText().toString());
                    user.setCardNum(cardNum);
                    user.setCardIn(true);
                    saveNowData();
                    Log.d("user", user.getCardNum().toString());
                    setResult(RESULT_OK);
//                    String email, String cardName, String userName, String cardNum, int money, int paied, Boolean isTransfer
//                    Call<User> access = NetworkHelper.getNetworkInstance().facebookLogin(loginResult.getAccessToken().getToken());
                    String json = new Gson().toJson(user);
                    Log.d("json", json);
                    Call<User> access = NetworkHelper.getNetworkInstance().cardInfo(json);
                    access.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.d("asd", "" + response.body());
                            Log.d("code", "" + response.code());
                            String user__ = new Gson().toJson(user);
                            Log.d("main_user", "user" + user__);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("fail", t.getMessage());
                        }
                    });
                    finish();
                }
            }
        });
    }

    void loadNowData() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        userName = pref.getString("add_card_name", null);
        user = new Gson().fromJson(userName, User.class);
    }

    void saveNowData() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String json = new Gson().toJson(user);
        editor.putString("add_card_name", json);
        editor.putBoolean("noCard", false);
        editor.apply();
    }

    public void setCustomActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.addcard_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        View mCustomView = LayoutInflater.from(this).inflate(R.layout.actionbar_addcard, null);
        actionBar.setCustomView(mCustomView);

        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView, params);
    }

    String cardNum() {
        String cardNum;
        cardNum = cardNumber1.getText().toString() + "-"
                + cardNumber2.getText().toString() + "-"
                + cardNumber3.getText().toString() + "-"
                + cardNumber4.getText().toString();
        return cardNum;
    }
}
