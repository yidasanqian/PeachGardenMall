package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dengfengdecao on 17/4/20.
 */

public class BannerInfo implements Serializable {

    @SerializedName("ad_data")
    private List<AdDataEntity> adList;
    @SerializedName("goods_data")
    private List<GoodsDataEntity> goodsList;

    public List<AdDataEntity> getAdList() {
        return adList;
    }

    public void setAdList(List<AdDataEntity> adList) {
        this.adList = adList;
    }

    public List<GoodsDataEntity> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsDataEntity> goodsList) {
        this.goodsList = goodsList;
    }

    public static class AdDataEntity implements Serializable {
        /**
         * ad_code : http://112.5.195.56:8098/Public/upload/ad/2017/04-17/58f4ba43bd0e5.jpg
         * ad_link : javascript:void();
         * type : 0
         */

        @SerializedName("ad_code")
        private String adImageUrl;
        @SerializedName("ad_link")
        private String adLink;
        private int type;

        public String getAdImageUrl() {
            return adImageUrl;
        }

        public void setAdImageUrl(String adImageUrl) {
            this.adImageUrl = adImageUrl;
        }

        public String getAdLink() {
            return adLink;
        }

        public void setAdLink(String adLink) {
            this.adLink = adLink;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class GoodsDataEntity implements Serializable {
        /**
         * goods_id : 1
         * type : 1
         * image_url : http://112.5.195.56:8098/Public/upload/goods/thumb/1/goods_thumb_1_222_222.jpeg
         */

        @SerializedName("goods_id")
        private int goodsId;
        private int type;
        @SerializedName("image_url")
        private String goodsImageUrl;

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getGoodsImageUrl() {
            return goodsImageUrl;
        }

        public void setGoodsImageUrl(String goodsImageUrl) {
            this.goodsImageUrl = goodsImageUrl;
        }
    }
}
