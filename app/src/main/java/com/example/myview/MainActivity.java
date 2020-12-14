package com.example.myview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listview);

        List<String> list = new ArrayList<>();
        for(int i = 0 ; i<=5 ;i++){
            list.add("第"+i+"条信息");
        }
        ListAdapter listAdapter = new ListAdapter(list,this);
        listView.setAdapter(listAdapter);

        loadingView = new LoadingView(this,R.style.CustomDialog);
        loadingView.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingView.dismiss();
            }
        },10000);
    }
}
