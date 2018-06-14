package com.omottec.demoapp.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
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
import java.util.Arrays;

/**
 * Created by omottec on 13/06/2018.
 */

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "PermissionActivity";
    public static final String APP_UPDATE_FILE = "appupdate.bat";
    public static final int REQUEST_CODE_PERMISSION_READ_PHONE_STATE = 1;
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
        int checkSelfPermission1 = PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        Logger.d(TAG, "PermissionChecker.checkSelfPermission READ_PHONE_STATE:" + checkSelfPermission1);
        boolean shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE);
        Logger.d(TAG, "ActivityCompat.shouldShowRequestPermissionRationale READ_PHONE_STATE:" + shouldShowRequestPermissionRationale);

        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission1 != PermissionChecker.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.READ_PHONE_STATE};
            Logger.d(TAG, "ActivityCompat.requestPermissions permissions:" + Arrays.toString(permissions)
                    + ", requestCode:" + REQUEST_CODE_PERMISSION_READ_PHONE_STATE);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION_READ_PHONE_STATE);
            return;
        }

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (telephonyManager == null) return;
        String deviceId = telephonyManager.getDeviceId();
        Logger.d(TAG, "deviceId:" + deviceId);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Logger.d(TAG, "onRequestPermissionsResult requestCode:" + requestCode
                + ", permissions:" + Arrays.toString(permissions)
                + ", grantResults:" + Arrays.toString(grantResults));

        int checkSelfPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        Logger.d(TAG, "ContextCompat.checkSelfPermission READ_PHONE_STATE:" + checkSelfPermission);
        int checkSelfPermission1 = PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        Logger.d(TAG, "PermissionChecker.checkSelfPermission READ_PHONE_STATE:" + checkSelfPermission1);
        boolean shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE);
        Logger.d(TAG, "ActivityCompat.shouldShowRequestPermissionRationale READ_PHONE_STATE:" + shouldShowRequestPermissionRationale);

        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission1 != PermissionChecker.PERMISSION_GRANTED) {
            showPermissionSettingDialog();
        }
    }

    private void startPermissionSetting() {
        Logger.d(TAG, "startPermissionSetting");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Logger.e(TAG, "startPermissionSetting", e);
        }
    }

    private void showPermissionSettingDialog() {
        new AlertDialog.Builder(this)
                .setTitle("权限申请")
                .setMessage("电话权限,存储权限为必选项，全部开通才可以正常使用App,请到设置中开启")
                .setCancelable(true)
                .setNegativeButton("取消", (dialog, which) -> {

                })
                .setPositiveButton("去设置", (dialog, which) -> {
                    startPermissionSetting();
                })
                .create()
                .show();
    }
}
