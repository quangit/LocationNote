package com.example.quang11t1.locationnote.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.Adapter_Friends;
import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.FriendBean;
import com.example.quang11t1.locationnote.modle.LocationNoteInfor;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luongvien_binhson on 24-Nov-15.
 */
public class Information extends Fragment {

    TextView txtViewuserInfor,txtViewTextEmail;
   // EditText editTextpass,editTextpass1,editTextpass2;
    GetJson getJson =new GetJson();
    int id;
    String username;
    String user="",email="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =this.getArguments();
        id=bundle.getInt("id");
        username=bundle.getString("user");
        doStartGet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_new_information, container, false);
        txtViewuserInfor=(TextView) view.findViewById(R.id.txtviewUserInformation);
        txtViewTextEmail=(TextView) view.findViewById(R.id.textViewTextEmail);
        return view;
    }

    public void doStartGet()
    {
        GetLocationNoteList getLocationNoteList = new GetLocationNoteList(getContext());
        getLocationNoteList.start();
    }

    public class GetLocationNoteList extends Thread{
        Context context;
        Gson gson=new Gson();
        public GetLocationNoteList(Context context)
        {
            this.context=context;
        }

        @Override
        public void run() {
                String getInforAccount = context.getString(R.string.link)+"login/user?USERNAME="+username;
                String inforAccount = getJson.getStringJson(getInforAccount);
            System.out.println("h" + inforAccount);
            ;
            Account account = gson.fromJson(inforAccount, Account.class);

            try{
            user=account.getUsername();
            email=account.getEmail();}
            catch (NullPointerException e){
                System.out.println(""+e);
            }
           // txtViewuserInfor.setText(user);
          //  txtViewTextEmail.setText(email);
        }
    }
}
