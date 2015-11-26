package com.example.quang11t1.locationnote.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.quang11t1.locationnote.Editphoto.CircleImage;
import com.example.quang11t1.locationnote.Entity.Myfriend;
import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.Adapter_Friends;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Friend extends Fragment{
    View rootview;
    public ArrayList<Myfriend> arraylistComment = new ArrayList<Myfriend>();
   Myfriend myfriend;
   private ListView listViewFriend;
   // ImageView imViewAndroid;
   // ListView lv_friends;
    CircleImage circleImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_friend, container, false);
        createtmpComment();
        listViewFriend = (ListView)rootview.findViewById(R.id.lv_friends);
        Adapter_Friends adapter_friends = new Adapter_Friends(getActivity(),arraylistComment);
        //commentAdapter adapter=new commentAdapter(getActivity(),arraylistComment);
        listViewFriend.setAdapter(adapter_friends);
       // listViewComment.setAdapter(adapter);
       // imViewAndroid = (ImageView) rootview.findViewById(R.id.imageviewAvarta);
        //imViewAndroid.setImageBitmap(roundCornerImage(BitmapFactory.decodeResource(getResources(), R.drawable.vie),60));
        //Bitmap bm = BitmapFactory.decodeResource(getResources(),
             //   R.drawable.vie);

        //new DownloadImageTask((ImageView) rootview.findViewById(R.id.imageviewAvarta))
              //  .execute("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
        //circleImage=new CircleImage();
        //imViewAndroid.setImageBitmap(circleImage.getCircleBitmap(bm));
        return rootview;
    }

    public void createtmpComment(){
        myfriend=new Myfriend("Nguyen Van A");
        arraylistComment.add(myfriend);
        myfriend=new Myfriend("Nguyen Van B");
        arraylistComment.add(myfriend);
        myfriend=new Myfriend("Nguyen Van C");
        arraylistComment.add(myfriend);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* lv_friends = (ListView)getActivity().findViewById(R.id.lv_friends);
        List<String> list = new ArrayList<String>();
        list.add("Nguyen Van A");
        list.add("Nguyen Van B");
        list.add("Nguyen Van C");
        list.add("Nguyen Van D");
        list.add("Nguyen Van E");
        list.add("Nguyen Van F");
        list.add("Nguyen Van I");
        list.add("Nguyen Van J");
        list.add("Nguyen Van K");
        list.add("Nguyen Van L");
        list.add("Nguyen Van M");
        list.add("Nguyen Van N");
        adapter_friends = new Adapter_Friends(getActivity(),list);
       lv_friends.setAdapter(adapter_friends);
       // adapter_friends.notifyDataSetChanged();*/
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
            circleImage=new CircleImage();
            mIcon11=circleImage.getCircleBitmap(mIcon11);
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
