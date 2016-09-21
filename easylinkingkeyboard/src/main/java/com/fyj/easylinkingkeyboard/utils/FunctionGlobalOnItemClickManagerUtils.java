package com.fyj.easylinkingkeyboard.utils;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.fyj.easylinkingkeyboard.adapter.EmotionGridViewAdapter;
import com.fyj.easylinkingkeyboard.model.KeyboardFunctionBean;

import java.util.List;

/**
 * Created by zejian
 * Time  16/1/8 下午5:05
 * Email shinezejian@163.com
 * Description:点击表情的全局监听管理类
 */
public class FunctionGlobalOnItemClickManagerUtils {

    private static FunctionGlobalOnItemClickManagerUtils instance;
    private OnFunctiomClickListener mOnFunctiomClickListener;

    public static FunctionGlobalOnItemClickManagerUtils getInstance() {

        if (instance == null) {
            synchronized (FunctionGlobalOnItemClickManagerUtils.class) {
                if(instance == null) {
                    instance = new FunctionGlobalOnItemClickManagerUtils();
                }
            }
        }
        return instance;
    }

    public void destoryUtils(){
        mOnFunctiomClickListener=null;
        instance=null;
    }


    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mOnFunctiomClickListener!=null){
                    mOnFunctiomClickListener.onEmotionItemClick(parent,position);
                }

            }
        };
    }

    public interface OnFunctiomClickListener {
        void onEmotionItemClick(AdapterView<?> parent, int position);
    }

    public void setOnFunctiomClickListener(OnFunctiomClickListener mOnFunctiomClickListener) {
        this.mOnFunctiomClickListener = mOnFunctiomClickListener;
    }

}
