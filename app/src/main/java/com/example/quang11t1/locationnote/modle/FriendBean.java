package com.example.quang11t1.locationnote.modle;

/**
 * Created by quang11t1 on 30/11/2015.
 */
public class FriendBean {
    private int idFriend;
    private String Account2;
    private String Account1;
    private Boolean confirmed;
    private String status;

    public int getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(int idFriend) {
        this.idFriend = idFriend;
    }

    public String getAccount2() {
        return Account2;
    }

    public void setAccount2(String account2) {
        Account2 = account2;
    }

    public String getAccount1() {
        return Account1;
    }

    public void setAccount1(String account1) {
        Account1 = account1;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

