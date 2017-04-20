package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用户信息
 * Created by dengfengdecao on 17/4/7.
 */

public class UserInfo implements Serializable {


    /**
     * user_id : 2598
     * email : 2216348885@qq.com
     * password : 444e3a7c6f6764a919fbab2334961304
     * sex : 保密
     * birthday : 0
     * user_money : 0.00
     * frozen_money : 0.00
     * distribut_money : 0.00
     * pay_points : 0
     * address_id : 0
     * reg_time : 1491916818
     * last_login : 0
     * last_ip :
     * qq : null
     * mobile : 18159801284
     * mobile_validated : 1
     * oauth :
     * openid : null
     * head_pic : null
     * province : 0
     * city : 0
     * district : 0
     * email_validated : 0
     * nickname : 18159801284
     * level : 1
     * discount : 1.00
     * total_amount : 0.00
     * is_lock : 0
     * is_distribut : 1
     * first_leader : 0
     * second_leader : 0
     * third_leader : 0
     * token : 6d71b33243d3bfe292cea71ad90e8e08
     * autograph :
     */

    @SerializedName("user_id")
    private int userId;
    private String email;
    private String password;
    private String sex;
    private int birthday;
    @SerializedName("user_money")
    private String userMoney;
    @SerializedName("frozen_money")
    private String frozenMoney;
    @SerializedName("distribut_money")
    private String distributMoney;
    @SerializedName("pay_points")
    private int payPoints;
    @SerializedName("address_id")
    private int addressId;
    @SerializedName("reg_time")
    private int regTime;
    @SerializedName("last_login")
    private int lastLogin;
    @SerializedName("last_ip")
    private String lastIp;
    private String qq;
    private String mobile;
    @SerializedName("mobile_validated")
    private int mobileValidated;
    private String oauth;
    private String openid;
    @SerializedName("head_pic")
    private String headPic;
    private int province;
    private int city;
    private int district;
    @SerializedName("email_validated")
    private int emailValidated;
    private String nickname;
    private int level;
    private String discount;
    @SerializedName("total_amount")
    private String totalAmount;
    @SerializedName("is_lock")
    private int isLock;
    @SerializedName("is_distribut")
    private int isDistribut;
    @SerializedName("first_leader")
    private int firstLeader;
    @SerializedName("second_leader")
    private int secondLeader;
    @SerializedName("third_leader")
    private int thirdLeader;
    private String token;
    /**
     * autograph :
     * user_level : {"level_id":1,"level_name":"注册会员","amount":"0.00","discount":100,"describe":"注册会员"}
     */

    private String autograph;
    @SerializedName("user_level")
    private UserLevelEntity userLevel;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public String getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(String userMoney) {
        this.userMoney = userMoney;
    }

    public String getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(String frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public String getDistributMoney() {
        return distributMoney;
    }

    public void setDistributMoney(String distributMoney) {
        this.distributMoney = distributMoney;
    }

    public int getPayPoints() {
        return payPoints;
    }

    public void setPayPoints(int payPoints) {
        this.payPoints = payPoints;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getRegTime() {
        return regTime;
    }

    public void setRegTime(int regTime) {
        this.regTime = regTime;
    }

    public int getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(int lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getMobileValidated() {
        return mobileValidated;
    }

    public void setMobileValidated(int mobileValidated) {
        this.mobileValidated = mobileValidated;
    }

    public String getOauth() {
        return oauth;
    }

    public void setOauth(String oauth) {
        this.oauth = oauth;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
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

    public int getEmailValidated() {
        return emailValidated;
    }

    public void setEmailValidated(int emailValidated) {
        this.emailValidated = emailValidated;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getIsLock() {
        return isLock;
    }

    public void setIsLock(int isLock) {
        this.isLock = isLock;
    }

    public int getIsDistribut() {
        return isDistribut;
    }

    public void setIsDistribut(int isDistribut) {
        this.isDistribut = isDistribut;
    }

    public int getFirstLeader() {
        return firstLeader;
    }

    public void setFirstLeader(int firstLeader) {
        this.firstLeader = firstLeader;
    }

    public int getSecondLeader() {
        return secondLeader;
    }

    public void setSecondLeader(int secondLeader) {
        this.secondLeader = secondLeader;
    }

    public int getThirdLeader() {
        return thirdLeader;
    }

    public void setThirdLeader(int thirdLeader) {
        this.thirdLeader = thirdLeader;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public UserLevelEntity getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevelEntity userLevel) {
        this.userLevel = userLevel;
    }


    public static class UserLevelEntity implements Serializable {
        /**
         * level_id : 1
         * level_name : 注册会员
         * amount : 0.00
         * discount : 100
         * describe : 注册会员
         */

        @SerializedName("level_id")
        private int levelId;
        @SerializedName("level_name")
        private String levelName;
        private String amount;
        @SerializedName("discount")
        private int discountX;
        private String describe;

        public int getLevelId() {
            return levelId;
        }

        public void setLevelId(int levelId) {
            this.levelId = levelId;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getDiscountX() {
            return discountX;
        }

        public void setDiscountX(int discountX) {
            this.discountX = discountX;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }
}
