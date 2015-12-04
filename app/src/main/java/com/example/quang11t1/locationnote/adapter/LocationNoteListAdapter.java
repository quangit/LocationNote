package com.example.quang11t1.locationnote.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.activity.MainActivity;
import com.example.quang11t1.locationnote.activity.detail_message;
import com.example.quang11t1.locationnote.modle.LocationNoteInfor;
import com.example.quang11t1.locationnote.support.GetJson;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

public class LocationNoteListAdapter extends RecyclerView.Adapter<LocationNoteListAdapter.MyViewHolder>{
    List<LocationNoteInfor> data ;
    Context context;
    private LayoutInflater inflater;
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
        holder.userName.setText("wdjkawhkjdwa");
        holder.locationName.setText(currentInformation.getLocation());
        holder.content.setText(currentInformation.getContent());
        holder.numberOfLike.setText(""+currentInformation.getNumberOfLike());
        holder.numberOfComment.setText(""+currentInformation.getNumberOfComment());
        holder.postTime.setText(""+currentInformation.getTime().toString());
        DownloadImageTask downloadImageTask =new DownloadImageTask(holder.iconProfile);
        downloadImageTask.execute(currentInformation.getAccount());

    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        GetJson getJson =new GetJson();
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String user = urls[0];
            String getMessageLink=  context.getString(R.string.link)+"login/getLinkAvatar?USERNAME="+user;
            String result = getJson.getStringJson(getMessageLink);
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(result).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context.getApplicationContext(),detail_message.class);
           context.startActivity(intent);
        }
    }
}
