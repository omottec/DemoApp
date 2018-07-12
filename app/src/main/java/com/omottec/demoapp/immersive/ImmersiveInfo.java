package com.omottec.demoapp.immersive;

import android.view.View;

/**
 * Created by qinbingbing on 12/07/2018.
 */

public class ImmersiveInfo {
    public boolean immersiveEnable;
    public int viewId = View.NO_ID;

    @Override
    public String toString() {
        return "{immersiveEnable:" + immersiveEnable + ", viewId:" + viewId + "}";
    }
}
