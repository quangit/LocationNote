package com.example.quang11t1.locationnote.activity;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.ArrayAdapterCustomMessage;
import com.example.quang11t1.locationnote.adapter.ArrayAdapterCustomSendedMessage;
import com.example.quang11t1.locationnote.modle.Message;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SendedMessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SendedMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendedMessageFragment extends Fragment {

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
        View view= inflater.inflate(R.layout.fragment_sended_message, container, false);

        listView_message =(ListView) view.findViewById(R.id.listView_sended_message);

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
            ArrayAdapterCustomSendedMessage arrayAdapterCustomSendedMessage =new ArrayAdapterCustomSendedMessage(getActivity(),messages);
            listView_message.setAdapter(arrayAdapterCustomSendedMessage);

        }

        @Override
        protected void onProgressUpdate(List<Message>... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Message> doInBackground(String... params) {
            String getMessageLink= getString(R.string.link)+"Message/GetSendMessage?IDSENDER="+idAccount;
            String result = getJson.getStringJson(getMessageLink);
            System.out.println("chuoi lay ve duoc :" + result);
            Message[] messages = gson.fromJson(result,Message[].class);
            List<Message> messageList = Arrays.asList(messages);
            return messageList;
        }
    }
}
