package com.omottec.demoapp.hook;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;

import android.widget.TextView;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.io.IoUtils;

import de.robv.android.xposed.DexposedBridge;
import de.robv.android.xposed.XC_MethodHook;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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

    private static Map<String, Pair<String, ReplaceScope>> sZhMap = new HashMap<>();

    private static Map<String, Pair<String, ReplaceScope>> sEnMap = new HashMap<>();

    private enum ReplaceScope {
        GLOBAL, LOCAL;
    }

    static {
        sZhMap.put("主体", new Pair<>("主体1", ReplaceScope.GLOBAL));
        sZhMap.put("全局替换的文案", new Pair<>("全局替换的文案1", ReplaceScope.GLOBAL));
        sZhMap.put("局部替换的文案", new Pair<>("局部替换的文案1", ReplaceScope.LOCAL));
        sZhMap.put("聊天", new Pair<>("聊天1", ReplaceScope.GLOBAL));
        sZhMap.put("通讯录", new Pair<>("通讯录1", ReplaceScope.GLOBAL));
        sZhMap.put("发现", new Pair<>("发现1", ReplaceScope.GLOBAL));
        sZhMap.put("我", new Pair<>("我1", ReplaceScope.GLOBAL));

        sEnMap.put("Body", new Pair<>("Body1", ReplaceScope.GLOBAL));
        sEnMap.put("Global Replace Item", new Pair<>("Global Replace Item1", ReplaceScope.GLOBAL));
        sEnMap.put("Local Replace Item", new Pair<>("Local Replace Item1", ReplaceScope.LOCAL));
        sEnMap.put("Chats", new Pair<>("Chats1", ReplaceScope.GLOBAL));
        sEnMap.put("Contacts", new Pair<>("Contacts1", ReplaceScope.GLOBAL));
        sEnMap.put("Discover", new Pair<>("Discover1", ReplaceScope.GLOBAL));
        sEnMap.put("Me", new Pair<>("Me1", ReplaceScope.GLOBAL));
    }

    public void hookLoadRes() {
        int sdkInt = Build.VERSION.SDK_INT;
        Log.i(TAG, "hookLoadRes sdkInt:" + sdkInt);
        //if (sdkInt <= Build.VERSION_CODES.KITKAT || sdkInt > Build.VERSION_CODES.O_MR1) return;
        //hookResourcesImpl();
        //hookAssetManager();
        //hookSystem();
        //hookRuntime();
        hookText();
        //writeLoadedRes();
    }

    private void hookText() {
        try {
            Log.i(TAG, "hookText");
            Class<?> clazz = Class.forName("android.widget.TextView");
            DexposedBridge.findAndHookMethod(clazz, "setText",
                CharSequence.class,
                TextView.BufferType.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Log.i(TAG, "before method:" + param.method
                            + ", this:" + param.thisObject
                            + ", text:" + param.args[0]
                            + ", type:" + param.args[1]);
                        TextView tv = (TextView) param.thisObject;
                        Log.i(TAG, "TextView tag:" + tv.getTag());
                        Locale locale;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            locale = tv.getContext().getResources().getConfiguration().getLocales().get(0);
                        } else {
                            locale = tv.getContext().getResources().getConfiguration().locale;
                        }
                        printLocal(locale);
                        printLocal(Locale.CHINESE);
                        printLocal(Locale.ENGLISH);
                        // locale:zh_CN_#Hans, language:zh, displayLanguage:中文, displayName:中文 (简体中文,中国)
                        // locale:en_CN, language:en, displayLanguage:English, displayName:English (China)

                        String str = (String) param.args[0];
                        if (locale.getLanguage().equals(Locale.CHINESE.getLanguage())) {
                            if (sZhMap.get(str) == null) return;
                            if (sZhMap.get(str).second == ReplaceScope.GLOBAL)
                                param.args[0] = sZhMap.get(str).first;
                            else {
                                if ("tag_tv_dynamic_text".equals(tv.getTag()))
                                    param.args[0] = sZhMap.get(str).first;
                            }
                        } else if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
                            if (sEnMap.get(str) == null) return;
                            if (sEnMap.get(str).second == ReplaceScope.GLOBAL)
                                param.args[0] = sEnMap.get(str).first;
                            else {
                                if ("tag_tv_dynamic_text".equals(tv.getTag()))
                                    param.args[0] = sEnMap.get(str).first;
                            }
                        }
                    }
                });
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "TextView Not Found", e);
        }
    }

    private static void printLocal(Locale locale) {
        // locale:zh_CN, hashCode:6041, language:zh, displayLanguage:中文, displayName:中文 (中国)
        // locale:zh, hashCode:3886, language:zh, displayLanguage:中文, displayName:中文
        // locale:en, hashCode:3241, language:en, displayLanguage:英文, displayName:英文

        // locale:en_US, hashCode:5959, language:en, displayLanguage:English, displayName:English (United States)
        // locale:zh, hashCode:3886, language:zh, displayLanguage:Chinese, displayName:Chinese
        // locale:en, hashCode:3241, language:en, displayLanguage:English, displayName:English
        Log.i(TAG, "locale:" + locale
            + ", hashCode:" + locale.hashCode()
            + ", language:" + locale.getLanguage()
            + ", displayLanguage:" + locale.getDisplayLanguage()
            + ", displayName:" + locale.getDisplayName());
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
