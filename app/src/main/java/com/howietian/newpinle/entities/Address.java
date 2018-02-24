package com.howietian.newpinle.entities;

import cn.bmob.v3.BmobObject;

/**
 * Created by 83624 on 2018/2/11 0011.
 */

public class Address extends BmobObject {
    private String people;
    private String phone;
    private String address;
    private User user;

    public Address() {
    }

    public Address(String people, String phone, String address, User user) {
        this.people = people;
        this.phone = phone;
        this.address = address;
        this.user = user;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Address{" +
                "people='" + people + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", user=" + user +
                '}';
    }
}
