package com.zafu.bbs;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class myhuifuActivity extends AppCompatActivity {
    private String user_id;
    RecyclerView _lstDetail;
    DetailAdapter _adapter;
    private List< HuifuDetailInfo > _data;
    private List<HuifuDetailInfo> _data2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhuifu);
        Intent intent=getIntent();
        user_id=intent.getStringExtra("user_id");
        _lstDetail = (RecyclerView)findViewById(R.id.hfofmine);
        _lstDetail.setLayoutManager(
                new LinearLayoutManager( this )
        );
        LinearLayoutManager lM=new LinearLayoutManager(this);
        lM.setOrientation(LinearLayoutManager.VERTICAL);
        _lstDetail.setLayoutManager( lM );
        _data = new ArrayList<HuifuDetailInfo>();
        _data2=new ArrayList<HuifuDetailInfo>();
        init();
        _adapter = new DetailAdapter( _data );
        _lstDetail.setAdapter( _adapter );
    }
    private void   init(){
        new mhaTask().execute();
    };
    private class DetailHolder
            extends RecyclerView.ViewHolder{

        View _detailView; // 1)保存界面本身
        TextView huifu_title;
        TextView huifu_content;
        // 控件要初始化
        public DetailHolder(View itemView) {
            super(itemView);
            _detailView = itemView; // 赋值，保存视图

            huifu_title = (TextView)itemView
                    .findViewById(R.id.tiezititle);
            huifu_content = (TextView)itemView
                    .findViewById(R.id.huifuinfo);


        }
    }
    // 适配器：数据管理
    private class DetailAdapter
            extends RecyclerView.Adapter<DetailHolder>{
        private List< HuifuDetailInfo > _data;

        public DetailAdapter( List< HuifuDetailInfo > data ){
            _data = data;
        }

        // 数量
        @Override
        public int getItemCount() {
            return _data.size();
        }

        @Override
        public DetailHolder onCreateViewHolder(
                ViewGroup parent,
                int viewType) {
            LayoutInflater inflater =
                    LayoutInflater.from( myhuifuActivity.this );
            // 根据train_detail.xml，解析界面
            View view = inflater.inflate( R.layout.myhuifu,
                    parent, false );
            final DetailHolder holder = new DetailHolder(view);
            holder._detailView.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          int position =
                                                                  holder.getAdapterPosition();
                                                          HuifuDetailInfo info = _data.get(position);
                                                          Intent intent =new Intent(myhuifuActivity.this,PostActivity.class);
                                                          intent.putExtra("tiezi_ID",info.getTiezi_id());
                                                          intent.putExtra("tiezi_name",info.getTitle());
                                                          intent.putExtra("user_id",user_id);
                                                          startActivity(intent);
                                                      }
                                                  }
            );
            return holder;
        }

        @Override
        public void onBindViewHolder(
                DetailHolder holder, // 控件
                int position) {// 在列表的下标
            // 得到数据
            HuifuDetailInfo info = _data.get( position );
            // 控件赋值
            holder.huifu_title.setText(info.getTitle());
            holder.huifu_content.setText(info.getContent());

        }
    }
    private void  postJson(){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("user_id", user_id)
                .build();
        HttpUtil.sendpost("http://10.0.2.2:8080/An_BBS/ServletGetMyhuifu",new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respnseData=response.body().string();

                parsehuifu(respnseData);
                //Log.i("ASYNC_TASK12",respnseData);
            }
        },body);



    }
    private void parsehuifu(String responseData){
        Gson gson=new Gson();
        List<HuifuDetailInfo> tiebaList=gson.fromJson(responseData,new TypeToken<List<HuifuDetailInfo>>(){}.getType());
        for (HuifuDetailInfo reD:tiebaList){
            String title=reD.getTitle();
            String content=reD.getContent();
            String id=reD.getTiezi_id();
            _data2.add(new HuifuDetailInfo(id,title,content));

        }
    }
    private class mhaTask extends AsyncTask<String,Integer,String> {


        @Override
        protected void onPreExecute(){
            // Log.i("ASYNC_TASK","called");

        }
        @Override
        protected String doInBackground(String... strings) {
            postJson();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            _data=_data2;

            //    Log.i("bk",TiebaName+"1");
            //   p_name.setText(TiebaName);
            _adapter = new DetailAdapter( _data );
            _lstDetail.setAdapter( _adapter );
        }
    }
}
