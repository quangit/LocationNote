package com.example.quang11t1.locationnote.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.Toast;


import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.Adapter_Friends;
import com.example.quang11t1.locationnote.adapter.ArrayAdapterCustom;
import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.FriendBean;
import com.example.quang11t1.locationnote.modle.Location;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Friend extends Fragment{

    ListView lv_friends;
    Adapter_Friends adapter_friends;
    List<Account> listAccount;
    int idAccount;
    String userName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idAccount=getArguments().getInt("idAccount", 0);
        userName = getArguments().getString("userName");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_friend, container, false);


        lv_friends = (ListView) view.findViewById(R.id.lv_friends);

       GetListAccount getListAccount =new GetListAccount(lv_friends);
        getListAccount.execute("");
        Toast.makeText(getContext(),"--------"+idAccount+" ----"+userName,Toast.LENGTH_LONG).show();


        return view;
    }

    public class GetListAccount extends AsyncTask<String,List<Account>,List<Account>>{
        Gson gson=new Gson();
        GetJson getJson=new GetJson();

        ListView listView;

        public GetListAccount(ListView listView) {
            this.listView = listView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Account> accounts) {
            super.onPostExecute(accounts);

            //Adapter_Friends adapter =new Adapter_Friends(getContext(),accounts);
            String[] list =new String[]{"awdaw","adwa","hbrth","asjgsc"};
            //ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,list);
            //Adapter_Friends adapter =new Adapter_Friends(getContext(),accounts);
            ArrayAdapterCustom adapter =new ArrayAdapterCustom(getActivity(),R.layout.custom_friend,accounts);
            listView.setAdapter(adapter);
        }

        @Override
        protected void onProgressUpdate(List<Account>... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Account> doInBackground(String... params) {
            String getFriendLink= getString(R.string.link)+"Friend/myFriend?IDACCOUNT="+idAccount;
            String result = getJson.getStringJson(getFriendLink);
            System.out.println("chuoi lay ve duoc :" + result);
            FriendBean[] friendList = gson.fromJson(result,FriendBean[].class);
            List<Account> listAcount =new ArrayList<>();
            for (FriendBean friendBean:friendList)
            {
                String user = friendBean.getAccount1();
                if(user.equals(userName)) user=friendBean.getAccount2();
                String getAccountLink =getString(R.string.link)+"login/user?USERNAME="+user;
                String resultAccount = getJson.getStringJson(getAccountLink);
                Account account =gson.fromJson(resultAccount, Account.class);
                listAcount.add(account);
            }
            return listAcount;
        }
    }

}
