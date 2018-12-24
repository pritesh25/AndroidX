package com.example.user.androidx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context applicationContext;
    private String[] data;

    public RecyclerViewAdapter(Context applicationContext, String[] data) {
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
            holder1.text.setText(data[position]);
        }
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public MyViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.text);
        }
    }
}
