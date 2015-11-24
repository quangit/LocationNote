package com.example.quang11t1.locationnote.activity.map;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quang11t1.locationnote.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;


public abstract class MapBase extends Fragment {

    private GoogleMap mMap;

    protected abstract void startDemo();

    protected int getLayoutId(){
        return R.layout.fragment_map_base;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = null;
        try {
            view = inflater.inflate(R.layout.fragment_map_base, container, false);

        } catch(InflateException e) {
            e.printStackTrace();
            System.out.println("Predicted Time Fragment");
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpMapIfNeeded();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap != null) {
            return;
        }
        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        if (mMap != null) {
            startDemo();
        }
    }

    protected GoogleMap getMap() {
        setUpMapIfNeeded();
        return mMap;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(getChildFragmentManager().findFragmentById(R.id.map)!= null)
            getFragmentManager().beginTransaction().remove(getChildFragmentManager().findFragmentById(R.id.map)).commit();
    }

}
