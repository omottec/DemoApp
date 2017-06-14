package com.omottec.demoapp.ref;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by qinbingbing on 14/06/2017.
 */

public class RefGcFragment extends Fragment {
    private ReferentObject mStrongReference;
    private SoftReference<ReferentObject> mSoftReference;
    private WeakReference<ReferentObject> mWeakReference;
    private ReferenceQueue<ReferentObject> mReferenceQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mReferenceQueue = new ReferenceQueue<>();
        mStrongReference = new ReferentObject();
        mSoftReference = new SoftReference<>(new ReferentObject(), mReferenceQueue);
        mWeakReference = new WeakReference<>(new ReferentObject(), mReferenceQueue);
        Log.d(Tag.REF, "mStrongReference:" + mStrongReference);
        Log.d(Tag.REF, "mSoftReference:" + mSoftReference + ", mSoftReference.get:" + mSoftReference.get());
        Log.d(Tag.REF, "mWeakReference:" + mWeakReference + ", mWeakReference.get:" + mWeakReference.get());
        for (int i = 0; i < 5; i++) {
            Log.d(Tag.REF, "gc " + i);
            System.gc();
            SystemClock.sleep(100);
            Reference<? extends ReferentObject> polledRef;
            while ((polledRef = mReferenceQueue.poll()) != null) {
                Log.d(Tag.REF, "polledRef:" + polledRef + ", polledRef.get:" + polledRef.get());
            }
        }
    }
}
