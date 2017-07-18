package com.example.choi.cracker.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs = null;
    TextView textView;
    String User_name;
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
    int num=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        loadNowData();
        checkFirstRun();
        callbackManager = CallbackManager.Factory.create();
        setCustomActionBar();
        usedListText = (TextView) findViewById(R.id.used_list_text);
        cardImg = (ImageView) findViewById(R.id.card_img);
        usedList = (RecyclerView) findViewById(R.id.used_list);
        listAdapter = new UsedListAdapter(RideDatas,getApplicationContext(),R.layout.used_list_item);

        if (noCard) {
            usedListText.setVisibility(View.GONE);
        }
        textView = (TextView) findViewById(R.id.add_card);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin) {
                    Toast.makeText(getApplicationContext(),"로그인해주세요",Toast.LENGTH_SHORT).show();
                } else if (textView.getText().toString().equals("카드 추가하기")) {
                    if (user == null)
                        user = new User("", "", ""
                                , "", 0, false, false, false, false);
                    saveNowData();
                    Intent intent = new Intent(MainActivity.this, AddCardInfo.class);
                    startActivityForResult(intent, 333);
                } else if (textView.getText().toString().equals("삭제하기")) {
                }
            }
        });

        bt = new BluetoothSPP(this);
        Log.d("asdf", bt.isBluetoothAvailable() + "");

        if (!bt.isBluetoothAvailable()) {
            Log.d("asdf", "asdf");
            Toast.makeText(getApplicationContext()
                    , "블루투스를 켜주세요"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }


        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener()

        {
            public void onDeviceConnected(String name, String address) {        //연결

            }

            public void onDeviceDisconnected() {    //연결끊김
                Toast.makeText(MainActivity.this, "하차", Toast.LENGTH_SHORT).show();
                RideDatas.add(new RideData(user.getMoney(),receive,"0"));
            }

            public void onDeviceConnectionFailed() {
            }
        });

        bt.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
            public void onNewConnection(String name, String address) {
                Log.d("asd", name + address);
            }

            public void onAutoConnectionStarted() {
                Log.d("asd", "asdasdas");
            }
        });
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                receive = message;
                if(num==0){
                    RideDatas.add(new RideData(user.getMoney(),receive,"720"));
                    num++;
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
                } else {
                    if (!user.getFacebook_ID().equals("")) {
                        usedListText.setVisibility(View.VISIBLE);
                        textView.setText("삭제하기");
                    }
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
            case 555:                   //블루투스 페어링했을때
                Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    // 페어링 된 장치가 있는 경우.
                    final BroadcastReceiver mReceiver = new BroadcastReceiver() {
                        public void onReceive(Context context, Intent intent) {
                            String action = intent.getAction();
                            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                                Log.d("devicename", "" + device.getName());
                            }
                        }
                    };

                } else {
                    Toast.makeText(getApplicationContext(), "블루투스 페어링을 해주세요", Toast.LENGTH_SHORT).show();

                }
                break;

        }
    }


    public void checkFirstRun() {
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        Log.d("isFirstRun", isFirstRun + "");
        if (isFirstRun) {
            Intent newIntent = new Intent(MainActivity.this, GuideActivity.class);
            startActivity(newIntent);
            prefs.edit().putBoolean("isFirstRun", false).apply();
            finish();
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
        noCard = pref.getBoolean("noCard", true);
        isLogin = pref.getBoolean("isLogin", false);
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
}
