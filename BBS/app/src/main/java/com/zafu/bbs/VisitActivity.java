package com.zafu.bbs;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

public class VisitActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView topBar;
    private TextView tabDeal;
    private TextView tabPoi;

    private TextView tabUser;
    private FrameLayout ly_content;

    private FirstFragment f1;
    private SecondFragment f3;
    private UserFragment f4;
    private FragmentManager fragmentManager;
   private String user;
    private String user_id;
    private  String user_nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_visit);
        Intent intent=getIntent();
        user=intent.getStringExtra("user_data");
        user_id=intent.getStringExtra("user_id");
        user_nickname=intent.getStringExtra("user_nickname");
        bindView();

    }
    private void bindView() {

        tabDeal = (TextView)this.findViewById(R.id.txt_deal);
        tabPoi = (TextView)this.findViewById(R.id.txt_poi);
        tabUser = (TextView)this.findViewById(R.id.txt_user);

        ly_content = (FrameLayout) findViewById(R.id.fragment_container);

        tabDeal.setOnClickListener(this);
        tabUser.setOnClickListener(this);
        tabPoi.setOnClickListener(this);

    }
    public void selected(){
        tabDeal.setSelected(false);

        tabPoi.setSelected(false);
        tabUser.setSelected(false);
    }
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }

        if(f3!=null){
            transaction.hide(f3);
        }
        if(f4!=null){
            transaction.hide(f4);
        }
    }
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
       hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_deal:
                selected();
                tabDeal.setSelected(true);
                if(f1==null){
                    f1 = FirstFragment.newInstance("第一个Fragment",user_id);

                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                break;



            case R.id.txt_poi:
                selected();
                tabPoi.setSelected(true);
                if(f3==null){
                   f3 = SecondFragment.newInstance("第二个Fragment",user_id);
                    transaction.add(R.id.fragment_container,f3);
                }else{
                    transaction.show(f3);
                }
                break;

            case R.id.txt_user:
                selected();
                tabUser.setSelected(true);
                if(f4==null){
                    f4 = UserFragment.newInstance("第三个Fragment",user,user_id,user_nickname);
                    transaction.add(R.id.fragment_container,f4);
                }else{
                    transaction.show(f4);
                }
                break;
        }

        transaction.commit();
    }
}
