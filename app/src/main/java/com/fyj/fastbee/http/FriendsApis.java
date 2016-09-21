package com.fyj.fastbee.http;

import com.fyj.fastbee.bean.YueProjectBean;
import com.fyj.fastbee.bean.base.BaseObjectBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/21<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public interface FriendsApis {

    String HOST = "http://t1.easylinking.net:10008/";

    @GET("yueServer/activity/getActivityList3.do")
    Observable<BaseObjectBean<YueProjectBean>> getActivityList(
            @Query("refUserId") String refUserId, @Query("channel") String channel, @Query("param") String param,@Query("updatedAt") String updatedAt);

}
