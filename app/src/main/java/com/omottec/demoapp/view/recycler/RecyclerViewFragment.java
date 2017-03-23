package com.omottec.demoapp.view.recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omottec.demoapp.R;
import com.omottec.demoapp.view.recycler.MultiRecyclerAdapter;
import com.omottec.demoapp.view.recycler.SimpleRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 10/03/2017.
 */

public class RecyclerViewFragment extends Fragment {
    public static final String TAG = "RecyclerViewFragment";
    private RecyclerView mRecyclerView;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.f_recycler_view, null);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        GridLayoutManager gridLayoutManager= new GridLayoutManager(mContext, 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (position % 3) {
                    case 0:
                    default:
                        return 1;
                    case 1:
                        return 1;
                    case 2:
                        return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++)
            list.add("item " + i);
        mRecyclerView.setAdapter(new SimpleRecyclerAdapter(list));
//        mRecyclerView.setAdapter(new MultiRecyclerAdapter(mContext, list));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Log.d(TAG, "onScrollStateChanged SCROLL_STATE_IDLE");
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Log.d(TAG, "onScrollStateChanged SCROLL_STATE_DRAGGING");
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Log.d(TAG, "onScrollStateChanged SCROLL_STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.d(TAG, "onScrolled dx:" + dx + ", dy:" + dy);
                int childCount = mRecyclerView.getChildCount();
                View firstChild = mRecyclerView.getChildAt(0);
                View lastChild = mRecyclerView.getChildAt(childCount - 1);
                int firstChildAdapterPosition = mRecyclerView.getChildAdapterPosition(firstChild);
                int lastChildAdapterPosition = mRecyclerView.getChildAdapterPosition(lastChild);
                Log.d(TAG, "childCount:" + childCount
                        + ", firstChildAdapterPosition:" + firstChildAdapterPosition
                        + ", firstChildTop:" + firstChild.getTop()
                        + ", lastChildAdapterPosition:" + lastChildAdapterPosition
                        + ", lastChildBottom:" + lastChild.getBottom()
                        + ", mRecyclerViewTop:" + mRecyclerView.getTop()
                        + ", mRecyclerViewBottom:" + mRecyclerView.getBottom());
            }
        });
        return mRecyclerView;
    }
}
