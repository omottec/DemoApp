package com.omottec.demoapp.ipc.binderPool;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.omottec.demoapp.Constants;
import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.aidl.ICompute;
import com.omottec.demoapp.aidl.ISecurityCenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qinbingbing on 9/14/16.
 */
public class BinderPoolActivity extends FragmentActivity {
    private TextView mTv;
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private BinderPool mBinderPool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag.IPC_BINDER_POOL, "BinderPoolActivity.onCreate");
        setContentView(R.layout.full_screen_text);
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setText("BinderPoolActivity");
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                mBinderPool = BinderPool.getInstance(getApplicationContext());
                mExecutor.submit(new ComputeTask());
                mExecutor.submit(new SecurityTask());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag.IPC_BINDER_POOL, "BinderPoolActivity.onDestroy");
        mExecutor.shutdownNow();
        mBinderPool.destroy();
    }

    private class ComputeTask implements Runnable {

        @Override
        public void run() {
            IBinder binder = mBinderPool.queryBinder(Constants.BINDER_CODE_COMPUTE);
            ICompute compute = ICompute.Stub.asInterface(binder);
            try {
                int sum = compute.add(3, 4);
                Log.d(Tag.IPC_BINDER_POOL, "ComputeTask.run success return:" + sum);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private class SecurityTask implements Runnable {

        @Override
        public void run() {
            IBinder binder = mBinderPool.queryBinder(Constants.BINDER_CODE_SECURITY_CENTER);
            ISecurityCenter securityCenter = ISecurityCenter.Stub.asInterface(binder);
            String plainText = "This is a plain text";
            Log.d(Tag.IPC_BINDER_POOL, "plain text:" + plainText);
            try {
                String cipherText = securityCenter.encrypt(plainText);
                Log.d(Tag.IPC_BINDER_POOL, "encrypt plain text:" + cipherText);
                plainText = securityCenter.decrypt(cipherText);
                Log.d(Tag.IPC_BINDER_POOL, "decrypt cypher text:" + plainText);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
