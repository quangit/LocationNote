package com.example.quang11t1.locationnote.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.activity.map.MapBase;
import com.example.quang11t1.locationnote.activity.model.LocationNote;
import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.Location;
import com.example.quang11t1.locationnote.modle.User;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.List;
import java.util.Random;
public class Home extends MapBase implements ClusterManager.OnClusterClickListener<LocationNote>, ClusterManager.OnClusterInfoWindowClickListener<LocationNote>, ClusterManager.OnClusterItemClickListener<LocationNote>, ClusterManager.OnClusterItemInfoWindowClickListener<LocationNote> {

    private ClusterManager<LocationNote> locationNoteClusterManager;
    // private ClusterManager<DiaDiem> diaDiemClusterManager;
    private Random mRandom = new Random(1984);
    public Location[] locationList;
    private Gson gson = new Gson();
    GetJson getJson  = new GetJson();
    Handler handler;
    LocationManager locationManager;
    GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doStart();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    private class LocationNoteRenderer extends DefaultClusterRenderer<LocationNote> {

        private final IconGenerator mIconGenerator = new IconGenerator(getActivity().getApplicationContext());
        private final IconGenerator mClusterGernerator = new IconGenerator(getActivity().getApplicationContext());

        private final ImageView oneImageView;
        private final ImageView multiImageView;

        public LocationNoteRenderer() {
            super(getActivity().getApplicationContext(), getMap(), locationNoteClusterManager);
            View oneLocation = getActivity().getLayoutInflater().inflate(R.layout.sigle_location,null);
            mIconGenerator.setContentView(oneLocation);
            oneImageView = (ImageView)oneLocation.findViewById(R.id.image);

            View multiLocation = getActivity().getLayoutInflater().inflate(R.layout.multi_location,null);
            mClusterGernerator.setContentView(multiLocation);
            multiImageView = (ImageView)oneLocation.findViewById(R.id.image);
        }

        @Override
        protected void onBeforeClusterItemRendered(LocationNote locationNote, MarkerOptions markerOptions) {
            oneImageView.setImageResource(locationNote.getAnhDiaDiem());
            Bitmap icon = mIconGenerator.makeIcon(String.valueOf(locationNote.getSoNote()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(locationNote.getTenDiaDiem());
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<LocationNote> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
            /*for(LocationNote locationNote : cluster.getItems()){
                multiImageView.setImageResource(locationNote.getAnhDiaDiem());
                break;
            }*/
            /*multiImageView.setImageResource(cluster.getItems().iterator().next().getAnhDiaDiem());
            Bitmap icon = mIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));*/
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 1;
        }
    }

    @Override
    public boolean onClusterClick(Cluster<LocationNote> cluster) {
        String tenDiaDiem = cluster.getItems().iterator().next().getTenDiaDiem();
        Toast.makeText(getActivity(), cluster.getSize() + " (including " + tenDiaDiem + ")", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<LocationNote> cluster) {

    }

    @Override
    public boolean onClusterItemClick(LocationNote item) {
        System.out.println("thong tin chi tiet " + item.getTenDiaDiem() + "  " + item.getSoNote());
        Intent intent = new Intent(getActivity(), LocationNoteList.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onClusterItemInfoWindowClick(LocationNote item) {

    }

    @Override
    protected void startDemo() {
        map = getMap();
        android.location.Location lastLocation = getLastKnownLocation();
        if(lastLocation != null){
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()))      // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        else{
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(0, 0), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(0, 0))      // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        //getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 9.5f));
        locationNoteClusterManager = new ClusterManager<>(getActivity(), getMap());
        locationNoteClusterManager.setRenderer(new LocationNoteRenderer());
        getMap().setOnCameraChangeListener(locationNoteClusterManager);
        getMap().setOnMarkerClickListener(locationNoteClusterManager);
        getMap().setOnInfoWindowClickListener(locationNoteClusterManager);
        locationNoteClusterManager.setOnClusterClickListener(this);
        locationNoteClusterManager.setOnClusterInfoWindowClickListener(this);
        locationNoteClusterManager.setOnClusterItemClickListener(this);
        locationNoteClusterManager.setOnClusterItemInfoWindowClickListener(this);
        addItems();
        locationNoteClusterManager.cluster();
    }

    private void addItems() {
        for(Location location : locationList){
            if(location.getTypelocation().equals("Nha Hang")){
                locationNoteClusterManager.addItem(new LocationNote(location.getLocationName(), location.getNumberOfNote(), position(location.getLongitude(), location.getLatitude()), R.drawable.bell));
            }
            else {
                if(location.getTypelocation().equals("Cafe")){
                    locationNoteClusterManager.addItem(new LocationNote(location.getLocationName(), location.getNumberOfNote(), position(location.getLongitude(), location.getLatitude()), R.drawable.tea));
                }
                else {
                    locationNoteClusterManager.addItem(new LocationNote(location.getLocationName(), location.getNumberOfNote(), position(location.getLongitude(), location.getLatitude()), R.drawable.muffin));
                }
            }
        }
        locationNoteClusterManager.addItem(new LocationNote("Molly", 10, position(),R.drawable.cafe));
        locationNoteClusterManager.addItem(new LocationNote("Hoan My", 10, position(),R.drawable.hospital));
        locationNoteClusterManager.addItem(new LocationNote("Phi Lu", 10, position(),R.drawable.restaurant));
        locationNoteClusterManager.addItem(new LocationNote("Molly1", 10, position(),R.drawable.cafe));
        locationNoteClusterManager.addItem(new LocationNote("Hoan My 1", 10, position(),R.drawable.hospital));
        locationNoteClusterManager.addItem(new LocationNote("Phi Lu 1", 10, position(),R.drawable.restaurant));

    }

    private LatLng position() {
        return new LatLng(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683));
    }

    private LatLng position(float kinhDo, float viDo){
        return new LatLng(kinhDo, viDo);
    }

    private double random(double min, double max) {
        return mRandom.nextDouble() * (max - min) + min;
    }

    public class GetLocationList extends Thread{

        //Location location;
        Context context;
        Gson gson=new Gson();
        public GetLocationList(Context context)
        {
            //this.location = location;
            this.context=context;
        }

        @Override
        public void run() {
            String locations= getString(R.string.link)+"Location/list?LONGITUDE=9&LATITUDE=9&RADIUS=5";
            String result = getJson.getStringJson(locations);
            System.out.println("chuoi lay ve duoc :"+result);
            locationList = gson.fromJson(result,Location[].class);
        }
    }

    public void doStart()
    {
        GetLocationList getLocationList =new GetLocationList(getActivity());
        getLocationList.start();
    }

    private android.location.Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        System.out.println(" provider :"+providers);
        android.location.Location bestLocation = null;
        for (String provider : providers) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                System.out.println(" truy nhap internet thanh cong");
                android.location.Location location = locationManager.getLastKnownLocation(provider);

                if (location == null) {
                    continue;
                }
                if (bestLocation == null
                        || location.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = location;
                }
            }

        }
        System.out.println(" lastLocation ====================================== :"+bestLocation);
        return bestLocation;
    }
}
