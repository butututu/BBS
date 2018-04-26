package com.zafu.bbs;

/**
 * Created by XXX on 2017/12/16.
 */

public class commDetailInfo {
    public commDetailInfo(String title,String info,String ID){
        this.title=title;
        this.info=info;
        this.id=ID;
    }

    @Override
    public String toString() {
        return  "recommDetailInfo{" +
                "title='" + title + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
    public String getTitle(){
        return title;
    }
    public String getInfo(){
        return info;
    }

    public String getId() {
        return id;
    }

    private  String title;
    private String info;
    private  String id;
}
