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
import com.omottec.demoapp.utils.Logger;

import java.security.Permission;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by qinbingbing on 9/9/16.
 */
public class BookManagerService extends Service {
    private CopyOnWriteArrayList<Book> mBooks = new CopyOnWriteArrayList<>();
//    private CopyOnWriteArrayList<IOnBookAddedListener> mBookAddedListeners = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnBookAddedListener> mBookAddedListeners = new RemoteCallbackList<>();
    private ScheduledExecutorService mExecutor = Executors.newScheduledThreadPool(1);

    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Log.d(Tag.IPC_AIDL, "onTransact");
            Logger.logThread(Tag.IPC_AIDL);
            int permission = checkCallingOrSelfPermission("com.omottec.demoapp.permission.ACCESS_BOOK_SERVICE");
            if (permission == PackageManager.PERMISSION_DENIED) {
                Log.d(Tag.IPC_AIDL, "permission == PackageManager.PERMISSION_DENIED");
                return false;
            }
            String[] packagesForUid = getPackageManager().getPackagesForUid(getCallingUid());
            if (packagesForUid == null
                    || packagesForUid.length <= 0
                    || !packagesForUid[0].startsWith("com.omottec")) {
                Log.d(Tag.IPC_AIDL, "packageName for calling uid is not starts with com.omottec");
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            Log.d(Tag.IPC_AIDL, "BookManagerService.getBooks");
            Logger.logThread(Tag.IPC_AIDL);
            SystemClock.sleep(15000);
            return mBooks;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d(Tag.IPC_AIDL, "BookManagerService.addBook");
            Logger.logThread(Tag.IPC_AIDL);
            mBooks.add(book);
        }

        @Override
        public void registerListener(IOnBookAddedListener listener) throws RemoteException {
            Log.d(Tag.IPC_AIDL, "BookManagerService.registerListener");
            Logger.logThread(Tag.IPC_AIDL);
            /*if (!mBookAddedListeners.contains(listener))
                mBookAddedListeners.add(listener);
            else
                Log.d(Tag.IPC_AIDL, "listener already exists");
            Log.d(Tag.IPC_AIDL, "registerListener, size:" + mBookAddedListeners.size());*/
            mBookAddedListeners.register(listener);
            int i = mBookAddedListeners.beginBroadcast();
            mBookAddedListeners.finishBroadcast();
            Log.d(Tag.IPC_AIDL, "registerListener, size:" + i);
        }

        @Override
        public void unregisterListener(IOnBookAddedListener listener) throws RemoteException {
            Log.d(Tag.IPC_AIDL, "BookManagerService.unregisterListener");
            Logger.logThread(Tag.IPC_AIDL);
//            boolean remove = mBookAddedListeners.remove(listener);
//            Log.d(Tag.IPC_AIDL, "unregisterListener remove:" + remove + ", size:" + mBookAddedListeners.size());
            boolean unregister = mBookAddedListeners.unregister(listener);
            int i = mBookAddedListeners.beginBroadcast();
            mBookAddedListeners.finishBroadcast();
            Log.d(Tag.IPC_AIDL, "unregisterListener, unregister:" + unregister + ", size:" + i);
        }
    };

    @Override
    public void onCreate() {
        Log.d(Tag.IPC_AIDL, "BookManagerService.onCreate");
        Logger.logThread(Tag.IPC_AIDL);
        mBooks.add(new Book(1, "007"));
        mBooks.add(new Book(2, "Bourne Identity"));
        mExecutor.scheduleWithFixedDelay(new AddBookTask(), 5000, 5000, TimeUnit.MILLISECONDS);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Tag.IPC_AIDL, "BookManagerService.onBind");
        Logger.logThread(Tag.IPC_AIDL);
        int permission = checkCallingOrSelfPermission("com.omottec.demoapp.permission.ACCESS_BOOK_SERVICE");
        if (permission == PackageManager.PERMISSION_DENIED) {
            Log.d(Tag.IPC_AIDL, "permission == PackageManager.PERMISSION_DENIED");
            return null;
        }
        return mBinder;
    }

    @Override
    public void onDestroy() {
        Log.d(Tag.IPC_AIDL, "BookManagerService.onDestroy");
        Logger.logThread(Tag.IPC_AIDL);
        mExecutor.shutdownNow();
    }

    private class AddBookTask implements Runnable {

        @Override
        public void run() {
            Log.d(Tag.IPC_AIDL, "AddBookTask run");
            int id = mBooks.size() + 1;
            Book book = new Book(id, "24 hours #" + id);
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
}
