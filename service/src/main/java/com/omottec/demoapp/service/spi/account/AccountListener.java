package com.omottec.demoapp.service.spi.account;

/**
 * Created by qinbingbing on 26/10/2018.
 */

public interface AccountListener {
    void onLogin(Account account);

    void onLogout();

    void onCancel();
}
