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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView txtViewuserInfor,editTextEmail;
    EditText editTextpass,editTextpass1,editTextpass2;
    GetJson getJson =new GetJson();
    int id;
    String username;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =this.getArguments();
        id=bundle.getInt("id");
        username=bundle.getString("user");
        doStartGet();
        txtViewuserInfor=(TextView) getActivity().findViewById(R.id.txtviewUserInformation);
        editTextEmail=(TextView) getActivity().findViewById(R.id.editTextEmail);
        editTextpass=(EditText) getActivity().findViewById(R.id.editTextPass);
        editTextpass1=(EditText) getActivity().findViewById(R.id.editTextPass1);
        editTextpass2=(EditText) getActivity().findViewById(R.id.editTextPass2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_new_information, container, false);

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
                Account account = gson.fromJson(inforAccount,Account.class);
          try {
              txtViewuserInfor.setText(account.getUsername());
              editTextEmail.setText(account.getEmail());
          }
          catch (Exception e){
              System.out.println("lol"+account.getUsername()+account.getEmail());
          }
        }
    }
}
