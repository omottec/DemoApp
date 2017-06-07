package com.omottec.demoapp.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.task.MainActivity;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 07/06/2017.
 */

public class PushReceiver extends BroadcastReceiver {
    public static final String ACTION_PUSH_MSG = "com.omottec.demoapp.intent.action_PUSH_MSG";
    public static final String EXTRA_CONTENT_TITLE = "com.omottec.demoapp.intent.extra_CONTENT_TITLE";
    public static final String EXTRA_CONTENT_TEXT = "com.omottec.demoapp.intent.extra_CONTENT_TEXT";
    public static final String EXTRA_MSG_ID = "com.omottec.demoapp.intent.extra_MSG_ID";
    private NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case ACTION_PUSH_MSG:
                Logger.d(Tag.PUSH, "onReceive msg");
                showNotification(context, intent);
                break;
        }
    }

    private void showNotification(Context context, Intent intent) {
        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = intent.getIntExtra(EXTRA_MSG_ID, 0);
        String contentTitle = intent.getStringExtra(EXTRA_CONTENT_TITLE);
        String contentText = intent.getStringExtra(EXTRA_CONTENT_TEXT);
        Notification notification = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setTicker(contentText)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();
        notification.defaults = Notification.DEFAULT_ALL;
        Intent routeIntent = new Intent(context, MainActivity.class);
        routeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent activityIntent = PendingIntent.getActivity(context, notificationId, routeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = activityIntent;
        mNotificationManager.notify(notificationId, notification);
    }
}
