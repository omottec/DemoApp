package com.omottec.demoapp.prop;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omottec.demoapp.R;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by qinbingbing on 20/11/2018.
 */

public class PropFragment extends Fragment {
    public static final String TAG = "PropFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Properties properties = System.getProperties();
        Properties cloneProps = (Properties) properties.clone();
        Log.d(TAG, cloneProps.toString());
        Set<Map.Entry<Object, Object>> entries = cloneProps.entrySet();
        for (Map.Entry entry : entries) {
            Log.d(TAG, entry.getKey() + ":" + entry.getValue());
        }
        Object httpAgent = cloneProps.get("http.agent");
        Log.d(TAG, "httpAgent:" + httpAgent);

        Log.d(TAG, "\n");
    }
}
