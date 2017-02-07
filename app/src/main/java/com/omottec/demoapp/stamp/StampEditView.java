package com.omottec.demoapp.stamp;


import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.text.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.FrameLayout;
import android.view.GestureDetector.*;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.MathUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * 单手对图片进行缩放，旋转，平移操作，详情请查看
 *
 * @blog http://blog.csdn.net/xiaanming/article/details/42833893
 *
 */
public class StampEditView extends View implements TextWatcher {

    private static final String TAG = "StampEditView";

    /**
     * 控制缩放，旋转图标所在四个点得位置
     */
    public static final int LEFT_TOP = 0;
    public static final int RIGHT_TOP = 1;
    public static final int RIGHT_BOTTOM = 2;
    public static final int LEFT_BOTTOM = 3;

    /**
     * 一些默认的常量
     */
    public static final int DEFAULT_FRAME_PADDING = 1;
    public static final int DEFAULT_FRAME_WIDTH = 1;
    public static final int DEFAULT_FRAME_COLOR = Color.WHITE;
    public static final float DEFAULT_SCALE = 1.0f;
    public static final float DEFAULT_DEGREE = 0;
    public static final int DEFAULT_CONTROL_LOCATION = RIGHT_BOTTOM;
    public static final boolean DEFAULT_EDITABLE = true;


    /**
     * 用于旋转缩放的Bitmap
     */
    private Bitmap mBitmap;

    /**
     * SingleTouchView的中心点坐标，相对于其父类布局而言的
     */
    private PointF mCenterPoint = new PointF();

    /**
     * View的宽度和高度，随着图片的旋转而变化(不包括控制旋转，缩放图片的宽高)
     */
    private int mViewWidth, mViewHeight;

    /**
     * 图片的旋转角度
     */
    private float mDegree = DEFAULT_DEGREE;

    /**
     * 图片的缩放比例
     */
    private float mScale = DEFAULT_SCALE;

    /**
     * 用于缩放，旋转，平移的矩阵
     */
    private Matrix matrix = new Matrix();

    /**
     * SingleTouchView距离父类布局的左间距
     */
    private int mViewPaddingLeft;

    /**
     * SingleTouchView距离父类布局的上间距
     */
    private int mViewPaddingTop;

    /**
     * 图片四个点坐标
     */
    private Point mLTPoint;
    private Point mRTPoint;
    private Point mRBPoint;
    private Point mLBPoint;

    /**
     * 用于缩放，旋转的控制点的坐标
     */
    private Point mControlPoint = new Point();

    /**
     * 用于缩放，旋转的图标
     */
    private Drawable controlDrawable;


    /**
     * 用于关闭的控制点坐标
     */
    private Point mClosePoint = new Point();

    /**
     * 用于关闭的图标
     */
    private Drawable closeDrawable;

    /**
     * 用于左右镜像的坐标
     */
    private Point mMirrorPoint = new Point();

    private Drawable mirrorDrawable;

    /**
     * 缩放，旋转图标的宽和高
     */
    private int mIconSize;

    /**
     * 画外围框的Path
     */
    private Path mPath = new Path();

    /**
     * 画外围框的画笔
     */
    private Paint mPaint ;

    /**
     * 初始状态
     */
    public static final int STATUS_INIT = 0;

    /**
     * 拖动状态
     */
    public static final int STATUS_DRAG = 1;

    /**
     * 旋转或者放大状态
     */
    public static final int STATUS_ROTATE_ZOOM = 2;

    /**
     * 旋转或者放大状态
     */
    public static final int STATUS_CLOSE = 3;

    /**
     * 镜像状态
     */
    public static final int STATUS_MIRROR = 4;

    /**
     * 当前所处的状态
     */
    private int mStatus = STATUS_INIT;

    /**
     * 外边框与图片之间的间距, 单位是dip
     */
    private int framePadding = DEFAULT_FRAME_PADDING;

    /**
     * 外边框颜色
     */
    private int frameColor = DEFAULT_FRAME_COLOR;

    /**
     * 外边框线条粗细, 单位是 dip
     */
    private int frameWidth = DEFAULT_FRAME_WIDTH;

    /**
     * 是否处于可以缩放，平移，旋转状态
     */
    private boolean isEditable = DEFAULT_EDITABLE;

    /**
     * 是否屏蔽一切事件，为true时不可切换为编辑状态
     */
    private boolean isDisabled = false;

    private PointF mPreMovePointF = new PointF();
    private PointF mCurMovePointF = new PointF();

    /**
     * 图片在旋转时x方向的偏移量
     */
    private int offsetX;
    /**
     * 图片在旋转时y方向的偏移量
     */
    private int offsetY;

    /**
     * 控制图标所在的位置（比如左上，右上，左下，右下）
     */
    private int controlLocation = DEFAULT_CONTROL_LOCATION;

//    private StampEditViewStatusListener mEditStatusListener;

//    private Stamp mStamp;

    private ScaleGestureDetector mScaleDetector;

    private GestureDetector mGestureDetector;

    // 是否使用了镜像
    private boolean mIsMirror;

    // 处理自动换行
    private StaticLayout mStaticLayout;

    // 用于绘制diy贴纸文本
    private TextPaint mTextPaint;
    // diy贴纸随文字缩放后Bitmap的宽度
    private float mBaseWidth;
    // diy贴纸随文字缩放后Bitmap的高度
    private float mBaseHeight;
    // 是否展示左上角关闭按钮,默认展示
    private boolean isShowCloseIcon = true;

    private Stamp mStamp;

    public StampEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StampEditView(Context context) {
        this(context, null);
    }

    public StampEditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(frameColor);
        mPaint.setStrokeWidth(frameWidth);
        mPaint.setStyle(Style.STROKE);

        controlDrawable = getContext().getResources().getDrawable(R.mipmap.publisher_stamp_scale_normal);
        closeDrawable = getContext().getResources().getDrawable(R.mipmap.publisher_stamp_close_normal);
        mirrorDrawable = getContext().getResources().getDrawable(R.mipmap.publisher_stamp_mirror_normal);

        mIconSize = controlDrawable.getIntrinsicWidth();
        if (mBitmap!=null){
            transformDraw();
        }

        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        mGestureDetector = new GestureDetector(getContext(), new GestureListener());
    }

//    public void setStamp(Stamp stamp){
//        this.mStamp = stamp;
//    }
//
//    public Stamp getStamp(){
//        return this.mStamp;
//    }

    /**
     * 调整View的大小，位置
     */
    private void adjustLayout(){
        int actualWidth = mViewWidth + mIconSize;
        int actualHeight = mViewHeight + mIconSize;

        int newPaddingLeft = (int) (mCenterPoint.x - actualWidth /2);
        int newPaddingTop = (int) (mCenterPoint.y - actualHeight/2);

        if(mViewPaddingLeft != newPaddingLeft || mViewPaddingTop != newPaddingTop){
            mViewPaddingLeft = newPaddingLeft;
            mViewPaddingTop = newPaddingTop;
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
            params.gravity = Gravity.LEFT|Gravity.TOP;
            params.leftMargin = newPaddingLeft;
            params.topMargin = newPaddingTop;
            params.width = actualWidth;
            params.height = actualHeight;
            requestLayout();
        }
    }

    /**
     * 设置旋转图
     * @param bitmap
     */
    public void setImageBitamp(Bitmap bitmap){
        this.mBitmap = bitmap;
        mBaseWidth = mBitmap.getWidth();
        mBaseHeight = mBitmap.getHeight();
        setFocus(this, (ViewGroup) getParent());
        transformDraw();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*if(mBitmap == null || mBitmap.isRecycled()) {
            Bitmap stampBitmap = UploadImageUtil.loadStampBitmap(mStamp);
            if (mStamp.with_water_mark == 1)
                stampBitmap = genWatermark(stampBitmap, mStamp);
            mBitmap = stampBitmap;
            mBaseWidth = mBitmap.getWidth();
            mBaseHeight = mBitmap.getHeight();
        }*/
        Log.d(TAG, "mBitmap width*height:" + mBitmap.getWidth() + "*" + mBitmap.getHeight());
        canvas.save();
        canvas.concat(matrix);
        canvas.drawBitmap(mBitmap, null, new Rect(0, 0, (int) mBaseWidth, (int) mBaseHeight), null);
        /*if (mStamp.withDiy == 1 && !TextUtils.isEmpty(mStamp.editDiyStr)) {
            if (mIsMirror) {
                canvas.restore();
                canvas.save();
                Matrix unMirrorMatrix = new Matrix();
                unMirrorMatrix.setScale(mScale, mScale);
                unMirrorMatrix.postRotate(mDegree % 360, mBaseWidth * mScale / 2, mBaseHeight * mScale / 2);
                unMirrorMatrix.postTranslate(offsetX + mIconSize / 2, offsetY + mIconSize / 2);
                canvas.concat(unMirrorMatrix);
            }
            canvas.save();
            float dx = mStamp.wordHorizontal * mStamp.factor;
            float dy = mStamp.wordVertical * mStamp.factor
                    + ((mBitmap.getHeight() - mStamp.wordVertical*2) * mStamp.factor - mStaticLayout.getHeight()) / 2;
            canvas.translate(dx, dy);
            mStaticLayout.draw(canvas);
            canvas.restore();
        }*/
        canvas.restore();

        //处于可编辑状态才画边框和控制图标
        if(isEditable){
            mPath.reset();
            mPath.moveTo(mLTPoint.x, mLTPoint.y);
            mPath.lineTo(mRTPoint.x, mRTPoint.y);
            mPath.lineTo(mRBPoint.x, mRBPoint.y);
            mPath.lineTo(mLBPoint.x, mLBPoint.y);
            mPath.lineTo(mLTPoint.x, mLTPoint.y);
            mPath.lineTo(mRTPoint.x, mRTPoint.y);
            canvas.drawPath(mPath, mPaint);
            //画旋图标
            drawIcon(canvas,controlDrawable,mControlPoint);
            if (isShowCloseIcon)
                drawIcon(canvas, closeDrawable, mClosePoint);
            drawIcon(canvas, mirrorDrawable, mMirrorPoint);
        }
    }

    private void drawIcon(Canvas canvas,Drawable drawable,Point point){
        drawable.setBounds(point.x - mIconSize / 2,
                point.y - mIconSize / 2,
                point.x + mIconSize / 2,
                point.y + mIconSize / 2);
        drawable.draw(canvas);
    }

    public int getIconSize() {
        return mIconSize;
    }

    /**
     * 设置Matrix, 强制刷新
     */
    public void transformDraw(){
        int bitmapWidth = (int)(mBaseWidth * mScale);
        int bitmapHeight = (int)(mBaseHeight* mScale);
        computeRect(-framePadding, -framePadding, bitmapWidth + framePadding, bitmapHeight + framePadding, mDegree);
        if (!mIsMirror) {
            //设置缩放比例
            matrix.setScale(mScale, mScale);
            //绕着图片中心进行旋转
            matrix.postRotate(mDegree % 360, bitmapWidth / 2, bitmapHeight / 2);
            //设置画该图片的起始点
            matrix.postTranslate(offsetX + mIconSize / 2, offsetY + mIconSize / 2);
        } else {
            matrix.setScale(-mScale, mScale);
            matrix.postRotate(mDegree % 360, -bitmapWidth / 2, bitmapHeight / 2);
            matrix.postTranslate(offsetX + mIconSize / 2 + bitmapWidth,  offsetY + mIconSize / 2);
        }
        adjustLayout();
        invalidate();
    }

    public void computeBaseSize() {
        /*if (!TextUtils.isEmpty(mStamp.editDiyStr)) {
            if (mTextPaint == null) {
                mTextPaint = new TextPaint();
                mTextPaint.setAntiAlias(true);
                mTextPaint.setColor(mStamp.wordColor);
//				mTextPaint.setTextAlign(Paint.Align.CENTER);
            }
            mTextPaint.setTextSize(mStamp.fontSize);
            if (mStamp.fontPosition != 0) {
                Typeface typeface = null;
                try {
                    typeface = Typeface.createFromFile(DiyStampFont.values()[mStamp.fontPosition].localPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mTextPaint.setTypeface(typeface);
            } else {
                mTextPaint.setTypeface(null);
            }
            mStamp.factor = 1.0f;
            mStaticLayout = new StaticLayout(mStamp.editDiyStr, mTextPaint,
                    (int) ((mBitmap.getWidth() - 2*mStamp.wordHorizontal) * mStamp.factor),
                    Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            int height = mStaticLayout.getHeight();
            while ((mBitmap.getHeight() - 2*mStamp.wordVertical)* mStamp.factor < height) {
                mStamp.factor += 0.05f;
                mStaticLayout = new StaticLayout(mStamp.editDiyStr, mTextPaint,
                        (int) ((mBitmap.getWidth() - 2*mStamp.wordHorizontal) * mStamp.factor),
                        Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
                height = mStaticLayout.getHeight();
            }
            if(mStamp.factor == 1.0f ){//默认字体时，stamp不需要放大，此时尝试使字体变大
                int nameFontSize = DisplayUtil.sp2px(UploadImageUtil.computeFontSize(mBitmap.getWidth() - 2 * mStamp.wordHorizontal, mBitmap.getHeight() - 2 * mStamp.wordVertical, mStamp.editDiyStr, mStamp.wordColor));
                mTextPaint.setTextSize(nameFontSize > mStamp.fontSize ? nameFontSize : mStamp.fontSize);
                mStaticLayout = new StaticLayout(mStamp.editDiyStr, mTextPaint,
                        (int) ((mBitmap.getWidth() - 2*mStamp.wordHorizontal) * mStamp.factor),
                        Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            }
            mBaseWidth = mBitmap.getWidth() * mStamp.factor;
            mBaseHeight = mBitmap.getHeight() * mStamp.factor;
            if (mEditStatusListener != null)
                mEditStatusListener.onTextChanged();
        }*/
    }

    public Matrix getImageMatrix(float imgScale){
        float[] values = new float[9];
        matrix.getValues(values);
        values[2]+=getLeft();
        values[5] += getTop();
        values[8] = values[8]/imgScale;
        Matrix m = new Matrix();
        m.setValues(values);
        Log.d(TAG, " sw=" + mBitmap.getWidth() + " " + "sh=" + mBitmap.getHeight() + " \n" + m.toString());
        return m;
    }

    public static Matrix transformMatrix(Matrix srcMatrix) {
        Matrix matrix = new Matrix();
        float[] values = new float[9];
        srcMatrix.getValues(values);
        float scale = 1 / values[8];
        values[8] = 1;
        for (int i = 0; i < 6; i++)
            values[i] *= scale;
        matrix.setValues(values);
        return matrix;
    }

    /**
     * 由于无法从做了镜像的matrix反推出未做镜像的matrix
     * 这里重新计算未做镜像的matrix
     * diy贴纸的需求：边框做镜像，文字不做镜像
     * @param imgScale
     * @return
     */
    public Matrix getUnMirrorImageMatrix(float imgScale) {
        Matrix matrix = new Matrix();
        matrix.setScale(mScale, mScale);
        matrix.postRotate(mDegree % 360, mBaseWidth * mScale / 2, mBaseHeight * mScale / 2);
        matrix.postTranslate(offsetX + mIconSize / 2, offsetY + mIconSize / 2);
        float[] values = new float[9];
        matrix.getValues(values);
        values[2]+=getLeft();
        values[5] += getTop();
        values[8] = values[8]/imgScale;
        Matrix m = new Matrix();
        m.setValues(values);
        return m;
    }

    public Matrix getSelfMatrix() {
        return matrix;
    }

    private void close(){
        if (!isShowCloseIcon)//如果设置了关闭按钮不展示，则不可以关闭消失
            return;
        ((ViewGroup)getParent()).removeView(this);
    }

    /**
     * 编辑完毕保存位置及缩放信息到贴纸中
     */
    public void saveEditInfo(int containerWidth, int containerHeight) {
        mStamp.editScale = mScale * 1000 / containerWidth;
        mStamp.editDegree = mDegree;
        mStamp.editCenterPointXRatio = mCenterPoint.x / (float)containerWidth;
        mStamp.editCenterPointYRatio = mCenterPoint.y / (float)containerHeight;
        mStamp.editMirror = mIsMirror;
        mStamp.mMatrixValues = new float[9];
        //注意 最终随机贴纸缩放率应该是 photoWidth/viewWidth,由于photoWidth不确定，所以再最后一步重新计算
        getImageMatrix(1.0f/containerWidth).getValues(mStamp.mMatrixValues);
        mStamp.unMirrorMatrixValues = new float[9];
        getUnMirrorImageMatrix(1.0f / containerWidth).getValues(mStamp.unMirrorMatrixValues);
        Log.i(TAG, "saveEditInfo editScale = " + mStamp.editScale);
        Log.i(TAG, "saveEditInfo editDegree = " + mStamp.editDegree);
        Log.i(TAG, "saveEditInfo editCenterPointXRatio = " + mStamp.editCenterPointXRatio);
        Log.i(TAG, "saveEditInfo editCenterPointYRatio = " + mStamp.editCenterPointYRatio);
    }

    private long start,end;
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");
        if(isDisabled) {
            return false;
        }
//        if (getParent() instanceof TouchFrameLayout) {
//            TouchFrameLayout frameLayout = (TouchFrameLayout) getParent();
//            if (frameLayout.getKeyboardStatus() == InputFrameLayout.KEYBOARD_SHOW) {
//                StampEditView editableDiyStampView = getEditableDiyStampView(frameLayout);
//                if (editableDiyStampView != null && editableDiyStampView != this)
//                    return true;
//            }
//        }
        if(!isEditable){
            setFocus(this, (ViewGroup) getParent());
            return true;
        }
        if (event.getPointerCount() == 2) {
            return mScaleDetector.onTouchEvent(event);
        } else {
            if (mGestureDetector.onTouchEvent(event))
                return true;
            switch (event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    start = System.currentTimeMillis();
                    mPreMovePointF.set(event.getX() + mViewPaddingLeft, event.getY() + mViewPaddingTop);
                    mStatus = judgeStatus(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    /*************调整结束后根据旋转的角度进行直角角度吸附 Start************************************/
                    if (mStatus == STATUS_ROTATE_ZOOM) {
                        Log.i("yj", "旋转停止后根据旋转角度再次调整");
                        if(mDegree > 0){
                            if(mDegree % 90 >= 0 && mDegree % 90 <= 5 ){
                                mDegree = mDegree - mDegree % 90;
                            }
                            if(90 - mDegree % 90 >= 0 && 90 - mDegree % 90 <= 5){
                                mDegree = mDegree + 90 - mDegree % 90;
                            }
                        }else if(mDegree < 0){
                            if(Math.abs(mDegree) % 90 >= 0 && Math.abs(mDegree) % 90 <= 5 ){
                                mDegree = -(Math.abs(mDegree) - Math.abs(mDegree) % 90);
                            }
                            if(90 - Math.abs(mDegree) % 90 >= 0 && 90 - Math.abs(mDegree) % 90 <= 5){
                                mDegree = -(Math.abs(mDegree) + 90 - Math.abs(mDegree) % 90);
                            }
                        }else{
                            mDegree = 0;
                        }
                        transformDraw();
//                        if(mEditStatusListener != null) {
//                            mEditStatusListener.onRotateOrZoom();
//                        }
                    }
                    /*************调整结束后根据旋转的角度进行直角角度吸附 End************************************/
                    end = System.currentTimeMillis();  // 拖动间隔时间小于128ms 默认为点击事件
                    if(mStatus == STATUS_DRAG && end - start < 128){
//                        mEditStatusListener.onClick(this);
                    }
                    if (mStatus == STATUS_CLOSE) {
                        close();
                    }
                    if (mStatus == STATUS_MIRROR) {
//						mBitmap = ImageUtil.horizontalMirrorBmp(mBitmap);
                        mIsMirror = !mIsMirror;
                        transformDraw();
//                        if(mEditStatusListener != null) {
//                            mEditStatusListener.onMirror();
//                        }
                    }
                    mStatus = STATUS_INIT;
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurMovePointF.set(event.getX() + mViewPaddingLeft, event.getY() + mViewPaddingTop);

                    if (mStatus == STATUS_ROTATE_ZOOM) {
                        float scale = MathUtil.getScale(mCurMovePointF, mCenterPoint, (int) mBaseWidth, (int) mBaseHeight);
                        float newDegree = MathUtil.getRotation(mPreMovePointF, mCurMovePointF, mCenterPoint);
                        float direction = MathUtil.getDirection(mPreMovePointF,mCurMovePointF,mCenterPoint);
                        if (direction < 0) {
                            newDegree = -newDegree;
                        }
                        mDegree = mDegree + newDegree;
                        mScale = scale;
                        transformDraw();
//                        if(mEditStatusListener != null) {
//                            mEditStatusListener.onRotateOrZoom();
//                        }
                    }
                    else if (mStatus == STATUS_DRAG) {
                        // 修改中心点
                        mCenterPoint.x += mCurMovePointF.x - mPreMovePointF.x;
                        mCenterPoint.y += mCurMovePointF.y - mPreMovePointF.y;
                        adjustLayout();
//                        if(mEditStatusListener != null) {
//                            mEditStatusListener.onDrag();
//                        }
                    }
                    mPreMovePointF.set(mCurMovePointF);
                    break;
            }
            return true;
        }
    }

    /**
     * 获取四个点和View的大小
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param degree
     */
    private void computeRect(int left, int top, int right, int bottom, float degree){
        Point lt = new Point(left, top);
        Point rt = new Point(right, top);
        Point rb = new Point(right, bottom);
        Point lb = new Point(left, bottom);
        Point cp = new Point((left + right) / 2, (top + bottom) / 2);
        mLTPoint = MathUtil.obtainRoationPoint(cp, lt, degree);
        mRTPoint = MathUtil.obtainRoationPoint(cp, rt, degree);
        mRBPoint = MathUtil.obtainRoationPoint(cp, rb, degree);
        mLBPoint = MathUtil.obtainRoationPoint(cp, lb, degree);

        //计算X坐标最大的值和最小的值
        int maxCoordinateX = MathUtil.getMaxValue(mLTPoint.x, mRTPoint.x, mRBPoint.x, mLBPoint.x);
        int minCoordinateX = MathUtil.getMinValue(mLTPoint.x, mRTPoint.x, mRBPoint.x, mLBPoint.x);;

        mViewWidth = maxCoordinateX - minCoordinateX ;


        //计算Y坐标最大的值和最小的值
        int maxCoordinateY = MathUtil.getMaxValue(mLTPoint.y, mRTPoint.y, mRBPoint.y, mLBPoint.y);
        int minCoordinateY = MathUtil.getMinValue(mLTPoint.y, mRTPoint.y, mRBPoint.y, mLBPoint.y);

        mViewHeight = maxCoordinateY - minCoordinateY ;


        //View中心点的坐标
        Point viewCenterPoint = new Point((maxCoordinateX + minCoordinateX) / 2, (maxCoordinateY + minCoordinateY) / 2);

        offsetX = mViewWidth / 2 - viewCenterPoint.x;
        offsetY = mViewHeight / 2 - viewCenterPoint.y;



        int halfDrawableWidth = mIconSize / 2;
        int halfDrawableHeight = mIconSize /2;

        //将Bitmap的四个点的X的坐标移动offsetX + halfDrawableWidth
        mLTPoint.x += (offsetX + halfDrawableWidth);
        mRTPoint.x += (offsetX + halfDrawableWidth);
        mRBPoint.x += (offsetX + halfDrawableWidth);
        mLBPoint.x += (offsetX + halfDrawableWidth);

        //将Bitmap的四个点的Y坐标移动offsetY + halfDrawableHeight
        mLTPoint.y += (offsetY + halfDrawableHeight);
        mRTPoint.y += (offsetY + halfDrawableHeight);
        mRBPoint.y += (offsetY + halfDrawableHeight);
        mLBPoint.y += (offsetY + halfDrawableHeight);

        mControlPoint = locationToPoint(controlLocation);
        mClosePoint = mLTPoint;
        mMirrorPoint = mRTPoint;
    }


    public Bitmap getBitmap(){
        return mBitmap;
    }
    /**
     * 根据位置判断控制图标处于那个点
     * @return
     */
    private Point locationToPoint(int location){
        switch(location){
            case LEFT_TOP:
                return mLTPoint;
            case RIGHT_TOP:
                return mRTPoint;
            case RIGHT_BOTTOM:
                return mRBPoint;
            case LEFT_BOTTOM:
                return mLBPoint;
        }
        return mLTPoint;
    }

    /**
     * 根据点击的位置判断是否点中控制旋转，缩放的图片， 初略的计算
     * @param x
     * @param y
     * @return
     */
    private int judgeStatus(float x, float y){
        PointF touchPoint = new PointF(x, y);
        PointF controlPointF = new PointF(mControlPoint);
        PointF closePointF = new PointF(mClosePoint);
        PointF mirrorPointF = new PointF(mMirrorPoint);
        //点击的点到控制旋转，缩放点的距离
        float distanceToControl = MathUtil.distance4PointF(touchPoint, controlPointF);
        float distanceToClose = MathUtil.distance4PointF(touchPoint, closePointF);
        float distanceToMirror = MathUtil.distance4PointF(touchPoint, mirrorPointF);

        //如果两者之间的距离小于 控制图标的宽度，高度的最小值，则认为点中了控制图标
        if(distanceToControl < mIconSize){
            return STATUS_ROTATE_ZOOM;
        }

        if(distanceToClose < mIconSize){
            return STATUS_CLOSE;
        }

        if (distanceToMirror < mIconSize)
            return STATUS_MIRROR;
        return STATUS_DRAG;

    }


    /**
     * 设置图片旋转角度
     * @param degree
     */
    public void setImageDegree(float degree) {
        if(this.mDegree != degree){
            this.mDegree = degree;
            transformDraw();
        }
    }

    public float getImageScale() {
        return mScale;
    }

    /**
     * 设置图片缩放比例
     * @param scale
     */
    public void setImageScale(float scale) {
        if(this.mScale != scale){
            this.mScale = scale;
            transformDraw();
        };
    }


    /**
     * 设置图片中心点位置，相对于父布局而言
     * @param mCenterPoint
     */
    public void setCenterPoint(PointF mCenterPoint) {
        this.mCenterPoint = mCenterPoint;
        adjustLayout();
    }

    public PointF getCenterPoint() {
        return this.mCenterPoint;
    }


    /**
     * 设置是否处于可缩放，平移，旋转状态
     * @param isEditable
     */
    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
        transformDraw();
    }

    public void setDisabled(boolean isDisabled) {
        if (isDisabled) {
            setEditable(!isDisabled);
        }
        this.isDisabled = isDisabled;
    }

    public void setIsMirror(boolean isMirror) {
        mIsMirror = isMirror;
        transformDraw();
    }

    public void setDiyStr() {
        computeBaseSize();
        transformDraw();
    }

    public void setDiyFont(int fontOrdinal) {
        mStamp.fontPosition = fontOrdinal;
    }

    /**
     * 增加贴纸
     * @param container			增加贴纸view的布局容器
     * @param stamp				增加的贴纸
     * @param containerWidth
     * @param containerHeight
     * @return
     * @add by zhenghao.qi
     * @time 2015年11月12日19:26:30
     */
    public static boolean add(ViewGroup container, Stamp stamp,
                              int containerWidth, int containerHeight){
        Log.d(TAG, "add container width = " + containerWidth + " height = " + containerHeight);
//        Bitmap stampBitmap = UploadImageUtil.loadStampBitmap(stamp);
        Bitmap stampBitmap = null;
        if(stampBitmap == null) {
            return false;
        }
        StampEditView stampEditView = new StampEditView(container.getContext());
        container.addView(stampEditView);
        stampEditView.setStamp(stamp);
//        if (stamp.with_water_mark == 1)
//            stampBitmap = genWatermark(stampBitmap, stamp);
//        if (UploadImageUtil.isBirthdayStamp(stamp))
//            stampBitmap = UploadImageUtil.genBirthdayBmp(stampBitmap, stamp);
        stampEditView.setImageBitamp(stampBitmap);
        if(stamp.editScale != 0) {
            Log.i(TAG, "add editScale = " + stamp.editScale);
            float realScale = stamp.editScale * containerWidth / 1000;
            Log.i(TAG, "add realScale = " + realScale);
            stampEditView.setImageScale(realScale);
        }else {
            float stampScale = getStampScale(containerWidth, containerHeight, stampBitmap, stamp.scale);
            stampEditView.setImageScale(stampScale);
        }
        if(stamp.editDegree != 0) {
            Log.i(TAG, "add editDegree = " + stamp.editDegree);
            stampEditView.setImageDegree(stamp.editDegree);
        }
        if(stamp.editCenterPointXRatio != 0 || stamp.editCenterPointYRatio != 0) {
            Log.i(TAG, "add editCenterPointXRatio = " + stamp.editCenterPointXRatio);
            Log.i(TAG, "add editCenterPointYRatio = " + stamp.editCenterPointYRatio);
            PointF centerPoint = new PointF();
            centerPoint.x = containerWidth * stamp.editCenterPointXRatio;
            centerPoint.y = containerHeight * stamp.editCenterPointYRatio;
            stampEditView.setCenterPoint(centerPoint);
        }else {
            /**根据下发的horizontal和vertical确定贴纸的位置**/
            // 增加了控制icon的距离
            //v838版本之后不再增加控制icon的距离
            float centerX = (float)(containerWidth * stamp.horizontal / 100.0f);
            float centerY = (float)(containerHeight * stamp.vertical / 100.0f);
            float minX = stampBitmap.getWidth() * stampEditView.getImageScale() / 2.0f/* + stampEditView.getIconSize() / 2.0f*/;
            float maxX = containerWidth - minX;
            float minY = stampBitmap.getHeight() * stampEditView.getImageScale() / 2.0f /*+ stampEditView.getIconSize() / 2.0f*/;
            float maxY = containerHeight - minY;
            centerX = Math.min(Math.max(centerX, minX), maxX);
            centerY = Math.min(Math.max(centerY, minY), maxY);
            stampEditView.setCenterPoint(new PointF(centerX, centerY));
        }
        if(stamp.editMirror) {
//			stampEditView.setImageBitamp(ImageUtil.horizontalMirrorBmp(stampEditView.getBitmap()));
            stampEditView.setIsMirror(true);
        }
        if (stamp.withDiy == 1 && !TextUtils.isEmpty(stamp.editDiyStr)) {
            stampEditView.setDiyStr();
        }
        if (stamp.withDiy == 1 && stamp.fontPosition != 0)
            stampEditView.setDiyFont(stamp.fontPosition);
//        stampEditView.setStampEditViewStatusListener(clearListener);
        return true;
    }

    public static void setFocus(StampEditView focus,ViewGroup container){
        int count = container.getChildCount();
        for (int i = 0; i <count ; i++) {
            View child = container.getChildAt(i);
            if (child instanceof StampEditView){
                StampEditView view = (StampEditView) child;
                if (view == focus){
                    view.setEditable(true);
                }else{
                    view.setEditable(false);
                }
            }
        }
    }

    public static void showAllStampView(ViewGroup container){
        int count = container.getChildCount();
        for (int i = 0; i <count ; i++) {
            View child = container.getChildAt(i);
            if (child instanceof StampEditView){
                child.setVisibility(View.VISIBLE);
            }
        }
    }


    public static void saveAllStampEditInfo(ViewGroup container) {
        int count = container.getChildCount();
        for (int i = 0; i <count ; i++) {
            View child = container.getChildAt(i);
            if (child instanceof StampEditView){
                StampEditView view = (StampEditView) child;
                view.saveEditInfo(container.getWidth(), container.getHeight());
            }
        }
    }

    /*public static Bitmap genWatermark(Bitmap src, Stamp stamp) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(stamp.localPath, options);
        options.inSampleSize = ImageUtil.computeSampleSize(options, -1, 1080 * 1080);
        if (stamp.mark_type == 1) {
            // 日期+地点  打卡贴纸
            return UploadImageUtil.genWatermark1(src, options.inSampleSize);
        } else if (stamp.mark_type == 2) {
            // 日期+天气+星期几  愿你心晴
            return UploadImageUtil.genWatermark2(src, options.inSampleSize);
        } else {
            return src;
        }
    }*/

    /**
     * 使所有贴纸都不可编辑（点击也不可以）
     * @param container
     */
    public static void disableAllStampView(boolean isDisabled, ViewGroup container) {
        int count = container.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = container.getChildAt(i);
            if (child instanceof StampEditView) {
                ((StampEditView) child).setDisabled(isDisabled);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!isEditable) return;
//        mStamp.editDiyStr = s.toString();
//        Log.d(TAG, "mStamp.editDiyStr:" + mStamp.editDiyStr);
        computeBaseSize();
        transformDraw();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setStamp(Stamp stamp) {
        mStamp = stamp;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleBegin");
            return super.onScaleBegin(detector);
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d(TAG, "SimpleOnScaleGestureListener onScale");
            mScale *= detector.getScaleFactor();
            transformDraw();
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleEnd");
            mStatus = STATUS_INIT;
            super.onScaleEnd(detector);
        }
    }

    private class GestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onDoubleTap");
            /*if (mStamp.withDiy == 1) {
                if (mStamp.defaultWords.equals(mStamp.editDiyStr)) {
                    mStamp.editDiyStr = "";
                    transformDraw();
                }
                mEditStatusListener.onDoubleTap(mStamp);
                return true;
            } else {
                return super.onDoubleTap(e);
            }*/
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d(TAG, "onDoubleTapEvent");
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d(TAG, "onSingleTapConfirmed");
            return super.onSingleTapConfirmed(e);
        }
    }

    public float getBaseWidth() {
        return mBaseWidth;
    }

    public float getBaseHeight() {
        return mBaseHeight;
    }

    public StaticLayout getStaticLayout() {
        return mStaticLayout;
    }

    public void setIsShowCloseIcon(boolean isShowCloseIcon) {
        this.isShowCloseIcon = isShowCloseIcon;
    }

    public interface OnOutsideTouchListener {
        void onOutsideTouch(MotionEvent event);
    }

    /**
     *
     *  view 图片控件
     * @param stampBitmap
     * @param scale 贴纸相对于图片缩放率
     * @return 贴纸自身缩放率
     */
    public static float getStampScale(int viewWidth, int viewHeight, Bitmap stampBitmap, double scale){
        float stampScale = 1.0f;
        if (stampBitmap != null) {
            int tagWidth = stampBitmap.getWidth();
            int tagHeight = stampBitmap.getHeight();
            if (viewWidth * tagHeight <= viewHeight * tagWidth) {
                stampScale = (float) (viewWidth * scale / 100.0f / tagWidth);
            } else {
                stampScale = (float) (viewHeight * scale / 100.0f / tagHeight);
            }
        }
        return stampScale;
    }

    public static Bitmap getThumbnail(String imagePath, int maxWidth, int[] photoSize) {
        if (TextUtils.isEmpty(imagePath)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = null;
        FileInputStream fis =null;
        try {
            BitmapFactory.decodeFile(imagePath, options);
            if(photoSize != null && photoSize.length >= 2) {
                photoSize[0] = options.outWidth;
                photoSize[1] = options.outHeight;
            }
            try {
                fis = new FileInputStream(imagePath);
            }catch (FileNotFoundException e){
                e.printStackTrace();
                return null;
            }

            /*boolean isNormal = isOrientationNormal(imagePath);
            bitmap = BitmapFactory
                    .decodeFileDescriptor(fis.getFD(), null, generalOptions(options, maxWidth, isNormal));
            bitmap = ImageUtil.processExifTransform(imagePath, bitmap);
            if (bitmap != null && bitmap.getWidth() > maxWidth) {
                int dstHeight = (int) (maxWidth * 1.0f / bitmap.getWidth() * bitmap.getHeight());
                bitmap = Bitmap.createScaledBitmap(bitmap, maxWidth, dstHeight, true);
                Methods.log("maxWidth=" + maxWidth + " bitmap.getWidth()=" + bitmap.getWidth() + " bitmap.getHeight()="
                        + bitmap.getHeight());
            }*/

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                fis.close();
            } catch (Exception e2) {
            }
        }
        return bitmap;
    }

}

