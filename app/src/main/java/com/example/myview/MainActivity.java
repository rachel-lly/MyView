package com.example.myview;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.myview.BottomDragLayout.BottomDragActivity;
import com.example.myview.LoadingView.LoadingView;
import com.example.myview.PaintActivity.PaintActivity;
import com.example.myview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int GET_DIALOG_PERMISSION = 111;
    private static String TAG = "MainActivity";

    private ActivityMainBinding mainBinding;
    private LoadingView loadingView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));

        setClick();

        setContentView(mainBinding.getRoot());

        try {
            showButton();
        } catch (Exception e) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, GET_DIALOG_PERMISSION);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_DIALOG_PERMISSION) {
            showButton();
        }
    }

    private void showButton(){
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Button button = new Button(MainActivity.this);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,0,0, PixelFormat.TRANSPARENT);

        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.x = 100;
        layoutParams.y = 100;
        button.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = layoutParams.x;
                        initialY = layoutParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        layoutParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        layoutParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(button, layoutParams);
                        return true;
                }
                return false;
            }
        });
        windowManager.addView(button,layoutParams);
    }

    private void setClick() {
        mainBinding.bottomdragLayoutButton.setOnClickListener(this);
        mainBinding.loadingViewButton.setOnClickListener(this);
        mainBinding.gradientTextViewButton.setOnClickListener(this);
        mainBinding.paintActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.bottomdrag_layout_button:

                intent = new Intent(this, BottomDragActivity.class);
                startActivity(intent);

                break;
            case R.id.loadingView_button:

                loadingView = new LoadingView(this,R.style.CustomDialog);
                loadingView.show();
                new Handler().postDelayed(() -> loadingView.dismiss(),10000);

                break;

            case R.id.gradientTextView_button:

                mainBinding.gradientTextViewText.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> mainBinding.gradientTextViewText.setVisibility(View.GONE),10000);


                break;

            case R.id.paint_activity:

                intent = new Intent(this, PaintActivity.class);
                startActivity(intent);

                break;

        }
    }
}
