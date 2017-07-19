package com.example.choi.cracker.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choi.cracker.Adapter.UsedListAdapter;
import com.example.choi.cracker.BluetoothService;
import com.example.choi.cracker.Data.RideData;
import com.example.choi.cracker.Network.NetworkHelper;
import com.example.choi.cracker.R;
import com.example.choi.cracker.Data.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs = null;
    TextView textView, cardNumText;
    String User_name;
    ImageView cardImgView;
    User user;
    Boolean noCard = true, isLogin;
    Button login;
    private final static int REQUEST_ENABLE_BT = 1;
    TextView usedListText;
    ImageView cardImg;
    BluetoothAdapter btAdapter;
    RecyclerView usedList;
    UsedListAdapter listAdapter;
    private CallbackManager callbackManager;
    private BluetoothService btService = null;
    BluetoothSPP bt;
    String receive;
    List<RideData> RideDatas = new ArrayList<>();
    int num;
    int num_ = 0;
    Boolean isRide = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        loadNowData();
        loadRideData();
        num = 0;
        callbackManager = CallbackManager.Factory.create();
        setCustomActionBar();
        usedListText = (TextView) findViewById(R.id.used_list_text);
        cardImg = (ImageView) findViewById(R.id.card_img);
        usedList = (RecyclerView) findViewById(R.id.used_list);
        cardImgView = (ImageView) findViewById(R.id.card_img);
        cardNumText = (TextView) findViewById(R.id.card_num);
        listAdapter = new UsedListAdapter(RideDatas, getApplicationContext(), R.layout.used_list_item);
        usedList.setAdapter(listAdapter);
        usedList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        usedList.setItemAnimator(new DefaultItemAnimator());
        textView = (TextView) findViewById(R.id.add_card);

        if (noCard) {
            usedListText.setVisibility(View.GONE);
        }
        if (user != null) {
            if (user.getCardIn()) {
                usedListText.setVisibility(View.VISIBLE);
                textView.setText("삭제하기");
                cardImgView.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.cardimg)));
                cardNumText.setText("" + user.getCardNum());
            } else {
                usedListText.setVisibility(View.GONE);
                textView.setText("카드 추가하기");
                cardImgView.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.no_card)));
            }
        } else {
            usedListText.setVisibility(View.GONE);
            textView.setText("카드 추가하기");
            cardImgView.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.no_card)));
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin) {
                    Toast.makeText(getApplicationContext(), "로그인해주세요", Toast.LENGTH_SHORT).show();
                } else if (textView.getText().toString().equals("카드 추가하기")) {
                    if (user == null)
                        user = new User("", "", ""
                                , "", 0, false, false, false, false);
                    saveNowData();
                    Intent intent = new Intent(MainActivity.this, AddCardInfo.class);
                    startActivityForResult(intent, 333);
                } else if (textView.getText().toString().equals("삭제하기")) {
                    user.setCardIn(false);
                    user.setMoney(1000);
                    user.setCardNum("");
                    user.setCardName("");
                    RideDatas.clear();
                    String json = new Gson().toJson(user);
                    Call<User> access = NetworkHelper.getNetworkInstance().cardInfo(json);
                    access.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                    cardImgView.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.no_card)));
                    listAdapter.notifyDataSetChanged();
                    saveNowData();
                    cardNumText.setText(user.getCardNum());
                    textView.setText("카드 추가하기");
                }
            }
        });


        bt = new BluetoothSPP(this);

        if (!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "블루투스를 켜주세요"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }


        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener()

        {
            public void onDeviceConnected(String name, String address) {        //연결
                Toast.makeText(MainActivity.this, "승차", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() {    //연결끊김
            }

            public void onDeviceConnectionFailed() {
            }
        });

        bt.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
            public void onNewConnection(String name, String address) {
                if (isRide) {
                    Toast.makeText(MainActivity.this, "하차", Toast.LENGTH_SHORT).show();
                    RideDatas.add(new RideData(user.getMoney(), receive, "0"));
                    isRide = false;
                    synchronized (listAdapter) {
                        listAdapter.notifyDataSetChanged();
                        saveNowData();
                    }
                    num = 0;
                }
            }

            public void onAutoConnectionStarted() {
            }
        });
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                receive = message;
                int mymoney = user.getMoney();
                if (user != null) {
                    if (user.getCardNum().equals("")) {
                        num = 0;
                    } else if (num == 0 && user.getMoney() >= 720) {
                        user.setMoney(mymoney - 720);
                        RideDatas.add(new RideData(user.getMoney(), receive, "720"));
                        Toast.makeText(getApplicationContext(), "승차", Toast.LENGTH_SHORT).show();
                        isRide = true;
                        synchronized (listAdapter) {
                            listAdapter.notifyDataSetChanged();
                            saveNowData();
                        }
                        num++;
                        num_ = 0;
                    } else if (num_ == 0 && mymoney < 720 && !isRide) {
                        Toast.makeText(getApplicationContext(), "돈 부족 현재잔액 확인!!", Toast.LENGTH_SHORT).show();
                        num_++;
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        loadNowData();
        if (resultCode == RESULT_OK) switch (requestCode) {
            case 333:
                if (!user.getCardIn()) {
                    usedListText.setVisibility(View.GONE);
                    textView.setText("카드 추가하기");
                    cardImgView.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.no_card)));
                    RideDatas.clear();
                    listAdapter.notifyDataSetChanged();

                } else {
                    usedListText.setVisibility(View.VISIBLE);
                    textView.setText("삭제하기");
                    cardImgView.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.cardimg)));
                    cardNumText.setText("" + user.getCardNum());
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // 확인 눌렀을 때
                    //Next Step
                    if (btService.getDeviceState()) {
                        // 블루투스가 지원 가능한 기기일 때
                        btService.enableBluetooth();
                    } else {
                        finish();
                    }
                } else {
                    // 취소 눌렀을 때
                    Log.d("asd", "Bluetooth is not enabled");
                }
                break;


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNowData();
        listAdapter.notifyDataSetChanged();
    }

    void saveNowData() { //items 안의 내용이 저장됨
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String json = new Gson().toJson(user);
        editor.putString("add_card_name", json);
        String json_ = new Gson().toJson(RideDatas);
        editor.putString("RideData", json_);
        editor.apply();
    }

    void loadNowData() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        User_name = pref.getString("add_card_name", null);
        user = new Gson().fromJson(User_name, User.class);
        noCard = pref.getBoolean("noCard", true);
        isLogin = pref.getBoolean("isLogin", false);
    }

    void loadRideData() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String ridedata_ = pref.getString("RideData", "");
        ArrayList<RideData> items_ = new ArrayList<>();
        items_ = new Gson().fromJson(ridedata_, new TypeToken<ArrayList<RideData>>() {
        }.getType());
        if (items_ != null) {
            RideDatas.addAll(items_);
        }
    }

    public void setCustomActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        View mCustomView = LayoutInflater.from(this).inflate(R.layout.actionbar_main, null);
        actionBar.setCustomView(mCustomView);

        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        ImageView setting = (ImageView) mCustomView.findViewById(R.id.setting_main);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);

                startActivityForResult(settingsIntent, 555);
            }
        });
        ImageView login = (ImageView) findViewById(R.id.login_menu);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView, params);

    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            bt.enable();
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            }
        }
    }

    public void setup() {
        bt.autoConnect("SUNRIN");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("asd", "Asdsadasd");
        bt.stopService();
    }
}
