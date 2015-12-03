package com.example.quang11t1.locationnote.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.activity.FriendFragment;
import com.example.quang11t1.locationnote.modle.Account;

import java.io.InputStream;
import java.util.List;

/**
 * Created by quang11t1 on 01/12/2015.
 */
public class ArrayAdapterAcceptFriend extends ArrayAdapter<Account>  {
    List<Account> listAccount;
    Activity context;

    private EditPlayerAdapterCallback callback;
    public interface EditPlayerAdapterCallback {


        void acceptFriend(int position);
        void noAcceptFriend(int position);

    }

    public void setCallback(EditPlayerAdapterCallback callback){

        this.callback = callback;
    }

    public ArrayAdapterAcceptFriend(Activity context, int resource, List<Account> listAccount) {
        super(context, resource,listAccount);
        this.listAccount = listAccount;
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ItemHolder holder =new ItemHolder();

        LayoutInflater inflater= context.getLayoutInflater();
        convertView=inflater.inflate(R.layout.custom_row_accept_friend, null);
        holder.textViewNameAcceptFriend =(TextView) convertView.findViewById(R.id.textView_name_accept_friend);
        holder.textViewNameAcceptFriend.setText(listAccount.get(position).getUsername());
        holder.imageViewAvaterAcceptFriend =(ImageView) convertView.findViewById(R.id.imageView_avarta_accept_friend);
        DownloadImageTask downloadImageTask =new DownloadImageTask(holder.imageViewAvaterAcceptFriend);
        downloadImageTask.execute(listAccount.get(position).getImage());
        holder.buttonAccept =(Button) convertView.findViewById(R.id.button_accept);
        holder.buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (customListner != null) {
                   // Toast.makeText(context,"ădawhd",Toast.LENGTH_LONG).show();
                if(callback != null) {
                    callback.acceptFriend(position);
                }
               // }
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"ădawhd",Toast.LENGTH_LONG).show();
            }
        });
        holder.buttonNoAccept =(Button) convertView.findViewById(R.id.button_noaccept);
        holder.buttonNoAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null) {
                    callback.noAcceptFriend(position);
                }
            }
        });
        convertView.setTag(holder);

        return convertView;
    }
    public class ItemHolder{
        TextView textViewNameAcceptFriend;
        ImageView imageViewAvaterAcceptFriend;
        Button buttonAccept;
        Button buttonNoAccept;
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
