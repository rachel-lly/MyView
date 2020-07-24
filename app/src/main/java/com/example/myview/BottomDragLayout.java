package com.example.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.customview.widget.ViewDragHelper;

public class BottomDragLayout extends ViewGroup {

    private ViewDragHelper mViewDragHelper;
    private View mBottomView;
    private View mContentView;
    private LayoutInflater mLayoutInflater;
    private int mBottomBorderHeigth = 20;


    public BottomDragLayout(Context context) {
        this(context,null,0);
    }

    public BottomDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initCustomAttrs(context, attrs);
    }

    private void init(Context context) {
        mLayoutInflater = LayoutInflater.from(context);


        ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return mBottomView == child;
            }
        };

        mViewDragHelper = ViewDragHelper.create(this, 1.0f, mCallback);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PullUpDragLayout);
        if (typedArray != null) {
            if (typedArray.hasValue(R.styleable.PullUpDragLayout_PullUpDrag_ContentView)) {
                inflateContentView(typedArray.getResourceId(R.styleable.PullUpDragLayout_PullUpDrag_ContentView, 0));
            }
            if (typedArray.hasValue(R.styleable.PullUpDragLayout_PullUpDrag_BottomView)) {
                inflateBottomView(typedArray.getResourceId(R.styleable.PullUpDragLayout_PullUpDrag_BottomView, 0));
            }
            if (typedArray.hasValue(R.styleable.PullUpDragLayout_PullUpDrag_BottomBorderHeigth)) {
                mBottomBorderHeigth = (int) typedArray.getDimension(R.styleable.PullUpDragLayout_PullUpDrag_BottomBorderHeigth, 20);
            }
            typedArray.recycle();
        }
    }

    private void inflateContentView(int resourceId) {
        mContentView = mLayoutInflater.inflate(resourceId, this, true);
    }

    private void inflateBottomView(int resourceId) {
        mBottomView = mLayoutInflater.inflate(resourceId, this, true);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mContentView = getChildAt(0);
        mBottomView = getChildAt(1);
        measureChild(mBottomView, widthMeasureSpec, heightMeasureSpec);
        int bottomViewHeight = mBottomView.getMeasuredHeight();
        measureChild(mContentView, widthMeasureSpec, heightMeasureSpec);
        int contentHeight = mContentView.getMeasuredHeight();
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), bottomViewHeight +contentHeight  + getPaddingBottom() + getPaddingTop());

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mContentView = getChildAt(0);
        mBottomView = getChildAt(1);

        mContentView.layout(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), mContentView.getMeasuredHeight());
        mBottomView.layout(getPaddingLeft(), mContentView.getHeight() - mBottomBorderHeigth, getWidth() - getPaddingRight(), getMeasuredHeight() - mBottomBorderHeigth);

    }


}
