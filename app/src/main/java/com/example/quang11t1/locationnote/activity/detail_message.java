package com.example.quang11t1.locationnote.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quang11t1.locationnote.Editphoto.CircleImage;
import com.example.quang11t1.locationnote.adapter.Location_list_Adapter;
import com.example.quang11t1.locationnote.modle.Comment;
import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.commentAdapter;
import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.LocationNoteInfor;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Z0NEN on 10/22/2014.
 */
public class detail_message extends AppCompatActivity {
    public ArrayList<Comment> arraylistComment = new ArrayList<Comment>();
    TextView txtViewAvartaDetail,txtViewLocationDetail,txtViewContentDetail;
    View rootview;
    Comment comment;
    ImageView imViewAndroid;
    CircleImage circleImage;
    GetJson getJson =new GetJson();
    LocationNoteInfor locationNoteInfor;
    int id;
    String username;
    Comment[] commentList ;
    List<Account> listAcount =new ArrayList<>();
    Date date=new Date();
    private ListView listViewComment;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_message);
        Bundle bundle = getIntent().getBundleExtra("data");
        locationNoteInfor=(LocationNoteInfor)bundle.getSerializable("locationNoteInfo");
        //Bundle bundle =this.getArguments();
        //id=bundle.getInt("id");
        //username=bundle.getString("user");
        doStartGet();
        doStartGetLocationNoteList();
        createtmpComment();
        txtViewAvartaDetail= (TextView) findViewById(R.id.textViewNameNickDetail);
        txtViewLocationDetail=(TextView) findViewById(R.id.textViewLocationDetail);
        txtViewContentDetail=(TextView) findViewById(R.id.textViewContentDetail);
       listViewComment = (ListView) findViewById(R.id.listviewComment);
       commentAdapter adapter=new commentAdapter(detail_message.this,arraylistComment);
        listViewComment.setAdapter(adapter);
        //GetListAccount getListAccount =new GetListAccount();
        //getListAccount.execute("a");
        System.out.println("then vien id " + locationNoteInfor.getIdNote());
        imViewAndroid = (ImageView) findViewById(R.id.imageviewAvarta);
        txtViewAvartaDetail.setText(locationNoteInfor.getAccount());
        txtViewLocationDetail.setText(locationNoteInfor.getLocation());
        txtViewContentDetail.setText(locationNoteInfor.getContent());

        //imViewAndroid.setImageBitmap(rountdCornerImage(BitmapFactory.decodeResource(getResources(), R.drawable.vie),60));
        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.vie);

        //new DownloadImageTask((ImageView) rootview.findViewById(R.id.imageviewAvarta))
           //     .execute("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
        circleImage=new CircleImage();
        imViewAndroid.setImageBitmap(circleImage.getCircleBitmap(bm));
    }
    public void doStartGetLocationNoteList()
    {
        GetCommentList getCommentList = new GetCommentList(this);
        getCommentList.start();
    }
    public ArrayList<Comment> getData(){
        System.out.println(" get data to display to screen");
        ArrayList<Comment> data = new ArrayList<Comment>();
      //  System.out.println("dư lieu khoi tao ban dau :"+data);
        for(int i=0;i<commentList.length;i++){
            data.add(commentList[i]);
        }

        //for(int i=0; i<data.size(); i++){
           // System.out.println("id :"+data.get(i).getIdComment()+" account :"+data.get(i).getAccount());
        //}
        return data;
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.detail_message, container, false);
       createtmpComment();
       listViewComment = (ListView) rootview.findViewById(R.id.listviewComment);
        commentAdapter adapter=new commentAdapter(this,arraylistComment);
        listViewComment.setAdapter(adapter);
        imViewAndroid = (ImageView) rootview.findViewById(R.id.imageviewAvarta);
        //imViewAndroid.setImageBitmap(roundCornerImage(BitmapFactory.decodeResource(getResources(), R.drawable.vie),60));
        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.vie);

        new DownloadImageTask((ImageView) rootview.findViewById(R.id.imageviewAvarta))
                .execute("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
        //circleImage=new CircleImage();
        //imViewAndroid.setImageBitmap(circleImage.getCircleBitmap(bm));
        return rootview;
    }*/
    public void createtmpComment(){
        comment=new Comment(1,"vien","Ngon that do",date);
        arraylistComment.add(comment);
        comment=new Comment(1,"quang","uhm Ngon that do",date);
        arraylistComment.add(comment);
       // comment=new Comment();
      //  arraylistComment.add(comment);
    }

    public void doStartGet()
    {
        GetDetail_Account getLocationNoteList = new GetDetail_Account(this);
        getLocationNoteList.start();
    }

    private class GetListAccount extends AsyncTask<String,List<Account>,List<Account>> {
        Gson gson=new Gson();
        GetJson getJson=new GetJson();

        ListView listView;

        public GetListAccount() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Account> accounts) {
            super.onPostExecute(accounts);

            //commentAdapter adapter=new commentAdapter(detail_message.this,arraylistComment,accounts);
            //listViewComment.setAdapter(adapter);
            //locationNoteListAdapter = new Location_list_Adapter(LocationNoteList.this, getData(),accounts);
            //recycleView.setAdapter(locationNoteListAdapter);
            //recycleView.setItemsCanFocus(true);
            //ArrayAdapterFriend adapter =new ArrayAdapterFriend(getActivity(),R.layout.custom_row_friends,accounts);
            //listView.setAdapter(adapter);
        }

        @Override
        protected void onProgressUpdate(List<Account>... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Account> doInBackground(String... params) {

            for (Comment comment:commentList)
            {
                String user = comment.getAccount();
                //if(user.equals(userName)) user=friendBean.getAccount2();
                String getAccountLink =getString(R.string.link)+"login/user?USERNAME="+user;
                String resultAccount = getJson.getStringJson(getAccountLink);
                Account account =gson.fromJson(resultAccount, Account.class);
                listAcount.add(account);
            }
            return listAcount;
        }
    }

    public class GetCommentList extends Thread{
        Context context;
        Gson gson=new Gson();
        public GetCommentList(Context context)
        {
            this.context=context;
        }

        @Override
        public void run() {
            String inforCommentList= getString(R.string.link)+"Comment/list?IDNOTE=1";
            System.out.println("link :"+inforCommentList);
            String result = getJson.getStringJson(inforCommentList);
            System.out.println(" ket qua lay duoc :"+result);
            commentList = gson.fromJson(result, Comment[].class);

        }
    }

    /*
    public Bitmap roundCornerImage(Bitmap src, float round) {
        // Source image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create result bitmap output
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // set canvas for painting
        Canvas canvas = new Canvas(result);
        canvas.drawARGB(0, 0, 0, 0);

        // configure paint
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.YELLOW);

        // configure rectangle for embedding
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        // draw Round rectangle to canvas
        canvas.drawRoundRect(rectF, round, round, paint);

        // create Xfer mode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // draw source image to canvas
        canvas.drawBitmap(src, rect, rect, paint);

        // return final image
        return result;
    }*/
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
            circleImage=new CircleImage();
            mIcon11=circleImage.getCircleBitmap(mIcon11);
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public class GetDetail_Account extends Thread{
        Context context;
        Gson gson=new Gson();
        public GetDetail_Account(Context context)
        {
            this.context=context;
        }

        @Override
        public void run() {


            String getInforAccount = context.getString(R.string.link)+"login/user?USERNAME="+username;
            String inforAccount = getJson.getStringJson(getInforAccount);
            System.out.println("h" + inforAccount);
            Account account = gson.fromJson(inforAccount,Account.class);
        }
    }

}
