package com.example.myview.paintactivity;

import android.graphics.Path;

interface IBrush {
    void down(Path path,float x,float y);
    void move(Path path,float x,float y);
    void up(Path path,float x,float y);
}
