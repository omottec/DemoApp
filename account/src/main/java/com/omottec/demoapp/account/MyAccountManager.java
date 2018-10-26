package com.omottec.demoapp.account;

import android.util.Log;

import com.omottec.demoapp.service.spi.account.Account;
import com.omottec.demoapp.service.spi.account.AccountListener;
import com.omottec.demoapp.service.spi.account.AccountManager;
import com.omottec.demoapp.service.spi.account.AccountParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 26/10/2018.
 */

public class MyAccountManager implements AccountManager {
    private static final String TAG = "MyAccountManager";
    private volatile boolean mIsLogin;
    private List<AccountListener> mListeners = new ArrayList<>();

    @Override
    public void init(AccountParam param) {
        Log.i(TAG, "init param:" + param);
    }

    @Override
    public void login() {
        Log.i(TAG, "login");
        mIsLogin = true;
        dispatchLogin();
    }

    private void dispatchLogin() {
        Log.i(TAG, "dispatchLogin");
        synchronized (mListeners) {
            for (AccountListener listener : mListeners)
                try {
                    listener.onLogin(getAccount());
                } catch (Throwable t) {
                    Log.e(TAG, listener + " throw exception onLogin", t);
                }
        }
    }

    @Override
    public void logout() {
        Log.i(TAG, "logout");
        mIsLogin = false;
        dispatchLogout();
    }

    private void dispatchLogout() {
        Log.i(TAG, "dispatchLogout");
        synchronized (mListeners) {
            for (AccountListener listener : mListeners)
                try {
                    listener.onLogout();
                } catch (Throwable t) {
                    Log.e(TAG, listener + " throw exception onLogout", t);
                }
        }
    }

    @Override
    public void addListener(AccountListener listener) {
        Log.i(TAG, "addListener:" + listener);
        synchronized (mListeners) {
            if (mListeners.contains(listener)) return;
            mListeners.add(listener);
        }
    }

    @Override
    public void removeListener(AccountListener listener) {
        Log.i(TAG, "removeListener:" + listener);
        synchronized (mListeners) {
            mListeners.remove(listener);
        }
    }

    @Override
    public boolean isLogin() {
        Log.i(TAG, "isLogin:" + mIsLogin);
        return mIsLogin;
    }

    @Override
    public Account getAccount() {
        Log.i(TAG, "getAccount");
        return new Account();
    }
}
