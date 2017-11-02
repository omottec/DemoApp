package com.omottec.demoapp.utils;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

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
