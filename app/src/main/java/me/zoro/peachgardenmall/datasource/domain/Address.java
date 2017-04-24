package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dengfengdecao on 17/4/23.
 */

public class Address implements Serializable {


    /**
     * address_id : 364
     * user_id : 2596
     * consignee : 哈哈111
     * email :
     * country : 0
     * province : 0
     * city : 0
     * district : 0
     * twon : 0
     * address : 非洲哦
     * zipcode :
     * mobile : 18159851826
     * is_default : false
     * is_pickup : 0
     */

    @SerializedName("address_id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    private String consignee;
    private String email;
    private int country;
    private int province;
    private int city;
    private int district;
    private int twon;
    private String address;
    private String zipcode;
    private String mobile;
    @SerializedName("is_default")
    private boolean isDefault;
    @SerializedName("is_pickup")
    private int isPickup;

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

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int getIsPickup() {
        return isPickup;
    }

    public void setIsPickup(int isPickup) {
        this.isPickup = isPickup;
    }
}
