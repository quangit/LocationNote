package com.example.quang11t1.locationnote.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.modle.Comment;
import com.example.quang11t1.locationnote.modle.Message;
import com.example.quang11t1.locationnote.support.GetJson;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by quang11t1 on 09/12/2015.
 */
public class ArrayAdapterCustomComment extends ArrayAdapter <Comment> {
    List<Comment> listComment;
    Context context;

    public ArrayAdapterCustomComment(Context context, List<Comment> comments) {
        super(context, R.layout.custom_row_comment, comments);
        this.listComment =comments;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_row_comment, null);

        }
        Comment comment =listComment.get(position);

        ImageView imageViewCommenter =(ImageView) convertView.findViewById(R.id.imageView_avatar_comment);
        TextView textViewNameCommenter =(TextView) convertView.findViewById(R.id.textView_name_comment);
        TextView textViewContentCommenter =(TextView) convertView.findViewById(R.id.textView_content_comment);

        new DownloadImageTask(imageViewCommenter).execute(comment.getAccount());
        textViewNameCommenter.setText(comment.getAccount());
        textViewContentCommenter.setText(comment.getContent());


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
}
