package com.omottec.demoapp.spi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.R;
import com.omottec.demoapp.service.spi.ServiceProvider;
import com.omottec.demoapp.service.spi.account.Account;
import com.omottec.demoapp.service.spi.account.AccountListener;
import com.omottec.demoapp.service.spi.account.AccountManager;

/**
 * Created by qinbingbing on 26/10/2018.
 */

public class SpiFragment extends Fragment implements
        View.OnClickListener,
        AccountListener {
    private TextView mLoadServiceTv;
    private TextView mAccountTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_spi, container, false);
        mLoadServiceTv = view.findViewById(R.id.tv_load_service);
        mAccountTv = view.findViewById(R.id.tv_account);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLoadServiceTv.setOnClickListener(this);
        mAccountTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_load_service:
                loadService();
                break;
            case R.id.tv_account:
                onClickAccountTv();
                break;
        }
    }

    private void onClickAccountTv() {
        AccountManager accountManager = ServiceProvider.getAccountManager();
        if (accountManager == null) {
            Toast.makeText(getContext(), "please loadService first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (accountManager.isLogin()) {
            accountManager.logout();
        } else {
            accountManager.login();
        }
    }

    private void loadService() {
        ServiceProvider.loadService();
        AccountManager accountManager = ServiceProvider.getAccountManager();
        if (accountManager == null) {
            Toast.makeText(getContext(), "accountManager == null", Toast.LENGTH_SHORT).show();
            return;
        }
        accountManager.addListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AccountManager accountManager = ServiceProvider.getAccountManager();
        if (accountManager != null)
            accountManager.removeListener(this);
    }

    @Override
    public void onLogin(Account account) {
        mAccountTv.setText("Login\n" + account);
    }

    @Override
    public void onLogout() {
        mAccountTv.setText("Logout");
    }

    @Override
    public void onCancel() {

    }
}
