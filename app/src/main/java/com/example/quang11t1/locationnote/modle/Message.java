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

    public Message(Integer idMessage, String accountByIdSender, String accountByIdReceiver, String content, Date time) {
        this.idMessage = idMessage;
        this.accountByIdSender = accountByIdSender;
        this.accountByIdReceiver = accountByIdReceiver;
        this.content = content;
        this.time = time;
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
}
