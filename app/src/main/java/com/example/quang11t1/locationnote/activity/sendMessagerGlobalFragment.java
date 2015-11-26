package com.example.quang11t1.locationnote.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;


public class sendMessagerGlobalFragment extends Fragment implements OnClickListener{
    ImageButton btnTakePhoto;
    View rootview;
    ImageView imgViewphoto;
    private  static  final int CAM_REQUEST = 1313;
    private static final int VIDEO_CAPTURE =101;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_send_messager_friend, container, false);
        btnTakePhoto = (ImageButton) rootview.findViewById(R.id.imageButton3);
        imgViewphoto = (ImageView) rootview.findViewById(R.id.imgviewPhoto);
        btnTakePhoto.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.imageButton3){
            Toast toast = Toast.makeText(getActivity(), "hihi", Toast.LENGTH_LONG);
            toast.show();
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,CAM_REQUEST);
        }
    }
}
