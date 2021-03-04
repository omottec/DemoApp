package com.omottec.demoapp.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.RequiresApi;

import com.omottec.demoapp.io.IoUtils;
import com.omottec.demoapp.utils.NetUtils;

import com.omottec.logger.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    public static final String TAG = "MyJobService";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.logClassAndMethod(this);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        boolean netConnected = NetUtils.isNetConnected(this);
        Logger.i(TAG, "onStartJob jobId:" + params.getJobId()
                + ", netConnected:" + netConnected);
        if (netConnected) {
            new SimpleDownloadTask().execute(params);
            return true;
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Logger.i(TAG, "onStopJob " + params.getJobId());
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.logClassAndMethod(this);
    }

    private class SimpleDownloadTask extends AsyncTask<JobParameters, Void, String> {
        protected JobParameters mJobParam;

        @Override
        protected String doInBackground(JobParameters... jobParameters) {
            mJobParam = jobParameters[0];
            Reader reader = null;
            InputStream is = null;
            int len = 50;
            try {
                URL url = new URL("https://www.google.com");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(15 * 1000);
                conn.setReadTimeout(10 * 1000);
                conn.setRequestMethod("GET");
                conn.connect();
                int responseCode = conn.getResponseCode();
                Logger.i(TAG, "job " + mJobParam.getJobId() + " responseCode:" + responseCode);
                is = conn.getInputStream();
                reader = new InputStreamReader(is, "UTF-8");
                char[] buffer = new char[len];
                reader.read(buffer);
                return new String(buffer);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Logger.e(TAG, "MalformedURLException", e);
            } catch (IOException e) {
                Logger.e(TAG, "IOException", e);
            } finally {
                IoUtils.close(is, reader);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            jobFinished(mJobParam, false);
            Logger.i(TAG, "job " + mJobParam.getJobId() + " onPostExecute " + s);
        }
    }
}
