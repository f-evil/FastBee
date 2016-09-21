package com.fyj.fastbee.bean;

import java.io.Serializable;

/**
 * Created by Fyj on 2016/8/23.
 */
public class LoginBean implements Serializable {


    /**
     * AliasName : Android项目主管
     * CompanyId : 42209692-62c0-4669-84f4-a50b011a52fb
     * CompanyName : 傅宇杰
     * Id : 4cb5cd44-d660-4b8f-8a69-a50b01124da3
     * ImgUrl : /file/6cdbaeaf-4f24-44db-bdc1-9c8fbff43ef7.png
     * IsApproved : true
     * IsLockedOut : false
     * ManagerId : 2043
     * PassWordSalt : 4aHTYYbQ8bSeeeVQaJVAup7ryEJkFBciajQlNaH4gng=
     * RefBusinessId : 2043
     * RefDefaultBoardId : eba847f1-a957-422c-b2ba-a5e100c2e38f
     * RegName : 傅宇杰
     * UserGrade : Boss
     * communityNames : 钢材,生意帮
     * refBoardIds : eba847f1-a957-422c-b2ba-a5e100c2e38f
     * refCommunityIds : cafb2d74-0975-454f-80fd-a5e100c31d20,fbd3105f-677c-4fe3-91ea-a5e100c322ee
     */

    private String AliasName;
    private String CompanyId;
    private String CompanyName;
    private String Id;
    private String ImgUrl;
//    private boolean IsApproved;
//    private boolean IsLockedOut;
    private String ManagerId;
    private String PassWordSalt;
    private String RefBusinessId;
    private String RefDefaultBoardId;
    private String RegName;
    private String UserGrade;
    private String communityNames;
    private String refBoardIds;
    private String refCommunityIds;

    public String getAliasName() {
        return AliasName;
    }

    public void setAliasName(String AliasName) {
        this.AliasName = AliasName;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String CompanyId) {
        this.CompanyId = CompanyId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String ImgUrl) {
        this.ImgUrl = ImgUrl;
    }

//    public boolean isIsApproved() {
//        return IsApproved;
//    }
//
//    public void setIsApproved(boolean IsApproved) {
//        this.IsApproved = IsApproved;
//    }
//
//    public boolean isIsLockedOut() {
//        return IsLockedOut;
//    }
//
//    public void setIsLockedOut(boolean IsLockedOut) {
//        this.IsLockedOut = IsLockedOut;
//    }

    public String getManagerId() {
        return ManagerId;
    }

    public void setManagerId(String ManagerId) {
        this.ManagerId = ManagerId;
    }

    public String getPassWordSalt() {
        return PassWordSalt;
    }

    public void setPassWordSalt(String PassWordSalt) {
        this.PassWordSalt = PassWordSalt;
    }

    public String getRefBusinessId() {
        return RefBusinessId;
    }

    public void setRefBusinessId(String RefBusinessId) {
        this.RefBusinessId = RefBusinessId;
    }

    public String getRefDefaultBoardId() {
        return RefDefaultBoardId;
    }

    public void setRefDefaultBoardId(String RefDefaultBoardId) {
        this.RefDefaultBoardId = RefDefaultBoardId;
    }

    public String getRegName() {
        return RegName;
    }

    public void setRegName(String RegName) {
        this.RegName = RegName;
    }

    public String getUserGrade() {
        return UserGrade;
    }

    public void setUserGrade(String UserGrade) {
        this.UserGrade = UserGrade;
    }

    public String getCommunityNames() {
        return communityNames;
    }

    public void setCommunityNames(String communityNames) {
        this.communityNames = communityNames;
    }

    public String getRefBoardIds() {
        return refBoardIds;
    }

    public void setRefBoardIds(String refBoardIds) {
        this.refBoardIds = refBoardIds;
    }

    public String getRefCommunityIds() {
        return refCommunityIds;
    }

    public void setRefCommunityIds(String refCommunityIds) {
        this.refCommunityIds = refCommunityIds;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "AliasName='" + AliasName + '\'' +
                ", CompanyId='" + CompanyId + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", Id='" + Id + '\'' +
                ", ImgUrl='" + ImgUrl + '\'' +
                ", ManagerId='" + ManagerId + '\'' +
                ", PassWordSalt='" + PassWordSalt + '\'' +
                ", RefBusinessId='" + RefBusinessId + '\'' +
                ", RefDefaultBoardId='" + RefDefaultBoardId + '\'' +
                ", RegName='" + RegName + '\'' +
                ", UserGrade='" + UserGrade + '\'' +
                ", communityNames='" + communityNames + '\'' +
                ", refBoardIds='" + refBoardIds + '\'' +
                ", refCommunityIds='" + refCommunityIds + '\'' +
                '}';
    }
}
