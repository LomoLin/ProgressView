package com.lomo.progressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Lomo on 2017/8/12.
 */

public class ProgressView extends View {
    //边框颜色
    private int mStrokeColor;
    //边框宽度
    private int mStrokeWidth;
    //背景色
    private int mBgColor;
    //进度条颜色
    private int mProgressColor;
    //是否显示进度值
    private boolean mShowText;
    //进度值字体大小
    private int mTextSize;
    //进度值字体颜色
    private int mTextColor;
    //进度值最大值
    private int mMaxProgress;
    //当前进度值
    private int mCurrentProgress;

    //画笔
    private Paint mPaint;

    private RectF mRectF;
    //边框
    private Path mStrokePath;
    //背景
    private Path mBgPath;
    //进度条
    private Path mProgressPath;

    //自定义组件的宽
    private int mViewWidth;
    //自定义组件的高
    private int mViewHeight;


    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        mStrokeColor = typedArray.getColor(R.styleable.ProgressView_pvStrokeColor, Color.DKGRAY);
        mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.ProgressView_pvStrokeWidth, 0);
        mBgColor = typedArray.getColor(R.styleable.ProgressView_pvBackgroundColor, Color.WHITE);
        mProgressColor = typedArray.getColor(R.styleable.ProgressView_pvProgressColor, Color.BLUE);
        mShowText = typedArray.getBoolean(R.styleable.ProgressView_pvShowText, true);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.ProgressView_pvTextSize, 24);
        mTextColor = typedArray.getColor(R.styleable.ProgressView_pvTextColor, Color.BLACK);
        mMaxProgress = typedArray.getInt(R.styleable.ProgressView_pvMax, 100);
        mCurrentProgress = typedArray.getInt(R.styleable.ProgressView_pvProgress, 0);

        //
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePath = new Path();
        mBgPath = new Path();
        mProgressPath = new Path();

        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewWidth = w;
        mViewHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        //是否需要画边框
        if (mStrokeWidth > 0) {
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setColor(mStrokeColor);
            mPaint.setStyle(Paint.Style.STROKE);
            //根据view的高度画一个圆弧
            mRectF = new RectF(0, 0, mViewHeight, mViewHeight);
            mStrokePath.addArc(mRectF, -90, -180);
            mStrokePath.rLineTo(mViewWidth - mViewHeight, 0);
            mRectF = new RectF(mViewWidth - mViewHeight, 0, mViewWidth , mViewHeight);
            mStrokePath.arcTo(mRectF, 90, -180);
            mStrokePath.close();
            canvas.drawPath(mStrokePath, mPaint);
        }
        //画背景
        mPaint.setColor(mBgColor);
        mPaint.setStyle(Paint.Style.FILL);
        mRectF = new RectF(mStrokeWidth, mStrokeWidth, mViewHeight - mStrokeWidth, mViewHeight - mStrokeWidth);
        mBgPath.addArc(mRectF, -90, -180);
        mBgPath.rLineTo(mViewWidth - mViewHeight, 0);
        mRectF = new RectF(mViewWidth - mViewHeight + mStrokeWidth, mStrokeWidth, mViewWidth - mStrokeWidth, mViewHeight - mStrokeWidth);
        mBgPath.arcTo(mRectF, 90, -180);
        mBgPath.close();
        canvas.drawPath(mBgPath, mPaint);
        //画进度条
        mPaint.setColor(mProgressColor);
        mRectF = new RectF(mStrokeWidth, mStrokeWidth, mViewHeight - mStrokeWidth, mViewHeight - mStrokeWidth);
        mProgressPath.addArc(mRectF, -90, -180);
        mProgressPath.rLineTo(mCurrentProgress * 100 / mMaxProgress * (mViewWidth - mStrokeWidth) / 100 - mViewHeight - 2 * mStrokeWidth, 0);
        mRectF = new RectF(mCurrentProgress * 100 / mMaxProgress * (mViewWidth - mStrokeWidth) / 100 - mViewHeight + 3 * mStrokeWidth, mStrokeWidth, mCurrentProgress * 100 / mMaxProgress * (mViewWidth - mStrokeWidth) / 100 + mStrokeWidth, mViewHeight - mStrokeWidth);
        mProgressPath.arcTo(mRectF, 90, -180);
        mBgPath.close();
        canvas.drawPath(mProgressPath, mPaint);
//        super.onDraw(canvas);
    }

    public void setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
        postInvalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
        postInvalidate();
    }

    public void setBgColor(int bgColor) {
        mBgColor = bgColor;
        postInvalidate();
    }

    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
        postInvalidate();
    }

    public void setShowText(boolean showText) {
        mShowText = showText;
        postInvalidate();
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
        postInvalidate();
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        postInvalidate();
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
        postInvalidate();
    }

    public void setCurrentProgress(int currentProgress) {
        mCurrentProgress = currentProgress;
        postInvalidate();
    }
}
