package com.zafu.bbs;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostActivity extends AppCompatActivity {
    RecyclerView _lstDetail;
    DetailAdapter _adapter;
    private List<tieziDetailInfo> _data2;
    private List< tieziDetailInfo> _data;
    private String TieziName;
    private  TextView TName;
    private    String Tiezi_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent intent=getIntent();
        Tiezi_ID=intent.getStringExtra("tiezi_ID");
        TieziName=intent.getStringExtra("tiezi_name");
        _lstDetail = (RecyclerView)findViewById(R.id.view_post_list);
        _lstDetail.setLayoutManager(
                new LinearLayoutManager( this )
        );
        LinearLayoutManager lM=new LinearLayoutManager(this);
        lM.setOrientation(LinearLayoutManager.VERTICAL);
        _lstDetail.setLayoutManager( lM );
        _data = new ArrayList<tieziDetailInfo>();
        _data2=new ArrayList<tieziDetailInfo>();
        TName=(TextView)findViewById(R.id.tiezi_name) ;
        TName.setText(TieziName);
        init();
        _adapter = new DetailAdapter( _data );
        _lstDetail.setAdapter( _adapter );
    }

    private  void init(){
        new PATask().execute();
    }
    private class DetailHolder
            extends RecyclerView.ViewHolder{

        View _detailView; // 1)保存界面本身
        TextView post_nick;
        TextView post_info;
        TextView post_lc;
        // 控件要初始化
        public DetailHolder(View itemView) {
            super(itemView);
            _detailView = itemView; // 赋值，保存视图

            post_info = (TextView)itemView
                    .findViewById(R.id.post_info);
            post_nick = (TextView)itemView
                    .findViewById(R.id.post_nickname);
            post_lc=(TextView)itemView.findViewById(R.id.post_louceng) ;


        }
    }
    // 适配器：数据管理
    private class DetailAdapter
            extends RecyclerView.Adapter<DetailHolder>{
        private List< tieziDetailInfo> _data;

        public DetailAdapter( List< tieziDetailInfo> data ){
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
                    LayoutInflater.from( PostActivity.this );
            // 根据train_detail.xml，解析界面
            View view = inflater.inflate( R.layout.post_view_list,
                    parent, false );
            final DetailHolder holder = new DetailHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(
                DetailHolder holder, // 控件
                int position) {// 在列表的下标
            // 得到数据
            tieziDetailInfo info = _data.get( position );
            // 控件赋值
            holder.post_nick.setText(info.getNickname());
            holder.post_info.setText(info.getInfo());
            holder.post_lc.setText(info.getLouceng());
        }
    }

    private void  postJson(){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("tieziid", Tiezi_ID)
                .build();
        HttpUtil.sendpost("http://10.0.2.2:8080/An_BBS/ServeletGetTieZi",new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respnseData=response.body().string();

                parsetuijian(respnseData);
               // Log.i("ASYNC_TASK12",respnseData);
            }
        },body);



    }
    private void parsetuijian(String responseData){
        Gson gson=new Gson();
        //   Tips tip2=gson.fromJson(responseData,Tips.class);
        List<tieziDetailInfo> tieziList=gson.fromJson(responseData,new TypeToken<List<tieziDetailInfo>>(){}.getType());
        int confirm=0;
        for (tieziDetailInfo reD:tieziList){
            String nickname=reD.getNickname();
            String title=reD.getTitle();
            String info=reD.getInfo();
            String weizhi=reD.getLouceng();
            if (confirm==0){
                TieziName=title;
                confirm=1;
            }
            _data2.add(new tieziDetailInfo(nickname,info,weizhi,title));

        }
    }
    private class PATask extends AsyncTask<String,Integer,String> {


        @Override
        protected void onPreExecute(){
            //Log.i("ASYNC_TASK","called");

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
            //for(int    i=0;    i<_data.size(); i++)    {
               // Log.i("ASYNC_TASK",_data.get(i).getInfo()+" "+_data.get(i).getNickname());
           // }
           // TName.setText(TieziName);
            _adapter = new DetailAdapter( _data );
            _lstDetail.setAdapter( _adapter );
        }
    }
}
