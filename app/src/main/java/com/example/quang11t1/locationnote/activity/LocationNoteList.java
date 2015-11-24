package com.example.quang11t1.locationnote.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.LocationNoteListAdapter;
import com.example.quang11t1.locationnote.modle.LocationNoteInfor;

import java.util.ArrayList;
import java.util.List;

public class LocationNoteList extends Fragment {
    private android.support.v7.widget.Toolbar toolbar;
    private RecyclerView recycleView;
    private LocationNoteListAdapter locationNoteListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = null;
        try {
            view = inflater.inflate(R.layout.fragment_location_note_list, container, false);

        } catch(InflateException e) {
            e.printStackTrace();
            System.out.println("Predicted Time Fragment");
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        locationNoteListAdapter = new LocationNoteListAdapter(getActivity(), getData());
        recycleView = (RecyclerView)getActivity().findViewById(R.id.locationnote_list);
        recycleView.setAdapter(locationNoteListAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static List<LocationNoteInfor> getData(){
        System.out.println(" get data to display to screen");
        List<LocationNoteInfor> data = new ArrayList<>();
        int[] iconProfiles = {R.drawable.profile, R.drawable.profile, R.drawable.profile, R.drawable.profile};
        String[] userNames = {"Chu Thien Tam", "Ngo Duc Quang", "Nguyen Van Quang Tan", "Le Luong Vien"};
        for(int i=0; i<iconProfiles.length && i< userNames.length; i++){
            LocationNoteInfor information = new LocationNoteInfor();
            information.setUserName(userNames[i]);
            information.setAddress("Molly Coffee");
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
