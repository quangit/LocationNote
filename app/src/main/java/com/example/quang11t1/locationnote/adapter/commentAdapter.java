package com.example.quang11t1.locationnote.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.Comment;
import com.example.quang11t1.locationnote.R;

/**
 * Created by luongvien_binhson on 20-Nov-15.
 */
public class commentAdapter extends ArrayAdapter<Comment>{
    Context mcontext;

    int resource;

    ArrayList<Comment> mlistcomment=new ArrayList<Comment>();

    List<Account> listAccount;
    public commentAdapter(Context context, ArrayList<Comment> objects) {
        super(context, R.layout.custom_commention, objects);
        this.mcontext=context;
        this.mlistcomment=new ArrayList<Comment>(objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_commention, null);

        }
        TextView txtviewNameNick=(TextView) convertView.findViewById(R.id.textViewNameNick);
        ImageView imgviewAvatar=(ImageView) convertView.findViewById(R.id.imageViewAvatarComment);
        TextView txtviewComment=(TextView) convertView.findViewById(R.id.textViewComment);
        Comment comment = mlistcomment.get(position);
        txtviewComment.setText(comment.getContent());
        imgviewAvatar.setImageResource(R.drawable.vie);
        txtviewNameNick.setText("Luong vien");
        return convertView;

    }

}
