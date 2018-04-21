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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class tiebaActivity extends AppCompatActivity {
    RecyclerView _lstDetail;
    tiebaActivity.DetailAdapter _adapter;
    private List< tiebaDetailInfo > _data;
    private List<tiebaDetailInfo> _data2;
    private static  String TiebaName;
    private    String Tieba_ID;
    private  TextView p_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tieba);

        Intent intent=getIntent();
        Tieba_ID=intent.getStringExtra("tieba_ID");
        TiebaName=intent.getStringExtra("tieba_name");
        _lstDetail = (RecyclerView)findViewById(R.id.tzintieba);
        _lstDetail.setLayoutManager(
                new LinearLayoutManager( this )
        );
        LinearLayoutManager lM=new LinearLayoutManager(this);
        lM.setOrientation(LinearLayoutManager.VERTICAL);
        _lstDetail.setLayoutManager( lM );
        _data = new ArrayList<tiebaDetailInfo>();
        _data2=new ArrayList<tiebaDetailInfo>();
        p_name=(TextView)findViewById(R.id.tieba_name);
        p_name.setText(TiebaName);
        init();
        _adapter = new tiebaActivity.DetailAdapter( _data );
        _lstDetail.setAdapter( _adapter );
    }
  private void   init(){
      new tATask().execute();
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
            extends RecyclerView.Adapter<tiebaActivity.DetailHolder>{
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
        public tiebaActivity.DetailHolder onCreateViewHolder(
                ViewGroup parent,
                int viewType) {
            LayoutInflater inflater =
                    LayoutInflater.from( tiebaActivity.this );
            // 根据train_detail.xml，解析界面
            View view = inflater.inflate( R.layout.post_list,
                    parent, false );
            final tiebaActivity.DetailHolder holder = new tiebaActivity.DetailHolder(view);
            holder._detailView.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          int position =
                                                                  holder.getAdapterPosition();
                                                          tiebaDetailInfo info = _data.get(position);
                                                          Intent intent =new Intent(tiebaActivity.this,PostActivity.class);
                                                          intent.putExtra("tiezi_ID",info.getId());
                                                          intent.putExtra("tiezi_name",info.getTitle());
                                                          startActivity(intent);
                                                      }
                                                  }
            );
            return holder;
        }

        @Override
        public void onBindViewHolder(
                tiebaActivity.DetailHolder holder, // 控件
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
                .add("tiebaid", Tieba_ID)
                .build();
        HttpUtil.sendpost("http://10.0.2.2:8080/An_BBS/ServletGetTieba",new okhttp3.Callback(){

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
        //   Tips tip2=gson.fromJson(responseData,Tips.class);
        List<tiebaDetailInfo> tiebaList=gson.fromJson(responseData,new TypeToken<List<tiebaDetailInfo>>(){}.getType());
        //int confirm=0;
        for (tiebaDetailInfo reD:tiebaList){
            String nickname=reD.getuserId();
            String title=reD.getTitle();
            String contentNum=reD.getContentNum();
            String id=reD.getId();
            String bankuaiming=reD.getBankuaiming();
           // Log.i("bank", bankuaiming+"1");
           // if (confirm==0){
            //    TiebaName=bankuaiming;
           // Log.i("bank", bankuaiming+"1");
              //  confirm=1;
            //}
            _data2.add(new tiebaDetailInfo(title,nickname,contentNum,id,bankuaiming));

        }
    }
    private class tATask extends AsyncTask<String,Integer,String> {


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
