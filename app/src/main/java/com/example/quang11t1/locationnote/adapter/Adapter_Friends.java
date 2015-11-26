package com.example.quang11t1.locationnote.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;

import java.util.List;


/**
 * Created by QuangTan on 11/24/2015.
 */
public class Adapter_Friends extends ArrayAdapter<Myfriend> {

    Context mcontext;
    int layoutID;
    ArrayList<Myfriend> mlistcomment=new ArrayList<Myfriend>();
    public Adapter_Friends(Context context,List<Myfriend>objects) {
        super(context, R.layout.custom_friend, objects);
        this.mcontext=context;
        this.mlistcomment = new ArrayList<Myfriend>(objects);
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_friend, null);
        }

        TextView tv_account = (TextView)convertView.findViewById(R.id.tv_account);
        Myfriend myfriend=mlistcomment.get(position);
        tv_account.setText(myfriend.getName());
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imV_profile);

        //convert to circle image view
        RoundImage roundedImage;
        Bitmap bitmap=null;
        bitmap = BitmapFactory.decodeResource(convertView.getResources(), R.drawable.default_profile);
        CircleImage circleImage=new CircleImage();
        imageView.setImageBitmap(circleImage.getCircleBitmap(bitmap));
        return convertView;
    }
}
