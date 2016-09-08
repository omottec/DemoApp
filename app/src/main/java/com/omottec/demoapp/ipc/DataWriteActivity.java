package com.omottec.demoapp.ipc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.io.IoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qinbingbing on 9/8/16.
 */
public class DataWriteActivity extends FragmentActivity {
    private TextView mTv;
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setText("DataWriteActivity");
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User bond = new User(2, "James Bond", true);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(DataWriteActivity.this, DataReadActivity.class);
                bundle.putParcelable(Constants.KEY_USER, bond);
                intent.putExtras(bundle);
                Log.d(Tag.IPC_FILE_BUNDLE, "send intent:" + intent.toString() + "@" + intent.hashCode());
                Log.d(Tag.IPC_FILE_BUNDLE, "send bundle:" + bundle.toString() + "@" + bundle.hashCode());
                Log.d(Tag.IPC_FILE_BUNDLE, "write user to bundle:" + bond);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                User bourne = new User(1, "Jason Bourne", true);
                File file = new File(getFilesDir(), Constants.FILE_USERS);
                if (!file.exists()) {
                    Log.d(Tag.IPC_FILE_BUNDLE, "file not exists");
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(new FileOutputStream(file));
                    oos.writeObject(bourne);
                    Log.d(Tag.IPC_FILE_BUNDLE, "write user to file:" + bourne);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    IoUtils.close(oos);
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
