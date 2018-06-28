package com.omottec.demoapp.permission;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.push.NotificationHandler;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 28/06/2018.
 */

public class NotificationFragment extends Fragment {
    public static final String TAG = "NotificationFragment";
    private int mId;
    private int mTitleId;
    private int mBodyId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView tv = view.findViewById(R.id.tv);
        tv.setText("NotificationFragment");
        tv.setOnClickListener(v -> {
            Logger.d(TAG, "onClick showNotification");
            boolean notificationEnabled = Permissions.isNotificationEnabled();
            Logger.d(TAG, "notificationEnabled:" + notificationEnabled);
            if (notificationEnabled) {
                NotificationHandler.INSTANCE.showNotification(mId++,
                        "title" + (mTitleId++),
                        "body" + (mBodyId++),
                        null);
            } else {
                Permissions.showPermissionSettingDialog(getActivity(), "权限申请", "请允许通知", true,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Permissions.startPermissionSetting(getActivity());
                            }
                });
            }
        });
    }
}
