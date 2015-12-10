package com.example.quang11t1.locationnote.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.quang11t1.locationnote.R;

/**
 * Created by luongvien_binhson on 09-Dec-15.
 */
public class NotificationMessage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationmessage);
        Bundle extras = getIntent().getExtras();
        if(null!=extras&&getIntent().getExtras().containsKey("message")&&getIntent().getExtras().containsKey("author")){
            TextView message=(TextView) findViewById(R.id.textViewNotificaitonMessageAuthor);
            TextView author= (TextView) findViewById(R.id.textViewNotificaitonMessageAuthor);
            message.setText(extras.getString("message"));
            author.setText(extras.getString("author"));
            System.out.println(""+extras.getString("message")+extras.getString("author"));
        }
        else{
            System.out.println(""+extras.getString("message")+extras.getString("author"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      int id=item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
