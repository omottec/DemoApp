package com.omottec.demoapp.view.statuslayout;

import android.app.Notification;
import android.content.Context;
import android.view.ViewStub;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 30/06/2017.
 */

public final class StatusConfiguration {
    final Context mContext;
    final int mLoadingResId;
    final int mContentResId;
    final ViewStub mNetErrVs;
    final ViewStub mEmptyVs;
    final OnRetryListener mOnRetryListener;

    private StatusConfiguration(Builder builder) {
        mContext = builder.mContext;
        mLoadingResId = builder.mLoadingResId != 0 ? builder.mLoadingResId : R.layout.l_loading;
        mContentResId = builder.mContentResId;
        if (builder.mNetErrVs != null) {
            mNetErrVs = builder.mNetErrVs;
        } else {
            ViewStub viewStub = new ViewStub(mContext);
            viewStub.setLayoutResource(R.layout.l_net_error);
            mNetErrVs = builder.mNetErrVs = viewStub;
        }
        if (builder.mEmptyVs != null) {
            mEmptyVs = builder.mEmptyVs;
        } else {
            ViewStub viewStub = new ViewStub(mContext);
            viewStub.setLayoutResource(R.layout.l_empty);
            mEmptyVs = builder.mEmptyVs = viewStub;
        }
        mOnRetryListener = builder.mOnRetryListener;
    }

    public static final class Builder {
        Context mContext;
        int mLoadingResId;
        int mContentResId;
        ViewStub mNetErrVs;
        ViewStub mEmptyVs;
        OnRetryListener mOnRetryListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder loadingView(int loadingResId) {
            mLoadingResId = loadingResId;
            return this;
        }

        public Builder contentView(int contentResId) {
            mContentResId = contentResId;
            return this;
        }

        public Builder netErrView(int netErrResId) {
            mNetErrVs = new ViewStub(mContext);
            mNetErrVs.setLayoutResource(netErrResId);
            return this;
        }

        public Builder emptyView(int emptyResId) {
            mEmptyVs = new ViewStub(mContext);
            mEmptyVs.setLayoutResource(emptyResId);
            return this;
        }

        public Builder onRetryListener(OnRetryListener onRetryListener) {
            mOnRetryListener = onRetryListener;
            return this;
        }

        public StatusConfiguration build() {
            return new StatusConfiguration(this);
        }
    }
}
