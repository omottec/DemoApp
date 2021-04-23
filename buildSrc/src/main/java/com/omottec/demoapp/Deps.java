package com.omottec.demoapp;

public final class Deps {

    public static final int MIN_SDK_VERSION = 21;
    public static final int TARGET_SDK_VERSION = 25;
    public static final int COMPILE_SDK_VERSION = 29;
    public static final String BUILD_TOOLS_VERSION = "29.0.2";
    public static final String SUPPORT_LIBRARY_VERSION = "27.1.1";

    public static final String ANDROID_APP_PLUGIN = "com.android.application";
    public static final String ANDROID_LIB_PLUGIN = "com.android.library";

    public static final String JAVA_LIB_PLUGIN = "java-library";
    public static final String JAVA_PLUGIN = "java";
    public static final String GROOVY_PLUGIN = "groovy";
    public static final String MAVEN_PLUGIN = "maven";
    public static final String MAVEN_PUBLISH_PLUGIN = "maven-publish";

    // have classpath
    public static final String KOTLIN_CLASSPATH = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21";
    public static final String KOTLIN_PLUGIN_ID = "kotlin-android";
    public static final String KOTLIN_AAR = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.4.21";

    public static final String GREENDAO_CLASSPATH = "org.greenrobot:greendao-gradle-plugin:3.3.0";
    public static final String GREENDAO_PLUGIN = "org.greenrobot.greendao";
    public static final String GREENDAO_AAR = "org.greenrobot:greendao:3.3.0";

    public static final String ASPECTJ_CLASSPATH = "com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.10";
    public static final String ASPECTJ_PLUGIN = "android-aspectjx";
    public static final String ASPECTJ_AAR = "org.aspectj:aspectjrt:1.9.5";

    // classpath "group:module:version"
    public static final String LIFECYCLE_CLASSPATH = "com.omottec.plugin.lifecycle:gradle_plugin_lifecycle:1.0.30";
    public static final String LIFECYCLE_PLUGIN = "com.omottec.plugin.lifecycle";

    public static final String GRADLE = "com.android.tools.build:gradle:3.6.1";

    public static final String BINTRAY_CLASSPATH = "com.jfrog.bintray.gradle:gradle-bintray-plugin:1.+";
    public static final String BINTRAY_PLUGIN = "com.jfrog.bintray";

    public static final String ASM_CLASSPATH = "com.omottec.plugin.asm:gradle_plugin_asm:2.0.3";
    public static final String ASM_PLUGIN = "com.omottec.plugin.asm";
    public static final String ASM_AAR = "org.ow2.asm:asm:9.0";
    public static final String ASM_COMMON_AAR = "org.ow2.asm:asm-commons:9.0";
}
