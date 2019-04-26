package com.cjxz.expressiontest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-26
 * @Version: 1.0
 */
public class UserInfos {
    private Integer uid;
    private String username;

    public UserInfos(){

    }

    public UserInfos(Integer uid, String username) {
        this.uid = uid;
        this.username = username;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInfo(){
        return "userName:"+this.username + "uid:"+this.uid;
    }

    public List getList(){
        List<UserInfos> userInfosList = new ArrayList<>();
        userInfosList.add(new UserInfos(1,"zhuchao1"));
        userInfosList.add(new UserInfos(2,"zhuchao2"));
        userInfosList.add(new UserInfos(3,"zhuchao3"));

        return userInfosList;
    }
}
