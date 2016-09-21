package com.fyj.fastbee.utils;

import com.fyj.fastbee.R;
import com.fyj.easylinkingkeyboard.model.KeyboardFunctionBean;
import com.fyj.fastbee.ui.activity.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/2<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class KeyboardFunctionUtils {

    public static final int MAP_TYEP_MSG = 0x000000001;
    public static final int MAP_TYPE_WEB = 0x000000002;

    public static final int ITEM_TYPE_JUMP = 0x000000001;
    public static final int ITEM_TYPE_NO_JUMP = 0x000000002;

    public static ArrayList<KeyboardFunctionBean> getFunctionList(int type) {

        ArrayList<KeyboardFunctionBean> mList;

        switch (type) {
            case MAP_TYEP_MSG:
                mList = getMsgFunctionList();
                break;

            case ITEM_TYPE_NO_JUMP:
                mList = getWebFunctionList();
                break;

            default:
                mList = new ArrayList<>();
                break;
        }

        return mList;
    }

    private static ArrayList<KeyboardFunctionBean> getMsgFunctionList() {

        ArrayList<KeyboardFunctionBean> mList = new ArrayList<>();

        mList.add(new KeyboardFunctionBean("1", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("2", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("3", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("4", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("5", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("6", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("7", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("8", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("9", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("10", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("11", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));


        return mList;
    }

    private static ArrayList<KeyboardFunctionBean> getWebFunctionList() {

        ArrayList<KeyboardFunctionBean> mList = new ArrayList<>();

        mList.add(new KeyboardFunctionBean("1", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("2", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("3", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("4", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("5", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));
        mList.add(new KeyboardFunctionBean("6", R.drawable.icon_pic_rec, ITEM_TYPE_JUMP, LoginActivity.class));

        return mList;
    }
}
