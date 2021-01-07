package com.omottec.demoapp.aspectj;

import android.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * https://www.eclipse.org/aspectj/doc/released/progguide/index.html
 * https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx
 */
@Aspect
public class AspectJHook {
    public static final String TAG = "AspectJHook";


    @Pointcut("execution(void com.omottec.demoapp.aspectj.AspectJActivity.onCreate(..))")
    public void onCreatePointcut() {}


    @After("onCreatePointcut()")
    public void onCreateMethod(JoinPoint joinPoint) {
        Log.i(TAG, "onCreateMethod");
        Log.i(TAG, "joinPoint.getStaticPart:" + joinPoint.getStaticPart());
        Log.i(TAG, "joinPoint.getKind:" + joinPoint.getKind());
        Log.i(TAG, "joinPoint.getSignature:" + joinPoint.getSignature());
        Log.i(TAG, "joinPoint.getArgs:" + joinPoint.getArgs());
        Log.i(TAG, "joinPoint.getSourceLocation:" + joinPoint.getSourceLocation());
        Log.i(TAG, "joinPoint.getThis:" + joinPoint.getThis());
        Log.i(TAG, "this:" + this);
        Log.i(TAG, "joinPoint.getTarget:" + joinPoint.getTarget());
        Log.i(TAG, "joinPoint.toLongString:" + joinPoint.toLongString());
        Log.i(TAG, "joinPoint.toShortString:" + joinPoint.toShortString());
        Log.i(TAG, "joinPoint.toString:" + joinPoint.toString());
    }

    @After("execution(* com.omottec.demoapp.aspectj.AspectJActivity.on*(..)) && !onCreatePointcut()")
    public void onLifecycleMethod(JoinPoint joinPoint) throws Exception {
        Log.i(TAG, "onLifecycleMethod");
        Log.i(TAG, "joinPoint.getStaticPart:" + joinPoint.getStaticPart());
        Log.i(TAG, "joinPoint.getKind:" + joinPoint.getKind());
        Log.i(TAG, "joinPoint.getSignature:" + joinPoint.getSignature());
        Log.i(TAG, "joinPoint.getArgs:" + joinPoint.getArgs());
        Log.i(TAG, "joinPoint.getSourceLocation:" + joinPoint.getSourceLocation());
        Log.i(TAG, "joinPoint.getThis:" + joinPoint.getThis());
        Log.i(TAG, "this:" + this);
        Log.i(TAG, "joinPoint.getTarget:" + joinPoint.getTarget());
        Log.i(TAG, "joinPoint.toLongString:" + joinPoint.toLongString());
        Log.i(TAG, "joinPoint.toShortString:" + joinPoint.toShortString());
        Log.i(TAG, "joinPoint.toString:" + joinPoint.toString());
    }

    @Before("call(@com.omottec.demoapp.aspectj.AspectJMarker * *(..))")
    public void methodWithMarker(JoinPoint joinPoint) {
        Log.i(TAG, "methodWithMarker");
        Log.i(TAG, "joinPoint.getKind:" + joinPoint.getKind());
        Log.i(TAG, "joinPoint.getSignature:" + joinPoint.getSignature());
        Object[] args = joinPoint.getArgs();
        Log.i(TAG, "joinPoint.getArgs:" + args);
        for (Object arg : args)
            Log.i(TAG, "arg:" + arg);
        Log.i(TAG, "joinPoint.getThis:" + joinPoint.getThis());
        Log.i(TAG, "joinPoint.getTarget:" + joinPoint.getTarget());
    }

}
