package com.omottec.demoapp.aspectj;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.Logger;

/**
 * doc test
 */
public class AspectJActivity extends Activity {
    public static final String TAG = "AspectJActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        Logger.i(TAG);
        showToast("Hello AspectJ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.i(TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i(TAG);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.i(TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.i(TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i(TAG);
    }

    @AspectJMarker
    private void showToast(String str) {
        Logger.i(TAG, str);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
