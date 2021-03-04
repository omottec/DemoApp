package com.omottec.demoapp.utils;

import androidx.core.app.NotificationManagerCompat;

import com.omottec.demoapp.app.MyApplication;

/**
 * Created by qinbingbing on 02/11/2017.
 */

public final class Notifications {
    private Notifications() { throw new AssertionError(); }

    public static boolean isNotificationAllowed() {
        return NotificationManagerCompat.from(MyApplication.getContext()).areNotificationsEnabled();
    }
}
