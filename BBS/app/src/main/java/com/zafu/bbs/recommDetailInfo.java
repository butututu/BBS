package com.zafu.bbs;

/**
 * Created by XXX on 2017/11/12.
 */

public class recommDetailInfo {
    public recommDetailInfo(String title,String info,String id){
        this.title=title;
        this.info=info;
        this.id=id;
    }

    @Override
    public String toString() {
        return  "recommDetailInfo{" +
                "title='" + title + '\'' +
                ", info='" + info + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
    public String getTitle(){
        return title;
    }
    public String getInfo(){
        return info;
    }
    public String getId(){return id;}
    private  String title;
    private String info;
    private String id;
}
