package com.fyj.fastbee.bean;

import com.fyj.easylinkingview.commentview.bean.DateCommentList;

import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/5<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class ActivitysBean {
    private String categoryNote;
    private int commentNum;
    private long createdAt;
    private String desc;
    private long endTime;
    private String groupChat;
    private String id;
    private String isApplyOrJoin;
    private String isDeleted;
    private String isLike;
    private int likeNum;
    private int limitedNum;
    private double locationLat;
    private double locationLng;
    private String locationName;
    private int memberNum;
    /**
     * aliasName :
     * companyName : 李闯
     * imgUrl : /file/edb61fb7-ab72-427e-a0c7-217f98d617ac.png
     * regName : 李闯
     */

    private OwnerBean owner;
    private int picNum;
    private String poster;
    private String refCategoryId;
    private String refOwnerId;
    private long startTime;
    private String status;
    private String thumbnails;
    private String title;
    private String type;
    private long updatedAt;
    /**
     * comment : High job
     * createdAt : 1472784673164
     * id : 5cbb35ec-d2dd-4bdd-b7b1-b36811617c43
     * isDeleted : 0
     * owner : {"aliasName":"客户代表","companyName":"湖南觅你时空酒店集团有限公司","imgUrl":"/file/01291434-0b28-4129-9f6e-21ba1cbbe26a.png","regName":"蔡浩","userBid":"2081"}
     * refActivityId : 2c01f809-82ab-456c-8783-ebfdc9add3be
     * refOwnerId : 2081
     * toUser : {}
     * toUserId :
     */

    private List<DateCommentList> commentList;
    /**
     * createdAt : 0
     * refUserId : 131847
     */

    private List<LikeListBean> likeList;

    public String getCategoryNote() {
        return categoryNote;
    }

    public void setCategoryNote(String categoryNote) {
        this.categoryNote = categoryNote;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getGroupChat() {
        return groupChat;
    }

    public void setGroupChat(String groupChat) {
        this.groupChat = groupChat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsApplyOrJoin() {
        return isApplyOrJoin;
    }

    public void setIsApplyOrJoin(String isApplyOrJoin) {
        this.isApplyOrJoin = isApplyOrJoin;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getLimitedNum() {
        return limitedNum;
    }

    public void setLimitedNum(int limitedNum) {
        this.limitedNum = limitedNum;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(double locationLng) {
        this.locationLng = locationLng;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public OwnerBean getOwner() {
        return owner;
    }

    public void setOwner(OwnerBean owner) {
        this.owner = owner;
    }

    public int getPicNum() {
        return picNum;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRefCategoryId() {
        return refCategoryId;
    }

    public void setRefCategoryId(String refCategoryId) {
        this.refCategoryId = refCategoryId;
    }

    public String getRefOwnerId() {
        return refOwnerId;
    }

    public void setRefOwnerId(String refOwnerId) {
        this.refOwnerId = refOwnerId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<DateCommentList> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<DateCommentList> commentList) {
        this.commentList = commentList;
    }

    public List<LikeListBean> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<LikeListBean> likeList) {
        this.likeList = likeList;
    }

}
