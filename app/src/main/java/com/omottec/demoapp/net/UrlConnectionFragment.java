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

import com.omottec.logger.Logger;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by qinbingbing on 21/12/2018.
 */

public class UrlConnectionFragment extends Fragment {
    public static final String TAG = "UrlConnectionFragment";
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
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL("http://www.mocky.io/v2/5b5ecd102e0000020a69466d");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(10 * 1000);
                    urlConnection.setReadTimeout(10 * 1000);
                    int responseCode = urlConnection.getResponseCode();
                    Logger.d(TAG, "responseCode:" + responseCode + ", responseMessage:" + urlConnection.getResponseMessage());
                    if (responseCode != HttpURLConnection.HTTP_OK) return null;
                    Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
                    Logger.d(TAG, "headerFields:" + headerFields);
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    int count;
                    byte[] buffer = new byte[1024];
                    while ((count = in.read(buffer)) != -1)
                        out.write(buffer, 0, count);
                    return new String(out.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
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
