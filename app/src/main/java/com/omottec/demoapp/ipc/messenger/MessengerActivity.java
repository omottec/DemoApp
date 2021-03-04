package com.omottec.demoapp.ipc.messenger;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.Constants;

/**
 * Created by qinbingbing on 9/8/16.
 */
public class MessengerActivity extends FragmentActivity {
    private TextView mTv;
    private static class ClientHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_FROM_SERVER:
                    Log.d(Tag.IPC_MESSENGER, "client receive msg from server:" + msg.getData().get(Constants.KEY_MSG));
                    break;
                default:
                    break;
            }
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(Tag.IPC_MESSENGER, "onServiceConnected");
            Messenger serverMessenger = new Messenger(service);
            Message msg = Message.obtain(null, Constants.MSG_FROM_CLIENT);
            Bundle data= new Bundle();
            data.putString(Constants.KEY_MSG, "Hello, server");
            msg.setData(data);
            msg.replyTo = new Messenger(new ClientHandler());
            try {
                Log.d(Tag.IPC_MESSENGER, "client send msg to server");
                serverMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(Tag.IPC_MESSENGER, "onServiceDisconnected");
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setText("MessengerActivity");
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
