package com.example.quang11t1.locationnote.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quang11t1.locationnote.R;

import java.util.List;

/**
 * Created by quang11t1 on 30/11/2015.
 */
public class ArrayAdapterFriend extends ArrayAdapter<String> {
    List<String> list;
    Activity context;

    public ArrayAdapterFriend(Activity context, int resource, List<String> list) {
        super(context, resource, list);
        this.list = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        convertView=inflater.inflate(R.layout.custom_row_friends, null);
        TextView tv =(TextView) convertView.findViewById(R.id.textView_name_friend);
        tv.setText(list.get(position));
        return convertView;
    }
}
