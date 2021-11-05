package com.example.myview.paintactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.example.myview.databinding.ActivityPaintBinding;

public class PaintActivity extends AppCompatActivity {

    private ActivityPaintBinding binding;

    private DrawCanvas canvas;
    private DrawPath mPath;
    private Paint paint;
    private IBrush brush;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaintBinding.inflate(LayoutInflater.from(this));

        paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        paint.setStrokeWidth(3);

        brush = new NormalBrush();

        canvas = binding.canvas;
        canvas.setOnTouchListener(new DrawTouchListener());

        binding.red.setOnClickListener(v -> {
            paint = new Paint();
            paint.setStrokeWidth(3);
            paint.setColor(0xFFFF0000);
        });

        binding.green.setOnClickListener(v -> {
            paint = new Paint();
            paint.setStrokeWidth(3);
            paint.setColor(0xFF00FF00);
        });

        binding.blue.setOnClickListener(v -> {
            paint = new Paint();
            paint.setStrokeWidth(3);
            paint.setColor(0xFF0000FF);
        });


        binding.undo.setOnClickListener(v -> {
            canvas.undo();
            if(!canvas.canUndo()){
                binding.undo.setEnabled(false);
            }
            binding.undo.setEnabled(true);
        });

        binding.redo.setOnClickListener(v -> {
            canvas.redo();
            if(!canvas.canRedo()){
                binding.redo.setEnabled(false);
            }
            binding.redo.setEnabled(true);
        });

        binding.circle.setOnClickListener(v -> {
            brush = new CircleBrush();
        });

        binding.normal.setOnClickListener(v -> {
            brush = new NormalBrush();
        });

        setContentView(binding.getRoot());
    }

    private class DrawTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if(event.getAction() == MotionEvent.ACTION_DOWN){

                mPath = new DrawPath();
                mPath.paint = paint;
                mPath.path = new Path();
                brush.down(mPath.path,event.getX(),event.getY());

            }else if(event.getAction() == MotionEvent.ACTION_MOVE){

                brush.move(mPath.path,event.getX(),event.getY());

            }else if(event.getAction() == MotionEvent.ACTION_UP){

                brush.up(mPath.path,event.getX(),event.getY());
                canvas.add(mPath);
                canvas.isDrawing = true;
                binding.undo.setEnabled(true);
                binding.redo.setEnabled(false);
            }

            return true;
        }
    }
}