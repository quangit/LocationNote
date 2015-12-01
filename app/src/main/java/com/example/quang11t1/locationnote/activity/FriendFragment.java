package com.example.quang11t1.locationnote.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.adapter.Adapter_Friends;
import com.example.quang11t1.locationnote.adapter.ArrayAdapterAcceptFriend;
import com.example.quang11t1.locationnote.adapter.ArrayAdapterCustom;
import com.example.quang11t1.locationnote.adapter.ArrayAdapterFriend;
import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.FriendBean;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
public class FriendFragment extends Fragment implements ArrayAdapterAcceptFriend.EditPlayerAdapterCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String userName;
    private int idAccount;
    private int idAccount_2;
    ArrayAdapterAcceptFriend arrayAdapterAcceptFriend=null;
    Object object =this;
    ListView listView_accept_friend;
    FriendBean[] arrayFriend=null;

    ImageView imageViewFriend;
    TextView textViewNameFriend;
    EditText editTextSearch;
    Button buttonAdd;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("user");
            idAccount = getArguments().getInt("id", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_friend2, container, false);
        ListView listView=(ListView) view.findViewById(R.id.listView_friend);
        LinearLayout layout_accept_friend =(LinearLayout) view.findViewById(R.id.layout_Accept_Friend);
        layout_accept_friend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_accept_friend);
                dialog.setTitle("Lời mời kết bạn");
                listView_accept_friend =(ListView) dialog.findViewById(R.id.listView_accept_friend);
               // GetListLocation getListLocation =new GetListLocation(listView);
                //getListLocation.execute("a");
                GetListAcceptAccount getListAcceptAccount =new GetListAcceptAccount(listView_accept_friend);
                getListAcceptAccount.execute("A");

                //String[]list = {"awdwa","adwa","adggada"};
                //ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
                //listView_accept_friend.setAdapter(adapter);
                dialog.show();
            }
        });

        GetListAccount getListAccount =new GetListAccount(listView);
        getListAccount.execute("a");


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_friend);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.search_add_friend);
                dialog.setTitle("Tìm kiếm bận bè");
                textViewNameFriend =(TextView) dialog.findViewById(R.id.textView_search_friend);
                editTextSearch =(EditText) dialog.findViewById(R.id.editText_search_friend);
                Button button_search =(Button) dialog.findViewById(R.id.button_search_friend);
                imageViewFriend =(ImageView) dialog.findViewById(R.id.imageView_search_friend);
                final LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.info_friend);
                linearLayout.setVisibility(View.GONE);
                buttonAdd =(Button) dialog.findViewById(R.id.button_add_friend);

                button_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetListInforFriend getListInforFriend =new GetListInforFriend(imageViewFriend,textViewNameFriend,linearLayout,editTextSearch.getText().toString());
                        getListInforFriend.execute("a");
                    }
                });
                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddFriend addFriend =new AddFriend(linearLayout);
                        addFriend.execute();
                    }
                });
                dialog.show();
            }
        });

        return view;
    }




    @Override
    public   void acceptFriend(int position){
        Toast.makeText(getContext(),"Accept"+arrayFriend[position].getIdFriend(),Toast.LENGTH_LONG).show();
        AcceptFriend acceptFriend =new AcceptFriend();

        acceptFriend.execute(arrayFriend[position].getIdFriend());
        GetListAcceptAccount getListAcceptAccount =new GetListAcceptAccount(listView_accept_friend);
        //getListAcceptAccount.execute("a");
        getListAcceptAccount.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"a");
    }

    @Override
    public void noAcceptFriend(int position){
        Toast.makeText(getContext(),"No accept",Toast.LENGTH_LONG).show();
        NoAcceptFriend noAcceptFriend =new NoAcceptFriend();

        noAcceptFriend.execute(arrayFriend[position].getIdFriend());
        GetListAcceptAccount getListAcceptAccount =new GetListAcceptAccount(listView_accept_friend);
        //getListAcceptAccount.execute("a");
        getListAcceptAccount.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, "a");
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


    // get List accept friend

    private class GetListAcceptAccount extends AsyncTask<String,List<Account>,List<Account>> {
        Gson gson=new Gson();
        GetJson getJson=new GetJson();

        ListView listView;

        public GetListAcceptAccount(ListView listView) {
            this.listView = listView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Account> accounts) {
            super.onPostExecute(accounts);

            ArrayAdapterAcceptFriend adapter =new ArrayAdapterAcceptFriend(getActivity(),R.layout.custom_row_accept_friend,accounts);

            adapter.setCallback((ArrayAdapterAcceptFriend.EditPlayerAdapterCallback)object);
            listView.setAdapter(adapter);
        }

        @Override
        protected void onProgressUpdate(List<Account>... values) {
            super.onProgressUpdate(values);
        }



        @Override
        protected List<Account> doInBackground(String... params) {
            String getFriendLink= getString(R.string.link)+"Friend/isFriend?IDACCOUNT="+idAccount;
            String result = getJson.getStringJson(getFriendLink);
            System.out.println("chuoi lay ve duoc :" + result);
            arrayFriend = gson.fromJson(result,FriendBean[].class);
            List<Account> listAcount =new ArrayList<>();
            for (FriendBean friendBean:arrayFriend)
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

    private class AcceptFriend extends AsyncTask<Integer,Void,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean bolean) {
            super.onPostExecute(bolean);
            if(bolean) Toast.makeText(getContext(),"Thanh cong",Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(),"that bai",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            GetJson getJson=new GetJson();
            int idFriend = params[0];
            String addNote= getString(R.string.link)+"Friend/AcceptFriend?IDFRIEND="+idFriend;
            String result = getJson.getStringJson(addNote);
            if (result.equals("1"))
            {
                return true;
            }
            else {
                return false;
            }
        }
    }

    private class NoAcceptFriend extends AsyncTask<Integer,Void,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean bolean) {
            super.onPostExecute(bolean);
            if(bolean) Toast.makeText(getContext(),"Thanh cong",Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(),"that bai",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            GetJson getJson=new GetJson();
            int idFriend = params[0];
            String addNote= getString(R.string.link)+"Friend/NoAcceptFriend?IDFRIEND="+idFriend;
            String result = getJson.getStringJson(addNote);
            if (result.equals("1"))
            {
                return true;
            }
            else {
                return false;
            }
        }
    }

    protected class GetListInforFriend extends AsyncTask<String,Account,Account> {
        Gson gson=new Gson();
        GetJson getJson=new GetJson();

        String userName;
        LinearLayout linearLayout;
        ImageView imageView;
        TextView textView;

        public GetListInforFriend(ImageView imageView,TextView textView,LinearLayout linearLayout,String userName) {
            this.imageView =imageView;
            this.textView = textView;
            this.userName = userName;
            this.linearLayout =linearLayout;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Account account) {
            super.onPostExecute(account);
            if (account !=null) {
                linearLayout.setVisibility(View.VISIBLE);
                textView.setText(account.getUsername());
                DownloadImageTask downloadImageTask = new DownloadImageTask(imageView);
                downloadImageTask.execute(account.getImage());
                IsNotFriend isNotFriend =new IsNotFriend();
                isNotFriend.execute(account.getIdAccount());
                idAccount_2=account.getIdAccount();
            }
            else {
                linearLayout.setVisibility(View.GONE);
            }

        }

        @Override
        protected void onProgressUpdate(Account... values) {
            super.onProgressUpdate(values);
        }



        @Override
        protected Account doInBackground(String... params) {
            Account account=null;
            try {
                String getFriendLink = getString(R.string.link) + "login/user?USERNAME=" + userName;
                String result = getJson.getStringJson(getFriendLink);
                System.out.println("chuoi lay ve duoc :" + result);
                account = gson.fromJson(result, Account.class);
            }catch (Exception e){}
            return account;
        }
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
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    private class IsNotFriend extends AsyncTask<Integer,Void,Boolean>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean bolean) {
            super.onPostExecute(bolean);
            if(bolean) buttonAdd.setVisibility(View.VISIBLE);
            else buttonAdd.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            GetJson getJson=new GetJson();
            int idAccount2 = params[0];
            String addNote= getString(R.string.link)+"Friend/isNotFriend?IDACCOUNT1="+idAccount+"&IDACCOUNT2="+idAccount2;
            String result = getJson.getStringJson(addNote);
            if (result.equals("1"))
            {
                return true;
            }
            else {
                return false;
            }
        }
    }

    private class AddFriend extends AsyncTask<Void,Void,Boolean>
    {
        LinearLayout linearLayout;

        public AddFriend(LinearLayout linearLayout) {
            this.linearLayout = linearLayout;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        @Override
        protected void onPostExecute(Boolean aBoolean) {



            super.onPostExecute(aBoolean);
            if (aBoolean) {
                linearLayout.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Thanh cong",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getContext(),"That bai",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            GetJson getJson=new GetJson();
            String addNote= getString(R.string.link)+"Friend/AddFriend?IDACCOUNT1="+idAccount+"&IDACCOUNT2="+idAccount_2;
            String result = getJson.getStringJson(addNote);
            if (result.equals("1"))
            {
                return true;
            }
            else {
                return false;
            }
        }
    }


}
