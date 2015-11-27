package com.example.quang11t1.locationnote.activity;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;


public class SendMessagerFriendFragment extends Fragment implements View.OnClickListener{
    private  static  final int CAM_REQUEST = 1313;
    private static int LOAD_IMAGE_RESULTS = 101;
    ImageButton btnTakePhoto;
    ImageButton btnTakephotofromgallery;
    View rootview;
    ImageView imgViewphoto;
    ImageView imgViewphotofromgallery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_send_messager_friend, container, false);
        btnTakePhoto = (ImageButton) rootview.findViewById(R.id.imageButtoncamera);
        btnTakephotofromgallery = (ImageButton) rootview.findViewById(R.id.imageButtonGallery);
        imgViewphoto = (ImageView) rootview.findViewById(R.id.imgviewPhoto);
        imgViewphotofromgallery = (ImageView) rootview.findViewById(R.id.imgviewPhotofromgallery);
        btnTakePhoto.setOnClickListener(this);
        btnTakephotofromgallery.setOnClickListener(this);
        // Inflate the layout for this fragment
        return rootview;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAM_REQUEST){
            Bitmap thumbai12 = (Bitmap) data.getExtras().get("data");
            imgViewphoto.setImageBitmap(thumbai12);
        }
        else   if (requestCode == LOAD_IMAGE_RESULTS && resultCode == getActivity().RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Now we need to set the GUI ImageView data with data read from the picked file.
            imgViewphotofromgallery.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.imageButtonGallery)
        {
            Toast toast = Toast.makeText(getActivity(), "hdihi", Toast.LENGTH_LONG);
            toast.show();
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, LOAD_IMAGE_RESULTS);
        }
        if(v.getId()==R.id.imageButtoncamera){
            Toast toast = Toast.makeText(getActivity(), "hihi", Toast.LENGTH_LONG);
            toast.show();
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,CAM_REQUEST);
        }
    }
}
