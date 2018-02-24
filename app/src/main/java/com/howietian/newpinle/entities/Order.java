package com.howietian.newpinle.entities;

/**
 * Created by 83624 on 2018/2/21 0021.
 */

public class Order {
    private Integer shopImage;
    private Integer mealImage;
    private String shopName;
    private String mealName;
    private String price;
    private Integer number;
    private String buyTime;
    private String endPrice;

    public Order() {
    }

    public Order(Integer shopImage, Integer mealImage, String shopName, String mealName, String price, Integer number, String buyTime, String endPrice) {
        this.shopImage = shopImage;
        this.mealImage = mealImage;
        this.shopName = shopName;
        this.mealName = mealName;
        this.price = price;
        this.number = number;
        this.buyTime = buyTime;
        this.endPrice = endPrice;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getShopImage() {
        return shopImage;
    }

    public void setShopImage(Integer shopImage) {
        this.shopImage = shopImage;
    }

    public Integer getMealImage() {
        return mealImage;
    }

    public void setMealImage(Integer mealImage) {
        this.mealImage = mealImage;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(String endPrice) {
        this.endPrice = endPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "shopImage=" + shopImage +
                ", mealImage=" + mealImage +
                ", mealName='" + mealName + '\'' +
                ", price='" + price + '\'' +
                ", number=" + number +
                ", buyTime='" + buyTime + '\'' +
                ", endPrice='" + endPrice + '\'' +
                '}';
    }
}
