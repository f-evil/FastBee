package com.fyj.fastbee.utils;

import com.fyj.fastbee.bean.PersonInfoBean;

import java.util.Comparator;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/26<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class FriendShipComparator implements Comparator<PersonInfoBean> {

    public int compare(PersonInfoBean o1, PersonInfoBean o2) {
        if (o1.getSortFirstName().equals("@")
                || o2.getSortFirstName().equals("#")) {
            return -1;
        } else if (o1.getSortFirstName().equals("#")
                || o2.getSortFirstName().equals("@")) {
            return 1;
        } else {
            if (!o1.getSortFirstName().equals(o2.getSortFirstName())) {
                return o1.getSortFirstName().compareTo(o2.getSortFirstName());
            } else {
                return o1.getSortSecondName().compareTo(o2.getSortSecondName());
            }

        }
    }

}
