package com.zafu.bbs;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class ResignActivity extends AppCompatActivity {
    private static String tips1="wait for internet";
    private static String tip1="10";
    private TextView Tipss;
    EditText reusername;
    EditText repassword;
    EditText rephone;
    EditText renickname;
    Button submit;
    String username=null;
    String password=null;
    String jsonString=null;
    String phone=null;
    String nickname=null;
    String str=null;
    private static final int REQUEST_EX = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resign);
        init();
      submit=(Button)findViewById(R.id.submit);
      submit.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v) {
              if(reusername.getText().toString().equals("")||repassword.getText().toString().equals("")||rephone.getText().toString().equals("")||renickname.getText().toString().equals("")){
                  Toast.makeText(ResignActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
              } else{
                  username=reusername.getText().toString();
                  password=repassword.getText().toString();
                  phone=rephone.getText().toString();
                  nickname=renickname.getText().toString();
                  resignTask rT=new resignTask ();
                  rT.execute(username,password,phone,nickname);
          }
          }
      });
    }
    private void init(){
        submit=(Button) findViewById(R.id.submit);
        renickname=(EditText)findViewById(R.id.nikoname);
        rephone=(EditText)findViewById(R.id.phone);
        repassword=(EditText)findViewById(R.id.repassword);
        reusername=(EditText)findViewById(R.id.reusername);
        Tipss =(TextView)findViewById(R.id.Tips);
    }

    private void  postJson(String username1,String password1,String phone1, String nickname1){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("username", username1)
                .add("password", password1)
                .add("phone", phone1)
                .add("nickname",  nickname1)
                .build();
        HttpUtil.sendpost("http://10.0.2.2:8080/An_BBS/ServletResign",new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respnseData=response.body().string();
                parseresigntips(respnseData);
            }
        },body);

    }
    private void parseresigntips(String responseData){
        Gson gson=new Gson();
        //   Tips tip2=gson.fromJson(responseData,Tips.class);
        List<Tips> tipsList=gson.fromJson(responseData,new TypeToken<List<Tips>>(){}.getType());
        for (Tips tip:tipsList){
            tip1=tip.getTip();
            tips1=tip.getTips();
        }
    }
    private class resignTask extends AsyncTask<String,Integer,String> {


        @Override
        protected void onPreExecute(){
            Log.i("ASYNC_TASK","called");
            Tipss.setVisibility(View.VISIBLE);


        }
        @Override
        protected String doInBackground(String... strings) {

                postJson(strings[0], strings[1], strings[2], strings[3]);



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(tip1.equals("-1")){
                Intent intent=new Intent(ResignActivity.this,LoginActivity.class);
                //intent.putExtra("user_data",username);
                Toast.makeText(ResignActivity.this, tips1, Toast.LENGTH_SHORT).show();
                // Tipss.setText(tips1);
                startActivity(intent);
                tip1="2";
                tips1="wait for internet";

                finish();
            }else {

                // Toast.makeText(LoginActivity.this, tips1, Toast.LENGTH_SHORT).show();
                Tipss.setText(tips1);
                tip1="2";
                tips1="wait for internet";


            }
        }
        @Override
        protected void onCancelled() {
            Toast.makeText(ResignActivity.this, "Login Canceled", Toast.LENGTH_SHORT).show();
        }
    }

}
