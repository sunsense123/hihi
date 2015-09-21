package com.unist.npc.queuing;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mintaewon on 2015. 8. 12..
 */
public class CusListAdapter extends ArrayAdapter<CusListItem> {
    private Context context;
    private ArrayList<CusListItem> items;
    int layoutResId;


    public CusListAdapter(Context context, int textViewResourceId, ArrayList<CusListItem> items){
        super(context,textViewResourceId,items);
        this.layoutResId = textViewResourceId;
        this.context=context;
        this.items = items;
        //mTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Questrial_Regular.otf");
        //mBoldTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand_Bold.otf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        ViewGroup vg = (ViewGroup) convertView;
        ViewGroup root = (ViewGroup) parent.findViewById(android.R.id.content);
        CusListHolder holder = null;
        if(v==null){
            LayoutInflater vi =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(layoutResId,parent,false);
            holder = new CusListHolder();
            holder.priority = (TextView) v.findViewById(R.id.cus_priority);
            holder.name = (TextView) v.findViewById(R.id.cus_name);
            holder.party = (TextView) v.findViewById(R.id.cus_party);
            holder.time = (TextView) v.findViewById(R.id.cus_time);
            holder.method = (TextView) v.findViewById(R.id.cus_method);

            v.setTag(holder);

        }
        else{
            holder = (CusListHolder)v.getTag();
        }

        CusListItem cus_item = items.get(position);


        if(cus_item!=null){
            holder.priority.setText(cus_item.cus_priority);
            holder.name.setText(cus_item.cus_name);
            holder.party.setText(cus_item.cus_party);
            //holder.res_name.setTypeface(mTypeface);
            holder.time.setText(cus_item.cus_time);
            //holder.res_cuisine.setTypeface(mTypeface);
            holder.method.setText(cus_item.cus_method);
            //holder.res_distance.setTypeface(mTypeface);
            //holder.res_imgurl = res_item.res_imgurl;

        }
        //setGlobalFont(parent);

        return v;
    }
    static class CusListHolder{
        TextView priority;
        TextView name;
        TextView party;
        TextView method;
        TextView time;

    }
}
