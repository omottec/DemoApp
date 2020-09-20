package com.omottec.demoapp.cpu;

import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.omottec.demoapp.io.IoUtils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CpuUtils {
    public static final String TAG = "CpuUtils";
    private CpuUtils() {}

    public static boolean isDevice64() {
        boolean isDevice64 = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.i(TAG, Arrays.toString(Build.SUPPORTED_64_BIT_ABIS));
            isDevice64 = Build.SUPPORTED_64_BIT_ABIS.length > 0;
        } else {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader("/proc/cpuinfo"));
                String processorLine = reader.readLine();
                Log.i(TAG, "processorLine:" + processorLine);
                if (processorLine.toLowerCase().contains("aarch64"))
                    isDevice64 = true;
            } catch (FileNotFoundException e) {
                Log.i(TAG, "", e);
            } catch (IOException e) {
                Log.i(TAG, "", e);
            } finally {
                IoUtils.close(reader);
            }
        }
        return isDevice64;
    }

    public static boolean isApp64() {
        boolean isApp64 = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "Process.is64Bit:" + Process.is64Bit());
            isApp64 = Process.is64Bit();
        } else {
            ClassLoader classLoader = Context.class.getClassLoader();
            Class<?> clazz = ClassLoader.class;
            try {
                Method method = clazz.getDeclaredMethod("findLibrary", String.class);
                method.setAccessible(true);
                Object object = method.invoke(classLoader, "art");
                Log.i(TAG, "artPath:" + object);
                if (object != null && ((String) object).contains("lib64")) {
                    isApp64 = true;
                }
            } catch (NoSuchMethodException e) {
                Log.i(TAG, "", e);
            } catch (IllegalAccessException e) {
                Log.i(TAG, "", e);
            } catch (InvocationTargetException e) {
                Log.i(TAG, "", e);
            }

            if (!isApp64) {
                String arch = System.getProperty("os.arch");
                Log.i(TAG, "arch:" + arch);
                if (!TextUtils.isEmpty(arch) && arch.contains("64")) {
                    isApp64 = true;
                }
            }
        }
        return isApp64;
    }
}
