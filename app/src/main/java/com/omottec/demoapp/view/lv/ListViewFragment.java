package com.omottec.demoapp.view.lv;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.fragment.BaseFragment;
import com.omottec.demoapp.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 22/03/2017.
 */

public class ListViewFragment extends BaseFragment {
    public static final String TAG = "ListViewFragment";
    private static final int ITEM_HEIGHT = UiUtils.dip2px(MyApplication.getContext(), 50);
    private ListView mLv;
    private TextView mHeaderTv;
    private TextView mFooterTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View createContentView() {
        mLv = (ListView) View.inflate(mActivity, R.layout.f_list_view, null);
        AbsListView.LayoutParams lp = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ITEM_HEIGHT);
        mHeaderTv = new TextView(getContext());
        mHeaderTv.setGravity(Gravity.CENTER);
        mHeaderTv.setText("Header");
        mHeaderTv.setBackgroundResource(android.R.color.holo_red_light);
        mHeaderTv.setLayoutParams(lp);
        mLv.addHeaderView(mHeaderTv);

        mFooterTv = new TextView(getContext());
        mFooterTv.setGravity(Gravity.CENTER);
        mFooterTv.setText("Footer");
        mFooterTv.setBackgroundResource(android.R.color.holo_blue_light);
        mFooterTv.setLayoutParams(lp);
        mLv.addFooterView(mFooterTv);
        return mLv;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 200; i++)
            items.add("item " + i);
        mLv.setAdapter(new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, items));
        mLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                String state = null;
                switch (scrollState) {
                    case SCROLL_STATE_TOUCH_SCROLL:
                        state = "SCROLL_STATE_TOUCH_SCROLL";
                        break;
                    case SCROLL_STATE_FLING:
                        state = "SCROLL_STATE_FLING";
                        break;
                    case SCROLL_STATE_IDLE:
                        state = "SCROLL_STATE_IDLE";
                        break;
                }
                Log.d(TAG, "onScrollStateChanged " + state);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem
                        + ", visibleItemCount:" + visibleItemCount
                        + ", totalItemCount:" + totalItemCount
                        + ", mLv.getCount:" + mLv.getCount()
                        + ", mLv.getFirstVisiblePosition:" + mLv.getFirstVisiblePosition()
                        + ", mLv.getLastVisiblePosition:" + mLv.getLastVisiblePosition());
            }
        });
    }
}
