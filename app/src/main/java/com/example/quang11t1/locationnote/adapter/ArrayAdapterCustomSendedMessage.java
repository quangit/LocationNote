package com.example.quang11t1.locationnote.adapter;

import android.app.Activity;
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
import com.example.quang11t1.locationnote.modle.Message;
import com.example.quang11t1.locationnote.support.GetJson;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by quang11t1 on 04/12/2015.
 */

public class ArrayAdapterCustomSendedMessage extends ArrayAdapter<Message> {
    List<Message> listMessage;
    Activity context;

    public ArrayAdapterCustomSendedMessage(Activity context, List<Message> messages) {
        super(context, R.layout.custom_row_recycleview_message, messages);
        this.listMessage = messages;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        convertView=inflater.inflate(R.layout.custom_row_recycleview_message, null);
        Message message =listMessage.get(position);
        TextView tv_sender =(TextView) convertView.findViewById(R.id.textView_sender);
        tv_sender.setText(message.getAccountByIdReceiver());
        TextView tv_content =(TextView) convertView.findViewById(R.id.textView_messager_receiver);
        tv_content.setText(message.getContent());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
        TextView tv_time =(TextView) convertView.findViewById(R.id.textView_time_receive);
        tv_time.setText(sdf.format(message.getTime()).toString());
        ImageView imageView =(ImageView) convertView.findViewById(R.id.imageView_avarta_sender);
        DownloadImageTask downloadImageTask =new DownloadImageTask(imageView);
        downloadImageTask.execute(message.getAccountByIdReceiver());

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

