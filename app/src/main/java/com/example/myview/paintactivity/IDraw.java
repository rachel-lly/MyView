package com.example.myview.paintactivity;

import android.graphics.Canvas;

interface IDraw {
    void draw(Canvas canvas);
    void undo();
}
