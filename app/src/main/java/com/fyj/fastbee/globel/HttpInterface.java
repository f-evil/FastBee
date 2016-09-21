package com.fyj.fastbee.globel;

/**
 * Created by Fyj on 2016/8/23.
 */
public interface HttpInterface {

    interface Login {

        String LOGIN = "http://t1.easylinking.net:10001/EasyLinking/login/loginSystem.do";
    }

    interface Friendship {

        String FRIEND = "http://t1.easylinking.net:10001/EasyLinking/relationShip/getFriendList.do?refOwnerUserId=2043";
    }

    interface YueService {

        String GET_ALL_FRIEND_CRICLE_LIST = "http://t1.easylinking.net:10008/yueServer/activity/getActivityList3.do?refUserId=2043&channel=all&param=cafb2d74-0975-454f-80fd-a5e100c31d20,fbd3105f-677c-4fe3-91ea-a5e100c322ee";
    }
}
