package com.omottec.demoapp.ipc.aidl;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.aidl.Book;
import com.omottec.demoapp.aidl.IBookManager;
import com.omottec.demoapp.aidl.IOnBookAddedListener;
import com.omottec.demoapp.Constants;
import com.omottec.demoapp.utils.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qinbingbing on 9/9/16.
 */
public class BookManagerActivity extends FragmentActivity {
    private TextView mTv;
    private ExecutorService mExecutor = Executors.newCachedThreadPool();
    private IBookManager mBookManager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_BOOK_ADDED:
                    Log.d(Tag.IPC_AIDL, Logger.getThreadInfo() + " handleMessage add new book:" + msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private IOnBookAddedListener mOnBookAddedListener = new IOnBookAddedListener.Stub() {
        @Override
        public void onBookAdded(Book book) throws RemoteException {
            Log.d(Tag.IPC_AIDL, Logger.getThreadInfo() + " BookManagerActivity.onBookAdded:" + book);
            mHandler.obtainMessage(Constants.MSG_BOOK_ADDED, book).sendToTarget();
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(Tag.IPC_AIDL, "binderDied");
            Logger.logThread(Tag.IPC_AIDL);
            if (mBookManager == null) return;
            mBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBookManager = null;
            bindService();
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(Tag.IPC_AIDL, Logger.getThreadInfo() + " onServiceConnected ComponentName:" + name
                    + ", IBinder:" + service);
            mBookManager = IBookManager.Stub.asInterface(service);
            Log.d(Tag.IPC_AIDL, "IBookManager:" + mBookManager);
            try {
//                mBookManager.asBinder().linkToDeath(null, 0);
                mBookManager.registerListener(mOnBookAddedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Book> books = mBookManager.getBooks();
                        Log.d(Tag.IPC_AIDL, "query books, list type:"
                                + books.getClass().getCanonicalName()
                                + ", list:" + books);
                        Book book = new Book(books.size() + 1, "Mission Impossible");
                        Log.d(Tag.IPC_AIDL, "add book:" + book);
                        mBookManager.addBook(book);
                        books = mBookManager.getBooks();
                        Log.d(Tag.IPC_AIDL, "query books:" + books);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(Tag.IPC_AIDL, "onServiceDisconnected ComponentName:" + name);
            Logger.logThread(Tag.IPC_AIDL);
            bindService();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag.IPC_AIDL, "BookManagerActivity.onCreate");
        setContentView(R.layout.full_screen_text);
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setText("BookManagerActivity");
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });
        bindService();
    }

    private void bindService() {
        Log.d(Tag.IPC_AIDL, "bindService");
        Intent intent = new Intent(getApplicationContext(), BookManagerService.class);
        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag.IPC_AIDL, "BookManagerActivity.onDestroy");
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            try {
                mBookManager.unregisterListener(mOnBookAddedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
        mExecutor.shutdownNow();
        mHandler.removeCallbacksAndMessages(null);
    }
}
