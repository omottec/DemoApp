package com.omottec.demoapp.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.omottec.demoapp.R;
import com.omottec.demoapp.app.GlideApp;

public class ReplaceResFragment extends Fragment {
    public static final String TAG = "ReplaceTextFragment";
    private TextView mBodyTv;
    private ImageView mTab1Iv;
    private ImageView mTab2Iv;
    private ImageView mTab3Iv;
    private ImageView mTab4Iv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_replace_res, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBodyTv = view.findViewById(R.id.tv_body);
        mTab1Iv = view.findViewById(R.id.iv_tab_1);
        mTab2Iv = view.findViewById(R.id.iv_tab_2);
        mTab3Iv = view.findViewById(R.id.iv_tab_3);
        mTab4Iv = view.findViewById(R.id.iv_tab_4);


        mBodyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBodyTv.setText(R.string.dynamic_text_body_click);
            }
        });

        GlideApp.with(this)
            .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F00%2F94%2F21%2F8356f2bd030acd8.jpg&refer=http%3A%2F%2Fbpic.588ku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1617435408&t=8915e4faf455bf540df593eb639dfd3c")
            //.load("null")
            .placeholder(R.drawable.yellow_face_1)
            .onlyRetrieveFromCache(true)
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
            .into(mTab4Iv);
    }
}
