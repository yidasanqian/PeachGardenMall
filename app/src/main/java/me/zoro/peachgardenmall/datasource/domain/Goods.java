package me.zoro.peachgardenmall.datasource.domain;

import java.io.Serializable;

/**
 * 商品信息
 * Created by dengfengdecao on 17/4/7.
 */

public class Goods implements Serializable {

    private String name;
    private String imgUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
