package com.omottec.demoapp.cpu;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.omottec.demoapp.io.IoUtils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CpuUtils {
    public static final String TAG = "CpuUtils";
    private CpuUtils() {}

    private static Boolean sApp64;
    private static Boolean sDevice64;

    public static boolean isDevice64() {
        if (sDevice64 != null) return sDevice64;
        String abi;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            abi = Arrays.toString(Build.SUPPORTED_ABIS);
        else
            abi = Build.CPU_ABI;
        Log.i(TAG, "abi:" + abi);
        sDevice64 = !TextUtils.isEmpty(abi) && abi.contains("64");
        return sDevice64;
    }

    public static boolean isDevice64ByBuild() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return false;
        Log.i(TAG, "ro.product.cpu.abilist64:" + Arrays.toString(Build.SUPPORTED_64_BIT_ABIS));
        Log.i(TAG, "ro.product.cpu.abilist32:" + Arrays.toString(Build.SUPPORTED_32_BIT_ABIS));
        Log.i(TAG, "ro.product.cpu.abilist:" + Arrays.toString(Build.SUPPORTED_ABIS));
        Log.i(TAG, "Build.CPU_ABI:" + Build.CPU_ABI);
        Log.i(TAG, "Build.CPU_ABI2:" + Build.CPU_ABI2);

        return Build.SUPPORTED_64_BIT_ABIS.length > 0;
    }

    public static boolean isDevice64ByProc() {
        boolean device64 = false;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String processorLine = reader.readLine();
            Log.i(TAG, "/proc/cpuinfo first line:" + processorLine);
            if (processorLine.toLowerCase().contains("aarch64"))
                device64 = true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "", e);
        } catch (IOException e) {
            Log.e(TAG, "", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "", e);
                }
            }
        }
        return device64;
    }

    public static boolean isDevice64ByAppInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        boolean device64 = false;
        try {
            ApplicationInfo applicationInfo =
                packageManager.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            Field primaryCpuAbi = ApplicationInfo.class.getDeclaredField("primaryCpuAbi");
            primaryCpuAbi.setAccessible(true);
            Object primaryCpuAbiValue = primaryCpuAbi.get(applicationInfo);
            Log.i(TAG, "primaryCpuAbiValue:" + primaryCpuAbiValue);
            if (primaryCpuAbiValue instanceof String)
                device64 = ((String) primaryCpuAbiValue).toLowerCase().contains("64");

            Field secondaryCpuAbi = ApplicationInfo.class.getDeclaredField("secondaryCpuAbi");
            secondaryCpuAbi.setAccessible(true);
            Object secondaryCpuAbiValue = secondaryCpuAbi.get(applicationInfo);
            Log.i(TAG, "secondaryCpuAbiValue:" + secondaryCpuAbiValue);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "", e);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "", e);
        }
        return device64;
    }

    public static boolean isApp64(Context context) {
        if (sApp64 != null) return sApp64;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "Process.is64Bit():" + Process.is64Bit());
            sApp64 = Process.is64Bit();
        } else {
            String nativeLibraryDir = context.getApplicationInfo().nativeLibraryDir;
            Log.i(TAG, "ApplicationInfo.nativeLibraryDir:" + nativeLibraryDir);
            sApp64 = !TextUtils.isEmpty(nativeLibraryDir) && nativeLibraryDir.endsWith("arm64");
        }
        return sApp64;
    }

    public static boolean isApp64ByProcess() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;
        boolean app64 = Process.is64Bit();
        Log.i(TAG, "Process.is64Bit():" + app64);
        return app64;
    }

    public static boolean isApp64ByAppInfo(Context context) {
        String nativeLibraryDir = context.getApplicationInfo().nativeLibraryDir;
        Log.i(TAG, "ApplicationInfo.nativeLibraryDir:" + nativeLibraryDir);
        return !TextUtils.isEmpty(nativeLibraryDir) && nativeLibraryDir.endsWith("arm64");
    }

    public static boolean isApp64ByClassLoader() {
        boolean app64 = false;
        ClassLoader classLoader = Context.class.getClassLoader();
        Class<?> clazz = ClassLoader.class;
        try {
            Method method = clazz.getDeclaredMethod("findLibrary", String.class);
            method.setAccessible(true);
            Object object = method.invoke(classLoader, "art");
            Log.i(TAG, "libart.so path:" + object);
            if (object != null && ((String) object).contains("lib64")) {
                app64 = true;
            }
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "", e);
        } catch (IllegalAccessException e) {
            Log.i(TAG, "", e);
        } catch (InvocationTargetException e) {
            Log.i(TAG, "", e);
        }
        return app64;
    }

    public static boolean isApp64BySystem() {
        String arch = System.getProperty("os.arch");
        Log.i(TAG, "System.getProperty(\"os.arch\"):" + arch);
        if (!TextUtils.isEmpty(arch) && arch.contains("64"))
            return true;
        else
            return false;
    }


}
