package com.example.quang11t1.locationnote.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quang11t1.locationnote.R;
import com.example.quang11t1.locationnote.controller.LoginController;
import com.example.quang11t1.locationnote.modle.Account;
import com.example.quang11t1.locationnote.modle.User;
import com.example.quang11t1.locationnote.support.GetJson;
import com.google.gson.Gson;

public class Login_activity extends AppCompatActivity {

    Handler handler;
    GetJson getJson =new GetJson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button button = (Button) findViewById(R.id.button_login);
        //final LoginController loginController=new LoginController(this);

        final EditText editText_user =(EditText) findViewById(R.id.editText_user);
        final EditText editText_pass=(EditText) findViewById(R.id.editText_password);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 LoginController loginController=new LoginController(getApplicationContext());

                User user =new User(editText_user.getText().toString(),editText_pass.getText().toString());
                //loginController.execute(user);
                doStart(user);
            }
        });
        handler=new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Account account = (Account)msg.obj;
                if(account!=null){
                    //saveInforLogin(account);
                    sendToMain(3,account);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không đúng",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getApplicationContext(),msg.arg1,Toast.LENGTH_SHORT).show();
            }
        };
    }

    public  void saveInforLogin(Account account)
    {
        try{
        //tạo đối tượng getSharedPreferences
        SharedPreferences pre=getSharedPreferences("inforLogin", MODE_PRIVATE);
        CheckBox checkBox_remember =(CheckBox) findViewById(R.id.checkBox_remember_login);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        boolean check_remember_login = checkBox_remember.isChecked();
        if (check_remember_login)
        {
            editor.putString("user",account.getUsername());
            editor.putString("pass",account.getPassword());
            editor.putString("url",account.getImage());
            editor.putString("email",account.getEmail());
            editor.putBoolean("check", check_remember_login);
           // Toast.makeText(this,"luu thong tin dang nhap",Toast.LENGTH_SHORT).show();
        }
        else {
            editor.clear();
            //Toast.makeText(this,"khong luu thong tin dang nhap",Toast.LENGTH_SHORT).show();
        }
        editor.commit();
        }catch ( Throwable e){Toast.makeText(this,"khong luu thong tin dang nhap "+e,Toast.LENGTH_SHORT).show();}
    }
    public void sendToMain(int resultcode,Account account)
    {
        Intent intent=getIntent();
        intent.putExtra("account", account);
        setResult(resultcode, intent);
        finish();
    }

    public class GetInforAccount extends Thread{

        User user;
        Context context;
        Gson gson=new Gson();
        public GetInforAccount(User user,Context context)
        {
            this.user=user;
            this.context=context;
        }

        @Override
        public void run() {
            String inforLogin= getString(R.string.link)+"login/isLogin?USERNAME="+user.getUsername()+
                    "&PASSWORD="+user.getPassword();
            String result = getJson.getStringJson(inforLogin);
            Message msg=handler.obtainMessage();

            if (result.equals("1"))
            {
                String getInforAccount = context.getString(R.string.link)+"login/user?USERNAME="+user.getUsername();
                String inforAccount = getJson.getStringJson(getInforAccount);
                Account account = gson.fromJson(inforAccount,Account.class);
                msg.obj=account;
            }
            else {
                //gán giá trị vào cho arg1 để gửi về Main thread
                msg.obj = null;
            }
            //gửi lại Message này về cho Main Thread
            handler.sendMessage(msg);
        }
    }

    public void doStart(User user)
    {
        GetInforAccount getInforAccount =new GetInforAccount(user,this);
        getInforAccount.start();
    }

}
