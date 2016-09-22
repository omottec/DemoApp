package com.omottec.demoapp.jni;

/**
 * Created by qinbingbing on 9/22/16.
 */
public class JniTest {
    static {
        System.loadLibrary("jni-test");
    }

    public static void main(String[] args) {
        JniTest jniTest = new JniTest();
        System.out.println(jniTest.get());
        jniTest.set("hello world");
        System.out.println(jniTest.get());
    }

    public native String get();

    public native void set(String str);
}
