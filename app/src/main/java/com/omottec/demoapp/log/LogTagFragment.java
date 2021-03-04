package com.omottec.demoapp.log;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.omottec.demoapp.R;

public class LogTagFragment extends Fragment {
    public static final String MEM_TAG = "MemorySizeCalculator";
    public static final String GLIDE_TAG = "Glide";
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_screen_text, container, false);
        mTv = view.findViewById(R.id.tv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean memoryV = Log.isLoggable("MemorySizeCalculator", Log.VERBOSE);
        boolean memoryD = Log.isLoggable("MemorySizeCalculator", Log.DEBUG);
        boolean memoryI = Log.isLoggable("MemorySizeCalculator", Log.INFO);
        boolean glideV = Log.isLoggable("Glide", Log.VERBOSE);
        boolean glideD = Log.isLoggable("Glide", Log.DEBUG);
        boolean glideI = Log.isLoggable("Glide", Log.INFO);
        String logInfo = new StringBuilder("memoryV:").append(memoryV)
            .append("\nmemoryD:")
            .append(memoryD)
            .append("\nmemoryI:")
            .append(memoryI)
            .append("\nglideV:")
            .append(glideV)
            .append("\nglideD:")
            .append(glideD)
            .append("\nglideI:")
            .append(glideI)
            .toString();
        mTv.setText(logInfo);
        Log.v(MEM_TAG, "memoryV:" + memoryV);
        Log.d(MEM_TAG, "memoryD:" + memoryD);
        Log.i(MEM_TAG, "memoryI:" + memoryI);
        Log.v(GLIDE_TAG, "glideV:" + glideV);
        Log.d(GLIDE_TAG, "glideD:" + glideD);
        Log.i(GLIDE_TAG, "glideI:" + glideI);
    }
}
