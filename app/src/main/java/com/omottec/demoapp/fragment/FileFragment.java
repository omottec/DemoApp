package com.omottec.demoapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.omottec.demoapp.R;
import com.omottec.demoapp.io.IoUtils;
import com.omottec.logger.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileFragment extends Fragment {
    public static final String TAG = "FileFragment";
    private ExecutorService mExecutor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mExecutor = Executors.newCachedThreadPool();

        File textDir = new File(view.getContext().getFilesDir(), "textDir");
        Logger.i(TAG, "textDir.delete:" + textDir.delete());

        logDir(textDir);
        logDir(textDir.getParentFile());

        if (!textDir.exists()) {
            textDir.mkdirs();
            File test = new File(textDir, "test");
            createFile(test, 1024);
        }

        File testFile = new File(view.getContext().getFilesDir(), "testFile");
        Logger.i(TAG, "testFile.delete:" + testFile.delete());
        //String filePath = null;
        //File testFile = new File(filePath);
        logFile(testFile);
        if (!testFile.exists())
            createFile(testFile, 2048);
    }

    private void logDir(File dir) {
        Logger.i(TAG, "dir.getAbsolutePath:" + dir.getAbsolutePath());
        Logger.i(TAG, "dir.exists:" + dir.exists());
        Logger.i(TAG, "dir.isDirector:" + dir.isDirectory());
        Logger.i(TAG, "dir.length:" + dir.length());
        Logger.i(TAG, "dir.listFiles:" + Arrays.toString(dir.listFiles()));
    }

    private void logFile(File file) {
        Logger.i(TAG, "file.getAbsolutePath:" + file.getAbsolutePath());
        Logger.i(TAG, "file.exists:" + file.exists());
        Logger.i(TAG, "file.isFile:" + file.isFile());
        Logger.i(TAG, "file.length:" + file.length());
        Logger.i(TAG, "file.listFiles:" + Arrays.toString(file.listFiles()));
    }

    private void createFile(File file, int size) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                FileOutputStream os = null;
                try {
                    os = new FileOutputStream(file);
                    os.write(new byte[size]);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    IoUtils.close(os);
                }
            }
        });
    }
}
