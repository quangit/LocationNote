package com.example.quang11t1.locationnote.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.activity.detailNoteActivity;
import com.example.quang11t1.locationnote.activity.detail_message;
import com.example.quang11t1.locationnote.modle.LocationNoteInfor;
import com.example.quang11t1.locationnote.support.GetJson;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by luongvien_binhson on 01-Dec-15.
 */
public class Location_list_Adapter extends ArrayAdapter<LocationNoteInfor> {
    Context mcontext;

    int idAccount;
    LocationNoteInfor locationNoteInfor;
    TextView userName, locationName, content, numberOfLike, numberOfComment, postTime;
    ImageView iconProfile, imgLike, imgComment;
    ArrayList<LocationNoteInfor> mlistlocationinfor=new ArrayList<LocationNoteInfor>();
    public Location_list_Adapter(Context context, ArrayList<LocationNoteInfor> objects,int idAccount) {
        super(context, R.layout.custom_locationnote_list, objects);
        this.mcontext=context;
        this.mlistlocationinfor=new ArrayList<LocationNoteInfor>(objects);
        this.idAccount =idAccount;

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
        locationNoteInfor=mlistlocationinfor.get(position);
        iconProfile = (ImageView)convertView.findViewById(R.id.img_profile);

        DownloadImageTask downloadImageTask =new DownloadImageTask(iconProfile);
        downloadImageTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, locationNoteInfor.getAccount());

        imgLike = (ImageView)convertView.findViewById(R.id.icon_like);
        imgComment = (ImageView)convertView.findViewById(R.id.icon_comment);

        userName.setText(locationNoteInfor.getAccount());
        locationName.setText(locationNoteInfor.getLocation());
        if(locationNoteInfor.getContent().length()>140){
            content.setText(locationNoteInfor.getContent().substring(0,139));
        }else content.setText(locationNoteInfor.getContent());
        numberOfLike.setText(locationNoteInfor.getNumberOfLike()+"");
        numberOfComment.setText(locationNoteInfor.getNumberOfComment()+"");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        postTime.setText(sdf.format(locationNoteInfor.getTime()).toString());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"thihi",Toast.LENGTH_LONG).show();
                locationNoteInfor=mlistlocationinfor.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("locationNoteInfo", locationNoteInfor);
                bundle.putInt("id",idAccount);
                Intent intent=new Intent(mcontext, detailNoteActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data",bundle);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        GetJson getJson =new GetJson();
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String user = urls[0];
            String getMessageLink=  mcontext.getString(R.string.link)+"login/getLinkAvatar?USERNAME="+user;
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
}
