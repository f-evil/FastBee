package com.fyj.easylinkingadapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * MBaseAdapter是对BaseAdapter的一个封装。
 * 主要封装了平时常用的{@link #getCount()},{@link #getItem(int)}
 * {@link #getItemId(int)} 和 add和remove方法。
 */
public abstract class MBaseAdapter<T> extends BaseAdapter{

    private Context mContext;
    private List<T> listData;

    public MBaseAdapter(Context context, List<T> listData){
        this.mContext = context;
        this.listData = (listData==null?new ArrayList<T>():listData);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        return listData==null?0:listData.size();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public T getItem(int position) {
        return listData==null?null:listData.get(position);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *
     * @return 返回上下文对象
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 获得所有的数据
     * @return List数据
     */
    public List<T> getListData(){
        return listData;
    }

    /**
     * 添加一个item的数据
     * @param data item数据
     */
    public void addItem(T data){
        listData.add(data);
        notifyDataSetChanged();
    }

    /**
     * 在指定位置添加item数据
     * @param data 待添加的数据
     * @param index 待添加的位置
     * @throws ClassCastException
     *                if the class of the object is inappropriate for this
     *                {@code List}.
     * @throws IllegalArgumentException
     *                if the object cannot be added to this {@code List}.
     * @throws IndexOutOfBoundsException
     *                if {@code location < 0 || location > size()}
     */
    public void addItemAtIndex(T data,int index){
        listData.add(index,data);
        notifyDataSetChanged();
    }

    /**
     * 添加一组item数据
     * @param datas 待添加的一组数据
     */
    public void addItems(List<T> datas){
        if(listData == null){
            listData = datas;
        }else{
            listData.addAll(datas);
        }
        notifyDataSetChanged();
    }

    /**
     * 移除listData 中第一出线data的数据
     * @param data 待移除的数据
     */
    public void removeItem(T data){
        listData.remove(data);
        notifyDataSetChanged();
    }

    /**
     * 移除特定位置的data数据
     * @param index 待移除的位置
     * @throws UnsupportedOperationException
     *                if removing from this {@code List} is not supported.
     * @throws IndexOutOfBoundsException
     *                if {@code location < 0 || location >= size()}
     */
    public void removeItemByIndex(int index){
        listData.remove(index);
        notifyDataSetChanged();
    }

    /**
     * 情况所有的item
     */
    public void removeAllItem(){
        if(listData!=null){
            listData.clear();
        } else {
            listData=new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    /**
     * 更新数据
     * @param listData 待更新数据
     */
    public void updateView(List<T> listData){
        this.listData=listData;
        notifyDataSetChanged();
    }
}
