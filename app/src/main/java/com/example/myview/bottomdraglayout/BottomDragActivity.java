package com.example.myview.bottomdraglayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.myview.R;

import java.util.ArrayList;
import java.util.List;

public class BottomDragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_drag);

        ListView listView = findViewById(R.id.listview);

        List<String> list = new ArrayList<>();
        for(int i = 0 ; i<=5 ;i++){
            list.add("第"+i+"条信息");
        }
        ListAdapter listAdapter = new ListAdapter(list,this);
        listView.setAdapter(listAdapter);
    }
}