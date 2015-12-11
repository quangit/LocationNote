package com.example.quang11t1.locationnote.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SuppressLint("ValidFragment")
public class Home extends MapBase implements ClusterManager.OnClusterClickListener<LocationNote>, ClusterManager.OnClusterInfoWindowClickListener<LocationNote>, ClusterManager.OnClusterItemClickListener<LocationNote>, ClusterManager.OnClusterItemInfoWindowClickListener<LocationNote> {

    public Location[] locationList;
    GetJson getJson = new GetJson();
    Handler handler;
    LocationManager locationManager;
    GoogleMap map;
    android.location.Location locationStatus=null;
    LatLng latLngLocation = null;
    int idAccount;
    float Latitude, Longitude, Latitude1, Longitude1,lastLongitude,lastLatitude;
    String provider = LocationManager.GPS_PROVIDER;
    int t = 5000; // milliseconds
    int distance = 5; // meters
    private ClusterManager<LocationNote> locationNoteClusterManager;
    //Location Listener

    LocationListener myLocationListener = new LocationListener() {

        public void onProviderDisabled(String provider) {
            // Update application if provider disabled.
        }

        public void onProviderEnabled(String provider) {
            // Update application if provider enabled.
        }

        @Override
        public void onLocationChanged(android.location.Location location) {
            //locationStatus =location;
            startDemo();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Update application if provider hardware status changed.
        }

    };
    // private ClusterManager<DiaDiem> diaDiemClusterManager;
    private Random mRandom = new Random(1984);
    private Gson gson = new Gson();

    public Home(){

    }
    @SuppressLint("ValidFragment")
    Home(float Latitude, float Longitude) {
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        //lastLatitude =Latitude;
        //lastLongitude =Longitude;
        System.out.println("========== Home ===========" + Latitude + " " + Longitude);
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(android.location.Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            locationStatus =location;
            saveStatus();
            //map.addMarker(new MarkerOptions().position(loc));
            if(map != null){
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                Toast.makeText(getContext(),"wadaw",Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Longitude1 = getArguments().getFloat("Longitude");
        Latitude1 = getArguments().getFloat("Latitude");

        System.out.println("========== Home1 ===========" + Latitude1 + " " + Longitude1);
        doStart();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, t, distance, myLocationListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // View rootView = inflater.inflate(R.layout.fragment_home,
           //     container, false);
        idAccount =getArguments().getInt("id",0);
        return super.onCreateView(inflater, container, savedInstanceState);

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
        intent.putExtra("idLocation",item.getIdLocation());
        intent.putExtra("id",idAccount);
        startActivity(intent);
        return true;
    }

    @Override
    public void onClusterItemInfoWindowClick(LocationNote item) {

    }

    @Override
    protected void startDemo() {
        map = getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
       // map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.setPadding(10, 10, 10, 10);

        android.location.Location lastLocation = getLastKnownLocation();
        //locationStatus=lastLocation;
        //saveStatus();
        latLngLocation= new LatLng(Latitude,Longitude);
       // Latitude = (float)lastLocation.getLatitude();
       // Longitude =(float) lastLocation.getLongitude();

       //map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(16.0678, 108.153), 9.5f));

        if(lastLocation != null){
            System.out.println(" "+lastLocation.getLatitude()+" "+lastLocation.getLongitude());
            LatLng latLng=new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 13));
            System.out.println("========== display location ===========" + Latitude + " " + Longitude);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            //MarkerOptions option=new MarkerOptions();
            //option.title("Vị trí của bạn");
            //option.snippet("Near");
           // option.position(latLng);
            //Marker currentMarker= map.addMarker(option);
            //currentMarker.showInfoWindow();
        }
        else{
            System.out.println("========== display location default ==========="+Latitude+" "+Longitude);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(Latitude, Longitude), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(Latitude, Longitude))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                 // Creates a CameraPosition from the builder
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
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                locationNoteClusterManager.onCameraChange(cameraPosition);
                latLngLocation = cameraPosition.target;

                double subLongitude, subLatitude;
                subLatitude = Math.abs(latLngLocation.latitude - lastLatitude);
                subLongitude = Math.abs(latLngLocation.longitude - lastLongitude);
                System.out.println("Toa do camera:" + subLatitude + " " + subLongitude);
                if ((subLatitude > 0.05) || (subLongitude > 0.05)) {
                    lastLongitude = (float) latLngLocation.longitude;
                    lastLatitude = (float) latLngLocation.latitude;
                    GetListLocation getListLocation = new GetListLocation();
                    getListLocation.execute();
                    System.out.println("-------------------------Loai lai ban do");
                }
            }
         });

        addItems();
        locationNoteClusterManager.cluster();
        map.setOnMyLocationChangeListener(myLocationChangeListener);
    }



    private void addItems() {

        locationNoteClusterManager.clearItems();
        if(locationList != null) {
            for (Location location : locationList) {
                int idImage;
                switch (location.getTypelocation()){
                    case "Bai Tam":               idImage=R.drawable.beach; break;
                    case "Cafe":                  idImage=R.drawable.tea; break;
                    case "Cong Vien":             idImage=R.drawable.park; break;
                    case "Cua Hang":              idImage=R.drawable.shopping; break;
                    case "Cua Hang Thoi Trang":   idImage=R.drawable.fashion; break;
                    case "Khach San":             idImage=R.drawable.hotel; break;
                    case "Ngan Hang":             idImage=R.drawable.bank; break;
                    case "Nha Hang":              idImage=R.drawable.bell; break;
                    case "Quan Game":             idImage=R.drawable.controller; break;
                    case "Quan Hang Rong":        idImage=R.drawable.noodles; break;
                    case "Quan Nhau":             idImage=R.drawable.beer; break;
                    case "Rap Chieu Phim":        idImage=R.drawable.movie; break;
                    case "Tram Xe Bus":           idImage=R.drawable.bus; break;
                    case "Truong Hoc":            idImage=R.drawable.school; break;
                    default:                      idImage=R.drawable.another; break;

                }
                locationNoteClusterManager.addItem(new LocationNote(location.getIdLocation(), location.getLocationName(), location.getLocationName(), position(location.getLatitude(), location.getLongitude()), idImage));

            }
        }
        else{


        }

    }

    public  void removeCluster(){
        locationNoteClusterManager.clearItems();
        addItems();
        locationNoteClusterManager.cluster();
    }

    private LatLng position() {
        return new LatLng(random(16, 17), random(108, 109));
    }

    private LatLng position(float kinhDo, float viDo){
        return new LatLng(kinhDo, viDo);
    }

    private double random(double min, double max) {
        return mRandom.nextDouble() * (max - min) + min;
    }

    public void doStart()
    {
        GetLocationList getLocationList =new GetLocationList(getActivity());
        getLocationList.start();


    }

    private android.location.Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        android.location.Location bestLocation = null;
        for (String provider : providers) {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
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
        return bestLocation;
    }

    public  android.location.Location getLocation(){
        return locationStatus;
    }

    public void saveStatus(){
        if(locationStatus!=null){
        SharedPreferences pre=this.getActivity().getSharedPreferences("location", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor=pre.edit();
        editor.putFloat("Latitude",(float)locationStatus.getLatitude());
        editor.putFloat("Longitude",(float) locationStatus.getLongitude());
        editor.commit();}}

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

    public class GetLocationList extends Thread{
        Context context;
        Gson gson=new Gson();
        public GetLocationList(Context context)
        {
            this.context=context;
        }

        @Override
        public void run() {
            String locationslink= getString(R.string.link)+"Location/list?LONGITUDE="+Longitude+"&LATITUDE="+Latitude+"&RADIUS=0.1";
            String result = getJson.getStringJson(locationslink);
            System.out.println("chuoi lay ve duoc :"+result);
            locationList = gson.fromJson(result,Location[].class);
        }
    }

    public class GetListLocation extends AsyncTask<Void,Void,Location[]>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Location[] locations) {
            super.onPostExecute(locations);
            removeCluster();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Location[] doInBackground(Void... params) {
            String locationslink= getString(R.string.link)+"Location/list?LONGITUDE="+lastLongitude+"&LATITUDE="+lastLatitude+"&RADIUS=0.1";
            String result = getJson.getStringJson(locationslink);
            System.out.println("chuoi lay ve duoc :"+result);
            locationList = gson.fromJson(result,Location[].class);
            return locationList;
        }
    }
}
