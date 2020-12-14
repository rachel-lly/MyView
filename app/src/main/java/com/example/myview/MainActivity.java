package com.example.myview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.example.myview.BottomDragLayout.BottomDragActivity;
import com.example.myview.LoadingView.LoadingView;
import com.example.myview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding mainBinding;
    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));

        setClick();

        setContentView(mainBinding.getRoot());



    }

    private void setClick() {
        mainBinding.bottomdragLayoutButton.setOnClickListener(this);
        mainBinding.loadingViewButton.setOnClickListener(this);
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingView.dismiss();
                    }
                },10000);

                break;
        }
    }
}
