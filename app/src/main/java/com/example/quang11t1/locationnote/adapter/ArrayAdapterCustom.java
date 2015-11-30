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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quang11t1 on 30/11/2015.
 */
public class ArrayAdapterCustom extends ArrayAdapter<Account> {
    Context context=null;
    List<Account> listAccount=null;
    int layoutId;
    /**
     * Constructor này dùng để khởi tạo các giá trị
     * từ MainActivity truyền vào
     * @param context : là Activity từ Main
     * @param layoutId: Là layout custom do ta tạo (my_item_layout.xml)
     * @param arr : Danh sách nhân viên truyền từ Main
     */
    public ArrayAdapterCustom(Context context,
                          int layoutId,
                          List<Account>arr){
        super(context, layoutId, arr);
        this.context=context;
        this.layoutId=layoutId;
        this.listAccount=arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_friend,parent,false);

        TextView tv_account = (TextView)convertView.findViewById(R.id.tv_account);
        //Account account=listAccount.get(position);
        tv_account.setText("ajdghjaw");
        //ImageView imageView = (ImageView)convertView.findViewById(R.id.imV_profile);
        //DownloadImageTask downloadImageTask =new DownloadImageTask(imageView);
        //downloadImageTask.execute(account.getImage());
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
