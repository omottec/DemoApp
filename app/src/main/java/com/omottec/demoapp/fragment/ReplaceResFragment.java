package com.omottec.demoapp.fragment;

import android.content.Intent;
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
import com.omottec.demoapp.launch.ActivityA;
import com.omottec.demoapp.utils.UiUtils;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(Tag.REPLACE_RES, Logger.getInflaterInfo(getLayoutInflater()));
        Logger.i(Tag.REPLACE_RES, "Fragment getResources:" + getResources());
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

        TextView textView = new TextView(getContext());
        textView.setText(R.string.dynamic_text_body);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        ViewGroup.LayoutParams layoutParams =
            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                UiUtils.dip2px(getContext(), 50));
        mBodyLl.addView(textView, layoutParams);

        LinearLayout tabLl = new LinearLayout(getContext());
        tabLl.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams tabParams =
            new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mNavLl.addView(tabLl, tabParams);
        ImageView iv = new ImageView(getContext());
        iv.setImageResource(R.drawable.yellow_face_4);
        int size = UiUtils.dip2px(getContext(), 40);
        LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(size, size);
        ivParams.topMargin = UiUtils.dip2px(getContext(), 3);
        ivParams.gravity = Gravity.CENTER_HORIZONTAL;
        tabLl.addView(iv, ivParams);
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams tvParams =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        tvParams.gravity = Gravity.CENTER_HORIZONTAL;
        tv.setGravity(Gravity.CENTER);
        tv.setText(R.string.dynamic_text_tab_4);
        tabLl.addView(tv, tvParams);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBodyTv = view.findViewById(R.id.tv_body);
        mTab1Iv = view.findViewById(R.id.iv_tab_1);
        mTab2Iv = view.findViewById(R.id.iv_tab_2);
        mTab3Iv = view.findViewById(R.id.iv_tab_3);
        Log.i(Tag.REPLACE_RES, "R.id.tv_body:" + R.id.tv_body);
        Log.i(Tag.REPLACE_RES, "R.id.iv_tab_1:" + R.id.iv_tab_1);
        Log.i(Tag.REPLACE_RES, "R.id.iv_tab_2:" + R.id.iv_tab_2);
        Log.i(Tag.REPLACE_RES, "R.id.iv_tab_3:" + R.id.iv_tab_3);


        mBodyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBodyTv.setText(R.string.dynamic_text_body_click);
                Log.i(Tag.REPLACE_RES, "onClick");
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
