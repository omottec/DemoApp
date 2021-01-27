package com.omottec.demoapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.utils.UiUtils;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 13/12/2017.
 */

public class TextDialogFragment extends DialogFragment {
    public static final String TAG = "TextDialogFragment";
    public static final String ARG_TITLE = "arg_title";

    private View mRootView;
    private EditText mEt;

    public static TextDialogFragment newInstance(String title) {
        TextDialogFragment fragment = new TextDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog_MinWidth);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
        getDialog().setCanceledOnTouchOutside(false);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (UiUtils.getScreenSize(MyApplication.getContext(), true) * 0.9);
        lp.height = (int) (UiUtils.getScreenSize(MyApplication.getContext(), false) * 0.5);
        window.setAttributes(lp);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog");
        mRootView = View.inflate(getActivity(), R.layout.f_text_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog = builder.setView(mRootView)
                .setCancelable(false)
                .create();
        return dialog;
    }
}
