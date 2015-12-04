package com.example.quang11t1.locationnote.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.modle.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quang11t1 on 03/12/2015.
 */
public class CustomRecycleMessageAdapter extends RecyclerView.Adapter<CustomRecycleMessageAdapter.RecycleViewMessageViewHolder> {

    private List<Message> listMessage = new ArrayList<>();
    Context context;

    public CustomRecycleMessageAdapter(List<Message> listMessage,Context context) {
        this.listMessage = listMessage;
        this.context=context;
    }

    public  void updateList(List<Message> listMessage)
    {
        this.listMessage=listMessage;
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecycleViewMessageViewHolder holder, int position) {
        holder.imageViewAvatarSender.setImageResource(R.drawable.ic_account_circle_24dp);
        holder.textViewSender.setText(listMessage.get(position).getAccountByIdSender());
        String contentMessage = listMessage.get(position).getContent().substring(0,140);
        holder.textViewContentReceiver.setText(contentMessage);
    }

    @Override
    public RecycleViewMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_row_recycleview_message, null);
        RecycleViewMessageViewHolder recycleViewMessageViewHolder =new RecycleViewMessageViewHolder(view);
        return recycleViewMessageViewHolder;
    }

    public  class RecycleViewMessageViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageViewAvatarSender;
        public TextView textViewSender;
        public TextView textViewContentReceiver;

        public RecycleViewMessageViewHolder(View itemView) {
            super(itemView);
            this.imageViewAvatarSender = (ImageView) itemView.findViewById(R.id.imageView_avarta_sender);
            this.textViewSender = (TextView) itemView.findViewById(R.id.textView_sender);
            this.textViewContentReceiver = (TextView) itemView.findViewById(R.id.textView_messager_receiver);
        }
    }
}
