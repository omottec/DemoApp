package com.omottec.demoapp.utils;

import com.omottec.logger.Logger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by omottec on 02/01/2018.
 */

public class TimeLogger {
    public static final String TAG = "TimeLogger";
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    private static List<String> sTimePointer = new ArrayList<>();
    private static List<Long> sTime = new ArrayList<>();
    private TimeLogger() {}

    public static void methodStart() {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] elements = new Throwable().fillInStackTrace().getStackTrace();
        if (elements != null && elements.length >= 2 && elements[1] != null) {
            Date date = new Date();
            sTime.add(date.getTime());
            sb.append(elements[1].getClassName())
                    .append("#")
                    .append(elements[1].getMethodName())
                    .append(" start")
                    .append(" ").append(sDateFormat.format(date));
            sTimePointer.add(sb.toString());
        }
    }

    public static void methodEnd() {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] elements = new Throwable().fillInStackTrace().getStackTrace();
        if (elements != null && elements.length >= 2 && elements[1] != null) {
            Date date = new Date();
            sTime.add(date.getTime());
            sb.append(elements[1].getClassName())
                    .append("#")
                    .append(elements[1].getMethodName())
                    .append(" end")
                    .append(" ").append(sDateFormat.format(date));
            sTimePointer.add(sb.toString());
        }
    }

    public static void addTimePoint(String timePointer) {
        Date date = new Date();
        sTime.add(date.getTime());
        StringBuilder sb = new StringBuilder();
        sb.append(timePointer)
                .append(" ").append(sDateFormat.format(date));
        sTimePointer.add(sb.toString());
    }

    public static void dump() {
        for (int i = 0; i < sTime.size(); i++) {
            StringBuilder sb = new StringBuilder(sTimePointer.get(i));
            if (i > 0)
                sb.append(" delta:").append(sTime.get(i) - sTime.get(i-1))
                        .append(" total:").append(sTime.get(i) - sTime.get(0));
            Logger.d(TAG, sb.toString());
        }
        sTime.clear();
        sTimePointer.clear();
    }
}
