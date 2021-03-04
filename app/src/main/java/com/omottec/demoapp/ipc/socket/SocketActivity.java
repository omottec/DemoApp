package com.omottec.demoapp.ipc.socket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.omottec.demoapp.Constants;
import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.io.IoUtils;
import com.omottec.demoapp.utils.TimeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qinbingbing on 9/13/16.
 */
public class SocketActivity extends FragmentActivity {
    private TextView mMsgTv;
    private EditText mMsgEt;
    private Button mSendBtn;
    private String[] mClientMsgList = {"Hello Server", "Hi Server", "Wow, Server"};
    private Random mRandom = new Random();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_SERVER_CONNECTED:
                    mSendBtn.setEnabled(true);
                    mMsgTv.setText("#connect server success\n");
                    break;
                case Constants.MSG_CLIENT_MESSAGE_2_SERVER:
                    String c2s = Constants.CLIENT_2_SERVER + msg.obj + '\n';
                    mMsgTv.setText(mMsgTv.getText() + c2s);
                    Log.d(Tag.IPC_SOCKET, c2s);
                    break;
                case Constants.MSG_SERVER_MESSAGE_2_CLIENT:
                    String s2c = Constants.SERVER_2_CLIENT + msg.obj + '\n';
                    mMsgTv.setText(mMsgTv.getText() + s2c);
                    Log.d(Tag.IPC_SOCKET, s2c);
                    break;
                default:
                    break;
            }
        }
    };
    private ExecutorService mExecutor = Executors.newCachedThreadPool();
    private PrintWriter mWriter;
    private Socket mSocket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag.IPC_SOCKET, "SocketActivity.onCreate");
        setContentView(R.layout.a_socket);
        mMsgTv = (TextView) findViewById(R.id.msg_tv);
        mMsgEt = (EditText) findViewById(R.id.msg_et);
        mSendBtn = (Button) findViewById(R.id.send_btn);
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mMsgEt.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    msg = TimeUtils.getCurHms() + '#' + msg;
                    mHandler.obtainMessage(Constants.MSG_CLIENT_MESSAGE_2_SERVER, msg).sendToTarget();
                    mMsgEt.setText("");
                    mWriter.println(msg);
                }
            }
        });
        Intent intent = new Intent(this.getApplicationContext(), SocketService.class);
        startService(intent);
        mExecutor.submit(new ConnectServer());
    }

    @Override
    protected void onDestroy() {
        Log.d(Tag.IPC_SOCKET, "SocketActivity.onDestroy");
        Intent intent = new Intent(this.getApplicationContext(), SocketService.class);
        stopService(intent);
        IoUtils.close(mWriter, mSocket);
        mHandler.removeCallbacksAndMessages(null);
        mExecutor.shutdownNow();
        super.onDestroy();
    }

    private class ConnectServer implements Runnable {

        @Override
        public void run() {
            Log.d(Tag.IPC_SOCKET, "ConnectServer.run");
            while (mSocket == null) {
                try {
                    mSocket = new Socket("localhost", Constants.SOCKET_PORT);
                    Log.d(Tag.IPC_SOCKET, "connect server success");
                    mHandler.obtainMessage(Constants.MSG_SERVER_CONNECTED).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                    SystemClock.sleep(1000);
                    Log.d(Tag.IPC_SOCKET, "connect server failed, retry");
                }
            }
            String line;
            String msg;
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                mWriter = new PrintWriter(new OutputStreamWriter(mSocket.getOutputStream()), true);
                while (!SocketActivity.this.isFinishing()) {
                    line = reader.readLine();
                    mHandler.obtainMessage(Constants.MSG_SERVER_MESSAGE_2_CLIENT, line).sendToTarget();
                    SystemClock.sleep(60000);
                    msg = TimeUtils.getCurHms()
                            + '#'
                            + mClientMsgList[mRandom.nextInt(mClientMsgList.length)];
                    mHandler.obtainMessage(Constants.MSG_CLIENT_MESSAGE_2_SERVER, msg).sendToTarget();
                    mWriter.println(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IoUtils.close(reader, mWriter, mSocket);
            }

        }
    }
}
