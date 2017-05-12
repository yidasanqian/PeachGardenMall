package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 订单信息
 * Created by dengfengdecao on 17/4/22.
 */

public class Order implements Serializable {

    private int id;
    private int userId;
    private int addressId;
    /**
     * 支付类型 0支付宝支付
     */
    private int payType;
    private double freight;
    private List<GoodsInfos> goodsInfos;
    private Address address;
    private Coupon coupon;
    private double goodsTotalMoney;
    /**
     * 促销减的费用
     */
    private double promotionMoney;
    private List<Integer> promotionIds;
    private List<Goods> goodses;
    /**
     * 订单总金额
     */
    @SerializedName("totalMoney")
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public List<GoodsInfos> getGoodsInfos() {
        return goodsInfos;
    }

    public void setGoodsInfos(List<GoodsInfos> goodsInfos) {
        this.goodsInfos = goodsInfos;
    }

    public List<Integer> getPromotionIds() {
        return promotionIds;
    }

    public void setPromotionIds(List<Integer> promotionIds) {
        this.promotionIds = promotionIds;
    }

    public static class GoodsInfos implements Serializable {

        /**
         * specKey : 123_124
         * goodsId : 1234
         * number : 1
         */

        private String specKey;
        private int goodsId;
        private int number;

        public String getSpecKey() {
            return specKey;
        }

        public void setSpecKey(String specKey) {
            this.specKey = specKey;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
