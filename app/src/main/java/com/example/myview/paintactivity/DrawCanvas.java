package com.example.myview.paintactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawCanvas extends SurfaceView implements SurfaceHolder.Callback {

    public boolean isDrawing,isRunning;

    private Bitmap bitmap;
    private DrawInvoker drawInvoker;
    private DrawThread drawThread;

    public DrawCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        drawInvoker = new DrawInvoker();
        drawThread = new DrawThread();

        getHolder().addCallback(this);
    }

    public void add(DrawPath drawPath){
        drawInvoker.add(drawPath);
    }

    public void redo(){
        isDrawing = true;
        drawInvoker.redo();
    }

    public void undo(){
        isDrawing = true;
        drawInvoker.undo();
    }

    public boolean canUndo(){
        return drawInvoker.canUndo();
    }

    public boolean canRedo(){
        return drawInvoker.canRedo();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        isRunning = false;
        while(retry){
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class DrawThread extends Thread{
        @Override
        public void run() {
            Canvas canvas = null;
            while (isRunning){
                if(isDrawing){
                    try{
                        canvas = getHolder().lockCanvas(null);
                        if(bitmap == null){
                            bitmap = Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888);
                        }
                        Canvas c = new Canvas(bitmap);
                        c.drawColor(0, PorterDuff.Mode.CLEAR);

                        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                        drawInvoker.execute(c);
                        canvas.drawBitmap(bitmap,0,0,null);
                    }finally {
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                    isDrawing = false;
                }
            }
        }
    }
}
