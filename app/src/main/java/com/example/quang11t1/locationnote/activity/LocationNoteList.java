package com.example.quang11t1.locationnote.activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.LocationNoteListAdapter;
import com.example.quang11t1.locationnote.modle.LocationNoteInfor;

import java.util.ArrayList;
import java.util.List;

public class LocationNoteList extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private RecyclerView recycleView;
    private LocationNoteListAdapter locationNoteListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_note_list);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationNoteListAdapter = new LocationNoteListAdapter(this, getData());
        recycleView = (RecyclerView)findViewById(R.id.locationnote_list);
        recycleView.setAdapter(locationNoteListAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static List<LocationNoteInfor> getData(){
        System.out.println(" get data to display to screen");
        List<LocationNoteInfor> data = new ArrayList<>();
        int[] iconProfiles = {R.drawable.profile, R.drawable.profile, R.drawable.profile, R.drawable.profile, R.drawable.profile};
        String[] userNames = {"Chu Thien Tam", "Ngo Duc Quang", "Nguyen Van Quang Tan", "Le Luong Vien", "Bla bla bla"};
        for(int i=0; i<iconProfiles.length && i< userNames.length; i++){
            LocationNoteInfor information = new LocationNoteInfor();
            information.setUserName(userNames[i]);
            information.setAddress("Molly Coffee "+i);
            information.setComment("Tuyet voi ong mat troi");
            information.setNumberOfLike("10");
            information.setGetNumberOfComment("20");
            information.setIdImage(iconProfiles[i]);
            information.setImageLike(R.drawable.like);
            information.setImageComment(R.drawable.comment);
            data.add(information);
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

        return super.onOptionsItemSelected(item);
    }

}
