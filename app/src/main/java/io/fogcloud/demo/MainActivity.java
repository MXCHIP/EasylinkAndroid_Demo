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
import android.widget.Toast;

import io.fogcloud.sdk.easylink.api.EasyLink;
import io.fogcloud.sdk.easylink.api.EasylinkP2P;
import io.fogcloud.sdk.easylink.helper.EasyLinkCallBack;
import io.fogcloud.sdk.easylink.helper.EasyLinkParams;

public class MainActivity extends AppCompatActivity {

    private String TAG = "---main---";
    private Context mContext;// 上下文
    private EditText log_view;
    private int countno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        final EasyLink el = new EasyLink(MainActivity.this);


        TextView easylinktest = (TextView) findViewById(R.id.easylinktest);
        TextView easylinkstop = (TextView) findViewById(R.id.easylinkstop);

        EditText et = (EditText) findViewById(R.id.ssid);
        et.setText(el.getSSID());

        final EditText psw = (EditText) findViewById(R.id.psw);
        final EditText ssid = (EditText) findViewById(R.id.ssid);

        log_view = (EditText) findViewById(R.id.log);
        log_view.setText("");

        easylinktest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasylinkP2P elp2p = new EasylinkP2P(mContext);
                EasyLinkParams elp = new EasyLinkParams();
                elp.ssid = ssid.getText().toString().trim();
                elp.password = psw.getText().toString().trim();
                elp.sleeptime = 50;
                elp.runSecond = 20000;
                Toast.makeText(mContext, "open easylink", Toast.LENGTH_SHORT).show();

//                el.startEasyLink(elp, new EasyLinkCallBack() {
                elp2p.startEasyLink(elp, new EasyLinkCallBack() {
                    @Override
                    public void onSuccess(int code, String message) {
                        Log.d(TAG, message);
                        send2handler(1, message);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, message);
                    }
                });
            }
        });

        easylinkstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasylinkP2P elp2p = new EasylinkP2P(mContext);
                Toast.makeText(mContext, "stop easylink", Toast.LENGTH_SHORT).show();
                elp2p.stopEasyLink(new EasyLinkCallBack() {
                    @Override
                    public void onSuccess(int code, String message) {
                        Log.d(TAG, message);
                        send2handler(2, message);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, message);
                    }
                });
            }
        });

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
            if(msg.what == 2) {
                log_view.setText("");
            }
        }
    };
}

