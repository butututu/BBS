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

public class mytieziActivity extends AppCompatActivity {
    RecyclerView _lstDetail;
    mytieziActivity.DetailAdapter _adapter;
    private List< tiebaDetailInfo > _data;
    private List<tiebaDetailInfo> _data2;
    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytiezi);
        Intent intent=getIntent();
        user_id=intent.getStringExtra("user_id");
        _lstDetail = (RecyclerView)findViewById(R.id.tzofmine);
        _lstDetail.setLayoutManager(
                new LinearLayoutManager( this )
        );
        LinearLayoutManager lM=new LinearLayoutManager(this);
        lM.setOrientation(LinearLayoutManager.VERTICAL);
        _lstDetail.setLayoutManager( lM );
        _data = new ArrayList<tiebaDetailInfo>();
        _data2=new ArrayList<tiebaDetailInfo>();
        init();
        _adapter = new DetailAdapter( _data );
        _lstDetail.setAdapter( _adapter );

    }
    private void   init(){
        new mtaTask().execute();
    };
    private class DetailHolder
            extends RecyclerView.ViewHolder{

        View _detailView; // 1)保存界面本身
        TextView tieba_title;
        TextView tieba_user;
        TextView tieba_num;
        // 控件要初始化
        public DetailHolder(View itemView) {
            super(itemView);
            _detailView = itemView; // 赋值，保存视图

            tieba_title = (TextView)itemView
                    .findViewById(R.id.post_list_name);
            tieba_user = (TextView)itemView
                    .findViewById(R.id.list_post_people_name);
            tieba_num=(TextView)itemView.findViewById(R.id.post_list_reply) ;


        }
    }
    // 适配器：数据管理
    private class DetailAdapter
            extends RecyclerView.Adapter<DetailHolder>{
        private List< tiebaDetailInfo > _data;

        public DetailAdapter( List< tiebaDetailInfo > data ){
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
                    LayoutInflater.from( mytieziActivity.this );
            // 根据train_detail.xml，解析界面
            View view = inflater.inflate( R.layout.post_list,
                    parent, false );
            final DetailHolder holder = new DetailHolder(view);
            holder._detailView.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          int position =
                                                                  holder.getAdapterPosition();
                                                          tiebaDetailInfo info = _data.get(position);
                                                          Intent intent =new Intent(mytieziActivity.this,PostActivity.class);
                                                          intent.putExtra("tiezi_ID",info.getId());
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
            tiebaDetailInfo info = _data.get( position );
            // 控件赋值
            holder.tieba_title.setText(info.getTitle());
            holder.tieba_user.setText(info.getuserId());
            holder.tieba_num.setText(info.getContentNum());
        }
    }
    private void  postJson(){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("user_id", user_id)
                .build();
        HttpUtil.sendpost("http://10.0.2.2:8080/An_BBS/ServletGetMytiezi",new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respnseData=response.body().string();

                parsetieba(respnseData);
                //Log.i("ASYNC_TASK12",respnseData);
            }
        },body);



    }
    private void parsetieba(String responseData){
        Gson gson=new Gson();
        List<tiebaDetailInfo> tiebaList=gson.fromJson(responseData,new TypeToken<List<tiebaDetailInfo>>(){}.getType());
        for (tiebaDetailInfo reD:tiebaList){
            String nickname=reD.getuserId();
            String title=reD.getTitle();
            String contentNum=reD.getContentNum();
            String id=reD.getId();
            String bankuaiming=reD.getBankuaiming();
            _data2.add(new tiebaDetailInfo(title,nickname,contentNum,id,bankuaiming));

        }
    }
    private class mtaTask extends AsyncTask<String,Integer,String> {


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
            _adapter = new mytieziActivity.DetailAdapter( _data );
            _lstDetail.setAdapter( _adapter );
        }
    }
}
