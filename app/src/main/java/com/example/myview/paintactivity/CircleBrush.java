package com.example.myview.paintactivity;

import android.graphics.Path;

class CircleBrush implements IBrush{
    @Override
    public void down(Path path, float x, float y) {

    }

    @Override
    public void move(Path path, float x, float y) {
        path.addCircle(x,y,10,Path.Direction.CW);
    }

    @Override
    public void up(Path path, float x, float y) {

    }
}
