package com.omottec.demoapp.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.io.IoUtils;
import com.omottec.demoapp.utils.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by omottec on 13/06/2018.
 */

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "PermissionActivity";
    public static final String APP_UPDATE_FILE = "appupdate.bat";
    private TextView mGetImeiTv;
    private TextView mCreateFileTv;
    private TextView mDeleteFileTv;
    private TextView mGetLocationTv;

    private File mFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_permission);
        mGetImeiTv = (TextView) findViewById(R.id.tv_get_imei);
        mCreateFileTv = (TextView) findViewById(R.id.tv_create_file);
        mDeleteFileTv = (TextView) findViewById(R.id.tv_delete_file);
        mGetLocationTv = (TextView) findViewById(R.id.tv_get_location);
        mGetImeiTv.setOnClickListener(this);
        mCreateFileTv.setOnClickListener(this);
        mDeleteFileTv.setOnClickListener(this);
        mGetLocationTv.setOnClickListener(this);

//        mFile = new File(MyApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), APP_UPDATE_FILE);
//        mFile = new File(MyApplication.getContext().getExternalCacheDir(), APP_UPDATE_FILE);
//        mFile = new File(MyApplication.getContext().getFilesDir(), APP_UPDATE_FILE);
//        mFile = new File(MyApplication.getContext().getCacheDir(), APP_UPDATE_FILE);
//        mFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), APP_UPDATE_FILE);
        mFile = new File(Environment.getExternalStorageDirectory(), APP_UPDATE_FILE);
        Logger.d(TAG, mFile.getAbsolutePath());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_imei:
                getImei();
                break;
            case R.id.tv_create_file:
                createFile();
                break;
            case R.id.tv_delete_file:
                deleteFile();
                break;
            case R.id.tv_get_location:
                getLocation();
                break;
        }
    }

    private void createFile() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(mFile));
            oos.writeObject("update to 2.5.0");
        } catch (IOException e) {
            Log.e(TAG, "write obj exception", e);
        } finally {
            Toast.makeText(MyApplication.getContext(),
                    "file create " + mFile.exists(),
                    Toast.LENGTH_SHORT).show();
            IoUtils.close(oos);
        }
    }

    private void deleteFile() {
        boolean delete = mFile.delete();
        Toast.makeText(MyApplication.getContext(),
                "file delete " + delete,
                Toast.LENGTH_SHORT).show();
    }

    private void getLocation() {

    }

    @SuppressLint("MissingPermission")
    public void getImei() {
        int checkSelfPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        Logger.d(TAG, "ContextCompat.checkSelfPermission READ_PHONE_STATE:" + checkSelfPermission);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (telephonyManager == null) return;
        String deviceId = telephonyManager.getDeviceId();
        Logger.d(TAG, "deviceId:" + deviceId);
    }
}
