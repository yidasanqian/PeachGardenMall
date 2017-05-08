package me.zoro.peachgardenmall.datasource.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 订单信息
 * Created by dengfengdecao on 17/4/22.
 */

public class Order implements Serializable {

    private int id;
    private Address address;
    private Coupon coupon;
    private double goodsTotalMoney;
    private double freight;
    private double promotionMoney;
    private List<Goods> goodses;
    private double factPayMoney;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public double getGoodsTotalMoney() {
        return goodsTotalMoney;
    }

    public void setGoodsTotalMoney(double goodsTotalMoney) {
        this.goodsTotalMoney = goodsTotalMoney;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getPromotionMoney() {
        return promotionMoney;
    }

    public void setPromotionMoney(double promotionMoney) {
        this.promotionMoney = promotionMoney;
    }

    public List<Goods> getGoodses() {
        return goodses;
    }

    public void setGoodses(List<Goods> goodses) {
        this.goodses = goodses;
    }

    public double getFactPayMoney() {
        return factPayMoney;
    }

    public void setFactPayMoney(double factPayMoney) {
        this.factPayMoney = factPayMoney;
    }
}
