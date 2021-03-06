package com.omottec.demoapp.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by qinbingbing on 07/06/2017.
 */

public final class MobileUtils {
    private MobileUtils() {}

    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
