package com.example.quang11t1.locationnote.activity.map;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.quang11t1.locationnote.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
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
        Spinner spinner_maps_type=(Spinner) view.findViewById(R.id.spinner_map_type);
        // String arrMap[]=getResources().getStringArray(R.array.maps_type);
        ArrayAdapter<CharSequence> adapterMap=ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.maps_type,android.R.layout.simple_list_item_1);
        adapterMap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_maps_type.setAdapter(adapterMap);
       spinner_maps_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int type=GoogleMap.MAP_TYPE_NORMAL;
                switch(arg2)
                {
                    case 0:
                        type=GoogleMap.MAP_TYPE_NORMAL;
                        break;
                    case 1:
                        type=GoogleMap.MAP_TYPE_SATELLITE;
                        break;
                    case 2:
                        type=GoogleMap.MAP_TYPE_TERRAIN;
                        break;
                    case 3:
                        type=GoogleMap.MAP_TYPE_HYBRID;
                        break;
                }
                mMap.setMapType(type);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpMapIfNeeded();
    }

    @Override
    public void onPause() {
        super.onPause();
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
