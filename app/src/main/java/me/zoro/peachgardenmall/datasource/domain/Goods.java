package me.zoro.peachgardenmall.datasource.domain;

import java.io.Serializable;

/**
 * 商品信息
 * Created by dengfengdecao on 17/4/7.
 */

public class Goods implements Serializable {

    private String name;
    private double money;
    private int count = 1;
    private String imgUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
