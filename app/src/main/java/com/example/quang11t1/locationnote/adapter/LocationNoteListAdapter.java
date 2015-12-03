package com.example.quang11t1.locationnote.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.modle.LocationNoteInfor;

import java.util.Collections;
import java.util.List;

public class LocationNoteListAdapter extends RecyclerView.Adapter<LocationNoteListAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    List<LocationNoteInfor> data = Collections.emptyList();
    Context context;
    public LocationNoteListAdapter(Context context, List<LocationNoteInfor> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
        System.out.println("size data is tranfered" +data.size());
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate( R.layout.custom_locationnote_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LocationNoteInfor currentInformation = data.get(position);
        holder.userName.setText(currentInformation.getAccount());
        holder.locationName.setText(currentInformation.getLocation());
        holder.content.setText(currentInformation.getContent());
        holder.numberOfLike.setText(""+currentInformation.getNumberOfLike());
        holder.numberOfComment.setText(""+currentInformation.getNumberOfComment());
        holder.postTime.setText(""+currentInformation.getTime().toString());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName, locationName, content, numberOfLike, numberOfComment, postTime;
        ImageView iconProfile, imgLike, imgComment;
        public MyViewHolder(View itemView) {
            super(itemView);
            userName = (TextView)itemView.findViewById(R.id.username);
            locationName = (TextView)itemView.findViewById(R.id.location_name);
            content = (TextView)itemView.findViewById(R.id.content);
            numberOfLike = (TextView)itemView.findViewById(R.id.txt_number_of_like);
            numberOfComment = (TextView)itemView.findViewById(R.id.txt_number_of_comment);
            postTime = (TextView)itemView.findViewById(R.id.txt_time_post);
            iconProfile = (ImageView)itemView.findViewById(R.id.img_profile);
            imgLike = (ImageView)itemView.findViewById(R.id.icon_like);
            imgComment = (ImageView)itemView.findViewById(R.id.icon_comment);
        }
    }
}
