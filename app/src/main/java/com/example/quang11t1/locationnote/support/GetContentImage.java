package com.example.quang11t1.locationnote.support;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.quang11t1.locationnote.R;

import java.io.InputStream;

/**
 * Created by quang11t1 on 09/12/2015.
 */
public class GetContentImage extends AsyncTask<String,Void,Bitmap> {
    ImageView bmImage;
    GetJson getJson =new GetJson();
    Context context;
    public GetContentImage(ImageView bmImage,Context context) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String link = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(link).openStream();
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
