package com.howietian.newpinle.entities;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 83624 on 2018/2/11 0011.
 */

public class User extends BmobUser{
    private String nickName;
    private String phone;
    private BmobFile avatar;

    public User() {
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar=" + avatar +
                '}';
    }
}
