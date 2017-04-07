package com.omottec.demoapp.dao;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.activity.BaseActivity;

/**
 * Created by qinbingbing on 07/04/2017.
 */

public class DaoActivity extends BaseActivity implements View.OnClickListener {
    private View mContentView;
    private TextView mUserTv;
    private TextView mNoteTv;

    @Override
    protected View createContentView() {
        mContentView = View.inflate(this, R.layout.a_dao, null);
        mUserTv = (TextView) mContentView.findViewById(R.id.tv_user);
        mNoteTv = (TextView) mContentView.findViewById(R.id.tv_note);
        mUserTv.setOnClickListener(this);
        mNoteTv.setOnClickListener(this);
        return mContentView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_user:
                intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_note:
                intent = new Intent(this, NoteActivity.class);
                break;
        }
        startActivity(intent);
    }
}
