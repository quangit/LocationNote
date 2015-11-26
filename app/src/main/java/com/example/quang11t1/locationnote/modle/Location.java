package com.example.quang11t1.locationnote.modle;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Location implements ClusterItem{
    private int idLocation;
    private String typelocation;
    private String locationName;
    private float longitude;
    private float latitude;
    private int numberOfNote;

    public Location() {
        super();
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public String getTypelocation() {
        return typelocation;
    }

    public void setTypelocation(String typelocation) {
        this.typelocation = typelocation;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public int getNumberOfNote() {
        return numberOfNote;
    }

    public void setNumberOfNote(int numberOfNote) {
        this.numberOfNote = numberOfNote;
    }

    @Override
    public LatLng getPosition() {
        return null;
    }
}
