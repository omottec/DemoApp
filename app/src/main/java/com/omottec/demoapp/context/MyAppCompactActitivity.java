package com.omottec.demoapp.context;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.utils.Views;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 16/05/2018.
 */

public class MyAppCompactActitivity extends AppCompatActivity {
    public static final String TAG = "MyAppCompactActitivity";

    private ImageView mIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_my_app_compact);
        mIv = (ImageView) findViewById(R.id.iv);

        Logger.d(TAG, "view get Context:" + mIv.getContext());
        Logger.d(TAG, "view get Activity:" + Views.getActivity(mIv));

        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Dialog(MyApplication.getContext()).show();
            }
        });
    }
}
