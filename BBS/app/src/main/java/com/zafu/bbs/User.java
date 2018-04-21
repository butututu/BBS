package com.zafu.bbs;


import java.util.Date;


public class User {


    private int id;

    private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除

    private String username;

    private String password;

    private Date createtime;

    private int role;//1表示系统管理员,3表示普通用户

    private String truename;

    private String lianxifangshi;//联系方式

    private int fatieshu;//发帖数

    private int huifushu;//回复数

    private String touxiang;//头像

    private String jianjie;//简介



    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public int getFatieshu() {
        return fatieshu;
    }

    public void setFatieshu(int fatieshu) {
        this.fatieshu = fatieshu;
    }

    public int getHuifushu() {
        return huifushu;
    }

    public void setHuifushu(int huifushu) {
        this.huifushu = huifushu;
    }

    public String getLianxifangshi() {
        return lianxifangshi;
    }

    public void setLianxifangshi(String lianxifangshi) {
        this.lianxifangshi = lianxifangshi;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }




}
