package com.omottec.demoapp.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.aidl.Book;
import com.omottec.demoapp.aidl.IBookManager;
import com.omottec.demoapp.aidl.IOnBookAddedListener;

import com.omottec.logger.Logger;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qinbingbing on 9/9/16.
 */
public class BookManagerService extends Service {
    private CopyOnWriteArrayList<Book> mBooks = new CopyOnWriteArrayList<>();
//    private CopyOnWriteArrayList<IOnBookAddedListener> mBookAddedListeners = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnBookAddedListener> mBookAddedListeners = new RemoteCallbackList<>();
    private ExecutorService mExecutor = Executors.newCachedThreadPool();

    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Logger.i(Tag.IPC_AIDL);
            int permission = checkCallingOrSelfPermission("com.omottec.demoapp.permission.ACCESS_BOOK_SERVICE");
            if (permission == PackageManager.PERMISSION_DENIED) {
                Logger.i(Tag.IPC_AIDL, "permission == PackageManager.PERMISSION_DENIED");
                return false;
            }
            String[] packagesForUid = getPackageManager().getPackagesForUid(getCallingUid());
            if (packagesForUid == null
                    || packagesForUid.length <= 0
                    || !packagesForUid[0].startsWith("com.omottec")) {
                Logger.i(Tag.IPC_AIDL, "packageName for calling uid is not starts with com.omottec");
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            Logger.i(Tag.IPC_AIDL);
            SystemClock.sleep(15 * 1000);
            return mBooks;
        }

        @Override
        public void addBookIn(Book book) throws RemoteException {
            Logger.i(Tag.IPC_AIDL);
            mExecutor.submit(new AddBookTask(book));
        }

        @Override
        public void addBookOut(Book book) throws RemoteException {
            Logger.i(Tag.IPC_AIDL);
            mExecutor.submit(new AddBookTask(book));
        }

        @Override
        public void addBookInOut(Book book) throws RemoteException {
            Logger.i(Tag.IPC_AIDL);
            mExecutor.submit(new AddBookTask(book));
        }

        @Override
        public void registerListener(IOnBookAddedListener listener) throws RemoteException {
            Logger.i(Tag.IPC_AIDL);
            /*if (!mBookAddedListeners.contains(listener))
                mBookAddedListeners.add(listener);
            else
                Log.d(Tag.IPC_AIDL, "listener already exists");
            Log.d(Tag.IPC_AIDL, "registerListener, size:" + mBookAddedListeners.size());*/
            mBookAddedListeners.register(listener);
            int i = mBookAddedListeners.beginBroadcast();
            mBookAddedListeners.finishBroadcast();
            Logger.i(Tag.IPC_AIDL, "registerListener size:" + i);
        }

        @Override
        public void unregisterListener(IOnBookAddedListener listener) throws RemoteException {
            Logger.i(Tag.IPC_AIDL);
//            boolean remove = mBookAddedListeners.remove(listener);
//            Log.d(Tag.IPC_AIDL, "unregisterListener remove:" + remove + ", size:" + mBookAddedListeners.size());
            boolean unregister = mBookAddedListeners.unregister(listener);
            int i = mBookAddedListeners.beginBroadcast();
            mBookAddedListeners.finishBroadcast();
            Log.d(Tag.IPC_AIDL, "unregisterListener unregister:" + unregister + ", size:" + i);
        }
    };

    @Override
    public void onCreate() {
        Logger.i(Tag.IPC_AIDL);
        mBooks.add(new Book(1, "007"));
        mBooks.add(new Book(2, "Bourne Identity"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int permission = checkCallingOrSelfPermission("com.omottec.demoapp.permission.ACCESS_BOOK_SERVICE");
        if (permission == PackageManager.PERMISSION_DENIED) {
            Logger.i(Tag.IPC_AIDL, "permission == PackageManager.PERMISSION_DENIED");
            return null;
        }
        Logger.i(Tag.IPC_AIDL, "mBinder:" + mBinder);
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.i(Tag.IPC_AIDL);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Logger.i(Tag.IPC_AIDL);
        mExecutor.shutdownNow();
    }

    private class AddBookTask implements Runnable {
        private Book book;

        public AddBookTask(Book book) {
            this.book = book;
        }

        @Override
        public void run() {
            Logger.i(Tag.IPC_AIDL);
            book.bookName = "Server " + book.bookName;
            mBooks.add(book);
            int i = mBookAddedListeners.beginBroadcast();
            while (i-- > 0) {
                try {
                    mBookAddedListeners.getBroadcastItem(i).onBookAdded(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            mBookAddedListeners.finishBroadcast();
            /*for (IOnBookAddedListener listener : mBookAddedListeners)
                try {
                    listener.onBookAdded(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }*/
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Logger.i(Tag.IPC_AIDL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i(Tag.IPC_AIDL);
        return super.onStartCommand(intent, flags, startId);
    }
}
