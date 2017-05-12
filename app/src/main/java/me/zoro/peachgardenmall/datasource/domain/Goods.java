package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

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
    /**
     * cat_id : 6
     * extend_cat_id : 0
     * goods_sn : TP0000002
     * click_count : 0
     * brand_id : 0
     * store_count : 40
     * comment_count : 0
     * weight : 155
     * market_price : 122.00
     * cost_price : 12.00
     * keywords :
     * goods_remark : 美酒美酒美酒美酒美酒美酒
     * goods_content :
     * original_img : http://112.5.195.56:8098/Public/upload/goods/2017/04-20/58f82160106a0.png
     * is_real : 1
     * is_on_sale : 1
     * is_free_shipping : 0
     * on_time : 1492676441
     * sort : 50
     * is_recommend : 1
     * is_new : 1
     * is_hot : 1
     * last_update : 0
     * goods_type : 36
     * spec_type : 36
     * give_integral : 0
     * exchange_integral : 0
     * suppliers_id : 0
     * sales_sum : 0
     * prom_type : 0
     * prom_id : 0
     * commission : 0.00
     * spu :
     * sku :
     * shipping_area_ids :
     * image_data : [{"img_id":2,"goods_id":2,"image_url":"http://112.5.195.56:8098/Public/upload/goods/2017/04-20/58f82160106a0.png"}]
     * filter_spec : [{"title":"精品包装","spec_items":[{"item_id":116,"item":"普通包装","src":"","price":"100.00","store_count":20},{"item_id":117,"item":"一般包装","src":"","price":"200.00","store_count":10},{"item_id":118,"item":"豪华包装","src":"","price":"20000.00","store_count":10}]}]
     * comment_data : {"number":0}
     */

    @SerializedName("cat_id")
    private int catId;
    @SerializedName("extend_cat_id")
    private int extendCatId;
    @SerializedName("goods_sn")
    private String goodsSn;
    @SerializedName("click_count")
    private int clickCount;
    @SerializedName("brand_id")
    private int brandId;
    @SerializedName("store_count")
    private int storeCount;
    @SerializedName("comment_count")
    private int commentCount;
    private int weight;
    @SerializedName("market_price")
    private String marketPrice;
    @SerializedName("cost_price")
    private String costPrice;
    private String keywords;
    @SerializedName("goods_remark")
    private String goodsRemark;
    @SerializedName("goods_content")
    private String goodsContent;
    @SerializedName("original_img")
    private String originalImg;
    @SerializedName("is_real")
    private int isReal;
    @SerializedName("is_on_sale")
    private int isOnSale;
    @SerializedName("on_time")
    private int onTime;
    private int sort;
    @SerializedName("is_recommend")
    private int isRecommend;
    @SerializedName("is_new")
    private int isNew;
    @SerializedName("is_hot")
    private int isHot;
    @SerializedName("last_update")
    private int lastUpdate;
    @SerializedName("goods_type")
    private int goodsType;
    @SerializedName("spec_type")
    private int specType;
    @SerializedName("give_integral")
    private int giveIntegral;
    @SerializedName("exchange_integral")
    private int exchangeIntegral;
    @SerializedName("suppliers_id")
    private int suppliersId;
    @SerializedName("sales_sum")
    private int salesSum;
    @SerializedName("prom_type")
    private int promType;
    @SerializedName("prom_id")
    private int promId;
    private String commission;
    private String spu;
    private String sku;
    @SerializedName("shipping_area_ids")
    private String shippingAreaIds;
    @SerializedName("comment_data")
    private Comment comment;
    /**
     * 商品图片地址列表
     */
    @SerializedName("image_data")
    private List<ImageDataEntity> imageData;

    /**
     * 商品图文详情地址
     */
    @SerializedName("content_url")
    private String mDetailInfoUrl;
    /**
     * filter_spec : [{"title":"颜色","spec_items":[{"item_id":125,"item":"红茶","src":"http://112.5.195.56:8098/Public/upload/goods/2017/04-27/59016e923ecc4.jpg"},{"item_id":126,"item":"绿茶","src":"http://112.5.195.56:8098/Public/upload/goods/2017/04-27/59016eca912ec.jpg"}]},{"title":"大小","spec_items":[{"item_id":127,"item":"小杯","src":""},{"item_id":128,"item":"中杯","src":""},{"item_id":129,"item":"大杯","src":""}]}]
     * spec_relation : [{"goods_id":2,"key":"125_127","key_name":"颜色:红茶 大小:小杯","price":"10.00","store_count":20,"bar_code":"","sku":""},{"goods_id":2,"key":"125_128","key_name":"颜色:红茶 大小:中杯","price":"15.00","store_count":30,"bar_code":"","sku":""},{"goods_id":2,"key":"125_129","key_name":"颜色:红茶 大小:大杯","price":"25.00","store_count":35,"bar_code":"","sku":""},{"goods_id":2,"key":"126_127","key_name":"颜色:绿茶 大小:小杯","price":"12.00","store_count":22,"bar_code":"","sku":""},{"goods_id":2,"key":"126_128","key_name":"颜色:绿茶 大小:中杯","price":"33.00","store_count":11,"bar_code":"","sku":""},{"goods_id":2,"key":"126_129","key_name":"颜色:绿茶 大小:大杯","price":"444.00","store_count":1,"bar_code":"","sku":""}]
     */
    @SerializedName("filter_spec")
    private List<FilterSpecEntity> filterSpec;
    @SerializedName("spec_relation")
    private List<SpecRelationEntity> specRelation;

    @SerializedName("prom_list")
    private List<Promotion> prom;


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

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getExtendCatId() {
        return extendCatId;
    }

    public void setExtendCatId(int extendCatId) {
        this.extendCatId = extendCatId;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(int storeCount) {
        this.storeCount = storeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getGoodsRemark() {
        return goodsRemark;
    }

    public void setGoodsRemark(String goodsRemark) {
        this.goodsRemark = goodsRemark;
    }

    public String getGoodsContent() {
        return goodsContent;
    }

    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent;
    }

    public String getOriginalImg() {
        return originalImg;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    public int getIsReal() {
        return isReal;
    }

    public void setIsReal(int isReal) {
        this.isReal = isReal;
    }

    public int getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(int isOnSale) {
        this.isOnSale = isOnSale;
    }

    public int getOnTime() {
        return onTime;
    }

    public void setOnTime(int onTime) {
        this.onTime = onTime;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public int getSpecType() {
        return specType;
    }

    public void setSpecType(int specType) {
        this.specType = specType;
    }

    public int getGiveIntegral() {
        return giveIntegral;
    }

    public void setGiveIntegral(int giveIntegral) {
        this.giveIntegral = giveIntegral;
    }

    public int getExchangeIntegral() {
        return exchangeIntegral;
    }

    public void setExchangeIntegral(int exchangeIntegral) {
        this.exchangeIntegral = exchangeIntegral;
    }

    public int getSuppliersId() {
        return suppliersId;
    }

    public void setSuppliersId(int suppliersId) {
        this.suppliersId = suppliersId;
    }

    public int getSalesSum() {
        return salesSum;
    }

    public void setSalesSum(int salesSum) {
        this.salesSum = salesSum;
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

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getShippingAreaIds() {
        return shippingAreaIds;
    }

    public void setShippingAreaIds(String shippingAreaIds) {
        this.shippingAreaIds = shippingAreaIds;
    }

    public List<ImageDataEntity> getImageData() {
        return imageData;
    }

    public void setImageData(List<ImageDataEntity> imageData) {
        this.imageData = imageData;
    }


    public String getDetailInfoUrl() {
        return mDetailInfoUrl;
    }

    public void setDetailInfoUrl(String detailInfoUrl) {
        mDetailInfoUrl = detailInfoUrl;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<FilterSpecEntity> getFilterSpec() {
        return filterSpec;
    }

    public void setFilterSpec(List<FilterSpecEntity> filterSpec) {
        this.filterSpec = filterSpec;
    }

    public List<SpecRelationEntity> getSpecRelation() {
        return specRelation;
    }

    public void setSpecRelation(List<SpecRelationEntity> specRelation) {
        this.specRelation = specRelation;
    }

    public List<Promotion> getProm() {
        return prom;
    }

    public void setProm(List<Promotion> prom) {
        this.prom = prom;
    }

    public static class ImageDataEntity implements Serializable {
        /**
         * img_id : 2
         * goods_id : 2
         * image_url : http://112.5.195.56:8098/Public/upload/goods/2017/04-20/58f82160106a0.png
         */

        @SerializedName("img_id")
        private int imgId;
        @SerializedName("goods_id")
        private int goodsId;
        @SerializedName("image_url")
        private String imageUrl;

        public int getImgId() {
            return imgId;
        }

        public void setImgId(int imgId) {
            this.imgId = imgId;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class FilterSpecEntity implements Serializable {
        /**
         * title : 颜色
         * spec_items : [{"item_id":125,"item":"红茶","src":"http://112.5.195.56:8098/Public/upload/goods/2017/04-27/59016e923ecc4.jpg"},{"item_id":126,"item":"绿茶","src":"http://112.5.195.56:8098/Public/upload/goods/2017/04-27/59016eca912ec.jpg"}]
         */

        private String title;
        @SerializedName("spec_items")
        private List<SpecItemsEntity> specItems;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<SpecItemsEntity> getSpecItems() {
            return specItems;
        }

        public void setSpecItems(List<SpecItemsEntity> specItems) {
            this.specItems = specItems;
        }

        public static class SpecItemsEntity implements Serializable {
            /**
             * item_id : 125
             * item : 红茶
             * src : http://112.5.195.56:8098/Public/upload/goods/2017/04-27/59016e923ecc4.jpg
             */

            @SerializedName("item_id")
            private int itemId;
            private String item;
            private String src;

            public int getItemId() {
                return itemId;
            }

            public void setItemId(int itemId) {
                this.itemId = itemId;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }
        }
    }

    public static class SpecRelationEntity implements Serializable {
        /**
         * goods_id : 2
         * key : 125_127
         * key_name : 颜色:红茶 大小:小杯
         * price : 10.00
         * store_count : 20
         * bar_code :
         * sku :
         */

        @SerializedName("goods_id")
        private int goodsId;
        private String key;
        @SerializedName("key_name")
        private String value;
        private String price;
        @SerializedName("store_count")
        private int stock;
        @SerializedName("bar_code")
        private String barCode;
        @SerializedName("sku")
        private String skuX;

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public String getSkuX() {
            return skuX;
        }

        public void setSkuX(String skuX) {
            this.skuX = skuX;
        }
    }

}
