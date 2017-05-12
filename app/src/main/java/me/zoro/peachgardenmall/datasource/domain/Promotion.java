package me.zoro.peachgardenmall.datasource.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dengfengdecao on 17/5/10.
 */

public class Promotion implements Serializable {
    /**
     * id : 2
     * name : 活动名称
     * type : 1
     * expression : 11
     * description : &lt;p&gt;满100减900&lt;/p&gt;
     * start_time : 2017-05-04
     * end_time : 2017-07-03
     * is_close : 0
     * group : null
     * prom_img : null
     * full_money : 110.00
     */

    private int id;
    private String name;
    private int type;
    /**
     * 优惠活动的金额
     */
    private String expression;
    private String description;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("is_close")
    private int isClose;
    private String remark;

    /**
     * 满减的金额,代表金额达到这个时才有优惠z
     */
    @SerializedName("money")
    private String fullMoney;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFullMoney() {
        return fullMoney;
    }

    public void setFullMoney(String fullMoney) {
        this.fullMoney = fullMoney;
    }
}
