package com.example.quang11t1.locationnote.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.activity.detail_message;
import com.example.quang11t1.locationnote.modle.LocationNoteInfor;

import java.util.ArrayList;

/**
 * Created by luongvien_binhson on 01-Dec-15.
 */
public class Location_list_Adapter extends ArrayAdapter<LocationNoteInfor> {
    Context mcontext;

    int resource;
    LocationNoteInfor locationNoteInfor;
    TextView userName, locationName, content, numberOfLike, numberOfComment, postTime;
    ImageView iconProfile, imgLike, imgComment;
    ArrayList<LocationNoteInfor> mlistlocationinfor=new ArrayList<LocationNoteInfor>();
    public Location_list_Adapter(Context context, ArrayList<LocationNoteInfor> objects) {
        super(context, R.layout.custom_locationnote_list, objects);
        this.mcontext=context;
        this.mlistlocationinfor=new ArrayList<LocationNoteInfor>(objects);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_locationnote_list, null);

        }
        userName = (TextView)convertView.findViewById(R.id.username);
        locationName = (TextView)convertView.findViewById(R.id.location_name);
        content = (TextView)convertView.findViewById(R.id.content);
        numberOfLike = (TextView)convertView.findViewById(R.id.txt_number_of_like);
        numberOfComment = (TextView)convertView.findViewById(R.id.txt_number_of_comment);
        postTime = (TextView)convertView.findViewById(R.id.txt_time_post);
        iconProfile = (ImageView)convertView.findViewById(R.id.img_profile);
        imgLike = (ImageView)convertView.findViewById(R.id.icon_like);
        imgComment = (ImageView)convertView.findViewById(R.id.icon_comment);
        locationNoteInfor=mlistlocationinfor.get(position);
        userName.setText(locationNoteInfor.getAccount());
        locationName.setText(locationNoteInfor.getLocation());
        content.setText(locationNoteInfor.getContent());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"thihi",Toast.LENGTH_LONG).show();
                locationNoteInfor=mlistlocationinfor.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("locationNoteInfo", locationNoteInfor);
                Intent intent=new Intent(mcontext, detail_message.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data",bundle);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }
}
