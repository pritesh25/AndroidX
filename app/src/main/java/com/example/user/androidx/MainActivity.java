package com.example.user.androidx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;

import com.example.user.androidx.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private RecyclerViewAdapter recyclerViewAdapter;
    private String[] data = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19"};

    private TextView tv1;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        ModelData data = new ModelData();
        data.setCelsius("27");
        activityMainBinding.setTv1(data);

//        recyclerview = findViewById(R.id.recyclerview);
//        recyclerview.setHasFixedSize(true);
//        recyclerview.setLayoutManager(new LinearLayoutManager(this));
//
//        recyclerViewAdapter = new RecyclerViewAdapter(this,data);
//        recyclerview.setAdapter(recyclerViewAdapter);

        tv1 = findViewById(R.id.tv1);


    }
}