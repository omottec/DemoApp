package com.omottec.demoapp.stamp;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;


/**
 * Created by bingbing.qin on 14-9-10.
 */
public class Stamp implements Parcelable, Cloneable{
    public String name;
    public double horizontal;
    public double vertical;
    /** 本地存储路径 */
    public String localPath;
    // 内置的贴纸：scale = width * 1.0 / 640; 单位：px
    public double scale;
    public int type;

    //保存贴纸matrix,生成最终图片时使用，不需要存数据库
    public float[] mMatrixValues;
    //保存未镜像的matrix，生成最终图片时使用
    public float[] unMirrorMatrixValues;

    //下面三组数据用于记录经过编辑的贴纸的位置及缩放信息，便于重新编辑显示时展示
    public float editScale = 0f;
    public float editDegree = 0f;
    //中心点位置与父布局宽度的比例来记录贴纸位置，防止父布局大小改变引起的贴纸位置与之前不一致的问题
    public float editCenterPointXRatio = 0f;
    public float editCenterPointYRatio = 0f;
    // 贴纸镜像
    public boolean editMirror;
    // diy贴纸编辑的字符串
    public String editDiyStr;


    //v810水印贴纸
    public int with_water_mark;
    public int mark_type;


    // v816 diy贴纸
    public int withDiy = 0;
    //贴纸默认下发文字
    public String defaultWords = "水印贴纸";
    // diy贴纸绘制文字时的水平偏移量
    public int wordHorizontal = 0;
    // diy贴纸绘制文字时的垂直偏移量
    public int wordVertical = 0;
    // diy贴纸随文字缩放的factor
    public float factor = 1.0f;
    // diy贴纸字号大小,默认取12px(使用sp，绘制时字显得过大)，以防服务端下发的字段为0
    public int fontSize = 12;
    // diy贴纸所选字体在枚举DiyStampFont中的序号
    public int fontPosition;
    // diy贴纸字体颜色
    public int wordColor = Color.BLACK;

    // 生日贴纸
    public int withBirthday;




    public Stamp() {
    }

    public Stamp(Parcel in) {
        type=in.readInt();
        localPath = in.readString();
        scale = in.readDouble();
        editScale = in.readFloat();
        editDegree = in.readFloat();
        editCenterPointXRatio = in.readFloat();
        editCenterPointYRatio = in.readFloat();
        editMirror = in.readInt() == 1;
        editDiyStr = in.readString();
        with_water_mark=in.readInt();
        mark_type = in.readInt();
        withDiy = in.readInt();
        defaultWords = in.readString();
        wordHorizontal = in.readInt();
        wordVertical = in.readInt();
        factor = in.readFloat();
        fontSize = in.readInt();
        fontPosition = in.readInt();
        wordColor = in.readInt();
        int length = in.readInt();
        if (length>0){
            mMatrixValues = new float[length];
            in.readFloatArray(mMatrixValues);
            unMirrorMatrixValues = new float[length];
            in.readFloatArray(unMirrorMatrixValues);
        }
        withBirthday = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(localPath);
        dest.writeDouble(scale);
        dest.writeFloat(editScale);
        dest.writeFloat(editDegree);
        dest.writeFloat(editCenterPointXRatio);
        dest.writeFloat(editCenterPointYRatio);
        dest.writeInt(editMirror ? 1 : 0);
        dest.writeString(editDiyStr);
        dest.writeInt(with_water_mark);
        dest.writeInt(mark_type);
        dest.writeInt(withDiy);
        dest.writeString(defaultWords);
        dest.writeInt(wordHorizontal);
        dest.writeInt(wordVertical);
        dest.writeFloat(factor);
        dest.writeInt(fontSize);
        dest.writeInt(fontPosition);
        dest.writeInt(wordColor);
        if (mMatrixValues ==null){
            dest.writeInt(0);
        }else{
            dest.writeInt(mMatrixValues.length);
            dest.writeFloatArray(mMatrixValues);
            dest.writeFloatArray(unMirrorMatrixValues);
        }
        dest.writeInt(withBirthday);
    }

    public static final Parcelable.Creator<Stamp> CREATOR
            = new Parcelable.Creator<Stamp>() {
        public Stamp createFromParcel(Parcel in) {
            return new Stamp(in);
        }

        public Stamp[] newArray(int size) {
            return new Stamp[size];
        }
    };
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stamp ::: ").append("localPath = ").append(localPath);
        return sb.toString();
    }

    @Override
    public Stamp clone() throws CloneNotSupportedException {
        Stamp cloneStamp = (Stamp)super.clone();
        return cloneStamp;
    }
}
