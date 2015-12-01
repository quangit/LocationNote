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
import com.example.quang11t1.locationnote.modle.Account;

import java.io.InputStream;
import java.util.List;

/**
 * Created by quang11t1 on 30/11/2015.
 */
public class ArrayAdapterFriend extends ArrayAdapter<Account> {
    List<Account> listAccount;
    Activity context;

    public ArrayAdapterFriend(Activity context, int resource, List<Account> listAccount) {
        super(context, resource, listAccount);
        this.listAccount = listAccount;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        convertView=inflater.inflate(R.layout.custom_row_friends, null);
        TextView tv =(TextView) convertView.findViewById(R.id.textView_name_friend);
        tv.setText(listAccount.get(position).getUsername());
        ImageView imageView =(ImageView) convertView.findViewById(R.id.imageView_avarta_friend);
        DownloadImageTask downloadImageTask =new DownloadImageTask(imageView);
        downloadImageTask.execute(listAccount.get(position).getImage());
        return convertView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
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
