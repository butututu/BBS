package com.zafu.bbs;

/**
 * Created by XXX on 2017/12/17.
 */

public class tiebaDetailInfo {
    public tiebaDetailInfo(String title,String userid,String contentNum,String id,String bankuaiming){
        this.contentNum=contentNum;
        this.userid=userid;
        this.title=title;
        this.id=id;
        this.bankuaiming=bankuaiming;
    }

    public String getContentNum() {
        return contentNum;
    }

    public String getTitle() {
        return title;
    }

    public String getuserId() {
        return userid;
    }

    public String getBankuaiming() {
        return bankuaiming;
    }

    public String getId() {
        return id;
    }
    private  String bankuaiming;
    private  String title;
    private String userid;
    private String contentNum;
    private  String id;

}
