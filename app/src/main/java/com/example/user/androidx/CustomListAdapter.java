package com.example.user.androidx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private Context context; //context
    private List<AppList> items; //data source of the list adapter
	
    //public constructor 
    public CustomListAdapter(Context context, List<AppList> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            convertView.setLayoutParams(new GridView.LayoutParams(150, 200));
        }
	
        // get current item to be displayed
        AppList currentItem = (AppList) getItem(position);
  
        // get the TextView for item name and item description
        TextView textViewItemName = convertView.findViewById(R.id.text);
        ImageView icon = convertView.findViewById(R.id.icon);

        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentItem.getName());

        icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        icon.setImageDrawable(currentItem.getIcon());

        // returns the view for the current row
        return convertView;
    }
}