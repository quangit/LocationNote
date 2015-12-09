package com.example.quang11t1.locationnote.modle;

import java.util.Date;

/**
 * Created by quang11t1 on 03/12/2015.
 */
public class Message {
    private Integer idMessage;
    private String accountByIdSender;
    private String accountByIdReceiver;
    private String content;
    private Date time;
    private String urlImage;
    private float longitude;
    private float latitude;

    public Message(Integer idMessage, String accountByIdSender, String accountByIdReceiver, String content, Date time,String urlImage,float longitude,float latitude) {
        this.idMessage = idMessage;
        this.accountByIdSender = accountByIdSender;
        this.accountByIdReceiver = accountByIdReceiver;
        this.content = content;
        this.time = time;
        this.urlImage = urlImage;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Integer getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public String getAccountByIdSender() {
        return accountByIdSender;
    }

    public void setAccountByIdSender(String accountByIdSender) {
        this.accountByIdSender = accountByIdSender;
    }

    public String getAccountByIdReceiver() {
        return accountByIdReceiver;
    }

    public void setAccountByIdReceiver(String accountByIdReceiver) {
        this.accountByIdReceiver = accountByIdReceiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
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
}
