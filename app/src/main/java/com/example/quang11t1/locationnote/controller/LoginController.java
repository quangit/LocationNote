package com.example.quang11t1.locationnote.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.User;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;


import java.net.HttpURLConnection;

/**
 * Created by quang11t1 on 24/11/2015.
 */
public class LoginController extends AsyncTask<User,String,Account> {



    GetJson getJson =new GetJson();
    Gson gson=new Gson();
    HttpURLConnection urlConnection;
    Context context;
    public LoginController(Context context) {
        super();
        this.context=context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Account account) {
        super.onPostExecute(account);
        if(account!=null) {
            Toast.makeText(context, account.getUsername(), Toast.LENGTH_SHORT).show();

        }
        else Toast.makeText(context,"Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Account doInBackground(User... users) {

        User user=users[0];
        String inforLogin= context.getString(R.string.link)+"login/isLogin?USERNAME="+user.getUsername()+
                "&PASSWORD="+user.getPassword();
        String result = getJson.getStringJson(inforLogin);
        if (result.equals("1"))
        {
            String getInforAccount = context.getString(R.string.link)+"login/user?USERNAME="+user.getUsername();
            String inforAccount = getJson.getStringJson(getInforAccount);
            Account account = gson.fromJson(inforAccount,Account.class);
            return  account;
        }
        return null;
    }
}
