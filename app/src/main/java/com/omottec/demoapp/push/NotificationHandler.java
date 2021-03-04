package com.omottec.demoapp.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;


/**
 * Created by qinbingbing on 07/06/2017.
 */

public enum  NotificationHandler {
    INSTANCE;

    private NotificationManager mNotificationManager = (NotificationManager) MyApplication.getContext()
            .getSystemService(Context.NOTIFICATION_SERVICE);

    public void showNotification(int id, CharSequence title, CharSequence text, PendingIntent contentIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getContext())
                .setAutoCancel(true)
                .setTicker(text)
                .setContentTitle(title)
                .setContentText(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder.setLargeIcon(BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.ic_launcher))
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setColor(Color.GRAY);
        else
            builder.setLargeIcon(BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.ic_launcher))
                    .setSmallIcon(R.drawable.ic_launcher);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_ALL;
        notification.contentIntent = contentIntent;
        mNotificationManager.notify(id, notification);
    }
}
