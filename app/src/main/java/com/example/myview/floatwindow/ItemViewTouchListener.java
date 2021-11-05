package com.example.myview.floatwindow;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class ItemViewTouchListener implements View.OnTouchListener{

    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;

    private final WindowManager.LayoutParams layoutParams;
    private final WindowManager windowManager;

    private final View view;

    public ItemViewTouchListener(WindowManager.LayoutParams layoutParams, WindowManager windowManager,View view) {
        this.layoutParams = layoutParams;
        this.windowManager = windowManager;
        this.view = view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                initialX = layoutParams.x;
                initialY = layoutParams.y;
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_MOVE:

                layoutParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                layoutParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                windowManager.updateViewLayout(view, layoutParams);

                break;

        }

        return true;
    }
}
