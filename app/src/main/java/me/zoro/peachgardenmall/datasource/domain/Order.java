package me.zoro.peachgardenmall.datasource.domain;

import java.io.Serializable;

/**
 * 订单信息
 * Created by dengfengdecao on 17/4/22.
 */

public class Order implements Serializable {

    private int mId;
    private String mName;
    private String mNumber;
    private Goods mGoods;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public Goods getGoods() {
        return mGoods;
    }

    public void setGoods(Goods goods) {
        mGoods = goods;
    }
}
