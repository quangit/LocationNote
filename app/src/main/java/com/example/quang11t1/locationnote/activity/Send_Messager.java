package com.example.quang11t1.locationnote.activity;


import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.TitleNavigationAdapter;
import com.example.quang11t1.locationnote.modle.ItemSpinner;

import java.util.ArrayList;

public class Send_Messager extends AppCompatActivity  {

    ArrayList<ItemSpinner> navSpinner;

    int idAccount=0;
    String userName ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__messager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Spinner spinner=(Spinner) findViewById(R.id.spinner_nav);

        Bundle infoLogin = getIntent().getExtras();
        idAccount=infoLogin.getInt("id",0);
        userName=infoLogin.getString("user");


        navSpinner =new ArrayList<>();
        navSpinner.add(new ItemSpinner("Bạn Bè", R.drawable.ic_people_24dp));
        navSpinner.add(new ItemSpinner("Cộng Đồng", R.drawable.ic_global));

        TitleNavigationAdapter spinnerAdapter =new TitleNavigationAdapter(this,navSpinner);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("id",idAccount);
                bundle.putString("user",userName);
                if (position == 0) {
                    SendMessagerFriendFragment sendMessagerFriendFragment = new SendMessagerFriendFragment();
                    sendMessagerFriendFragment.setArguments(bundle);
                    displayView(sendMessagerFriendFragment);
                }
                if(position ==1 ){
                    sendMessagerGlobalFragment sendmessagerGlobalFragment = new sendMessagerGlobalFragment();
                    sendmessagerGlobalFragment.setArguments(bundle);
                    displayView(sendmessagerGlobalFragment);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Toast.makeText(getApplicationContext(), "???????",
                        Toast.LENGTH_LONG).show();

            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.secound_menu, menu);


        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void displayView(Fragment fragment) {

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_send, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
