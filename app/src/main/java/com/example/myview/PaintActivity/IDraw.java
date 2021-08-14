package com.example.myview.PaintActivity;

import android.graphics.Canvas;

interface IDraw {
    void draw(Canvas canvas);
    void undo();
}
