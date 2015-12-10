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
   // Account[] accountList;
    private android.support.v7.widget.Toolbar toolbar;
    private ListView listView;
    private Location_list_Adapter locationNoteListAdapter;
    private Location[] locationList;
    int idAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_note_list);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        idLocation = intent.getIntExtra("idLocation",0);
        idAccount=intent.getIntExtra("id",0);
        System.out.println("========= idlocation ======================= :" + idLocation);

        /*doStartGetLocationNoteList(idLocation);
        try {
            System.out.println(" cho trong 5s");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        listView = (ListView)findViewById(R.id.listView_location);
        GetListNote getListNote =new GetListNote(listView);
        getListNote.execute("a");
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(), detailNoteActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.putExtra("data",bundle);
                getApplicationContext().startActivity(intent);
            }
        });*/
        listView.setItemsCanFocus(true);
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

    private class GetListNote extends AsyncTask<String,List<LocationNoteInfor>,List<LocationNoteInfor>> {
        Gson gson=new Gson();
        GetJson getJson=new GetJson();

        ListView listView;

        public GetListNote(ListView listView) {
            this.listView = listView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<LocationNoteInfor> locationNoteInfors) {
            super.onPostExecute(locationNoteInfors);
            locationNoteListAdapter = new Location_list_Adapter(getApplicationContext(),getData(),idAccount);
            listView.setAdapter(locationNoteListAdapter);
        }

        @Override
        protected void onProgressUpdate(List<LocationNoteInfor>... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<LocationNoteInfor> doInBackground(String... params) {
            String inforLocationNoteList= getString(R.string.link)+"Note/list?IDLOCATION="+idLocation;
            System.out.println("link :"+inforLocationNoteList);
            String result = getJson.getStringJson(inforLocationNoteList);
            System.out.println(" ket qua lay duoc :"+result);
            locationNoteInforList = gson.fromJson(result, LocationNoteInfor[].class);
            return Arrays.asList(locationNoteInforList);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doStartGetLocationNoteList(int idLocation)
    {
        GetLocationNoteList getLocationNoteList = new GetLocationNoteList(idLocation,this);
        getLocationNoteList.start();
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
