package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dengfengdecao on 17/4/12.
 */

public class Comment implements Serializable {
    private static final long serialVersionUID = -5714148449617140764L;

    /**
     * comment_id : 58
     * goods_id : 2
     * email : 47924712411@qq.com
     * username : nihao24
     * content : cacacacasudhsak24
     * deliver_rank : 5
     * add_time : 2017-04-25 12:58:02
     * ip_address : 222.78.87.254
     * is_show : 1
     * parent_id : 0
     * user_id : 1
     * img : null
     * order_id : 918
     * goods_rank : 5
     * service_rank : 5
     * nickname : null
     * head_pic : http://112.5.195.56:8098/Public/Static/images/img88.jpg
     * number : 30
     */

    @SerializedName("comment_id")
    private int commentId;
    @SerializedName("goods_id")
    private int goodsId;
    private String email;
    private String username;
    private String content;
    @SerializedName("deliver_rank")
    private int deliverRank;
    @SerializedName("add_time")
    private String addTime;
    @SerializedName("ip_address")
    private String ipAddress;
    @SerializedName("is_show")
    private int isShow;
    @SerializedName("parent_id")
    private int parentId;
    @SerializedName("user_id")
    private int userId;
    private Object img;
    @SerializedName("order_id")
    private int orderId;
    @SerializedName("goods_rank")
    private int goodsRank;
    @SerializedName("service_rank")
    private int serviceRank;
    private Object nickname;
    @SerializedName("head_pic")
    private String headPic;
    private int number;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDeliverRank() {
        return deliverRank;
    }

    public void setDeliverRank(int deliverRank) {
        this.deliverRank = deliverRank;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getGoodsRank() {
        return goodsRank;
    }

    public void setGoodsRank(int goodsRank) {
        this.goodsRank = goodsRank;
    }

    public int getServiceRank() {
        return serviceRank;
    }

    public void setServiceRank(int serviceRank) {
        this.serviceRank = serviceRank;
    }

    public Object getNickname() {
        return nickname;
    }

    public void setNickname(Object nickname) {
        this.nickname = nickname;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
