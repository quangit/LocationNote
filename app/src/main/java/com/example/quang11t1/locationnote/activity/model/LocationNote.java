package com.example.quang11t1.locationnote.activity.model;


import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class LocationNote implements ClusterItem {
    private String tenDiaDiem;
    private long soNote;
    private LatLng viTri;
    private int anhDiaDiem;

    public LocationNote(String tenDiaDiem, long soNote, LatLng viTri, int anhDiaDiem) {
        this.tenDiaDiem = tenDiaDiem;
        this.soNote = soNote;
        this.viTri = viTri;
        this.anhDiaDiem = anhDiaDiem;
    }

    public int getAnhDiaDiem() {
        return anhDiaDiem;
    }

    public void setAnhDiaDiem(int anhDiaDiem) {
        this.anhDiaDiem = anhDiaDiem;
    }

    public String getTenDiaDiem() {
        return tenDiaDiem;
    }

    public void setTenDiaDiem(String tenDiaDiem) {
        this.tenDiaDiem = tenDiaDiem;
    }

    public long getSoNote() {
        return soNote;
    }

    public void setSoNote(long soNote) {
        this.soNote = soNote;
    }

    public LatLng getViTri() {
        return viTri;
    }

    public void setViTri(LatLng viTri) {
        this.viTri = viTri;
    }

    @Override
    public LatLng getPosition() {
        return viTri;
    }
}
