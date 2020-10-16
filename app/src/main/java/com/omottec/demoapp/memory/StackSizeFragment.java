package com.omottec.demoapp.memory;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.omottec.demoapp.R;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class StackSizeFragment extends Fragment {
    public static final String TAG = "StackSizeFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView tv = view.findViewById(R.id.tv);
        Thread defaultStackSizeThread = new Thread("t_default_stack_size") {
            @Override
            public void run() {
                recursive("default", 0);
            }
        };
        Thread optStackSizeThread = new Thread(null, null, "t_opt_stack_size", -512 * 1024) {
            @Override
            public void run() {
                recursive("opt", 0);
            }
        };
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //defaultStackSizeThread.start();
                //optStackSizeThread.start();
                sysEnv();
            }
        });
    }

    private void recursive(String name, int i) {
        Log.i(TAG, "recursive " + name + i);
        i++;
        recursive(name, i);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void sysEnv() {
        Map<String, String> env = System.getenv();
        Log.i(TAG, "System.getenv:" + env.toString());
        String rustMinStack = Os.getenv("RUST_MIN_STACK");
        Log.i(TAG, "rustMinStack:" + rustMinStack);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Os.setenv("RUST_MIN_STACK", "1048576", true);
            } catch (ErrnoException e) {
                Log.e(TAG, "", e);
            }
        }
    }
}
