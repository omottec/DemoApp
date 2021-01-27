package com.omottec.demoapp.permission;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.app.MyApplication;

import com.omottec.logger.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by omottec on 14/06/2018.
 */

public final class Permissions {

    private Permissions() {}

    public static boolean permissionGranted(Context context, String... permissions) {
        Logger.d(Tag.PERMISSION, "permissionGranted permissions:" + Arrays.toString(permissions));
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) return true;
        for (String perm : permissions) {
            try {
                int selfPermission = PermissionChecker.checkSelfPermission(context, perm);
                Logger.d(Tag.PERMISSION, perm + ":" + selfPermission);
                if (selfPermission != PermissionChecker.PERMISSION_GRANTED) return false;
            } catch (Throwable t) {
                t.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static List<String> getDeniedPermissions(Context context, List<String> permissions) {
        Iterator<String> it = permissions.iterator();
        String perm;
        while (it.hasNext()) {
            perm = it.next();
            if (permissionGranted(context, perm)) it.remove();
        }
        return permissions;
    }

    public static void startPermissionSetting(Context context) {
        Logger.d(Tag.PERMISSION, "startPermissionSetting");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        try {
            context.startActivity(intent);
        } catch (Throwable t) {
            Logger.e(Tag.PERMISSION, "startPermissionSetting throwable", t);
        }
    }

    public static void showPermissionSettingDialog(Activity activity,
                                                   String title,
                                                   String msg,
                                                   boolean cancelable,
                                                   DialogInterface.OnClickListener onNegativeClickListener,
                                                   DialogInterface.OnClickListener onPositiveClickListener) {
        Logger.d(Tag.PERMISSION, "showPermissionSettingDialog");
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(cancelable)
                .setNegativeButton("取消", (dialog, which) -> {
                    onNegativeClickListener.onClick(dialog, which);
                })
                .setPositiveButton("去设置", (dialog, which) -> {
                    onPositiveClickListener.onClick(dialog, which);
                })
                .create()
                .show();
    }

    public static boolean isNotificationEnabled() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return true;
        Field opPostNotification = null;
        try {
            opPostNotification = AppOpsManager.class.getField("OP_POST_NOTIFICATION");
            opPostNotification.setAccessible(true);
            int opInt = (int) opPostNotification.get(int.class);
            Method checkOpNoThrow = AppOpsManager.class.getMethod("checkOpNoThrow", int.class, int.class, String.class);
            AppOpsManager appOpsManager = (AppOpsManager) MyApplication.getContext().getSystemService(Context.APP_OPS_SERVICE);
            int mode = (int) checkOpNoThrow.invoke(appOpsManager, opInt, Process.myUid(), MyApplication.getContext().getPackageName());
            return mode == AppOpsManager.MODE_ALLOWED;
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

}
