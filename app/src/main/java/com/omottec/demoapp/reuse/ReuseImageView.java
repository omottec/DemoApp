package com.omottec.demoapp.reuse;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 09/09/2018.
 */

public class ReuseImageView extends android.support.v7.widget.AppCompatImageView {
    public ReuseImageView(Context context) {
        super(context);
        Logger.d(Tag.REUSE, this +  " ReuseImageView(Context context)");
    }

    public ReuseImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Logger.d(Tag.REUSE, this +  " ReuseImageView(Context context, @Nullable AttributeSet attrs)");
    }

    public ReuseImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Logger.d(Tag.REUSE, this +  " ReuseImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Logger.d(Tag.REUSE, this + " finalize()");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Logger.d(Tag.REUSE, this + " onAttachedToWindow()");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Logger.d(Tag.REUSE, this + " onDetachedFromWindow()");
    }
}