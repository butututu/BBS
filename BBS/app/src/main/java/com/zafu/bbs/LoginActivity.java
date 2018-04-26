package com.zafu.bbs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class LoginActivity extends AppCompatActivity {
    private static String tips1="login error!";
    private static String tip1="2";
    private TextView Tipss;
    private EditText user2;
    private EditText password2;
   private Button login;
   private UserDetailinfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.login);
        user2=(EditText)findViewById(R.id.user);
        user=new UserDetailinfo();
        password2=(EditText)findViewById(R.id.password);
       Tipss =(TextView)findViewById(R.id.Tips);
       user2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {
               Tipss.setVisibility(View.INVISIBLE);
           }
       });
        password2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Tipss.setVisibility(View.INVISIBLE);
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText user=(EditText)findViewById(R.id.user);
                EditText password=(EditText)findViewById(R.id.password);

                if(user.getText().toString().equals("")||password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    String username=user.getText().toString();
                    String passwordn=password.getText().toString();
                    loginTask lT=new loginTask();
                    lT.execute(username,passwordn);



                }
            }
        });
        Button resign=(Button)findViewById(R.id.resign);
        resign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginActivity.this,ResignActivity.class);
                startActivity(intent);
            }
        });

    }
    private void  postJson(String username,String password){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        HttpUtil.sendpost("http://10.0.2.2:8080/An_BBS/ServletForPOSTMethod",new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                tips1="network error";
                tip1="3";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respnseData=response.body().string();
                //tips1=respnseData;
                parselogintips(respnseData);
            }
        },body);

    }

    private void parselogintips(String responseData){
        Gson gson=new Gson();
     //   Tips tip2=gson.fromJson(responseData,Tips.class);
       List<UserDetailinfo> tipsList=gson.fromJson(responseData,new TypeToken<List<UserDetailinfo>>(){}.getType());
       for (UserDetailinfo tip:tipsList){
            tip1=tip.getTip();
            tips1=tip.getTips();
            user.setUserid(tip.getUserid());
            user.setNickname(tip.getNickname());
            user.setId(tip.getId());
        }
    }

private class loginTask extends AsyncTask<String,Integer,String>{


    @Override
  protected void onPreExecute(){
        Log.i("ASYNC_TASK","called");
        Tipss.setVisibility(View.VISIBLE);


    }
    @Override
    protected String doInBackground(String... strings) {

            postJson(strings[0],strings[1]);



        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String username=((EditText)findViewById(R.id.user)).getText().toString();
        if(tip1.equals("1")){
                        Intent intent=new Intent(LoginActivity.this,VisitActivity.class);
                       intent.putExtra("user_data",user.getUserid());
                        Toast.makeText(LoginActivity.this, tips1, Toast.LENGTH_SHORT).show();
                        intent.putExtra("user_nickname",user.getNickname());
                       intent.putExtra("user_id",user.getId());
                       // Tipss.setText(tips1);
                        startActivity(intent);
            tip1="2";
            tips1="wait for internet";

                       finish();
                }else if(tip1.equals("0")){

                       // Toast.makeText(LoginActivity.this, tips1, Toast.LENGTH_SHORT).show();
                        Tipss.setText(tips1);
                        user2.setText("");
                        password2.setText("");
                       tip1="2";
                       tips1="wait for internet";


        }else if(tip1.equals("2")){
            Tipss.setText(tip1+tips1);
            tip1="2";


        }else if(tip1.equals("3")){
            Tipss.setText(tip1+tips1);
        }
    }
    @Override
    protected void onCancelled() {
        Toast.makeText(LoginActivity.this, "Login Canceled", Toast.LENGTH_SHORT).show();
    }
}


}
