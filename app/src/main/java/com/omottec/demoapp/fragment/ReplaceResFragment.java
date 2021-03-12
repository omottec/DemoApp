package com.omottec.demoapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.activity.SingleFragmentActivity;
import com.omottec.demoapp.launch.ActivityA;
import com.omottec.demoapp.utils.UiUtils;
import com.omottec.demoapp.view.log.LogImageView;
import com.omottec.demoapp.view.log.LogTextView;
import com.omottec.logger.Logger;
import org.jetbrains.annotations.NotNull;

public class ReplaceResFragment extends Fragment {
    private LinearLayout mBodyLl;
    private LinearLayout mNavLl;
    private TextView mBodyTv;
    private ImageView mTab1Iv;
    private ImageView mTab2Iv;
    private ImageView mTab3Iv;
    private ImageView mTab4Iv;
    private TextView mGlobalItem1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(Tag.REPLACE_RES, Logger.getInflaterInfo(getLayoutInflater()));
        Logger.i(Tag.REPLACE_RES, "getResources:" + getResources());
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Logger.i(Tag.REPLACE_RES, Logger.getInflaterInfo(inflater));
        View view = inflater.inflate(R.layout.f_replace_res, container, false);
        mBodyLl = view.findViewById(R.id.ll_body);
        mNavLl = view.findViewById(R.id.ll_nav);

        TextView tv = new TextView(getContext());
        tv.setId(R.id.tv_new_sys);
        tv.setText(R.string.dynamic_text_new_sys);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(getResources().getColor(R.color.itemColor));
        LinearLayout.LayoutParams tvParams =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                UiUtils.dip2px(getContext(), 50));
        tvParams.setMargins(0,0, 0, UiUtils.dip2px(getContext(), 2));
        mBodyLl.addView(tv, tvParams);

        LogTextView logTv = new LogTextView(getContext());
        tv.setId(R.id.tv_new_define);
        logTv.setText(R.string.dynamic_text_new_define);
        logTv.setGravity(Gravity.CENTER);
        logTv.setBackgroundColor(getResources().getColor(R.color.itemColor));
        LinearLayout.LayoutParams logTvParams =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                UiUtils.dip2px(getContext(), 50));
        logTvParams.setMargins(0,0, 0, UiUtils.dip2px(getContext(), 2));
        mBodyLl.addView(logTv, logTvParams);

        int imgSize = UiUtils.dip2px(getContext(), 40);
        LinearLayout tab3Ll = new LinearLayout(getContext());
        tab3Ll.setId(R.id.ll_tab_3);
        tab3Ll.setOrientation(LinearLayout.VERTICAL);
        tab3Ll.setBackgroundResource(R.color.tabColor);
        LinearLayout.LayoutParams tab3LlParams =
            new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mNavLl.addView(tab3Ll, tab3LlParams);
        ImageView tab3Iv = new ImageView(getContext());
        tab3Iv.setId(R.id.iv_tab_3);
        tab3Iv.setImageResource(R.drawable.yellow_face_3);
        LinearLayout.LayoutParams tab3IvParams = new LinearLayout.LayoutParams(imgSize, imgSize);
        tab3IvParams.topMargin = UiUtils.dip2px(getContext(), 3);
        tab3IvParams.gravity = Gravity.CENTER_HORIZONTAL;
        tab3Ll.addView(tab3Iv, tab3IvParams);
        TextView tab3Tv = new TextView(getContext());
        tab3Tv.setId(R.id.tv_tab_3);
        LinearLayout.LayoutParams tab3TvParams =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        tab3TvParams.gravity = Gravity.CENTER_HORIZONTAL;
        tab3Tv.setGravity(Gravity.CENTER);
        tab3Tv.setText(R.string.dynamic_text_tab_3_new_sys);
        tab3Ll.addView(tab3Tv, tab3TvParams);

        LinearLayout tab4Ll = new LinearLayout(getContext());
        tab4Ll.setId(R.id.ll_tab_4);
        tab4Ll.setOrientation(LinearLayout.VERTICAL);
        tab4Ll.setBackgroundResource(R.color.tabColor);
        LinearLayout.LayoutParams tab4LlParams =
            new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mNavLl.addView(tab4Ll, tab4LlParams);
        LogImageView tab4Iv = new LogImageView(getContext());
        tab4Iv.setId(R.id.iv_tab_4);
        tab4Iv.setImageResource(R.drawable.yellow_face_4);
        LinearLayout.LayoutParams tab4IvParams = new LinearLayout.LayoutParams(imgSize, imgSize);
        tab4IvParams.topMargin = UiUtils.dip2px(getContext(), 3);
        tab4IvParams.gravity = Gravity.CENTER_HORIZONTAL;
        tab4Ll.addView(tab4Iv, tab4IvParams);
        LogTextView tab4Tv = new LogTextView(getContext());
        tab4Tv.setId(R.id.tv_tab_4);
        LinearLayout.LayoutParams tab4TvParams =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        tab4TvParams.gravity = Gravity.CENTER_HORIZONTAL;
        tab4Tv.setGravity(Gravity.CENTER);
        tab4Tv.setText(R.string.dynamic_text_tab_4_new_define);
        tab4Ll.addView(tab4Tv, tab4TvParams);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBodyTv = view.findViewById(R.id.tv_body);
        mTab1Iv = view.findViewById(R.id.iv_tab_1);
        mTab2Iv = view.findViewById(R.id.iv_tab_2);
        Log.i(Tag.REPLACE_RES, "R.id.tv_body:" + R.id.tv_body);
        Log.i(Tag.REPLACE_RES, "R.id.iv_tab_1:" + R.id.iv_tab_1);
        Log.i(Tag.REPLACE_RES, "R.id.iv_tab_2:" + R.id.iv_tab_2);


        mBodyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBodyTv.setText(R.string.dynamic_text_body_click);
                SingleFragmentActivity activity = (SingleFragmentActivity) getActivity();
                Resources resources = activity.getResources();
                Resources appRes = activity.getAppRes();
                AssetManager assets = appRes.getAssets();
                Context baseContext = activity.getBaseContext();
                Logger.i(Tag.REPLACE_RES, "resources:" + resources
                    + ", appRes:" + appRes
                    + ", assets:" + assets
                    + ", baseContext:" + baseContext);
                startActivity(new Intent(getContext(), ActivityA.class));
            }
        });


        /*GlideApp.with(this)
            .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F00%2F94%2F21%2F8356f2bd030acd8.jpg&refer=http%3A%2F%2Fbpic.588ku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1617435408&t=8915e4faf455bf540df593eb639dfd3c")
            //.load("null")
            .placeholder(R.drawable.yellow_face_1)
            //.onlyRetrieveFromCache(true)
            .into(mTab1Iv);
        GlideApp.with(this)
            .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F00%2F29%2F09%2F4556d22602384e5.jpg&refer=http%3A%2F%2Fbpic.588ku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1617435534&t=35061944e9505123fef69f0151e729f3")
            .placeholder(R.drawable.yellow_face_2)
            .into(mTab2Iv);
        GlideApp.with(this)
            .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F00%2F85%2F22%2F6256e8958f8b6e2.jpg&refer=http%3A%2F%2Fbpic.588ku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1617435665&t=df79a3a662e52b32acf45d36b38c49be")
            .placeholder(R.drawable.yellow_face_3)
            .into(mTab3Iv);
        GlideApp.with(this)
            .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fstatic.easyicon.net%2Fpreview%2F53%2F530838.gif&refer=http%3A%2F%2Fstatic.easyicon.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1617435723&t=72229e661003141a2ec8edbc748ca0a7")
            .placeholder(R.drawable.yellow_face_4)
            .into(mTab4Iv);*/
    }
}
