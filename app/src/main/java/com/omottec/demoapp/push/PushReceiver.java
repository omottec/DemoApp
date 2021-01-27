package com.omottec.demoapp.push;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.task.MainActivity;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 07/06/2017.
 */

public class PushReceiver extends BroadcastReceiver {
    public static final String ACTION_PUSH_MSG = "com.omottec.demoapp.intent.action_PUSH_MSG";
    public static final String EXTRA_CONTENT_TITLE = "com.omottec.demoapp.intent.extra_CONTENT_TITLE";
    public static final String EXTRA_CONTENT_TEXT = "com.omottec.demoapp.intent.extra_CONTENT_TEXT";
    public static final String EXTRA_MSG_ID = "com.omottec.demoapp.intent.extra_MSG_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case ACTION_PUSH_MSG:
                Logger.d(Tag.PUSH, "onReceive msg");
                processPushMsg(context, intent);
                break;
        }
    }

    private void processPushMsg(Context context, Intent intent) {
        int notificationId = intent.getIntExtra(EXTRA_MSG_ID, 0);
        String contentTitle = intent.getStringExtra(EXTRA_CONTENT_TITLE);
        String contentText = intent.getStringExtra(EXTRA_CONTENT_TEXT);
        Intent routeIntent = new Intent(context, MainActivity.class);
        routeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent activityIntent = PendingIntent.getActivity(context, notificationId, routeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationHandler.INSTANCE.showNotification(notificationId, contentTitle, contentText, activityIntent);
    }
}
