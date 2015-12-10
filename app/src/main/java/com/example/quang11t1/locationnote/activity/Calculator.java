package com.example.quang11t1.locationnote.activity;

import android.location.Location;

/**
 * Created by luongvien_binhson on 10-Dec-15.
 */
public class Calculator {
    public static float calculateDistance(float lat1, float lon1, float lat2, float lon2)
    {
        Location location1=new Location("Point1");
        Location location2=new Location("Point2");
        location1.setLatitude(lat1);
        location1.setLongitude(lon1);
        location2.setLatitude(lat2);
        location2.setLongitude(lon2);
        return location1.distanceTo(location2);
    }
}