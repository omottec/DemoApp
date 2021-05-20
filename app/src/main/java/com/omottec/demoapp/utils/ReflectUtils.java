package com.omottec.demoapp.utils;

import android.util.Log;
import com.omottec.logger.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class ReflectUtils {
    public static final String TAG = "ReflectUtils";

    private ReflectUtils() {}

    public static Field getField(Class clazz, String name) throws NoSuchFieldException {
        try {
            Field field = clazz.getField(name);
            if (Modifier.isFinal(field.getModifiers()))
                field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            Logger.e(TAG, e);
            do {
                try {
                    Field field = clazz.getDeclaredField(name);
                    Log.i(TAG, "" + field);
                    Log.i(TAG, field.getDeclaringClass() + " is public:" + Modifier.isPublic(field.getDeclaringClass().getModifiers()));
                    if (!field.isAccessible())
                        field.setAccessible(true);
                    return field;
                } catch (NoSuchFieldException ignore) {
                    Logger.e(TAG, ignore);
                    clazz = clazz.getSuperclass();
                }
            } while (clazz != null);
            throw e;
        }
    }
}
