package com.zafu.bbs;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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

/**
 * Created by XXX on 2017/12/16.
 */

public class SecondFragment extends Fragment {
    private String context;
    private TextView mTextView;
    RecyclerView _2ndDetail;
    private String user_id;
    SecondFragment.DetailAdapter _adapter;
    private List<commDetailInfo> _data;
    private List<commDetailInfo> _data2;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment,container,false);
        // mTextView = (TextView)view.findViewById(R.id.txt_content);
        //mTextView = (TextView)getActivity().findViewById(R.id.txt_content);
        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshtb);
        _2ndDetail = (RecyclerView)view.findViewById(R.id.commDetail);
        // 布局管理器
        _2ndDetail.setLayoutManager(
                new LinearLayoutManager( getActivity() )
        );
//        StaggeredGridLayoutManager layoutManager =
//                new StaggeredGridLayoutManager( 8, StaggeredGridLayoutManager.HORIZONTAL );
//        LinearLayoutManager lM=new LinearLayoutManager(getActivity());
      //  LinearLayoutManager lM = new LinearLayoutManager(getActivity());
     //   lM.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager  lM = new GridLayoutManager(container.getContext(), 3);
        _2ndDetail.setLayoutManager( lM );
        _data = new ArrayList<commDetailInfo>();
        _data2=new ArrayList<commDetailInfo>();
        init();
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh() {

                init();

                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                _adapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });



//        _adapter = new SecondFragment.DetailAdapter( _data );
//        _2ndDetail.setAdapter( _adapter );
        // mTextView.setText(context);

        return view;
    }
    private  void init(){

        new sfTask().execute();
    }
    private class DetailHolder extends RecyclerView.ViewHolder{
        View _detailView;
        TextView commid;
        TextView commInfo;
        public DetailHolder(View itemView){
            super(itemView);
            _detailView=itemView;
            commid=(TextView)itemView.findViewById(R.id.commid);
            commInfo=(TextView)itemView.findViewById(R.id.comminfo);
        }
    }
    private class DetailAdapter extends RecyclerView.Adapter<SecondFragment.DetailHolder>{
        private List<commDetailInfo> _data;
        public DetailAdapter(List<commDetailInfo> data){
            _data=data;
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        @Override
        public SecondFragment.DetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.commdetail,parent,false);
            final SecondFragment.DetailHolder holder=new SecondFragment.DetailHolder(view);
            holder._detailView.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          int position =
                                                                  holder.getAdapterPosition();
                                                          commDetailInfo info = _data.get(position);
                                                          Intent intent =new Intent(getActivity(),tiebaActivity.class);
                                                          intent.putExtra("tieba_ID",info.getId());
                                                          intent.putExtra("tieba_name",info.getTitle());
                                                          intent.putExtra("user_id",user_id);
                                                          getActivity().startActivity(intent);
                                                      }
                                                  }
            );
            return holder;
        }

        @Override
        public void onBindViewHolder(SecondFragment.DetailHolder holder, int position) {
            commDetailInfo info=_data.get(position);
            holder.commid.setText(info.getTitle());
            holder.commInfo.setText(info.getInfo());
        }
    }
    public static SecondFragment newInstance(String context,String user_id){
        SecondFragment ff =new SecondFragment();
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
        HttpUtil.sendpost("http://10.0.2.2:8080/An_BBS/ServeletGetBanKuai",new okhttp3.Callback(){

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
            _data2.add(new commDetailInfo(title,info,id));
            }
        }
    }

    private class sfTask extends AsyncTask<String,Integer,String> {


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
            _2ndDetail.setAdapter( _adapter );
        }
    }
}
