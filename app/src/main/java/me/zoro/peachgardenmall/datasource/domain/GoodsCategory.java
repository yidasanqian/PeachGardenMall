package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dengfengdecao on 17/4/21.
 */

public class GoodsCategory implements Serializable {

    /**
     * id : 4
     * image : http://112.5.195.56:8098/Public/upload/category/2017/04-17/58f4c01cb091f.jpg
     * mobile_name :
     */

    private int id;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("mobile_name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
