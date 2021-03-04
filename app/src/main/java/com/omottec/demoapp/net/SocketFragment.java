package com.omottec.demoapp.net;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.io.IoUtils;
import com.omottec.demoapp.utils.NetUtils;

import com.omottec.logger.Logger;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by qinbingbing on 21/12/2018.
 */

public class SocketFragment extends Fragment {
    public static final String TAG = "SocketFragment";
    private TextView mTv;
    private AsyncTask<Void, Void, String> mNetTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv = view.findViewById(R.id.tv);
        mTv.setOnClickListener(v -> {
            accessNet();
        });
    }

    private void accessNet() {
        mNetTask = new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                Logger.d(TAG, "doInBackground");
                Socket socket = new Socket();
                BufferedWriter writer = null;
                InputStream in = null;
                try {

                    InetSocketAddress socketAddress = new InetSocketAddress("www.mocky.io", 80);
                    socket.connect(socketAddress);

                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write("GET /v2/5b5ecd102e0000020a69466d HTTP/1.1\r\n");
                    writer.write("Host: www.mocky.io\r\n");
                    writer.write("User-Agent: " + NetUtils.getUserAgent(null) + "\r\n");
                    writer.write("Connection: Keep-Alive\r\n");
                    writer.write("Accept-Encoding: gzip\r\n");
                    writer.write("\r\n");
                    writer.flush();

                    in = new BufferedInputStream(socket.getInputStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    int count;
                    byte[] buffer = new byte[1024];
                    while ((count = in.read(buffer)) != -1)
                        out.write(buffer, 0, count);
                    return new String(out.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    IoUtils.close(socket, writer, in);
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                Logger.d(TAG, "onPreExecute");
            }

            @Override
            protected void onPostExecute(String s) {
                Logger.d(TAG, "onPostExecute " + s);
                mTv.setText(s);
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                Logger.d(TAG, "onProgressUpdate");
            }

            @Override
            protected void onCancelled(String s) {
                Logger.d(TAG, "onCancelled " + s);
            }

            @Override
            protected void onCancelled() {
                Logger.d(TAG, "onCancelled");
            }
        }.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNetTask.cancel(true);
    }
}
