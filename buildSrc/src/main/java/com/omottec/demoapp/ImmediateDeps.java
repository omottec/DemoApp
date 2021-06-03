package com.omottec.demoapp;

public final class ImmediateDeps {

    public static final int MIN_SDK_VERSION = 21;
    public static final int TARGET_SDK_VERSION = 25;
    public static final int COMPILE_SDK_VERSION = 29;
    public static final String BUILD_TOOLS_VERSION = "29.0.2";

    public static final String ANDROID_APP_PLUGIN = "com.android.application";
    public static final String ANDROID_LIB_PLUGIN = "com.android.library";

    public static final String JAVA_LIB_PLUGIN = "java-library";
    public static final String JAVA_PLUGIN = "java";
    public static final String GROOVY_PLUGIN = "groovy";
    public static final String MAVEN_PLUGIN = "maven";
    public static final String JAVA_GRADLE_PLUGIN = "java-gradle-plugin";
    public static final String MAVEN_PUBLISH_PLUGIN = "maven-publish";
    public static final String MAVEN_SIGNING_PLUGIN = "signing";

    // have classpath
    public static final String GRADLE_PLUGIN_REPO = "https://plugins.gradle.org/m2/";
    public static final String GRADLE_PLUGIN_PUBLISH_PLUGIN_CLASSPATH = "com.gradle.publish:plugin-publish-plugin:0.14.0";
    public static final String GRADLE_PLUGIN_PUBLISH_PLUGIN_ID = "com.gradle.plugin-publish";

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
    public static final String OMOTTEC_ASM_CLASSPATH = "com.github.omottec:asm-plugin:1.1.16";
    public static final String OMOTTEC_ASM_PLUGIN = "com.github.omottec.asm";
    public static final String OMOTTEC_JAVASSIST_CLASSPATH = "com.github.omottec:javassist-plugin:1.1.14";
    public static final String OMOTTEC_JAVASSIST_PLUGIN = "com.github.omottec.javassist";

    public static final String AGP_CLASSPATH = "com.android.tools.build:gradle:4.2.1";
}
