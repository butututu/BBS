package com.zafu.bbs;

import java.util.Date;


/**
 * 回复
 *
 */

public class Huifu {



    private int id;

    private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除


    private String content;

    private Date createtime;


    private String user;

    private Tiezi tiezi;

    private String weizhi;//位置

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeletestatus() {
        return deletestatus;
    }

    public void setDeletestatus(int deletestatus) {
        this.deletestatus = deletestatus;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Tiezi getTiezi() {
        return tiezi;
    }

    public void setTiezi(Tiezi tiezi) {
        this.tiezi = tiezi;
    }

    public String getWeizhi() {
        return weizhi;
    }

    public void setWeizhi(String weizhi) {
        this.weizhi = weizhi;
    }












}
