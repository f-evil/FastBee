package com.fyj.easylinkingadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

/**
 * 对Adapter的分装具体view和数据的绑定分到MViewItem{@link MViewItem}中去实现，
 * 实现了Adapter和View的分离
 */
public class MAdapter<T> extends MBaseAdapter<T> {

    private MViewController<T> mViewController;
    private HashMap<Integer,MViewItem<T>> mViewItems;

    /**
     * 创建一个MBaseAdapter 对象
     * @param context 上下文对象
     * @param listData 数据对象
     * @param controller Item控制器对象
     */
    public MAdapter(Context context, List<T> listData,/* @NonNull*/ MViewController<T> controller){
        super(context,listData);
        this.mViewController = controller;
        this.mViewItems= new HashMap<>(controller.getViewTypeCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemViewType(int position) {
        return mViewController.getItemViewType(getItem(position),position);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getViewTypeCount() {
        return mViewController.getViewTypeCount();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T data = getItem(position);
        int type = getItemViewType(position);
        MViewItem<T> item = mViewItems.get(type);
        if(item==null){
            item = mViewController.newViewItem(type);
            mViewItems.put(type,item);
        }
        if (convertView==null){
            convertView = item.newConvertView(getContext(),parent);
        }
        item.bindData(convertView,position,data);
        return convertView;
    }
}
