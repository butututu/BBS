package com.zafu.bbs;

/**
 * Created by XXX on 2017/12/19.
 */

public class UserDetailinfo {
    private String id;
    private String nickname;
    private String userid;
    private String tips;
    private String tip;

    public String getTips() {
        return tips;
    }

    public String getTip() {
        return tip;
    }

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


}
