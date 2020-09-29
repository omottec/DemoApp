package com.omottec.demoapp.memory;

import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.omottec.demoapp.R;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DumpHeapFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "DumpHeapFragment";
    private TextView mJavaHeapTv;
    private TextView mNativeHeapTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_dump_heap, container, false);
        mJavaHeapTv = view.findViewById(R.id.tv_java_heap);
        mNativeHeapTv = view.findViewById(R.id.tv_native_heap);

        mJavaHeapTv.setOnClickListener(this);
        mNativeHeapTv.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_java_heap:
                dumpJavaHeap();
                break;
            case R.id.tv_native_heap:
                dumpNativeHeap();
                break;
        }
    }

    private void dumpNativeHeap() {
        try {
            Method method =
                Debug.class.getDeclaredMethod("dumpNativeHeap", FileDescriptor.class);
            method.setAccessible(true);
            File file = new File(getContext().getExternalCacheDir(), "native.heap");
            if (!file.exists()) file.createNewFile();
            FileDescriptor fd = new FileOutputStream(file).getFD();
            method.invoke(null, fd);
            Toast.makeText(getContext(), "dump native heap success", Toast.LENGTH_SHORT).show();
        } catch (NoSuchMethodException | FileNotFoundException e) {
            Log.e(TAG, "", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "", e);
        } catch (IOException e) {
            Log.e(TAG, "", e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "", e);
        }
    }

    private void dumpJavaHeap() {
        File file = new File(getContext().getExternalCacheDir(), "demo.hprof");
        if (!file.exists()) {
            try {
                file.createNewFile();
                Debug.dumpHprofData(file.getAbsolutePath());
                Toast.makeText(getContext(), "dump java heap success", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.e(TAG, "", e);
            }
        }
    }
}
