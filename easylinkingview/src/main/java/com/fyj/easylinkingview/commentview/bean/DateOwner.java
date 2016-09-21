package com.fyj.easylinkingview.commentview.bean;


import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/24.
 */
public class DateOwner implements Serializable {

    public DateOwner() {
    }

    /**
     * aliasName : 总经理
     * companyName : 周灵挥
     */

    private String aliasName;
    private String companyName;
    private String imgUrl;
    private String regName;
    private String userBid;

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public String getUserBid() {
        return userBid;
    }

    public void setUserBid(String userBid) {
        this.userBid = userBid;
    }

    @Override
    public String toString() {
        return "DateOwner{" +
                "aliasName='" + aliasName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", regName='" + regName + '\'' +
                ", userBid='" + userBid + '\'' +
                '}';
    }
}
