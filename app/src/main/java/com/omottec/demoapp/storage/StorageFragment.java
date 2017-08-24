package com.omottec.demoapp.storage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.io.IoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by omottec on 01/08/2017.
 */

public class StorageFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "StorageFragment";
    public static final String APP_UPDATE_FILE = "appupdate.bat";
    private Button mCreateFileBtn;
    private Button mDeleteFileBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_storage, container, false);
        mCreateFileBtn = (Button) view.findViewById(R.id.btn_create_file);
        mDeleteFileBtn = (Button) view.findViewById(R.id.btn_delete_file);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCreateFileBtn.setOnClickListener(this);
        mDeleteFileBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_file:
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(new FileOutputStream(
                            new File(MyApplication.getContext().getFilesDir(), APP_UPDATE_FILE)));
                    oos.writeObject("");
                } catch (IOException e) {
                    Log.e(TAG, "write obj exception", e);
                } finally {
                    Toast.makeText(MyApplication.getContext(),
                            "file create " + new File(MyApplication.getContext().getFilesDir(), APP_UPDATE_FILE).exists(),
                            Toast.LENGTH_SHORT).show();
                    IoUtils.close(oos);
                }
                break;
            case R.id.btn_delete_file:
                boolean delete = new File(MyApplication.getContext().getFilesDir(), APP_UPDATE_FILE).delete();
                Toast.makeText(MyApplication.getContext(),
                        "file delete " + delete,
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
