package com.omottec.demoapp.aspectj;

import android.util.Log;

import com.omottec.demoapp.utils.Logger;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TestAspect {
    public static final String TAG = "TestAspect";

//    @After(value = "staticinitialization(*..People)")
//    public void afterStaticInitialization() {
//        Log.i(TAG, "hook code after people static initialization");
//    }
//
//    @Before(value = "staticinitialization(*..People)")
//    public void beforeStaticInitialization() {
//        Log.i(TAG, "hook code before people static initialization");
//    }

    @Around(value = "staticinitialization(*..People)")
    public void aroundStaticInitialization() {
        Logger.i(TAG, "hook code around people static initialization");
    }

    @Pointcut(value = "within(*..AspectJFragment)")
    public void codeInAspectJFragment() {

    }

    @Pointcut(value = "handler(Exception)")
    public void handleException() {

    }

    @Before(value = "codeInAspectJFragment() && handleException()")
    public void catchException() {
        Logger.i(TAG, "hook catch exception");
    }

}
