package com.howietian.newpinle.entities;

/**
 * Created by 83624 on 2018/2/22 0022.
 */

public class Friend {
    private String phone;
    private Integer image;

    public Friend() {
    }

    public Friend(String phone, Integer image) {
        this.phone = phone;
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "phone='" + phone + '\'' +
                ", image=" + image +
                '}';
    }
}
