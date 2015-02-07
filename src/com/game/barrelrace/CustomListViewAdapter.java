package com.game.barrelrace;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import appsrox.example.accelerometer.R;
import android.R.color;
import android.app.Activity;
import android.app.LauncherActivity.ListItem;
import android.content.Context;
import android.graphics.Color;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * 
 * @author Kamesh Santhanam  -  kxs142530
 *
 * Adapter for listView of contacts in MainActivity 
 */
public class CustomListViewAdapter extends ArrayAdapter<HighScore>{
 
    Context context;
    LinearLayout linearLayout;
    public CustomListViewAdapter(Context context, int resourceId,List<HighScore> items) {
        super(context, resourceId, items);
        this.context = context;
        
    }
 
    /**
     * private view holder class
     */
    private class ViewHolder {
        TextView nameView;
        TextView timeView;
      
    }
    
    /**
     * the getView method inflates the layout for each list item and populates it with appropriate data from the contacts arrayList.
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;        
        HighScore rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.highscores_listitem, null);
            holder = new ViewHolder();       
            holder.nameView = (TextView) convertView.findViewById(R.id.nameTextHiSc);         
            holder.timeView = (TextView) convertView.findViewById(R.id.timeTextHiSc);
            convertView.setTag(holder);
        } 
        else{
            holder = (ViewHolder) convertView.getTag();
        }    
        holder.nameView.setText(rowItem.getName().toString());
        holder.timeView.setText(""+ (new SimpleDateFormat("mm:ss:SSS")).format(new Date(rowItem.getMilliSeconds())));
        return convertView;
    }
}


