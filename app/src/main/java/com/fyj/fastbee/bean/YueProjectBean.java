package com.fyj.fastbee.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/5<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class YueProjectBean implements Serializable {


    /**
     * categoryNote : 时尚·潮流
     * commentList : [{"comment":"High job","createdAt":1472784673164,"id":"5cbb35ec-d2dd-4bdd-b7b1-b36811617c43","isDeleted":"0","owner":{"aliasName":"客户代表","companyName":"湖南觅你时空酒店集团有限公司","imgUrl":"/file/01291434-0b28-4129-9f6e-21ba1cbbe26a.png","regName":"蔡浩","userBid":"2081"},"refActivityId":"2c01f809-82ab-456c-8783-ebfdc9add3be","refOwnerId":"2081","toUser":{},"toUserId":""},{"comment":"统一战线","createdAt":1472784604821,"id":"e510ca2a-c9fe-4ecf-8140-5a5260734351","isDeleted":"0","owner":{"aliasName":"客户代表","companyName":"湖南觅你时空酒店集团有限公司","imgUrl":"/file/01291434-0b28-4129-9f6e-21ba1cbbe26a.png","regName":"蔡浩","userBid":"2081"},"refActivityId":"2c01f809-82ab-456c-8783-ebfdc9add3be","refOwnerId":"2081","toUser":{},"toUserId":""},{"comment":"咯弄","createdAt":1472784492590,"id":"c9342a09-54b4-4447-a0ce-d7255a5e9a0a","isDeleted":"0","owner":{"aliasName":"副厂长、培训专员","companyName":"易镀网","regName":"施上浮","userBid":"131847"},"refActivityId":"2c01f809-82ab-456c-8783-ebfdc9add3be","refOwnerId":"131847","toUser":{},"toUserId":""}]
     * commentNum : 3
     * createdAt : 1472626751376
     * desc : <html><body><p>asdasda</p></body></html>
     * endTime : 1472893200000
     * groupChat : ad0b04e1-7684-4d29-aafd-736c5183ef96
     * id : 2c01f809-82ab-456c-8783-ebfdc9add3be
     * isApplyOrJoin : 0
     * isDeleted : 0
     * isLike : 0
     * likeList : [{"createdAt":0,"refUserId":"131847"}]
     * likeNum : 1
     * limitedNum : 0
     * locationLat : 0
     * locationLng : 0
     * locationName :
     * memberNum : 0
     * owner : {"aliasName":" ","companyName":"李闯","imgUrl":"/file/edb61fb7-ab72-427e-a0c7-217f98d617ac.png","regName":"李闯"}
     * picNum : 1
     * poster : /yue/240*330_af492829-cbe5-49ba-b554-e7ec865fede5.jpg
     * refCategoryId : 47629c89-5314-41fc-9b3c-a563011a37fa
     * refOwnerId : 1956
     * startTime : 1471251600000
     * status : 2
     * thumbnails : /yue/small_240*330_af492829-cbe5-49ba-b554-e7ec865fede5.jpg
     * title : 1000503:sdasd
     * type : Project_SalePromotion
     * updatedAt : 1472626751376
     */

    private List<ActivitysBean> activitys;
    private List<HotsBean> hots;
    private List<HotsBean> tops;

    public List<ActivitysBean> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<ActivitysBean> activitys) {
        this.activitys = activitys;
    }

    public List<HotsBean> getHots() {
        return hots;
    }

    public void setHots(List<HotsBean> hots) {
        this.hots = hots;
    }

    public List<HotsBean> getTops() {
        return tops;
    }

    public void setTops(List<HotsBean> tops) {
        this.tops = tops;
    }

}
