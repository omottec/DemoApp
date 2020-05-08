package com.omottec.demoapp.view.async;

import android.content.Context;
import android.view.View;

public final class AsyncViewLoader {
    private AsyncViewLoader() {}

    private static final class SingletonHolder {
        private static final AsyncViewLoader INSTANCE = new AsyncViewLoader();
    }

    public AsyncViewLoader getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public <T extends View> T getView(Context context, Class<T> viewClass, View parentView) {
        return null;
    }
}
