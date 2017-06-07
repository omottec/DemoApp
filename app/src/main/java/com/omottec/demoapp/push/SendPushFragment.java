package com.omottec.demoapp.push;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 07/06/2017.
 */

public class SendPushFragment extends Fragment {
    public static final String NOTIFICATION_TITLE_PREFIX = "Title:";
    public static final String NOTIFICATION_CONTENT_PREFIX = "Content:";
    private Button mSendPushBtn;
    private TextView mPushContentTv;
    private int mNotificationId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_send_push, container, false);
        mSendPushBtn = (Button) rootView.findViewById(R.id.btn_send_push);
        mPushContentTv = (TextView) rootView.findViewById(R.id.tv_push_content);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mSendPushBtn.setOnClickListener(v -> {
            mNotificationId++;
            String contentTitle = NOTIFICATION_TITLE_PREFIX + mNotificationId;
            String contentText = NOTIFICATION_CONTENT_PREFIX + mNotificationId;
            String content = new StringBuilder("NotificationId:")
                    .append(mNotificationId).append("\n")
                    .append(contentTitle).append("\n")
                    .append(contentText)
                    .toString();
            mPushContentTv.setText(content);
            Intent intent = new Intent(PushReceiver.ACTION_PUSH_MSG);
            Bundle extras = new Bundle();
            extras.putInt(PushReceiver.EXTRA_MSG_ID, mNotificationId);
            extras.putString(PushReceiver.EXTRA_CONTENT_TITLE, contentTitle);
            extras.putString(PushReceiver.EXTRA_CONTENT_TEXT, contentText);
            intent.putExtras(extras);
            Logger.d(Tag.PUSH, "send msg:" + content);
            getContext().sendBroadcast(intent);
        });
    }
}
