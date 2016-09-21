package com.fyj.fastbee.bean;

import java.io.Serializable;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/26<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class PersonInfoBean implements Serializable {


    /**
     * aliasName : 总经理
     * companyName : 宁波易联汇商信息科技有限公司
     * groupId : 123,456
     * groupName : 默认,分组2
     * id : 137c26b6-61bc-457f-b335-a51400f07cda
     * imgUrl : /file/ac955fb1-0105-4b14-bce8-549de86c42bc.png
     * refBusinessId : 105716
     * refCompanyId : 1e184e94-4b67-4cf7-be95-a53100f61d46
     * regMobile : 13957419101
     * regName : 纪鸿聪
     * userGrade : Boss
     */

    private String aliasName;
    private String companyName;
    private String groupId;
    private String groupName;
    private String id;
    private String imgUrl;
    private String refBusinessId;
    private String refCompanyId;
    private String regMobile;
    private String regName;
    private String userGrade;
    private String sortFirstName;
    private String sortSecondName;

    public PersonInfoBean() {
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRefBusinessId() {
        return refBusinessId;
    }

    public void setRefBusinessId(String refBusinessId) {
        this.refBusinessId = refBusinessId;
    }

    public String getRefCompanyId() {
        return refCompanyId;
    }

    public void setRefCompanyId(String refCompanyId) {
        this.refCompanyId = refCompanyId;
    }

    public String getRegMobile() {
        return regMobile;
    }

    public void setRegMobile(String regMobile) {
        this.regMobile = regMobile;
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public String getUserGrade() {
        return userGrade;
    }

    public String getSortFirstName() {
        return sortFirstName;
    }

    public void setSortFirstName(String sortFirstName) {
        this.sortFirstName = sortFirstName;
    }

    public String getSortSecondName() {
        return sortSecondName;
    }

    public void setSortSecondName(String sortSecondName) {
        this.sortSecondName = sortSecondName;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }

    @Override
    public String toString() {
        return "PersonInfoBean{" +
                "aliasName='" + aliasName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", id='" + id + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", refBusinessId='" + refBusinessId + '\'' +
                ", refCompanyId='" + refCompanyId + '\'' +
                ", regMobile='" + regMobile + '\'' +
                ", regName='" + regName + '\'' +
                ", userGrade='" + userGrade + '\'' +
                ", sortFirstName='" + sortFirstName + '\'' +
                ", sortSecondName='" + sortSecondName + '\'' +
                '}';
    }
}
