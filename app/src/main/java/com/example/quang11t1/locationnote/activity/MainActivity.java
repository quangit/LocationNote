package com.example.quang11t1.locationnote.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.Editphoto.CircleImage;
import com.example.quang11t1.locationnote.R;

import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.Location;
import com.example.quang11t1.locationnote.modle.customHandler;
import com.google.android.gms.maps.model.LatLng;
import com.pushbots.push.Pushbots;

import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
//Bien
    int idAccount=0;
    String userName;
    boolean isLoginValue=false;
    FloatingActionButton fab;
    android.location.Location location;
    float Latitude, Longitude;
    //Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Pushbots.sharedInstance().init(this);
       // Pushbots.sharedInstance().setCustomHandler(customHandler.class);
        Pushbots.sharedInstance().init(this);
        Pushbots.sharedInstance().setCustomHandler(customHandler.class);
        fab = (FloatingActionButton) findViewById(R.id.fab_send_messager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (isLoginValue) {
                    Intent intent = new Intent(getApplicationContext(), Send_Messager.class);
                    getPositionRecent();
                    intent.putExtra("id", idAccount);
                    intent.putExtra("user", userName);
                    intent.putExtra("long", Longitude);
                    intent.putExtra("lat", Latitude);
                    startActivity(intent);
                }else moveLogin();

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences pre=getSharedPreferences("location", MODE_PRIVATE);
        Latitude = pre.getFloat("Latitude", (float) 16.056);
        Longitude = pre.getFloat("Longitude",(float) 108.208);
        // init fragment home
        isLoginValue= isLogin(navigationView);
        Fragment fragment = new Home(Latitude,Longitude);
        Bundle bundle=new Bundle();
        bundle.putFloat("Longitude",Longitude);
        bundle.putFloat("Latitude",Latitude);
        bundle.putInt("id", idAccount);

        setTitle("Trang Chu");
        fragment.setArguments(bundle);
        navigationView.getMenu().getItem(0).setChecked(true);
        displayView(fragment);
        //final TextView textView_User= (TextView) findViewById(R.id.text_UserName);
        //textView_User.setText("adwa");
        TextView textView =(TextView)  navigationView.findViewById(R.id.text_UserName);
        //textView.setText("aaaaaaaa");
       // new Thread(new task()).start();
    }
/*
    class task implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<4;i++){
                try{
                    //random time to sleep
                    Random randomGenerator = new Random();
                    int randomInt = randomGenerator.nextInt(1);
                    Thread.sleep(1000*60);
                    // display notification
                    getNotification();

                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

        }

    }
*/
    /*
    private void getNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.launcher_icon)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);
        mBuilder.setAutoCancel(true);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(101, mBuilder.build());
*/    // Resume
    @Override
    protected void onResume() {
        super.onResume();
       getPositionRecent();
    }

    public void getPositionRecent(){
        SharedPreferences pre=getSharedPreferences("location", MODE_PRIVATE);
        Latitude = pre.getFloat("Latitude", (float) 16.056);
        Longitude = pre.getFloat("Longitude",(float) 108.208);
        System.out.println("========== display  ==========="+Latitude+" "+Longitude);
    }
    @Override
    protected void onPause() {
        super.onPause();
        /*Home home= new Home(Latitude,Longitude);
        location=home.getLocation();
        if(location!=null){
        SharedPreferences pre=getSharedPreferences("location", MODE_PRIVATE);
        SharedPreferences.Editor editor=pre.edit();
        editor.putFloat("Latitude",(float)location.getLatitude());
        editor.putFloat("Longitude",(float) location.getLongitude());
        editor.commit();}*/
    }


    //Back onlick
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
        Bundle bundle = new Bundle();
        bundle.putInt("id",idAccount);
        bundle.putString("user",userName);
        bundle.putFloat("Longitude", Longitude);
        bundle.putFloat("Latitude", Latitude);
        if (id == R.id.nav_home) {

            fab.setVisibility(View.VISIBLE);
            Fragment fragment = new Home(Latitude,Longitude);
            Bundle bundle1=new Bundle();
            fragment.setArguments(bundle);
            displayView(fragment);
            setTitle("Trang Chu");
        } else if (id == R.id.nav_friend) {
            if(!isLoginValue) moveLogin();
            else {
                //Fragment fragment = new Friend();
                //
                fab.setVisibility(View.GONE);
                FriendFragment fragment =new FriendFragment();
                fragment.setArguments(bundle);
                displayView(fragment);
                setTitle("Ban Be");
            }
        } else if (id == R.id.nav_messager) {
            if(!isLoginValue) moveLogin();
            else {
                fab.setVisibility(View.VISIBLE);
                Fragment fragment = new Messager();
                fragment.setArguments(bundle);
                displayView(fragment);
                setTitle("Hop Tin Nhan");
            }
        } else if (id == R.id.nav_send_messager) {
            if(!isLoginValue) moveLogin();
            else {
                fab.setVisibility(View.VISIBLE);
                Fragment fragment =new SendedMessageFragment();
                fragment.setArguments(bundle);
                displayView(fragment);
                setTitle("Tin Nhan Da Gui");
            }
        } else if (id == R.id.nav_information) {
           if(!isLoginValue) moveLogin();
            else {
               Fragment fragment = new Information();
               fragment.setArguments(bundle);
               displayView(fragment);
               setTitle("Thong Tin Ca Nhan");
            }
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
        TextView textView =(TextView) findViewById(R.id.text_UserName);
        textView.setText("");
        ImageView imageView =(ImageView) findViewById(R.id.imageView_avatar);
        imageView.setImageResource(R.drawable.ic_account_circle_48dp);
        item.setTitle("Đăng nhập");
        item.setIcon(R.drawable.ic_login);
        SharedPreferences preferences=getSharedPreferences("inforLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();
        idAccount=0;
        userName="";
        editor.commit();

        navigationView.getMenu().getItem(0).setChecked(true);
        Bundle bundle = new Bundle();
        bundle.putInt("id",idAccount);
        bundle.putString("user",userName);
        bundle.putFloat("Longitude", Longitude);
        bundle.putFloat("Latitude", Latitude);
        fab.setVisibility(View.VISIBLE);
        Fragment fragment = new Home(Latitude,Longitude);
        Bundle bundle1=new Bundle();
        fragment.setArguments(bundle);
        displayView(fragment);
        setTitle("Trang Chu");
       // Fragment fragment = new Home();
      //  navigationView.getMenu().getItem(0).setChecked(true);
       // displayView(fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //ImageView imageView_avatar=(ImageView) findViewById(R.id.imageView_avatar);
        Account account = (Account)data.getSerializableExtra("account");
        if(account!=null)
        {
            TextView textView_User= (TextView) findViewById(R.id.text_UserName);
            textView_User.setText(account.getUsername());
            ImageView imageView = (ImageView) findViewById(R.id.imageView_avatar);
            //imageView.setImageResource(R.drawable.ic_global);
            DownloadImageTask downloadImageTask =new DownloadImageTask(imageView);
            downloadImageTask.execute(account.getImage());
            isLoginValue=true;
            idAccount =account.getIdAccount();
            userName=account.getUsername();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            MenuItem item = navigationView.getMenu().getItem(6);
            item.setTitle("Đăng xuất");
            item.setIcon(R.drawable.ic_menu_logout);

            navigationView.getMenu().getItem(0).setChecked(true);
            Bundle bundle = new Bundle();
            bundle.putInt("id",idAccount);
            bundle.putString("user",userName);
            bundle.putFloat("Longitude", Longitude);
            bundle.putFloat("Latitude", Latitude);
            setTitle("Trang Chu");
            fab.setVisibility(View.VISIBLE);
            Fragment fragment = new Home(Latitude,Longitude);
            fragment.setArguments(bundle);
            displayView(fragment);
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
            idAccount = preferences.getInt("id",0);
            userName = preferences.getString("user","");
            String pass = preferences.getString("pass","");
            String url = preferences.getString("url","");
            String email =preferences.getString("email","");


            MenuItem item = view.getMenu().getItem(6);
            item.setTitle("Đăng xuất");
            item.setIcon(R.drawable.ic_menu_logout);
            Toast.makeText(this,"da login",Toast.LENGTH_SHORT).show();
            return true;
        }
        MenuItem item = view.getMenu().getItem(6);
        item.setTitle("Đăng nhập");
        item.setIcon(R.drawable.ic_login);
        Toast.makeText(this, "chua login",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
