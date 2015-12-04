package com.example.quang11t1.locationnote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.ArrayAdapterFriend;
import com.example.quang11t1.locationnote.adapter.LocationNoteListAdapter;
import com.example.quang11t1.locationnote.adapter.Location_list_Adapter;
import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.FriendBean;
import com.example.quang11t1.locationnote.modle.Location;
import com.example.quang11t1.locationnote.modle.LocationNoteInfor;
import com.example.quang11t1.locationnote.modle.User;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationNoteList extends AppCompatActivity {

    int idLocation = 0;
    GetJson getJson =new GetJson();
    LocationNoteInfor[] locationNoteInforList ;
    List<Account> listAcount =new ArrayList<>();
   // Account[] accountList;
    private android.support.v7.widget.Toolbar toolbar;
    private ListView recycleView;
    private Location_list_Adapter locationNoteListAdapter;
    private Location[] locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_note_list);
       // toolbar = (Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Intent intent = getIntent();
        idLocation = intent.getIntExtra("idLocation",0);
        System.out.println("========= idlocation ======================= :" + idLocation);

        doStartGetLocationNoteList(idLocation);
        try {
            System.out.println(" cho trong 5s");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recycleView = (ListView)findViewById(R.id.listView_location);

        GetListAccount getListAccount =new GetListAccount();
        getListAccount.execute("a");
    }

    public ArrayList<LocationNoteInfor> getData(){
        System.out.println(" get data to display to screen");
        ArrayList<LocationNoteInfor> data = new ArrayList<LocationNoteInfor>();
        System.out.println("dư lieu khoi tao ban dau :"+data);
        for(int i=0;i<locationNoteInforList.length;i++){
            data.add(locationNoteInforList[i]);
        }

        for(int i=0; i<data.size(); i++){
            System.out.println("id :"+data.get(i).getIdNote()+" account :"+data.get(i).getAccount());
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void doStartGetLocationNoteList(int idLocation)
    {
        GetLocationNoteList getLocationNoteList = new GetLocationNoteList(idLocation,this);
        getLocationNoteList.start();
    }

    private class GetListAccount extends AsyncTask<String,List<Account>,List<Account>> {
        Gson gson=new Gson();
        GetJson getJson=new GetJson();

        ListView listView;

        public GetListAccount() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Account> accounts) {
            super.onPostExecute(accounts);

            locationNoteListAdapter = new Location_list_Adapter(LocationNoteList.this, getData(),accounts);
            recycleView.setAdapter(locationNoteListAdapter);
            recycleView.setItemsCanFocus(true);
            //ArrayAdapterFriend adapter =new ArrayAdapterFriend(getActivity(),R.layout.custom_row_friends,accounts);
            //listView.setAdapter(adapter);
        }

        @Override
        protected void onProgressUpdate(List<Account>... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Account> doInBackground(String... params) {

            for (LocationNoteInfor locationNoteInfor:locationNoteInforList)
            {
                String user = locationNoteInfor.getAccount();
                //if(user.equals(userName)) user=friendBean.getAccount2();
                String getAccountLink =getString(R.string.link)+"login/user?USERNAME="+user;
                String resultAccount = getJson.getStringJson(getAccountLink);
                Account account =gson.fromJson(resultAccount, Account.class);
                listAcount.add(account);
            }
            return listAcount;
        }
    }

    public class GetLocationNoteList extends Thread{
        Context context;
        Gson gson=new Gson();
        int idLocation;
        public GetLocationNoteList(int idLocation, Context context)
        {
            this.idLocation = idLocation;
            this.context=context;
        }

        @Override
        public void run() {
            String inforLocationNoteList= getString(R.string.link)+"Note/list?IDLOCATION="+idLocation;
            System.out.println("link :"+inforLocationNoteList);
            String result = getJson.getStringJson(inforLocationNoteList);
            System.out.println(" ket qua lay duoc :"+result);
            locationNoteInforList = gson.fromJson(result, LocationNoteInfor[].class);

        }
    }


    public class GetAccountList extends Thread{
        Context context;
        Gson gson=new Gson();
        int idLocation;
        public GetAccountList(int idLocation, Context context)
        {
            this.idLocation = idLocation;
            this.context=context;
        }

        @Override
        public void run() {
            String inforLocationNoteList= getString(R.string.link)+"Note/list?IDLOCATION="+idLocation;
            System.out.println("link :"+inforLocationNoteList);
            String result = getJson.getStringJson(inforLocationNoteList);
            System.out.println(" ket qua lay duoc :"+result);
            locationNoteInforList = gson.fromJson(result, LocationNoteInfor[].class);

        }
    }
}
