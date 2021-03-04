package com.omottec.demoapp.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omottec.demoapp.R;

import com.omottec.logger.Logger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by qinbingbing on 03/07/2017.
 */

public class DynamicProxyFragment extends Fragment {
    public static final String TAG = "DynamicProxyFragment";
    interface A {
        Object getItem();
    }

    interface B extends A {
        String getItem();
    }

    static final InvocationHandler STUB_HANDLER = (proxy, method, args) -> {
        /*Logger.d(TAG, "proxy:" + proxy
                + ", method:" + method
                + ", args:" + Arrays.toString(args));*/
        return null;
    };

    static Object createInstance(Class<?> clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, STUB_HANDLER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        A a = (A) createInstance(B.class);
        B b = (B) createInstance(B.class);
        System.out.println(a);
        System.out.println(b);
        Logger.d(TAG, a == null ? "null" : a.getItem() + "");
        Logger.d(TAG, b == null ? "null" : b.getItem() + "");
    }
}
