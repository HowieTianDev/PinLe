package com.howietian.newpinle.entities;

/**
 * Created by 83624 on 2018/2/11 0011.
 */

public class Meal {
    private Integer photoId;
    private String name;
    private Double score;
    private Integer saleNum;
    private Integer buyNum;
    private String clssify;

    public Meal() {
    }

    public Meal(Integer photoId, String name, Double score, Integer saleNum, Integer buyNum, String clssify) {
        this.photoId = photoId;
        this.name = name;
        this.score = score;
        this.saleNum = saleNum;
        this.buyNum = buyNum;
        this.clssify = clssify;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
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

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public String getClssify() {
        return clssify;
    }

    public void setClssify(String clssify) {
        this.clssify = clssify;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "photoId=" + photoId +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", saleNum=" + saleNum +
                ", buyNum=" + buyNum +
                ", clssify='" + clssify + '\'' +
                '}';
    }
}
