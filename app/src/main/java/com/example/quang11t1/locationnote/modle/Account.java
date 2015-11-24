package com.example.quang11t1.locationnote.modle;

import java.io.Serializable;

/**
 * Created by quang11t1 on 24/11/2015.
 */
public class Account implements Serializable{
    private int idAccount;
    private String username;
    private String image;
    private String password;
    private String email;

    public Account()  {
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
