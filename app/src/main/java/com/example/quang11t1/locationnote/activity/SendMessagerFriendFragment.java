package com.example.quang11t1.locationnote.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.activity.model.LocationNote;
import com.example.quang11t1.locationnote.adapter.ArrayAdapterFriend;
import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.FriendBean;
import com.example.quang11t1.locationnote.modle.Location;
import com.example.quang11t1.locationnote.modle.LocationNoteInfor;
import com.example.quang11t1.locationnote.modle.User;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SendMessagerFriendFragment extends Fragment implements View.OnClickListener{
    private  static  final int CAM_REQUEST = 1313;
    private static int LOAD_IMAGE_RESULTS = 101;
    ImageButton btnTakePhoto;
    ImageButton btnTakephotofromgallery;
    View rootview;
    ImageView imgViewphoto;
    ImageView imgViewphotofromgallery;
    String imagepath="";
    EditText editText_content;
    int idAccount=0;
    String userName="";
    Handler handler;
    int idFriend=0;
    List<Account> listAccount;
    EditText editText_friend;

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
        editText_content = (EditText) rootview.findViewById(R.id.editText_context);
        editText_friend = (EditText) rootview.findViewById(R.id.editText_friend);

        editText_friend.setOnClickListener(this);
        ImageButton imageButton_send = (ImageButton) rootview.findViewById(R.id.imageButton_send);
        imageButton_send.setOnClickListener(this);
        idAccount=getArguments().getInt("id",0);
        userName=getArguments().getString("user");
        Toast.makeText(getContext(),""+idAccount,Toast.LENGTH_LONG).show();

        handler=new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int result = msg.arg1;
                if(result==1){
                    Toast.makeText(getContext(),"add thanh cong",Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    /*Intent intent=getIntent();
                    intent.putExtra("account", account);
                    setResult(1, intent);
                    finish();*/
                }
                else {
                    Toast.makeText(getContext(),"add Khong thanhj cong",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getApplicationContext(),msg.arg1,Toast.LENGTH_SHORT).show();
            }
        };
        // Inflate the layout for this fragment
        return rootview;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && requestCode !=0 &&resultCode!= 0) {
            if (requestCode == CAM_REQUEST) {
                Bitmap thumbai12 = (Bitmap) data.getExtras().get("data");
                imgViewphoto.setImageBitmap(thumbai12);
            } else if (requestCode == LOAD_IMAGE_RESULTS && resultCode == getActivity().RESULT_OK && data != null) {
                // Let's read picked image data - its URI
                Uri pickedImage = data.getData();
                // Let's read picked image path using content resolver
                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
                cursor.moveToFirst();
                String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                imagepath=imagePath;
                Toast.makeText(getContext(),imagePath,Toast.LENGTH_SHORT).show();
                // Now we need to set the GUI ImageView data with data read from the picked file.
                imgViewphotofromgallery.setImageBitmap(BitmapFactory.decodeFile(imagePath));


                // At the end remember to close the cursor or you will end with the RuntimeException!
                cursor.close();
            }
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
        if(v.getId()==R.id.imageButton_send){


            if(!imagepath.equals("")){

            }
            String contextMessager = null;
            if(!editText_content.getText().toString().trim().equals(""))
            {
                contextMessager=editText_content.getText().toString();
                //AddNote addNote =new AddNote(contextMessager,idLocal,getContext());
                //addNote.start();
                SendMesage sendMesage =new SendMesage(contextMessager,idFriend,getContext());
                sendMesage.start();
                Toast.makeText(getActivity(),contextMessager, Toast.LENGTH_LONG).show();
            }

        }
        if(v.getId()==R.id.editText_friend){

            // custom dialog
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.choose_location_custom);
            dialog.setTitle("Chọn vị trí");
            ListView listView =(ListView) dialog.findViewById(R.id.listView_location);
            GetListAccount getListLocation =new GetListAccount(listView);
            getListLocation.execute("a");

            // set the custom dialog components - text, image and button
            final EditText editText_Location = (EditText) dialog.findViewById(R.id.editText_search_location);
            dialog.show();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //idFriend =listAccount.get(position).getIdAccount();
                    Account account =(Account) parent.getItemAtPosition(position);
                    idFriend=account.getIdAccount();
                    Toast.makeText(getActivity(),"location:"+account.getUsername(), Toast.LENGTH_LONG).show();
                    editText_friend.setText("" + account.getUsername());
                    dialog.cancel();
                }
            });

        }
    }


    private class SendMesage extends Thread{

        String content;
        int idFriend;
        Context context;
        Gson gson=new Gson();
        GetJson getJson=new GetJson();

        public SendMesage(String content,int idFriend,Context context)
        {
            this.content=content;
            this.idFriend=idFriend;
            this.context=context;
        }

        @Override
        public void run() {
            String addNote= getString(R.string.link)+"Message/AddMessage?IDSENDER="+idAccount+"&IDRECEIVER="+idFriend+"&CONTENT="+content;
            String result = getJson.getStringJson(addNote);
            Message msg=handler.obtainMessage();

            if (result.equals("1"))
            {
                msg.arg1=1;
            }
            else {
                //gán giá trị vào cho arg1 để gửi về Main thread
                msg.arg1=0;
            }
            //gửi lại Message này về cho Main Thread
            handler.sendMessage(msg);
        }
    }

    private class GetListAccount extends AsyncTask<String,List<Account>,List<Account>> {
        Gson gson=new Gson();
        GetJson getJson=new GetJson();

        ListView listView;

        public GetListAccount(ListView listView) {
            this.listView = listView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Account> accounts) {
            super.onPostExecute(accounts);

            ArrayAdapterFriend adapter =new ArrayAdapterFriend(getActivity(),R.layout.custom_row_friends,accounts);
            listView.setAdapter(adapter);
        }

        @Override
        protected void onProgressUpdate(List<Account>... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Account> doInBackground(String... params) {
            String getFriendLink= getString(R.string.link)+"Friend/myFriend?IDACCOUNT="+idAccount;
            String result = getJson.getStringJson(getFriendLink);
            System.out.println("chuoi lay ve duoc :" + result);
            FriendBean[] friendList = gson.fromJson(result,FriendBean[].class);
            List<Account> listAcount =new ArrayList<>();
            for (FriendBean friendBean:friendList)
            {
                String user = friendBean.getAccount1();
                if(user.equals(userName)) user=friendBean.getAccount2();
                String getAccountLink =getString(R.string.link)+"login/user?USERNAME="+user;
                String resultAccount = getJson.getStringJson(getAccountLink);
                Account account =gson.fromJson(resultAccount, Account.class);
                listAcount.add(account);
            }
            return listAcount;
        }
    }




}
