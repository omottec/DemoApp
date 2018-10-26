package com.omottec.demoapp.service.spi.account;

/**
 * Created by qinbingbing on 26/10/2018.
 */

public interface AccountManager {
    void init(AccountParam param);

    void login();

    void logout();

    void addListener(AccountListener listener);

    void removeListener(AccountListener listener);

    boolean isLogin();

    Account getAccount();
}
