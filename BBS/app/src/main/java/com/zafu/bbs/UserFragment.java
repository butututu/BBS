package com.zafu.bbs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by XXX on 2017/12/16.
 */

public class UserFragment  extends Fragment{
    private String context;
    private String user;
    private String user_id;
    private String user_nickname;
    private Button mytiezi;
    private Button myhuifu;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment,container,false);
        TextView username=(TextView)view.findViewById(R.id.username);
        username.setText(user_nickname);
        TextView userid=(TextView)view.findViewById(R.id.userid);
        userid.setText(user_id);
        mytiezi=(Button)view.findViewById(R.id.mytiezi);
        myhuifu=(Button)view.findViewById(R.id.myhuifu);
        mytiezi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(),mytieziActivity.class);
                intent.putExtra("user_id",user_id);
                getActivity().startActivity(intent);
            }
        });
        myhuifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(),myhuifuActivity.class);
                intent.putExtra("user_id",user_id);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
    public static UserFragment newInstance(String context,String user,String user_id,String user_nickname){
        UserFragment ff =new UserFragment();
        Bundle bundle=new Bundle();
        bundle.putString("context",context);
        bundle.putString("user",user);
        bundle.putString("user_id",user_id);
        bundle.putString("user_nickname",user_nickname);
        ff.setArguments(bundle);
        return ff;
        //this.context = context;
    }
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args=getArguments();
        if (args!=null){
            context=args.getString("context");
            user=args.getString("user");
            user_id=args.getString("user_id");
            user_nickname=args.getString("user_nickname");
        }
    }
}
