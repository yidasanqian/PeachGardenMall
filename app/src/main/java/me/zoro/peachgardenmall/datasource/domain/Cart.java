package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

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
    private String addTime;
    @SerializedName("prom_type")
    private int promType;
    @SerializedName("prom_id")
    private int promId;
    private String sku;
    @SerializedName("image_url")
    private String imageUrl;
    /**
     * is_free_shipping : false
     * freight : 22.00
     * free_freight : 0.00
     * prom_list : [{"id":9,"name":"开业大典","type":1,"expression":"90","description":"&lt;p&gt;东方航空建国后路口东风路工行卡&lt;/p&gt;","start_time":1471968000,"end_time":1477152000,"is_close":0,"group":null,"prom_img":null,"full_money":"1123.00","remark":"dasdsadsadsa","goods_id":2,"prom_goods_id":1}]
     */

    @SerializedName("is_free_shipping")
    private boolean isFreeShipping;
    private String freight;
    @SerializedName("free_freight")
    private String freeFreight;
    @SerializedName("prom_list")
    private List<PromListEntity> promList;

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

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
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

    public boolean isIsFreeShipping() {
        return isFreeShipping;
    }

    public void setIsFreeShipping(boolean isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getFreeFreight() {
        return freeFreight;
    }

    public void setFreeFreight(String freeFreight) {
        this.freeFreight = freeFreight;
    }

    public List<PromListEntity> getPromList() {
        return promList;
    }

    public void setPromList(List<PromListEntity> promList) {
        this.promList = promList;
    }

    public static class PromListEntity implements Serializable {
        /**
         * id : 9
         * name : 开业大典
         * type : 1
         * expression : 90
         * description : &lt;p&gt;东方航空建国后路口东风路工行卡&lt;/p&gt;
         * start_time : 1471968000
         * end_time : 1477152000
         * is_close : 0
         * group : null
         * prom_img : null
         * full_money : 1123.00
         * remark : dasdsadsadsa
         * goods_id : 2
         * prom_goods_id : 1
         */

        @SerializedName("id")
        private int id;
        private String name;
        private int type;
        private String expression;
        private String description;
        @SerializedName("start_time")
        private String startTime;
        @SerializedName("end_time")
        private String endTime;
        @SerializedName("is_close")
        private int isClose;
        private Object group;
        @SerializedName("prom_img")
        private Object promImg;
        @SerializedName("full_money")
        private String fullMoney;
        private String remark;
        @SerializedName("goods_id")
        private int goodsId;
        @SerializedName("prom_goods_id")
        private int promGoodsId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getIsClose() {
            return isClose;
        }

        public void setIsClose(int isClose) {
            this.isClose = isClose;
        }

        public Object getGroup() {
            return group;
        }

        public void setGroup(Object group) {
            this.group = group;
        }

        public Object getPromImg() {
            return promImg;
        }

        public void setPromImg(Object promImg) {
            this.promImg = promImg;
        }

        public String getFullMoney() {
            return fullMoney;
        }

        public void setFullMoney(String fullMoney) {
            this.fullMoney = fullMoney;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getPromGoodsId() {
            return promGoodsId;
        }

        public void setPromGoodsId(int promGoodsId) {
            this.promGoodsId = promGoodsId;
        }
    }
}
