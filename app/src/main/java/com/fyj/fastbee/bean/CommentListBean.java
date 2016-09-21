package com.fyj.fastbee.bean;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/5<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class CommentListBean {
    private String comment;
    private long createdAt;
    private String id;
    private String isDeleted;
    /**
     * aliasName : 客户代表
     * companyName : 湖南觅你时空酒店集团有限公司
     * imgUrl : /file/01291434-0b28-4129-9f6e-21ba1cbbe26a.png
     * regName : 蔡浩
     * userBid : 2081
     */

    private OwnerBean owner;
    private String refActivityId;
    private String refOwnerId;
    private OwnerBean toUser;
    private String toUserId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public OwnerBean getOwner() {
        return owner;
    }

    public void setOwner(OwnerBean owner) {
        this.owner = owner;
    }

    public String getRefActivityId() {
        return refActivityId;
    }

    public void setRefActivityId(String refActivityId) {
        this.refActivityId = refActivityId;
    }

    public String getRefOwnerId() {
        return refOwnerId;
    }

    public void setRefOwnerId(String refOwnerId) {
        this.refOwnerId = refOwnerId;
    }

    public OwnerBean getToUser() {
        return toUser;
    }

    public void setToUser(OwnerBean toUser) {
        this.toUser = toUser;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

}
