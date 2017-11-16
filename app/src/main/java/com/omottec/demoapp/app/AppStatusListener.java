package com.omottec.demoapp.app;

/**
 * Created by qinbingbing on 16/11/2017.
 */

public interface AppStatusListener {
    void onEnterBackground();

    void onEnterForeground();
}
