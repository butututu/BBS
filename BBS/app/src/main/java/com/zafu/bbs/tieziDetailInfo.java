package com.zafu.bbs;

/**
 * Created by XXX on 2017/12/17.
 */

public class tieziDetailInfo {
    public tieziDetailInfo(String nickname,String info,String louceng,String title){
        this.nickname=nickname;
        this.info=info;
        this.louceng=louceng;
        this.title=title;
    }
    public String getNickname(){
        return nickname;
    }
    public String getInfo(){
        return info;
    }
    public String getLouceng(){
        return louceng;
    }
    public String getTitle(){return  title;}
    private  String nickname;
    private String info;
    private String louceng;
    private String title;
}
