package com.example.quang11t1.locationnote.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;

import java.io.File;

/**
 * Created by luongvien_binhson on 26-Nov-15.
 */
public class Take_photo extends Activity implements View.OnClickListener {
    Button btnTakePhoto;
    Button btnTakVideo;
    ImageView imgViewPhoto;
    private  static  final int CAM_REQUEST = 1313;
    private static final int VIDEO_CAPTURE =101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
        //btnTakVideo = (Button) findViewById(R.id.btnTakeVideo);
        imgViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
        btnTakePhoto.setOnClickListener(this);
        btnTakVideo.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAM_REQUEST){
            Bitmap thumbai12 = (Bitmap) data.getExtras().get("data");
            imgViewPhoto.setImageBitmap(thumbai12);
        }
        else if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Video saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnTakePhoto){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,CAM_REQUEST);
        }
       //else if(v.getId()==R.id.btnTakeVideo){
        //   File mediaFile =
           //         new File(Environment.getExternalStorageDirectory().getAbsolutePath()
              //              + "/myvideo.mp4");

//            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//
   //         Uri videoUri = Uri.fromFile(mediaFile);
//
   //         intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
      //      startActivityForResult(intent, VIDEO_CAPTURE);
       //}
    }
}
