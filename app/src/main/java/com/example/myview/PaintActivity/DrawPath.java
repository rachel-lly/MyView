package com.example.myview.PaintActivity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

class DrawPath implements IDraw {

    public Path path;
    public Paint paint;

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path,paint);
    }

    @Override
    public void undo() {

    }
}
