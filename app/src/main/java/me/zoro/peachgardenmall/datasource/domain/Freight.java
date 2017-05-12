package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dengfengdecao on 17/5/10.
 */

public class Freight implements Serializable {


    /**
     * freight_free : 100
     * freight : 20
     * is_free : true
     */

    /**
     * 商品合计金额满足 {@link #freightFree}则运费为0
     */
    @SerializedName("freight_free")
    private String freightFree;
    /**
     * 运费
     */
    private String freight;
    /**
     * true, 表示需要计算运费。false，表示不需要计算运费 {@link #freight}
     */
    @SerializedName("is_free")
    private boolean isFree;

    public String getFreightFree() {
        return freightFree;
    }

    public void setFreightFree(String freightFree) {
        this.freightFree = freightFree;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public boolean isIsFree() {
        return isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }
}
