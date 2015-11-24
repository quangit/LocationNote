package com.example.quang11t1.locationnote.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.activity.map.MapBase;
import com.example.quang11t1.locationnote.activity.model.LocationNote;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.Random;
public class Home extends MapBase implements ClusterManager.OnClusterClickListener<LocationNote>, ClusterManager.OnClusterInfoWindowClickListener<LocationNote>, ClusterManager.OnClusterItemClickListener<LocationNote>, ClusterManager.OnClusterItemInfoWindowClickListener<LocationNote> {

    private ClusterManager<LocationNote> locationNoteClusterManager;
    // private ClusterManager<DiaDiem> diaDiemClusterManager;
    private Random mRandom = new Random(1984);
    // public static DiaDiem[] danhSachDiaDiem ;

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
            // Draw a single person.
            // Set the info window to show their name.
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
        System.out.println("thong tin chi tiet "+item.getTenDiaDiem()+"  "+item.getSoNote());
        /*Intent intent = new Intent(this, LocationMessageListActivity.class);
        startActivity(intent);*/
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(LocationNote item) {

    }

    @Override
    protected void startDemo() {
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 9.5f));
        locationNoteClusterManager = new ClusterManager<>(getActivity(), getMap());
        locationNoteClusterManager.setRenderer(new LocationNoteRenderer());
        getMap().setOnCameraChangeListener(locationNoteClusterManager);
        getMap().setOnMarkerClickListener(locationNoteClusterManager);
        getMap().setOnInfoWindowClickListener(locationNoteClusterManager);
        locationNoteClusterManager.setOnClusterClickListener(this);
        locationNoteClusterManager.setOnClusterInfoWindowClickListener(this);
        locationNoteClusterManager.setOnClusterItemClickListener(this);
        locationNoteClusterManager.setOnClusterItemInfoWindowClickListener(this);
        /*System.out.println("class mapmessage ======================================");
        for(int i=0; i<danhSachDiaDiem.length; i++){
            System.out.println("id :"+i+" = "+danhSachDiaDiem[i].getIdDiaDiem());
            System.out.println("ten :"+i+" = "+danhSachDiaDiem[i].getTenDiaDiem());
            System.out.println("loai :"+i+" = "+danhSachDiaDiem[i].getLoaiDiaDiem());
            System.out.println("kinh do :"+i+" = "+danhSachDiaDiem[i].getKinhDo());
            System.out.println("vi do :"+i+" = "+danhSachDiaDiem[i].getViDo());
        }*/
        addItems();
        locationNoteClusterManager.cluster();
    }

    private void addItems() {
        /*for(int i=0; i<danhSachDiaDiem.length; i++){
            DiaDiem diaDiem = danhSachDiaDiem[i];
            if(diaDiem.getLoaiDiaDiem().equals("Nha Hang")){
                locationNoteClusterManager.addItem(new LocationNote(diaDiem.getTenDiaDiem(), (int)diaDiem.getSoNote(), position(diaDiem.getKinhDo(), diaDiem.getViDo()), R.drawable.restaurant));
            }
            else {
                if(diaDiem.getLoaiDiaDiem().equals("Cafe")){
                    locationNoteClusterManager.addItem(new LocationNote(diaDiem.getTenDiaDiem(), (int)diaDiem.getSoNote(), position(diaDiem.getKinhDo(), diaDiem.getViDo()), R.drawable.cafe));
                }
                else {
                    locationNoteClusterManager.addItem(new LocationNote(diaDiem.getTenDiaDiem(), (int)diaDiem.getSoNote(), position(diaDiem.getKinhDo(), diaDiem.getViDo()), R.drawable.hotel));
                }
            }
        }*/
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
}
