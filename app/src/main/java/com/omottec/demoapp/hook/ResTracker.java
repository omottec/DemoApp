package com.omottec.demoapp.hook;

import android.os.Build;
import android.util.Log;
import android.util.TypedValue;

import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.io.IoUtils;
import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.android.dexposed.XC_MethodHook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ResTracker {
    private ResTracker() { }

    private static final class SingletonHolder {
        private static final ResTracker INSTANCE = new ResTracker();
    }

    public static ResTracker getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static final String TAG = "ResTracker";

    private static Set<String> sResFiles = new CopyOnWriteArraySet<>();

    public void hookLoadRes() {
        int sdkInt = Build.VERSION.SDK_INT;
        Log.i(TAG, "hookLoadRes sdkInt:" + sdkInt);
        if (sdkInt <= Build.VERSION_CODES.KITKAT || sdkInt > Build.VERSION_CODES.O_MR1) return;
        hookResourcesImpl();
        hookAssetManager();
        hookSystem();
        hookRuntime();
        writeLoadedRes();
    }

    private void hookResourcesImpl() {
        try {
            Class<?> clazz = Class.forName("android.content.res.ResourcesImpl");
            DexposedBridge.findAndHookMethod(clazz,
                    "loadXmlResourceParser",
                    String.class,
                    int.class,
                    int.class,
                    String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            sResFiles.add(param.args[0].toString());
                            Log.i(TAG, "ResourcesImpl#loadXmlResourceParser file:" + param.args[0]
                                    + ", id:" + param.args[1]
                                    + ", assetCookie:" + param.args[2]
                                    + ", type:" + param.args[3]);
                        }
                    });

            DexposedBridge.hookAllMethods(clazz,
                    "loadDrawableForCookie",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            String value = ((TypedValue)param.args[1]).string.toString();
                            sResFiles.add(value);
                            Log.i(TAG, "ResourcesImpl#loadDrawableForCookie value:" + value + ", id:" + param.args[2]);
                        }
                    });

            DexposedBridge.hookAllMethods(clazz,
                    "openRawResource",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            String value = ((TypedValue)param.args[1]).string.toString();
                            sResFiles.add(value);
                            Log.i(TAG, "ResourcesImpl#openRawResource id:" + param.args[0] + ", value:" + value);
                        }
                    });
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ResourcesImpl NotFound", e);
        } catch (Throwable t) {
            Log.e(TAG, "Other Throwable", t);
        }
    }

    private void hookAssetManager() {
        try {
            Class<?> clazz = Class.forName("android.content.res.AssetManager");
            DexposedBridge.hookAllMethods(clazz,
                    "open",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            sResFiles.add("assets/" + param.args[0]);
                            Log.i(TAG, "AssetManager#open fileName:" + param.args[0]);
                        }
                    });
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "AssetManager NotFound", e);
        } catch (Throwable t) {
            Log.e(TAG, "Other Throwable", t);
        }
    }

    private void hookSystem() {
        try {
            Class<?> clazz = Class.forName("java.lang.System");
            DexposedBridge.hookAllMethods(clazz,
                    "load",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            sResFiles.add(param.args[0].toString());
                            Log.i(TAG, "System#load filename:" + param.args[0]);
                        }
                    });
            DexposedBridge.hookAllMethods(clazz,
                    "loadLibrary",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            sResFiles.add(param.args[0].toString());
                            Log.i(TAG, "System#loadLibrary libname:" + param.args[0]);
                        }
                    });
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "System NotFound", e);
        } catch (Throwable t) {
            Log.e(TAG, "Other Throwable", t);
        }
    }

    private void hookRuntime() {
        try {
            Class<?> clazz = Class.forName("java.lang.Runtime");
            DexposedBridge.findAndHookMethod(clazz,
                    "load",
                    String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            sResFiles.add(param.args[0].toString());
                            Log.i(TAG, "Runtime#load filename:" + param.args[0]);
                        }
                    });
            DexposedBridge.findAndHookMethod(clazz,
                    "load",
                    String.class,
                    ClassLoader.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            sResFiles.add(param.args[0].toString());
                            Log.i(TAG, "Runtime#load filename:" + param.args[0]);
                        }
                    });
            DexposedBridge.hookAllMethods(clazz, "load0",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            sResFiles.add(param.args[1].toString());
                            Log.i(TAG, "Runtime#load0 filename:" + param.args[1]);
                        }
                    });
            DexposedBridge.findAndHookMethod(clazz,
                    "loadLibrary",
                    String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            sResFiles.add(param.args[0].toString());
                            Log.i(TAG, "Runtime#loadLibrary libname:" + param.args[0]);
                        }
                    });
            DexposedBridge.findAndHookMethod(clazz,
                    "loadLibrary",
                    String.class,
                    ClassLoader.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            sResFiles.add(param.args[0].toString());
                            Log.i(TAG, "Runtime#loadLibrary libname:" + param.args[0]);
                        }
                    });
            DexposedBridge.hookAllMethods(clazz, "loadLibrary0",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            sResFiles.add(param.args[1].toString());
                            Log.i(TAG, "Runtime#loadLibrary0 libname:" + param.args[1]);
                        }
                    });
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Runtime NotFound", e);
        } catch (Throwable t) {
            Log.e(TAG, "Other Throwable", t);
        }
    }

    private void writeLoadedRes() {
        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "writeLoadedRes");
//                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "lark_launch_res");
                File file = new File(MyApplication.getContext().getFilesDir(), "lark_launch_res");
                BufferedWriter writer = null;
                try {
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    if (!file.exists())
                        file.createNewFile();
                    writer = new BufferedWriter(new FileWriter(file));
                    for (String resName : sResFiles) {
                        Log.i(TAG, resName);
                        writer.write(resName + '\n');
                    }
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    Log.e(TAG, "createNewFile IOException", e);
                } finally {
                    IoUtils.close(writer);
                }
            }
        }, 20, TimeUnit.SECONDS);
    }
}
