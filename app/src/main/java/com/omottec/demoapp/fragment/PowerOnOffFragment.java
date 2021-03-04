package com.omottec.demoapp.fragment;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;

import java.io.IOException;

public class PowerOnOffFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "PowerOnOffFragment";
    private TextView mRestartTv;
    private TextView mPowerOffTv;
    private TextView mScreenBrightPlusTv;
    private TextView mScreenBrightMinusTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_power_on_off, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRestartTv = view.findViewById(R.id.tv_restart);
        mPowerOffTv = view.findViewById(R.id.tv_power_off);
        mScreenBrightPlusTv = view.findViewById(R.id.tv_screen_bright_plus);
        mScreenBrightMinusTv = view.findViewById(R.id.tv_screen_bright_minus);

        mRestartTv.setOnClickListener(this);
        mPowerOffTv.setOnClickListener(this);
        mScreenBrightPlusTv.setOnClickListener(this);
        mScreenBrightMinusTv.setOnClickListener(this);

        setScreenManualMode();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_restart:
                onClickRestart();
                break;
            case R.id.tv_power_off:
                onClickPowerOff();
                break;
            case R.id.tv_screen_bright_plus:
                onClickScreenBrightPlus();
                break;
            case R.id.tv_screen_bright_minus:
                onClickScreenBrightMinus();
                break;
        }
    }

    private void onClickScreenBrightMinus() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        int defVal = -1;
        int currentVal = Settings.System.getInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, defVal);
        Log.i(TAG, "screen current bright:" + currentVal);
        Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, currentVal + 50);
    }

    private void onClickScreenBrightPlus() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        int defVal = -1;
        int currentVal = Settings.System.getInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, defVal);
        Log.i(TAG, "screen current bright:" + currentVal);
        Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, currentVal - 50);
    }

    private void onClickPowerOff() {

    }

    private void onClickRestart() {
        Log.i(TAG, "onClickRestart");
        try {
            Runtime.getRuntime().exec("am start -a android.intent.action.REBOOT");
        } catch (IOException e) {
            Log.e(TAG, "onClickRestart IOException", e);
        }
    }

    private void setScreenManualMode() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        try {
            int mode = Settings.System.getInt(contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
            Log.i(TAG, "screen current mode:" + mode);
            if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC)
                Settings.System.putInt(contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "setScreenManualMode SettingNotFoundException", e);
        }
    }
}
