package com.fyj.easylinkingview.commentview.bean;

import java.io.Serializable;

/**
 * Created by haitao on 2015/11/27.
 */
public class DateCommentList implements Serializable {
    private String id;
    private String comment;
    private String refOwnerId;
    private String toUserId;
    private String refActivityId;
    private long createdAt;
    private DateOwner owner;
    private DateOwner toUser;





//    comment: "吐了啦",
//    createdAt: 1448862994091,
//    id: "d049ccbd-46ce-47ca-9431-c16eae2419fd",
//    isDeleted: "",
//    owner: {
//        aliasName: "总经理",
//                companyName: "倪海涛公司",
//                imgUrl: "/file/2b856d0d-9a0c-410a-8d6b-41c8e2e6a35c.png",
//                regName: "倪海涛",
//                userBid: "2064"
//    },
//    refActivityId: "",
//    refOwnerId: "2064",
//    toUser: {
//        aliasName: "",
//                companyName: "",
//                imgUrl: "",
//                regName: "",
//                userBid: ""
//    },
//    toUserId: ""


    public DateCommentList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public DateOwner getToUser() {
        return toUser;
    }

    public void setToUser(DateOwner toUser) {
        this.toUser = toUser;
    }

    public DateOwner getOwner() {
        return owner;
    }

    public void setOwner(DateOwner owner) {
        this.owner = owner;
    }

    public String getRefOwnerId() {
        return refOwnerId;
    }

    public void setRefOwnerId(String refOwnerId) {
        this.refOwnerId = refOwnerId;
    }

    public String getRefActivityId() {
        return refActivityId;
    }

    public void setRefActivityId(String refActivityId) {
        this.refActivityId = refActivityId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    @Override
    public String toString() {
        return "DateCommentList{" +
                ", comment='" + comment + '\'' +
                ", owner=" + owner +
                ", toUser=" + toUser +
                '}';
    }
}
