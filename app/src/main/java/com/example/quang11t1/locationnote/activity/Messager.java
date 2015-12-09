package com.example.quang11t1.locationnote.activity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.ArrayAdapterCustomMessage;
import com.example.quang11t1.locationnote.adapter.ArrayAdapterFriend;
import com.example.quang11t1.locationnote.adapter.CustomRecycleMessageAdapter;
import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.FriendBean;
import com.example.quang11t1.locationnote.modle.Message;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Messager extends Fragment {

    CustomRecycleMessageAdapter customRecycleMessageAdapter;
    RecyclerView recyclerView_Message;
    ListView listView_message;
    int idAccount ;
    String userName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idAccount =getArguments().getInt("id",0);
        userName =getArguments().getString("user");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_messager, container, false);

        listView_message =(ListView) view.findViewById(R.id.listView_message);
        /*recyclerView_Message =(RecyclerView) view.findViewById(R.id.recycler_message);
        recyclerView_Message.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_Message.setLayoutManager(linearLayoutManager);*/



        //customRecycleMessageAdapter =new CustomRecycleMessageAdapter(list,getContext());
        //recyclerView_Message.setAdapter(customRecycleMessageAdapter);

        GetListMessage getListMessage =new GetListMessage(listView_message);
        getListMessage.execute("a");

        return view;
    }

    private class GetListMessage extends AsyncTask<String,List<Message>,List<Message>> {
        Gson gson=new Gson();
        GetJson getJson=new GetJson();

      //  RecyclerView recyclerView;
        ListView listView;

        public GetListMessage(ListView listView) {
            this.listView=listView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            super.onPostExecute(messages);

            //customRecycleMessageAdapter =new CustomRecycleMessageAdapter(messages);
            //recyclerView.setAdapter(customRecycleMessageAdapter);
            ArrayAdapterCustomMessage arrayAdapterCustomMessage =new ArrayAdapterCustomMessage(getActivity(),messages);
            listView_message.setAdapter(arrayAdapterCustomMessage);

        }

        @Override
        protected void onProgressUpdate(List<Message>... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Message> doInBackground(String... params) {
            String getMessageLink= getString(R.string.link)+"Message/GetMessage?IDRECEIVER="+idAccount;
            String result = getJson.getStringJson(getMessageLink);
            System.out.println("chuoi lay ve duoc :" + result);
            Message[] messages = gson.fromJson(result,Message[].class);
            List<Message> messageList = Arrays.asList(messages);
            return messageList;
        }
    }

}
