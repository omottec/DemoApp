package com.omottec.demoapp.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.Constants;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 9/8/16.
 */
public class MessengerService extends Service {
    private static class ServerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_FROM_CLIENT:
                    Log.d(Tag.IPC_MESSENGER, "server receive msg from client:" + msg.getData().get(Constants.KEY_MSG));
                    Message serverMsg = Message.obtain(null, Constants.MSG_FROM_SERVER);
                    Bundle data = new Bundle();
                    data.putString(Constants.KEY_MSG, "Hello, client");
                    serverMsg.setData(data);
                    try {
                        Log.d(Tag.IPC_MESSENGER, "server send msg to client");
                        msg.replyTo.send(serverMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    Log.d(Tag.IPC_MESSENGER, "msg is not from client");
                    break;
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new ServerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Tag.IPC_MESSENGER, "MessengerService.onBind");
        Logger.logThread(Tag.IPC_MESSENGER);
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Tag.IPC_MESSENGER, "MessengerService.onCreate");
        Logger.logThread(Tag.IPC_MESSENGER);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Tag.IPC_MESSENGER, "MessengerService.onStartCommand");
        Logger.logThread(Tag.IPC_MESSENGER);
        return super.onStartCommand(intent, flags, startId);
    }
}
