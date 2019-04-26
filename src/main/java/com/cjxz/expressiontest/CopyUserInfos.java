package com.cjxz.expressiontest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: chao.zhu
 * @description:
 * @CreateDate: 2019-04-26
 * @Version: 1.0
 */
@Component("user1")
public class CopyUserInfos {
    @Value("${uid1}")
    private Integer uid;
    @Value("${userName1}")
    private String username;

    public CopyUserInfos(){

    }

    public CopyUserInfos(Integer uid, String username) {
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
        List<CopyUserInfos> userInfosList = new ArrayList<>();
        userInfosList.add(new CopyUserInfos(1,"zhuchao1"));
        userInfosList.add(new CopyUserInfos(2,"zhuchao2"));
        userInfosList.add(new CopyUserInfos(3,"zhuchao3"));

        return userInfosList;
    }
}
