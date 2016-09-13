package com.omottec.demoapp.ipc.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.omottec.demoapp.Constants;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.io.IoUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by qinbingbing on 9/13/16.
 */
public class SocketService extends Service {
    private volatile boolean mServiceDestroy = false;
    private ExecutorService mExecutor = Executors.newCachedThreadPool();

    @Override
    public void onCreate() {
        Log.d(Tag.IPC_SOCKET, "SocketService.onCreate");
        mExecutor.submit(new WaitClient());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Tag.IPC_SOCKET, "SocketService.onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Tag.IPC_SOCKET, "SocketService.onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        mServiceDestroy = true;
        mExecutor.shutdownNow();
        Log.d(Tag.IPC_SOCKET, "SocketService.onDestroy");
    }

    private class WaitClient implements Runnable {

        @Override
        public void run() {
            Log.d(Tag.IPC_SOCKET, "WaitClient.run");
            ServerSocket ss;
            try {
                ss = new ServerSocket(Constants.SOCKET_PORT);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            while (!mServiceDestroy) {
                try {
                    Socket client = ss.accept();
                    mExecutor.submit(new ResponseClient(client));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ResponseClient implements Runnable {
        private Socket socket;

        public ResponseClient(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            Log.d(Tag.IPC_SOCKET, "ResponseClient.run");
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                writer.println("Hi client");
                String line;
                String serverMsg;
                while (!mServiceDestroy) {
                    line = reader.readLine();
                    if (line != null && line.startsWith("exit")) break;
                    Log.d(Tag.IPC_SOCKET, Constants.CLIENT_2_SERVER + line);
                    serverMsg = line.toUpperCase();
                    Log.d(Tag.IPC_SOCKET, Constants.SERVER_2_CLIENT + serverMsg);
                    writer.println(serverMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Log.d(Tag.IPC_SOCKET, "ResponseClient.finalize");
                IoUtils.close(reader, writer, socket);
            }
        }
    }
}
