package com.example.user.androidx;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.androidx.library.SqaureImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();
    private Context applicationContext;
    private List<AppList> data;

    public RecyclerViewAdapter(Context applicationContext, List<AppList> data) {
        this.applicationContext=applicationContext;
        this.data=data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MyViewHolder)
        {
            MyViewHolder holder1 = (MyViewHolder)holder;
            holder1.text.setText(data.get(position).getName());
            holder1.icon.setImageDrawable(data.get(position).getIcon());
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG,"size = "+data.size());
        return data.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private SqaureImageView icon;

        public MyViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.text);
            icon = view.findViewById(R.id.icon);
        }
    }

}