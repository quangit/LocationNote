package com.example.quang11t1.locationnote.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.quang11t1.locationnote.Editphoto.CircleImage;
import com.example.quang11t1.locationnote.Entity.Comment;
import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.commentAdapter;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Z0NEN on 10/22/2014.
 */
public class detail_message extends Activity {
    View rootview;
    public ArrayList<Comment> arraylistComment = new ArrayList<Comment>();
    Comment comment;
    private ListView listViewComment;
    ImageView imViewAndroid;
    CircleImage circleImage;
    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_message);
        createtmpComment();
        listViewComment = (ListView) rootview.findViewById(R.id.listviewComment);
        commentAdapter adapter=new commentAdapter(detail_message.this,arraylistComment);
        listViewComment.setAdapter(adapter);
        imViewAndroid = (ImageView) rootview.findViewById(R.id.imageviewAvarta);
        //imViewAndroid.setImageBitmap(roundCornerImage(BitmapFactory.decodeResource(getResources(), R.drawable.vie),60));
        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.vie);

        new DownloadImageTask((ImageView) rootview.findViewById(R.id.imageviewAvarta))
                .execute("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
        //circleImage=new CircleImage();
        //imViewAndroid.setImageBitmap(circleImage.getCircleBitmap(bm));
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
        comment=new Comment("tuyet zoi");
        arraylistComment.add(comment);
        comment=new Comment("ua cho nao ma co may mon nay ngon vay, chi vs");
        arraylistComment.add(comment);
        comment=new Comment("cung thuong thoi");
        arraylistComment.add(comment);
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
}
