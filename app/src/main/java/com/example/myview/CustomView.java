package com.example.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class CustomView extends View {

    private int lastX;
    private int lastY;


    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context) {
        super(context);
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(100, widthMeasureSpec);
        int height = getMySize(100, heightMeasureSpec);

        if (width < height) {
            height = width;
        } else {
            width = height;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int r = getMeasuredWidth()/2;
        int centerX = lastX+r;
        int centerY = lastY+r;

        Paint paint = new Paint();
        paint.setAlpha(10);
        paint.setColor(Color.LTGRAY);

        canvas.drawCircle(centerX,centerY,r,paint);


    }

    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
//                //计算移动距离
                int offsetX = x - lastX;
                int offsetY = y - lastY;

////                layout(getLeft()+offsetX,getTop()+offsetY,
////                        getRight()+offsetX,getBottom()+offsetY);

//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);

                ((View)getParent()).scrollBy(-offsetX,-offsetY);
                break;
        }
        return true;
    }
}