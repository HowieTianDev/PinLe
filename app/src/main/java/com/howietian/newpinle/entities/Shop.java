package com.howietian.newpinle.entities;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 83624 on 2018/2/11 0011.
 */

public class Shop extends BmobObject {
    private String name;
    private Double score;
    private Integer orderNum;
    private Integer lowPrice;
    private BmobFile shopImage;
    private String type;

    private BmobRelation collect;
    private ArrayList<String> collectIdList;

    public Shop() {
    }

    public Shop(String name, Double score, Integer orderNum, Integer lowPrice, BmobFile shopImage, String type, BmobRelation collect, ArrayList<String> collectIdList) {
        this.name = name;
        this.score = score;
        this.orderNum = orderNum;
        this.lowPrice = lowPrice;
        this.shopImage = shopImage;
        this.type = type;
        this.collect = collect;
        this.collectIdList = collectIdList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Integer lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BmobFile getShopImage() {
        return shopImage;
    }

    public void setShopImage(BmobFile shopImage) {
        this.shopImage = shopImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BmobRelation getCollect() {
        return collect;
    }

    public void setCollect(BmobRelation collect) {
        this.collect = collect;
    }

    public ArrayList<String> getCollectIdList() {
        return collectIdList;
    }

    public void setCollectIdList(ArrayList<String> collectIdList) {
        this.collectIdList = collectIdList;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", orderNum=" + orderNum +
                ", lowPrice=" + lowPrice +
                ", shopImage=" + shopImage +
                ", type='" + type + '\'' +
                ", collect=" + collect +
                ", collectIdList=" + collectIdList +
                '}';
    }
}
