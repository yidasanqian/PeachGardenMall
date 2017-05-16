package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 订单信息
 * Created by dengfengdecao on 17/4/22.
 */

public class Order implements Serializable {

    @SerializedName("order_id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    private int addressId;
    /**
     * 支付类型 0支付宝支付
     */
    private int payType;
    private double freight;
    /**
     * 订单类型:0所有订单 1待付款 2待发货 3待收货 4待评价
     */
    @SerializedName("order_type")
    private int orderType;
    @SerializedName("goods_list")
    private List<GoodsInfo> mGoodsInfo;
    private Address addressObj;
    private Coupon coupon;

    /**
     * 促销减的费用
     */
    private double promotionMoney;
    private List<Integer> promotionIds;
    private List<Goods> goodses;
    /**
     * 本地请求到服务器的订单总金额参数
     */
    @SerializedName("totalMoney")
    private double factPayMoney;

    /**
     * 订单编号
     */
    @SerializedName("order_sn")
    private String outTraceNo;
    /**
     * order_status : 1
     * shipping_status : 1
     * pay_status : 1
     * consignee : name
     * country : 0
     * province : 0
     * city : 0
     * district : 0
     * twon : 0
     * address : privo,gggggg
     * zipcode :
     * mobile : 18159801283
     * email :
     * shipping_code : ztoexpress
     * shipping_name : 中通
     * pay_code : alipay
     * pay_name : 支付宝支付
     * invoice_title : 0
     * goods_price : 15.00
     * shipping_price : 20.00
     * user_money : 0.00
     * coupon_price : 0.00
     * integral : 0
     * integral_money : 0.00
     * order_amount : 35.00
     * total_amount : 35.00
     * add_time : 1494559205
     * shipping_time : 1494561808
     * confirm_time : 0
     * pay_time : 1494559251
     * order_prom_id : 0
     * order_prom_amount : 0.00
     * discount : 0.00
     * user_note :
     * admin_note :
     * parent_sn : null
     * is_distribut : 0
     * is_true : 1
     * goods_list : [{"rec_id":38835,"order_id":96,"goods_id":2,"goods_name":"茶","goods_sn":"TP0000002","goods_num":1,"market_price":"16.00","goods_price":"15.00","cost_price":"10.00","member_goods_price":null,"give_integral":0,"spec_key":"125_128","spec_key_name":"颜色:红茶 大小:中杯","bar_code":"","is_comment":0,"prom_type":3,"prom_id":2,"is_send":1,"delivery_id":27,"sku":""}]
     */

    @SerializedName("order_status")
    private int orderStatus;
    @SerializedName("shipping_status")
    private int shippingStatus;
    @SerializedName("pay_status")
    private int payStatus;
    private String consignee;
    private int country;
    private int province;
    private int city;
    private int district;
    private int twon;
    @SerializedName("address")
    private String addressStr;
    private String zipcode;
    private String mobile;
    private String email;
    @SerializedName("shipping_code")
    private String shippingCode;
    @SerializedName("shipping_name")
    private String shippingName;
    @SerializedName("pay_code")
    private String payCode;
    @SerializedName("pay_name")
    private String payName;
    @SerializedName("invoice_title")
    private String invoiceTitle;
    @SerializedName("goods_price")
    private String goodsPrice;
    @SerializedName("shipping_price")
    private String shippingPrice;
    @SerializedName("user_money")
    private String userMoney;
    @SerializedName("coupon_price")
    private String couponPrice;
    private int integral;
    @SerializedName("integral_money")
    private String integralMoney;
    @SerializedName("order_amount")
    private String orderAmount;
    /**
     * 从服务器获取的实际付款金额,是{@link #factPayMoney}的字符串形式
     */
    @SerializedName("total_amount")
    private String totalAmount;
    @SerializedName("add_time")
    private String addTime;
    @SerializedName("shipping_time")
    private String shippingTime;
    @SerializedName("confirm_time")
    private String confirmTime;
    @SerializedName("pay_time")
    private String payTime;
    @SerializedName("order_prom_id")
    private int orderPromId;
    @SerializedName("order_prom_amount")
    private String orderPromAmount;
    private String discount;
    @SerializedName("user_note")
    private String userNote;
    @SerializedName("admin_note")
    private String adminNote;
    @SerializedName("parent_sn")
    private Object parentSn;
    @SerializedName("is_distribut")
    private int isDistribut;
    @SerializedName("is_true")
    private int isTrue;

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddressObj() {
        return addressObj;
    }

    public void setAddressObj(Address addressObj) {
        this.addressObj = addressObj;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
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

    public List<GoodsInfo> getGoodsInfo() {
        return mGoodsInfo;
    }

    public void setGoodsInfo(List<GoodsInfo> goodsInfo) {
        this.mGoodsInfo = goodsInfo;
    }

    public List<Integer> getPromotionIds() {
        return promotionIds;
    }

    public void setPromotionIds(List<Integer> promotionIds) {
        this.promotionIds = promotionIds;
    }

    public String getOutTraceNo() {
        return outTraceNo;
    }

    public void setOutTraceNo(String outTraceNo) {
        this.outTraceNo = outTraceNo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(int shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public int getTwon() {
        return twon;
    }

    public void setTwon(int twon) {
        this.twon = twon;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(String userMoney) {
        this.userMoney = userMoney;
    }

    public String getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
        this.couponPrice = couponPrice;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getIntegralMoney() {
        return integralMoney;
    }

    public void setIntegralMoney(String integralMoney) {
        this.integralMoney = integralMoney;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public int getOrderPromId() {
        return orderPromId;
    }

    public void setOrderPromId(int orderPromId) {
        this.orderPromId = orderPromId;
    }

    public String getOrderPromAmount() {
        return orderPromAmount;
    }

    public void setOrderPromAmount(String orderPromAmount) {
        this.orderPromAmount = orderPromAmount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }

    public Object getParentSn() {
        return parentSn;
    }

    public void setParentSn(Object parentSn) {
        this.parentSn = parentSn;
    }

    public int getIsDistribut() {
        return isDistribut;
    }

    public void setIsDistribut(int isDistribut) {
        this.isDistribut = isDistribut;
    }

    public int getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(int isTrue) {
        this.isTrue = isTrue;
    }

    public static class GoodsInfo implements Serializable {

        /**
         * rec_id : 38835
         * order_id : 96
         * goods_id : 2
         * goods_name : 茶
         * goods_sn : TP0000002
         * goods_num : 1
         * market_price : 16.00
         * goods_price : 15.00
         * cost_price : 10.00
         * member_goods_price : null
         * give_integral : 0
         * spec_key : 125_128
         * spec_key_name : 颜色:红茶 大小:中杯
         * bar_code :
         * is_comment : 0
         * prom_type : 3
         * prom_id : 2
         * is_send : 1
         * delivery_id : 27
         * sku :
         */

        @SerializedName("rec_id")
        private int recId;
        @SerializedName("order_id")
        private int orderId;
        @SerializedName("goods_id")
        private int goodsId;
        @SerializedName("goods_name")
        private String goodsName;
        @SerializedName("goods_sn")
        private String goodsSn;
        @SerializedName("goods_num")
        private int goodsNum;
        @SerializedName("market_price")
        private String marketPrice;
        @SerializedName("goods_price")
        private String goodsPrice;
        @SerializedName("cost_price")
        private String costPrice;
        @SerializedName("member_goods_price")
        private Object memberGoodsPrice;
        @SerializedName("give_integral")
        private int giveIntegral;
        @SerializedName("spec_key")
        private String specKey;
        @SerializedName("spec_key_name")
        private String specKeyName;
        @SerializedName("bar_code")
        private String barCode;
        @SerializedName("is_comment")
        private int isComment;
        @SerializedName("prom_type")
        private int promType;
        @SerializedName("prom_id")
        private int promId;
        @SerializedName("is_send")
        private int isSend;
        @SerializedName("delivery_id")
        private int deliveryId;
        private String sku;
        @SerializedName("original_img")
        private String goodsImsg;

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

        public int getRecId() {
            return recId;
        }

        public void setRecId(int recId) {
            this.recId = recId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsSn() {
            return goodsSn;
        }

        public void setGoodsSn(String goodsSn) {
            this.goodsSn = goodsSn;
        }

        public int getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(int goodsNum) {
            this.goodsNum = goodsNum;
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

        public String getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(String costPrice) {
            this.costPrice = costPrice;
        }

        public Object getMemberGoodsPrice() {
            return memberGoodsPrice;
        }

        public void setMemberGoodsPrice(Object memberGoodsPrice) {
            this.memberGoodsPrice = memberGoodsPrice;
        }

        public int getGiveIntegral() {
            return giveIntegral;
        }

        public void setGiveIntegral(int giveIntegral) {
            this.giveIntegral = giveIntegral;
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

        public int getIsComment() {
            return isComment;
        }

        public void setIsComment(int isComment) {
            this.isComment = isComment;
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

        public int getIsSend() {
            return isSend;
        }

        public void setIsSend(int isSend) {
            this.isSend = isSend;
        }

        public int getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(int deliveryId) {
            this.deliveryId = deliveryId;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getGoodsImsg() {
            return goodsImsg;
        }

        public void setGoodsImsg(String goodsImsg) {
            this.goodsImsg = goodsImsg;
        }
    }
}
