package com.example.quang11t1.locationnote.modle;

/**
 * Created by quang11t1 on 26/11/2015.
 */
public class ItemSpinner {
    private String title;
    private int icon;

    public ItemSpinner(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }
}
