package com.omottec.demoapp.rxjava.scheduler;

import java.util.Objects;

/**
 * Created by qinbingbing on 28/04/2018.
 */

public class ContextManager {
    static final ThreadLocal<Object> ctx = new ThreadLocal<>();

    private ContextManager() {
        throw new IllegalStateException();
    }

    public static Object get() {
        return ctx.get();
    }

    public static void set(Object context) {
        ctx.set(context);
    }
}
