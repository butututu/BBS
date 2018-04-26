package com.zafu.bbs;


 /* 帖子
 *
 */


import java.util.Date;

public class Tiezi {


    private int id;

    private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除

    private String title;


    private String content;


    private String user;

    private Date createtime;


    private Bankuai bankuai;

    private int dianjishu;//查看数

    private int huifushu;//回复数

    private String leixing;//帖子  精华  求助   活动

    private Date huifutime;//最后回复

    private String tuijian;//版主推荐

    public String getTuijian() {
        return tuijian;
    }

    public void setTuijian(String tuijian) {
        this.tuijian = tuijian;
    }

    public Date getHuifutime() {
        return huifutime;
    }

    public void setHuifutime(Date huifutime) {
        this.huifutime = huifutime;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Bankuai getBankuai() {
        return bankuai;
    }

    public void setBankuai(Bankuai bankuai) {
        this.bankuai = bankuai;
    }

    public int getDianjishu() {
        return dianjishu;
    }

    public void setDianjishu(int dianjishu) {
        this.dianjishu = dianjishu;
    }

    public int getHuifushu() {
        return huifushu;
    }

    public void setHuifushu(int huifushu) {
        this.huifushu = huifushu;
    }

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }







}
