package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dengfengdecao on 17/5/3.
 */

public class Cart implements Serializable {


    /**
     * id : 1683
     * user_id : 2596
     * session_id : 1
     * goods_id : 2
     * goods_sn : TP0000002
     * goods_name : 茶
     * market_price : 16.00
     * goods_price : 10.00
     * member_goods_price : 10.00
     * goods_num : 3
     * spec_key : 125_127
     * spec_key_name : 颜色:红茶 大小:小杯
     * bar_code :
     * selected : 1
     * add_time : 1493810392
     * prom_type : 0
     * prom_id : 0
     * sku :
     * image_url : http://112.5.195.56:8098/Public/upload/goods/thumb/2/goods_thumb_2_222_222.jpeg
     */

    /**
     * 购物车id
     */
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("session_id")
    private String sessionId;
    @SerializedName("goods_id")
    private int goodsId;
    @SerializedName("goods_sn")
    private String goodsSn;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("market_price")
    private String marketPrice;
    @SerializedName("goods_price")
    private String goodsPrice;
    @SerializedName("member_goods_price")
    private String memberGoodsPrice;
    @SerializedName("goods_num")
    private int goodsNum;
    @SerializedName("spec_key")
    private String specKey;
    @SerializedName("spec_key_name")
    private String specKeyName;
    @SerializedName("bar_code")
    private String barCode;
    private int selected;
    @SerializedName("add_time")
    private int addTime;
    @SerializedName("prom_type")
    private int promType;
    @SerializedName("prom_id")
    private int promId;
    private String sku;
    @SerializedName("image_url")
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getMemberGoodsPrice() {
        return memberGoodsPrice;
    }

    public void setMemberGoodsPrice(String memberGoodsPrice) {
        this.memberGoodsPrice = memberGoodsPrice;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getSpecKey() {
        return specKey;
    }

    public void setSpecKey(String specKey) {
        this.specKey = specKey;
    }

    public String getSpecKeyName() {
        return specKeyName;
    }

    public void setSpecKeyName(String specKeyName) {
        this.specKeyName = specKeyName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getAddTime() {
        return addTime;
    }

    public void setAddTime(int addTime) {
        this.addTime = addTime;
    }

    public int getPromType() {
        return promType;
    }

    public void setPromType(int promType) {
        this.promType = promType;
    }

    public int getPromId() {
        return promId;
    }

    public void setPromId(int promId) {
        this.promId = promId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
