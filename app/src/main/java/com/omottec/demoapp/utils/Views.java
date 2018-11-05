package com.omottec.demoapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

/**
 * Created by qinbingbing on 22/01/2018.
 */

public final class Views {
    private Views() {}

    public static Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public static String getVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            return "View.VISIBLE";
        } else if (view.getVisibility() == View.INVISIBLE) {
            return "View.INVISIBLE";
        } else if (view.getVisibility() == View.GONE) {
            return "View.GONE";
        } else {
            return "Unknown";
        }
    }
}
