package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 商品信息
 * Created by dengfengdecao on 17/4/7.
 */

public class Goods implements Serializable {

    private static final long serialVersionUID = -7701024332307906557L;
    /**
     * 购物车商品默认数量
     */
    private int count = 1;
    /**
     * goods_id : 2
     * goods_name : 美酒
     * shop_price : 122.00
     * image_url : http://112.5.195.56:8098/Public/upload/goods/thumb/2/goods_thumb_2_222_222.png
     */

    @SerializedName("goods_id")
    private int goodsId;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("shop_price")
    private String price;
    @SerializedName("image_url")
    private String imageUrl;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
