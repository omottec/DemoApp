package com.omottec.demoapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

/**
 * Created by qinbingbing on 12/07/2018.
 */

public final class Activities {
    private Activities() {}

    public static Activity getActivity(View view) {
        return getActivity(view.getContext());
    }

    public static Activity getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}
