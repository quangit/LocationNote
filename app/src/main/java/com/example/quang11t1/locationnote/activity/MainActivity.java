package com.example.quang11t1.locationnote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;

import com.example.quang11t1.locationnote.activity.model.Information;
import com.example.quang11t1.locationnote.modle.Account;

import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean isLoginValue=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // init fragment home
        Fragment fragment = new Home();
        navigationView.getMenu().getItem(0).setChecked(true);
        displayView(fragment);
        //final TextView textView_User= (TextView) findViewById(R.id.text_UserName);
        //textView_User.setText("adwa");
        isLoginValue= isLogin(navigationView);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Fragment fragment = new Home();
            displayView(fragment);
        } else if (id == R.id.nav_friend) {
            if(!isLoginValue) moveLogin();
            else {
                Fragment fragment = new Friend();
                displayView(fragment);
            }
        } else if (id == R.id.nav_messager) {
            if(!isLoginValue) moveLogin();
            else {
                Fragment fragment = new Messager();
                displayView(fragment);
            }
        } else if (id == R.id.nav_send_messager) {
            if(!isLoginValue) moveLogin();
            else {

            }
        } else if (id == R.id.nav_information) {
           // if(!isLoginValue) moveLogin();
            //else {
            //}
            Fragment fragment = new Information();
            displayView(fragment);
        } else if (id == R.id.nav_setting) {


        } else if (id == R.id.nav_logout) {
           if(!isLoginValue) moveLogin();
            else Logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void moveLogin()
    {
        Intent intent =new Intent(this,Login.class);
        startActivityForResult(intent, 1);
    }
    private void Logout(){
        isLoginValue=false;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        MenuItem item = navigationView.getMenu().getItem(6);
        item.setTitle("Đăng nhập");
        item.setIcon(R.drawable.ic_login);
        SharedPreferences preferences=getSharedPreferences("inforLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        ImageView imageView_avatar=(ImageView) findViewById(R.id.imageView_avatar);
        Account account = (Account)data.getSerializableExtra("account");
        if(account!=null)
        {
            TextView textView_User= (TextView) findViewById(R.id.text_UserName);
            textView_User.setText(account.getUsername());
            isLoginValue=true;
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            MenuItem item = navigationView.getMenu().getItem(6);
            item.setTitle("Đăng xuất");
            item.setIcon(R.drawable.ic_menu_logout);
        }

        /*try {
            URL url = new URL("http://www.thehindu.com/multimedia/dynamic/02263/31_ronaldo_jpg_2263058f.jpg");
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageView_avatar.setImageBitmap(bmp);
        }catch (Exception e){}*/


       // Toast.makeText(this,"----"+account.getUsername(),Toast.LENGTH_SHORT).show();
    }

    public  boolean isLogin(NavigationView view) {
        SharedPreferences preferences=getSharedPreferences("inforLogin", MODE_PRIVATE);
        boolean check_remember = preferences.getBoolean("check", false);
        //TextView textView_User= (TextView) findViewById(R.id.text_UserName);
        //view.getMenu().getItem(6).setChecked(true);
        if(check_remember)
        {
            String user = preferences.getString("user","");
            String pass = preferences.getString("pass","");
            String url = preferences.getString("url","");
            String email =preferences.getString("email","");
            //textView_User.setText("add");
            MenuItem item = view.getMenu().getItem(6);
            item.setTitle("Đăng xuất");
            item.setIcon(R.drawable.ic_menu_logout);
            Toast.makeText(this,"da login",Toast.LENGTH_SHORT).show();
            return true;
        }
        MenuItem item = view.getMenu().getItem(6);
        item.setTitle("Đăng nhập");
        item.setIcon(R.drawable.ic_login);
        Toast.makeText(this,"chua login",Toast.LENGTH_SHORT).show();
        return false;
    }

    public void displayView(Fragment fragment) {

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
