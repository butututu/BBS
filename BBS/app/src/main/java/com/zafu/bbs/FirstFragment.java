package com.zafu.bbs;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import okhttp3.Response;


/**
 * Created by XXX on 2017/11/11.
 */

public class FirstFragment extends Fragment {
    private String context;
    private TextView mTextView;
    RecyclerView _lstDetail;
    DetailAdapter _adapter;
    private String user_id;
    private List<recommDetailInfo> _data;
    private List<recommDetailInfo> _data2;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment,container,false);
       // mTextView = (TextView)view.findViewById(R.id.txt_content);
        //mTextView = (TextView)getActivity().findViewById(R.id.txt_content);
        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.layout_swipe_refresh);
        _lstDetail = (RecyclerView)view.findViewById(R.id.recommDetail);
        // 布局管理器
       _lstDetail.setLayoutManager(
                new LinearLayoutManager( getActivity() )
        );
//        StaggeredGridLayoutManager layoutManager =
//                new StaggeredGridLayoutManager( 8, StaggeredGridLayoutManager.HORIZONTAL );
//        LinearLayoutManager lM=new LinearLayoutManager(getActivity());
        LinearLayoutManager lM = new LinearLayoutManager(getActivity());
       lM.setOrientation(LinearLayoutManager.VERTICAL);
        _lstDetail.setLayoutManager( lM );
        _data = new ArrayList<recommDetailInfo>();
        _data2=new ArrayList<recommDetailInfo>();
        init();
       // _data.add( new recommDetailInfo("D7367", "衢州","id"));

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh() {
                //我在List最前面加入一条数据
                init();

                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                _adapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });





        _adapter = new DetailAdapter( _data );
        _lstDetail.setAdapter( _adapter );
       // mTextView.setText(context);

        return view;
    }
    private  void init(){

        new ffTask().execute();
    }
    private class DetailHolder extends RecyclerView.ViewHolder{
        View _detailView;
        TextView recommTitle;
        TextView recomInfo;
        public DetailHolder(View itemView){
            super(itemView);
            _detailView=itemView;
            recommTitle=(TextView)itemView.findViewById(R.id.recommtitle);
            recomInfo=(TextView)itemView.findViewById(R.id.recomminfo);
        }
    }
    private class DetailAdapter extends RecyclerView.Adapter<DetailHolder>{
        private List<recommDetailInfo> _data;
        public DetailAdapter(List<recommDetailInfo> data){
            _data=data;
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        @Override
        public DetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.recomdetail,parent,false);
            final  DetailHolder holder=new DetailHolder(view);
            holder._detailView.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          int position =
                                                                  holder.getAdapterPosition();
                                                          recommDetailInfo info = _data.get(position);
                                                         // Toast.makeText(getActivity(), user_id, Toast.LENGTH_SHORT).show();
                                                          Intent it =new Intent(getActivity(),PostActivity.class);
                                                          it.putExtra("tiezi_ID",info.getId());
                                                          it.putExtra("tiezi_name",info.getTitle());
                                                          it.putExtra("user_id",user_id);
                                                          getActivity().startActivity(it);
                                                      }
                                                  }
            );
            return holder;
        }

        @Override
        public void onBindViewHolder(DetailHolder holder, int position) {
            recommDetailInfo info=_data.get(position);
            holder.recommTitle.setText(info.getTitle());
            holder.recomInfo.setText(info.getInfo());
        }
    }
    public static FirstFragment newInstance(String context,String user_id){
        FirstFragment ff =new FirstFragment();
        Bundle bundle=new Bundle();
        bundle.putString("context",context);
        bundle.putString("user_id",user_id);
        ff.setArguments(bundle);
        return ff;
        //this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args=getArguments();
        if (args!=null){
            context=args.getString("context");
            user_id=args.getString("user_id");
        }
    }
    private void  postJson(){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .build();
        HttpUtil.sendpost("http://10.0.2.2:8080/An_BBS/ServeletGetTuiJianTieZi",new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respnseData=response.body().string();

                parsetuijian(respnseData);
            }
        },body);



    }
    private void parsetuijian(String responseData){
        Gson gson=new Gson();
        //   Tips tip2=gson.fromJson(responseData,Tips.class);
        List<recommDetailInfo> recommList=gson.fromJson(responseData,new TypeToken<List<recommDetailInfo>>(){}.getType());
        for (recommDetailInfo reD:recommList){
           String id=reD.getId();
           String title=reD.getTitle();
           String info=reD.getInfo();
           int in=1;
            for(int    i=0;    i<_data2.size(); i++)    {
                if(id.equals(_data2.get(i).getId())){
                    in=0;
                }
            }
            if(in==1){
                _data2.add(0,new recommDetailInfo(title,info,id));
            }

        }
    }

    private class ffTask extends AsyncTask<String,Integer,String> {


        @Override
        protected void onPreExecute(){
            Log.i("ASYNC_TASK","called");

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
            _adapter = new DetailAdapter( _data );
            _lstDetail.setAdapter( _adapter );
        }
    }
}