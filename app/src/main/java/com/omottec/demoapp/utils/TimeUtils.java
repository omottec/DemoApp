package com.omottec.demoapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qinbingbing on 9/13/16.
 */
public final class TimeUtils {
    private TimeUtils() {}

    public static String getCurHms() {
        return new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
    }
}
