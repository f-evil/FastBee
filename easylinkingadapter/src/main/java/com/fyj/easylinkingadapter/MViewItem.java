package com.fyj.easylinkingadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 *  MViewItem不是一个View类型，它是一种ItemType的控制器
 *  （有ItemType{@link MViewController#getViewTypeCount()}就有几个MViewItem），
 *  它的主要职责是负责对ConvertView的创建{@link #newConvertView(Context, ViewGroup)}
 *  和对数据的绑定{@link #bindData(View, int,Object)}。
 */
public interface MViewItem<T> {
    /**
     * 创建一个ItemView
     * @param context 上下文对象
     * @param viewGroup 父视图的ViewGroup
     * @return 返回一个ItemView
     */
    View newConvertView(Context context, ViewGroup viewGroup);

    /**
     * 将View 和数据进行绑定
     * @param convertView 待绑定的View
     * @param data 待绑定的数据
     */
    void bindData(View convertView,int pos, T data);
}
