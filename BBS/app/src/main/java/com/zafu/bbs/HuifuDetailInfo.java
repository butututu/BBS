package com.zafu.bbs;

/**
 * Created by XXX on 2017/12/20.
 */

public class HuifuDetailInfo {
    private String tiezi_id;
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTiezi_id() {
        return tiezi_id;
    }
    public HuifuDetailInfo(String tiezi_id,String title,String content){
        this.tiezi_id=tiezi_id;
        this.title=title;
        this.content=content;
    }
}
