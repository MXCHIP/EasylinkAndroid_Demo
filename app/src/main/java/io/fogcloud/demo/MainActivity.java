package io.fogcloud.demo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.fogcloud.sdk.easylink.api.EasyLink;
import io.fogcloud.sdk.easylink.api.EasylinkP2P;
import io.fogcloud.sdk.easylink.helper.EasyLinkCallBack;
import io.fogcloud.sdk.easylink.helper.EasyLinkParams;

public class MainActivity extends AppCompatActivity {

    private String TAG = "---main---";
    private Context mContext;// 上下文
//    private EditText ssid;
//    private EditText psw;
    private EditText log_view;
    private int countno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        final EasyLink el = new EasyLink(MainActivity.this);

        final EasylinkP2P elp2p = new EasylinkP2P(mContext);

        TextView easylinktest = (TextView) findViewById(R.id.easylinktest);

        EditText et = (EditText) findViewById(R.id.ssid);
        et.setText(el.getSSID());

        final EditText psw = (EditText) findViewById(R.id.psw);

        log_view = (EditText) findViewById(R.id.log);
        log_view.setText("");
        easylinktest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyLinkParams elp = new EasyLinkParams();
                elp.ssid = el.getSSID();
                elp.password = psw.getText().toString().trim();
                elp.sleeptime = 50;
                elp.runSecond = 20000;

//                el.startEasyLink(elp, new EasyLinkCallBack() {
                elp2p.startEasyLink(elp, new EasyLinkCallBack() {
                    @Override
                    public void onSuccess(int code, String message) {
                        Log.d(TAG, message);
                        send2handler(1,message);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, message);
                    }
                });

            }
        });

        new Thread() {
            @Override
            public void run() {
//                MDNS mdns = new MDNS();
            }
        }.start();
    }

    private void send2handler(int code, String message) {
        Message msg = new Message();
        msg.what = code;
        msg.obj = message;
        LHandler.sendMessage(msg);
    }

    Handler LHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                if(countno >2){
                    log_view.setText("");
                    countno = 1;
                }
                log_view.append(msg.obj.toString().trim() + "\r\n");
                countno ++;
            }
        }
    };
}

