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
import com.omottec.demoapp.touch.TouchActivity;
import com.omottec.demoapp.utils.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qinbingbing on 9/9/16.
 */
public class BookManagerActivity extends FragmentActivity implements View.OnClickListener {
    private TextView mQueryBookTv;
    private TextView mAddBookInTv;
    private TextView mAddBookOutTv;
    private TextView mAddBookInOutTv;
    private ExecutorService mExecutor = Executors.newCachedThreadPool();
    private IBookManager mBookManager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_BOOK_ADDED:
                    Logger.i(Tag.IPC_AIDL, "msg.obj:" + msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private IOnBookAddedListener mOnBookAddedListener = new IOnBookAddedListener.Stub() {
        @Override
        public void onBookAdded(Book book) throws RemoteException {
            Logger.i(Tag.IPC_AIDL, "book:" + book);
            mHandler.obtainMessage(Constants.MSG_BOOK_ADDED, book).sendToTarget();
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Logger.i(Tag.IPC_AIDL);
            if (mBookManager == null) return;
            mBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBookManager = null;
            bindService();
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.i(Tag.IPC_AIDL, "ComponentName:" + name
                    + ", IBinder:" + service);
            mBookManager = IBookManager.Stub.asInterface(service);
            Logger.i(Tag.IPC_AIDL, "IBookManager:" + mBookManager);
            try {
//                mBookManager.asBinder().linkToDeath(null, 0);
                mBookManager.registerListener(mOnBookAddedListener);
            } catch (RemoteException e) {
                Logger.e(Tag.IPC_AIDL, e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.i(Tag.IPC_AIDL, "ComponentName:" + name);
            bindService();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(Tag.IPC_AIDL);
        setContentView(R.layout.a_book_manager);
        mQueryBookTv = findViewById(R.id.tv_query_book);
        mAddBookInTv = findViewById(R.id.tv_add_book_in);
        mAddBookOutTv = findViewById(R.id.tv_add_book_out);
        mAddBookInOutTv = findViewById(R.id.tv_add_book_in_out);

        mQueryBookTv.setOnClickListener(this);
        mAddBookInTv.setOnClickListener(this);
        mAddBookOutTv.setOnClickListener(this);
        mAddBookInOutTv.setOnClickListener(this);

        bindService();
    }

    private void bindService() {
        Logger.i(Tag.IPC_AIDL);
        Intent intent = new Intent(getApplicationContext(), BookManagerService.class);
        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i(Tag.IPC_AIDL);
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            try {
                mBookManager.unregisterListener(mOnBookAddedListener);
            } catch (RemoteException e) {
                Logger.e(Tag.IPC_AIDL, e);
            }
        }
        unbindService(mServiceConnection);
        mExecutor.shutdownNow();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_query_book:
                queryBook();
                break;
            case R.id.tv_add_book_in:
                addBookIn();
                break;
            case R.id.tv_add_book_out:
                addBookOut();
                break;
            case R.id.tv_add_book_in_out:
                addBookInOut();
                break;
            default:
                break;
        }
    }

    private void addBookInOut() {
        Logger.i(Tag.IPC_AIDL);
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Book> books = mBookManager.getBooks();
                    Book book = new Book(books.size() + 1, "Client addBookInOut");
                    Logger.i(Tag.IPC_AIDL, "addBookInOut:" + book);
                    mBookManager.addBookInOut(book);
                    Logger.i(Tag.IPC_AIDL, "after addBookInOut:" + book);
                } catch (RemoteException e) {
                    Logger.e(Tag.IPC_AIDL, e);
                }
            }
        });
    }

    private void addBookOut() {
        Logger.i(Tag.IPC_AIDL);
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Book> books = mBookManager.getBooks();
                    Book book = new Book(books.size() + 1, "Client addBookOut");
                    Logger.i(Tag.IPC_AIDL, "addBookOut:" + book);
                    mBookManager.addBookOut(book);
                    Logger.i(Tag.IPC_AIDL, "after addBookOut:" + book);
                } catch (RemoteException e) {
                    Logger.e(Tag.IPC_AIDL, e);
                }
            }
        });
    }

    private void addBookIn() {
        Logger.i(Tag.IPC_AIDL);
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Book> books = mBookManager.getBooks();
                    Book book = new Book(books.size() + 1, "Client addBookIn");
                    Logger.i(Tag.IPC_AIDL, "addBookIn:" + book);
                    mBookManager.addBookIn(book);
                    Logger.i(Tag.IPC_AIDL, "after addBookIn:" + book);
                } catch (RemoteException e) {
                    Logger.e(Tag.IPC_AIDL, e);
                }
            }
        });
    }

    private void queryBook() {
        Logger.i(Tag.IPC_AIDL);
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Book> books = mBookManager.getBooks();
                    Logger.i(Tag.IPC_AIDL, "queryBook list type:"
                            + books.getClass().getCanonicalName()
                            + ", list:" + books);
                } catch (RemoteException e) {
                    Logger.e(Tag.IPC_AIDL, e);
                }
            }
        });
    }
}
