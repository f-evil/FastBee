package com.fyj.easylinkingkeyboard.utils;

import android.view.View;
import android.widget.AdapterView;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: 2<br>
 * 描述: 表情全局监听
 */
public class EmotionGlobalOnItemClickManagerUtils {

    private static EmotionGlobalOnItemClickManagerUtils instance;
    private OnEmotionClickListener mOnEmotionClickListener;

    public static EmotionGlobalOnItemClickManagerUtils getInstance() {

        if (instance == null) {
            synchronized (EmotionGlobalOnItemClickManagerUtils.class) {
                if (instance == null) {
                    instance = new EmotionGlobalOnItemClickManagerUtils();
                }
            }
        }
        return instance;
    }

    public void destoryUtils(){
        mOnEmotionClickListener=null;
        instance=null;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener(final int emotion_map_type) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mOnEmotionClickListener != null) {
                    mOnEmotionClickListener.onEmotionItemClick(parent, emotion_map_type, position);
                }


            }
        };
    }

    public interface OnEmotionClickListener {
        void onEmotionItemClick(AdapterView<?> parent, int emotion_map_type, int position);
    }

    public void setOnEmotionClickListener(OnEmotionClickListener mOnEmotionClickListener) {
        this.mOnEmotionClickListener = mOnEmotionClickListener;
    }

}
