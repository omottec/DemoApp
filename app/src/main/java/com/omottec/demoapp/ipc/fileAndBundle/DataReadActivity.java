package com.omottec.demoapp.ipc.fileAndBundle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.io.IoUtils;
import com.omottec.demoapp.ipc.Constants;
import com.omottec.demoapp.ipc.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qinbingbing on 9/8/16.
 */
public class DataReadActivity extends FragmentActivity {
    private TextView mTv;
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setText("DataReadActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        User user = bundle.getParcelable(Constants.KEY_USER);
        Log.d(Tag.IPC_FILE_BUNDLE, "receive intent:" + intent.toString() + "@" + intent.hashCode());
        Log.d(Tag.IPC_FILE_BUNDLE, "receive bundle:" + bundle.toString() + "@" + bundle.hashCode());
        Log.d(Tag.IPC_FILE_BUNDLE, "read user from bundle:" + user);
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                File file = new File(getFilesDir(), Constants.FILE_USERS);
                if (!file.exists()) return;
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(new FileInputStream(file));
                    User fileUser = (User) ois.readObject();
                    Log.d(Tag.IPC_FILE_BUNDLE, "read user from file:" + fileUser);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    IoUtils.close(ois);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutor.shutdownNow();
    }
}
