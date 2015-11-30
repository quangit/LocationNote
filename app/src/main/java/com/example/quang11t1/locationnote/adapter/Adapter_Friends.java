package com.example.quang11t1.locationnote.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quang11t1.locationnote.Editphoto.CircleImage;
import com.example.quang11t1.locationnote.Editphoto.RoundImage;
import com.example.quang11t1.locationnote.Entity.Myfriend;
import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.modle.Account;

import java.io.InputStream;
import java.util.ArrayList;

import java.util.List;


/**
 * Created by QuangTan on 11/24/2015.
 */
public class Adapter_Friends extends ArrayAdapter<Account> {

    Context mcontext;

    List<Account> listAccount=new ArrayList<Account>();
    public Adapter_Friends(Context context,List<Account>listAccount) {
        super(context,R.layout.custom_friend,listAccount);
        this.mcontext=context;
        this.listAccount = listAccount;
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_friend, null);
        }

        TextView tv_account = (TextView)convertView.findViewById(R.id.tv_account);
        Account account=listAccount.get(position);
        tv_account.setText(account.getUsername());
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imV_profile);
        DownloadImageTask downloadImageTask =new DownloadImageTask(imageView);
        downloadImageTask.execute(account.getImage());
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
