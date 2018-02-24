package com.omottec.demoapp.fresco;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.utils.UiUtils;
import com.omottec.demoapp.utils.Views;

import java.util.List;

/**
 * Created by qinbingbing on 11/02/2018.
 */

public class BatchLoadAdapter extends RecyclerView.Adapter<BatchLoadAdapter.BatchLoadHolder> {
    private List<ImageData> mImageDataList;
    private FrescoControllerListener mListener = new FrescoControllerListener();

    public BatchLoadAdapter(List<ImageData> imageDataList) {
        mImageDataList = imageDataList;
    }

    @Override
    public BatchLoadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BatchLoadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.l_pic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BatchLoadHolder holder, int position) {
        ImageData imageData = mImageDataList.get(position);
        Frescos.rawLoad(holder.mSdv, imageData.url, mListener);
//        int screenSize = UiUtils.getScreenSize(MyApplication.getContext(), true);
//        Frescos.load(holder.mSdv, imageData.url, screenSize/2, screenSize/2, mListener);
        holder.mTv.setText(imageData.title);
        holder.itemView.setOnClickListener(v -> {
            BigPicActivity.show(Views.getActivity(v), imageData);
        });
    }

    @Override
    public int getItemCount() {
        return mImageDataList.size();
    }

    protected static class BatchLoadHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView mSdv;
        private TextView mTv;

        public BatchLoadHolder(View itemView) {
            super(itemView);
            mSdv = itemView.findViewById(R.id.sdv);
            mTv = itemView.findViewById(R.id.tv);
        }
    }

    public static class ImageData implements Parcelable {
        public String url;
        public String title;

        public ImageData() {}

        protected ImageData(Parcel in) {
            url = in.readString();
            title = in.readString();
        }

        public static final Creator<ImageData> CREATOR = new Creator<ImageData>() {
            @Override
            public ImageData createFromParcel(Parcel in) {
                return new ImageData(in);
            }

            @Override
            public ImageData[] newArray(int size) {
                return new ImageData[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(url);
            dest.writeString(title);
        }
    }
}
