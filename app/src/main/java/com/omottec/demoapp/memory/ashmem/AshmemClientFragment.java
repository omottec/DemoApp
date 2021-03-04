package com.omottec.demoapp.memory.ashmem;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.aidl.ashmem.IAshmemService;
import com.omottec.logger.Logger;

public class AshmemClientFragment extends Fragment implements View.OnClickListener {
    private EditText mEt;
    private TextView mClearTv;
    private TextView mReadTv;
    private TextView mWriteTv;
    private IAshmemService mAshmemService;
    private ServiceConnection mServiceConnection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_ashmem_client, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mEt = view.findViewById(R.id.et);
        mClearTv = view.findViewById(R.id.tv_clear);
        mReadTv = view.findViewById(R.id.tv_read);
        mWriteTv = view.findViewById(R.id.tv_write);

        mClearTv.setOnClickListener(this);
        mReadTv.setOnClickListener(this);
        mWriteTv.setOnClickListener(this);

        Intent intent = new Intent(getContext(), AshmemServer.class);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Logger.i(Tag.ASHMEM, "ComponentName:" + name + ", IBinder:" + service);
                mAshmemService = IAshmemService.Stub.asInterface(service);
                Logger.i(Tag.ASHMEM, "mAshmemService:" + mAshmemService);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Logger.i(Tag.ASHMEM, "ComponentName:" + name);
            }
        };
        getContext().bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear:
                onClickClear();
                break;
            case R.id.tv_read:
                onClickRead();
                break;
            case R.id.tv_write:
                onClickWrite();
                break;
        }
    }

    private void onClickWrite() {
        try {
            mAshmemService.writeInt(Integer.valueOf(mEt.getText().toString()));
        } catch (RemoteException e) {
            Logger.e(Tag.ASHMEM, e);
        }
    }

    private void onClickRead() {
        try {
            mEt.setText("" + mAshmemService.readInt());
        } catch (RemoteException e) {
            Logger.e(Tag.ASHMEM, e);
        }
    }

    private void onClickClear() {
        mEt.setText("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unbindService(mServiceConnection);
    }
}
