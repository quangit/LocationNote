package com.example.quang11t1.locationnote.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.ArrayAdapterCustomComment;
import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.Comment;
import com.example.quang11t1.locationnote.modle.FriendBean;
import com.example.quang11t1.locationnote.modle.ImageNote;
import com.example.quang11t1.locationnote.modle.LocationNoteInfor;
import com.example.quang11t1.locationnote.support.GetContentImage;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class detailNoteActivity extends AppCompatActivity {

    LocationNoteInfor locationNoteInfor;
    int idAcount;
    int idNote;
    ListView listViewComment;
    EditText editTextComment;
    LinearLayout linearLayoutComment;
    int statusFloating=0;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getBundleExtra("data");
        locationNoteInfor =(LocationNoteInfor) bundle.getSerializable("locationNoteInfo");
        idAcount = bundle.getInt("id",0);
        idNote =locationNoteInfor.getIdNote();
        Toast.makeText(this,"id account la :"+idAcount,Toast.LENGTH_LONG).show();

        ImageView imageView_avatar_sender = (ImageView) findViewById(R.id.imageView_avatar_sender_detail);
        ImageView imageViewContent = (ImageView) findViewById(R.id.imageView_content_note);
        TextView textViewName =(TextView) findViewById(R.id.textView_name_sender_detail);
        TextView textViewLocation =(TextView) findViewById(R.id.textView_location);
        TextView textViewContent =(TextView) findViewById(R.id.textView_content_note);
        TextView textViewNumberLike =(TextView) findViewById(R.id.textView_number_like);
        TextView textViewNumberComment =(TextView) findViewById(R.id.textView_number_comment);
        TextView textViewDateNote =(TextView) findViewById(R.id.textView_date_note);
        linearLayoutComment =(LinearLayout) findViewById(R.id.layout_Comment);
        editTextComment =(EditText) findViewById(R.id.editText_content_comment);


        fab = (FloatingActionButton) findViewById(R.id.fab_send_comment);

        if(idAcount ==0) fab.setVisibility(View.GONE);
        else fab.setVisibility(View.VISIBLE);

        textViewName.setText(locationNoteInfor.getAccount());
        textViewLocation.setText(locationNoteInfor.getLocation());
        textViewNumberLike.setText(locationNoteInfor.getNumberOfLike()+"");
        textViewNumberComment.setText(locationNoteInfor.getNumberOfComment() + "");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        textViewDateNote.setText(sdf.format(locationNoteInfor.getTime()).toString());



        new DownloadImageTask(imageView_avatar_sender).execute(locationNoteInfor.getAccount());

        GetLinkImageNote getLinkImageNote =new GetLinkImageNote(imageViewContent,getApplicationContext());
        getLinkImageNote.execute(locationNoteInfor.getIdNote());
        textViewContent.setText(locationNoteInfor.getContent());

        listViewComment =(ListView) findViewById(R.id.listView_comment);
        GetComment getComment =new GetComment(listViewComment,getApplicationContext());
        getComment.execute(locationNoteInfor.getIdNote());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusFloating ==0){
                    linearLayoutComment.setVisibility(View.VISIBLE);
                }
                if(statusFloating == 1){
                    String contentComment =editTextComment.getText().toString();
                    if(!contentComment.equals("")){
                        new AddComment().execute(contentComment);
                        linearLayoutComment.setVisibility(View.GONE);
                        statusFloating =-1;
                    }else{
                        linearLayoutComment.setVisibility(View.GONE);
                        statusFloating =-1;
                    }
                }
                statusFloating++;
            }
        });


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        GetJson getJson =new GetJson();
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String user = urls[0];
            String getMessageLink=  getApplicationContext().getString(R.string.link)+"login/getLinkAvatar?USERNAME="+user;
            String result = getJson.getStringJson(getMessageLink);
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(result).openStream();
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


    public class GetLinkImageNote extends AsyncTask<Integer,Void,String>{
        GetJson getJson =new GetJson();
        Gson gson =new Gson();
        ImageView imageView;
        Context context;

        public GetLinkImageNote(ImageView imageView,Context context) {
            this.imageView = imageView;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!="") {
                GetContentImage getContentImage = new GetContentImage(imageView, context);
                getContentImage.execute(s);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Integer... params) {
            int idNote = params[0];
            String link = getString(R.string.link) + "Image/getImageNote?IDNOTE="+idNote;
            String result = getJson.getStringJson(link);
            ImageNote imageNote = gson.fromJson(result,ImageNote.class);
            if(imageNote == null) return "";
            return imageNote.getUrlImage();
        }
    }

    private class GetComment extends AsyncTask<Integer,Void,List<Comment>>{
        ListView listView;
        Context acontext;
        GetJson getJson =new GetJson();
        Gson gson =new Gson();

        public GetComment(ListView listView, Context acontext) {
            this.listView = listView;
            this.acontext = acontext;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Comment> comments) {
            super.onPostExecute(comments);
            ArrayAdapterCustomComment arrayAdapterCustomComment =new ArrayAdapterCustomComment(acontext,comments);
            listView.setAdapter(arrayAdapterCustomComment);
            int size = comments.size();
            int sizeListView =0;

            if (size > 0) sizeListView = 200;
            if (size > 2) sizeListView = 600;
            if (size > 5) sizeListView = 1000;
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) listView.getLayoutParams();
            lp.height = sizeListView;
            listView.setLayoutParams(lp);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Comment> doInBackground(Integer... params) {
            int idNote = params[0];
            String link = getString(R.string.link) + "Comment/list?IDNOTE="+idNote;
            String result = getJson.getStringJson(link);
            Comment[] comments = gson.fromJson(result,Comment[].class);
            return Arrays.asList(comments);
        }
    }

    private class  AddComment extends AsyncTask<String,Void,Boolean>{
        GetJson getJson =new GetJson();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                new  GetComment(listViewComment,getApplicationContext()).execute(idNote);
                editTextComment.setText("");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                Toast.makeText(getApplicationContext(),"Comment thanh cong",Toast.LENGTH_LONG).show();
            }else {
                editTextComment.setText("");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                Toast.makeText(getApplicationContext(),"Comment khong thanh cong",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String content= params[0];
            String link = getString(R.string.link) + "Comment/addComment?IDNOTE="+idNote+"&IDACCOUNT="+idAcount+"&CONTENT="+content;
            String result = getJson.getStringJson(link);
            if (result.equals("1")) return true;
            return false;
        }
    }


}
