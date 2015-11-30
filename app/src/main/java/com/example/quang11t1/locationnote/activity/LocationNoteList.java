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
import android.widget.ListView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.LocationNoteListAdapter;
import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.Location;
import com.example.quang11t1.locationnote.modle.LocationNoteInfor;
import com.example.quang11t1.locationnote.modle.User;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationNoteList extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private RecyclerView recycleView;
    private LocationNoteListAdapter locationNoteListAdapter;
    private Location[] locationList;
    int idLocation = 0;
    GetJson getJson =new GetJson();
    LocationNoteInfor[] locationNoteInforList ;
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
        System.out.println("========= idlocation ======================= :" + idLocation);

        //doStartGetLocationNoteList(idLocation);
        /*try {
            System.out.println(" cho trong 5s");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //locationNoteListAdapter = new LocationNoteListAdapter(this, getData());
        recycleView = (RecyclerView)findViewById(R.id.locationnote_list);
        //recycleView.setAdapter(locationNoteListAdapter);
        GetListLocation getListLocation =new GetListLocation(recycleView);
        getListLocation.execute(" ");
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    public  List<LocationNoteInfor> getData(){
        System.out.println(" get data to display to screen");
        List<LocationNoteInfor> data = new ArrayList<>();
        System.out.println("dư lieu khoi tao ban dau :"+data);
         data = Arrays.asList(locationNoteInforList);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            locationNoteInforList = gson.fromJson(result,LocationNoteInfor[].class);
        }
    }

    public void doStartGetLocationNoteList(int idLocation)
    {
        GetLocationNoteList getLocationNoteList = new GetLocationNoteList(idLocation,this);
        getLocationNoteList.start();
    }


    ///get note
    public class GetListLocation extends AsyncTask<String,List<LocationNoteInfor>,List<LocationNoteInfor>> {
        Gson gson = new Gson();
        GetJson getJson = new GetJson();

        RecyclerView recyclerView;

        public GetListLocation(RecyclerView recyclerView) {
            this.recyclerView=recyclerView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<LocationNoteInfor> locations) {
            super.onPostExecute(locations);
            locationNoteListAdapter = new LocationNoteListAdapter(getApplicationContext(),locations);
            recyclerView.setAdapter(locationNoteListAdapter);

        }

        @Override
        protected void onProgressUpdate(List<LocationNoteInfor>... values) {
            super.onProgressUpdate(values);


        }

        @Override
        protected List<LocationNoteInfor> doInBackground(String... params) {
            String inforLocationNoteList= getString(R.string.link)+"Note/list?IDLOCATION="+idLocation;
            String result = getJson.getStringJson(inforLocationNoteList);
            System.out.println("chuoi lay ve duoc :" + result);
            LocationNoteInfor[] locationList = gson.fromJson(result, LocationNoteInfor[].class);
            return Arrays.asList(locationList);
        }
    }

}
