package com.omottec.demoapp.service.spi;

import com.omottec.demoapp.service.spi.account.AccountManager;
import com.omottec.demoapp.service.spi.account.AccountParam;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by qinbingbing on 26/10/2018.
 */

public class ServiceProvider {
    private ServiceProvider() {};

    private static AccountManager sAccountManager;

    public static void loadService() {
        ServiceLoader<AccountManager> accountManagerLoader = ServiceLoader.load(AccountManager.class);
        Iterator<AccountManager> accountManagerIterator = accountManagerLoader.iterator();
        if (accountManagerIterator.hasNext()) {
            sAccountManager = accountManagerIterator.next();
            ServiceLoader<AccountParam> accountParamLoader = ServiceLoader.load(AccountParam.class);
            Iterator<AccountParam> accountParamIterator = accountParamLoader.iterator();
            if (accountParamIterator.hasNext()) {
                sAccountManager.init(accountParamIterator.next());
            }
        }
    }

    public static AccountManager getAccountManager() {
        return sAccountManager;
    }
}
