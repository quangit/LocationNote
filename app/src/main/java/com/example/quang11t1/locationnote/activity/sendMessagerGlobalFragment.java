package com.example.quang11t1.locationnote.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.modle.Location;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;


public class sendMessagerGlobalFragment extends Fragment implements OnClickListener {
    private static final int CAM_REQUEST = 1313;
    private static int LOAD_IMAGE_RESULTS = 101;
    ImageButton btnTakePhoto;
    ImageButton btnTakephotofromgallery,btnEmoticons;
    View rootview;
    ImageView imgViewphoto;
    ImageView imgViewphotofromgallery;
    String imagepath = "";
    EditText editText_content;
    int idAccount = 0;
    Handler handler;
    int idLocal = 2;
    float Longitude,Latitude;
    List<Location> locationList;
    EditText editText_friend;
    RequestParams params = new RequestParams();
    ProgressDialog msgDialog;
    int idNote;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msgDialog = new ProgressDialog(getContext());
        // Set Cancelable as False
        msgDialog.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_send_messager_global, container, false);
        btnTakePhoto = (ImageButton) rootview.findViewById(R.id.imageButtoncamera);
        btnTakephotofromgallery = (ImageButton) rootview.findViewById(R.id.imageButtonGallery);
        btnEmoticons=(ImageButton) rootview.findViewById(R.id.imageButtonenomotion);
        imgViewphoto = (ImageView) rootview.findViewById(R.id.imgviewPhoto);
        imgViewphotofromgallery = (ImageView) rootview.findViewById(R.id.imgviewPhotofromgallery);
        btnTakePhoto.setOnClickListener(this);
        btnTakephotofromgallery.setOnClickListener(this);
        btnEmoticons.setOnClickListener(this);
        editText_content = (EditText) rootview.findViewById(R.id.editText_context);
        editText_friend = (EditText) rootview.findViewById(R.id.editText_friend);

        editText_friend.setOnClickListener(this);
        ImageButton imageButton_send = (ImageButton) rootview.findViewById(R.id.imageButton_send);
        imageButton_send.setOnClickListener(this);
        idAccount = getArguments().getInt("id", 0);
        Longitude =getArguments().getFloat("long",0);
        Latitude =getArguments().getFloat("lat",0);
        Toast.makeText(getContext(), "" + idAccount, Toast.LENGTH_LONG).show();

        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int result = msg.arg1;
                if (result != 0) {
                    idNote = result;
                    if (!imagepath.equals("")) {
                        msgDialog.setMessage("Sending...");
                        msgDialog.show();
                        encodeImagetoString();
                    } else {
                        Toast.makeText(getContext(), "add thanh cong" + result, Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                    /*Intent intent=getIntent();
                    intent.putExtra("account", account);
                    setResult(1, intent);
                    finish();*/
                } else {
                    Toast.makeText(getContext(), "add Khong thanhj cong", Toast.LENGTH_SHORT).show();
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
        if (data != null && requestCode != 0 && resultCode != 0) {
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
                imagepath = imagePath;
                Toast.makeText(getContext(), imagePath, Toast.LENGTH_SHORT).show();
                // Now we need to set the GUI ImageView data with data read from the picked file.
                imgViewphotofromgallery.setImageBitmap(BitmapFactory.decodeFile(imagePath));

                String fileNameSegments[] = imagePath.split("/");
                String fileName = fileNameSegments[fileNameSegments.length - 1];
                // Put file name in Async Http Post Param which will used in Java web app
                params.put("filename", fileName);


                // At the end remember to close the cursor or you will end with the RuntimeException!
                cursor.close();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageButtonGallery) {
            Toast toast = Toast.makeText(getActivity(), "hdihi", Toast.LENGTH_LONG);
            toast.show();
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, LOAD_IMAGE_RESULTS);
        }
        if (v.getId() == R.id.imageButtoncamera) {
            Toast toast = Toast.makeText(getActivity(), "hihi", Toast.LENGTH_LONG);
            toast.show();
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAM_REQUEST);
        }
        if (v.getId() == R.id.imageButton_send) {


            String contextMessager = null;
            if (!editText_content.getText().toString().trim().equals("")) {

                contextMessager = editText_content.getText().toString();
                AddNote addNote = new AddNote(contextMessager, idLocal, getContext());
                addNote.start();
                Toast.makeText(getActivity(), contextMessager, Toast.LENGTH_LONG).show();
            }
        }
        if(v.getId()==R.id.imageButtonenomotion){
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.choose_emoticons);
            dialog.setTitle("Chon emoticons");
            dialog.show();
            final ImageView imageView401=(ImageView) dialog.findViewById(R.id.imageButtone401);
            final ImageView imageView402=(ImageView) dialog.findViewById(R.id.imageButtone402);
            final ImageView imageView403=(ImageView) dialog.findViewById(R.id.imageButtone403);
            final ImageView imageView404=(ImageView) dialog.findViewById(R.id.imageButtone404);
            final ImageView imageView405=(ImageView) dialog.findViewById(R.id.imageButtone405);
            final ImageView imageView406=(ImageView) dialog.findViewById(R.id.imageButtone406);
            final ImageView imageView407=(ImageView) dialog.findViewById(R.id.imageButtone407);
            final ImageView imageView408=(ImageView) dialog.findViewById(R.id.imageButtone408);
            final ImageView imageView409=(ImageView) dialog.findViewById(R.id.imageButtone409);
            final ImageView imageView410=(ImageView) dialog.findViewById(R.id.imageButtone410);
            final ImageView imageView411=(ImageView) dialog.findViewById(R.id.imageButtone411);
            final ImageView imageView412=(ImageView) dialog.findViewById(R.id.imageButtone412);
            final ImageView imageView413=(ImageView) dialog.findViewById(R.id.imageButtone413);
            final ImageView imageView414=(ImageView) dialog.findViewById(R.id.imageButtone414);
            final ImageView imageView415=(ImageView) dialog.findViewById(R.id.imageButtone415);
            final ImageView imageView416=(ImageView) dialog.findViewById(R.id.imageButtone416);
            final ImageView imageView417=(ImageView) dialog.findViewById(R.id.imageButtone417);
            final ImageView imageView418=(ImageView) dialog.findViewById(R.id.imageButtone418);

            dialog.show();
            imageView401.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView401.getDrawable());
                    dialog.cancel();
                }
            });
            imageView402.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView402.getDrawable());
                    dialog.cancel();
                }
            });
            imageView403.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView403.getDrawable());
                    dialog.cancel();
                }
            });
            imageView404.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView404.getDrawable());
                    dialog.cancel();
                }
            });
            imageView405.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView405.getDrawable());
                    dialog.cancel();
                }
            });
            imageView406.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView406.getDrawable());
                    dialog.cancel();
                }
            });
            imageView407.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView407.getDrawable());
                    dialog.cancel();
                }
            });
            imageView408.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView408.getDrawable());
                    dialog.cancel();
                }
            });
            imageView409.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView409.getDrawable());
                    dialog.cancel();
                }
            });
            imageView410.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView410.getDrawable());
                    dialog.cancel();
                }
            });
            imageView411.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView411.getDrawable());
                    dialog.cancel();
                }
            });
            imageView412.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView412.getDrawable());
                    dialog.cancel();
                }
            });
            imageView413.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView413.getDrawable());
                    dialog.cancel();
                }
            });
            imageView414.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView414.getDrawable());
                    dialog.cancel();
                }
            });
            imageView415.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView415.getDrawable());
                    dialog.cancel();
                }
            });
            imageView416.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView416.getDrawable());
                    dialog.cancel();
                }
            });
            imageView417.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView417.getDrawable());
                    dialog.cancel();
                }
            });
            imageView418.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addImageBetweentext(imageView418.getDrawable());
                    dialog.cancel();
                }
            });
        }
        if (v.getId() == R.id.editText_friend) {

            // custom dialog
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.choose_location_custom);
            dialog.setTitle("Chọn vị trí");
            ListView listView = (ListView) dialog.findViewById(R.id.listView_location);
            GetListLocation getListLocation = new GetListLocation(listView);
            getListLocation.execute("a");

            // set the custom dialog components - text, image and button
            final EditText editText_Location = (EditText) dialog.findViewById(R.id.editText_search_location);
            dialog.show();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    idLocal = locationList.get(position).getIdLocation();
                    Toast.makeText(getActivity(), "location:" + idLocal, Toast.LENGTH_LONG).show();
                    editText_friend.setText(locationList.get(position).getLocationName());
                    dialog.cancel();
                }
            });

        }

    }

    // AsyncTask - To convert Image to String
    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                Bitmap bitmap = BitmapFactory.decodeFile(imagepath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                String encodedString = Base64.encodeToString(byte_arr, 0);
                return encodedString;
            }

            @Override
            protected void onPostExecute(String msg) {
                msgDialog.setMessage("Calling Upload");
                // Put converted Image string into Async Http Post param
                params.put("image", msg);
                // Trigger Image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public void triggerImageUpload() {
        makeHTTPCall();
    }

    // http://192.168.2.4:9000/imgupload/upload_image.php
    // http://192.168.2.4:9999/ImageUploadWebApp/uploadimg.jsp
    // Make Http call to upload Image to Java server
    public void makeHTTPCall() {
        msgDialog.setMessage("Invoking JSP");
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post("http://104.155.202.249/ImageUploadWebApp/uploadimg.jsp",
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        msgDialog.hide();
                        Toast.makeText(getContext(), response,
                                Toast.LENGTH_LONG).show();
                        String contextMessager = null;
                        if (!editText_content.getText().toString().trim().equals("")) {
                            contextMessager = editText_content.getText().toString();
                            //AddNote addNote =new AddNote(contextMessager,idLocal,getContext());
                            //addNote.start();
                            String tam = response.substring(4, response.length() - 2);
                            System.out.println(tam);
                            System.out.println(response);
                            tam = "http://104.155.202.249/LocationNote/Image/getImage?LINK=C:/Image/" + tam;
                            //SendMesage sendMesage = new SendMesage(contextMessager, idFriend,tam,Longitude,Latitude, getContext());
                            //sendMesage.start();
                            new AddImageNote().execute(tam);
                            Toast.makeText(getActivity(), contextMessager, Toast.LENGTH_LONG).show();
                        }
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog
                        msgDialog.hide();
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getContext(),
                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

    private void addImageBetweentext(Drawable drawable) {
        drawable .setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        int selectionCursor = editText_content.getSelectionStart();
        editText_content.getText().insert(selectionCursor, ".");
        selectionCursor = editText_content.getSelectionStart();

        SpannableStringBuilder builder = new SpannableStringBuilder(editText_content.getText());
        builder.setSpan(new ImageSpan(drawable), selectionCursor - ".".length(), selectionCursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText_content.setText(builder);
        editText_content.setSelection(selectionCursor);
    }

    public class AddNote extends Thread {

        String content;
        int idLocation;
        Context context;
        Gson gson = new Gson();
        GetJson getJson = new GetJson();

        public AddNote(String content, int idLocation, Context context) {
            this.content = content;
            this.idLocation = idLocation;
            this.context = context;
        }

        @Override
        public void run() {
            String addNote = getString(R.string.link) + "Note/addNote?IDACCOUNT=" + idAccount + "&IDLOCATION=" + idLocation + "&CONTENT=" + content;
            String result = getJson.getStringJson(addNote);
            Message msg = handler.obtainMessage();

            msg.arg1 = Integer.parseInt(result);
            //gửi lại Message này về cho Main Thread
            handler.sendMessage(msg);
        }
    }

    public class GetListLocation extends AsyncTask<String, List<Location>, List<Location>> {
        Gson gson = new Gson();
        GetJson getJson = new GetJson();

        ListView listView;

        public GetListLocation(ListView listView) {
            this.listView = listView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Location> locations) {
            super.onPostExecute(locations);
            String[] arrayString = new String[locations.size()];
            locationList = locations;
            int i = 0;
            for (Location location : locations) {
                arrayString[i] = location.getLocationName();
                i++;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (getContext(), android.R.layout.simple_list_item_1, arrayString);
            listView.setAdapter(adapter);


        }

        @Override
        protected void onProgressUpdate(List<Location>... values) {
            super.onProgressUpdate(values);


        }

        @Override
        protected List<Location> doInBackground(String... params) {
            String locationslink = getString(R.string.link) + "Location/list?LONGITUDE="+Longitude+"&LATITUDE="+Latitude+"&RADIUS=0.005";
            String result = getJson.getStringJson(locationslink);
            System.out.println("chuoi lay ve duoc :" + result);
            Location[] locationList = gson.fromJson(result, Location[].class);
            return Arrays.asList(locationList);
        }
    }

    protected class AddImageNote extends  AsyncTask<String,Void,Boolean>{

        GetJson getJson =new GetJson();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                Toast.makeText(getContext(), "add thanh cong Image", Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(getContext(),"add khong thanh cong Image",Toast.LENGTH_LONG).show();
            }
            getActivity().finish();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String link = params[0];
            String addNote = getString(R.string.link) + "Image/addImageNote?LINK="+link+"&IDNOTE="+idNote;
            String result = getJson.getStringJson(addNote);
            if(result.equals("1"))
            {
                return true;
            }
            return false;
        }
    }
}
