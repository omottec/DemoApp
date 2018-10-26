package com.omottec.demoapp.spi.account;

import com.omottec.demoapp.service.spi.account.AccountParam;

/**
 * Created by qinbingbing on 26/10/2018.
 */

public class MyAccountParam implements AccountParam {
    @Override
    public int versionCode() {
        return 10000;
    }

    @Override
    public String versionName() {
        return "1.0.0";
    }

    @Override
    public int appCode() {
        return 0;
    }

    @Override
    public String toString() {
        return new StringBuilder("{versionCode:")
                .append(versionCode())
                .append(", versionName:")
                .append(versionName())
                .append(", appCode:")
                .append(appCode())
                .append(", hashCode:")
                .append(hashCode())
                .append("}").toString();
    }
}
