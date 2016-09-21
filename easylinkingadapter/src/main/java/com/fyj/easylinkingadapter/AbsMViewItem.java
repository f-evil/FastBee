package com.fyj.easylinkingadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public abstract class AbsMViewItem<T> implements MViewItem<T>{
    private Context mContext;

    /**
     * {@inheritDoc}
     */
    @Override
    public View newConvertView(Context context, ViewGroup viewGroup) {
        mContext = context;
        View convertView = LayoutInflater.from(context).inflate(getLayout(),viewGroup,false);
        convertView.setTag(initView(convertView));
        return convertView;
    }

    /**
     * 获取View所需要绑定的layout ID
     * @return 返回布局id (<code>R.layout.xxx</code>)
     */
//    @LayoutRes
    protected abstract int getLayout();

    /**
     * 在{@link #newConvertView(Context, ViewGroup)}
     * 创建View后初始化View
     * @param convertView 创建的View
     * @return 返回一个ViewHolder
     */
    protected abstract ViewHolder initView(View convertView);

    /**
     * 返回{@link #newConvertView(Context, ViewGroup)}中传入的Context对象
     * @return 上下文对象
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 一个ViewHolder,子类需要继承并实现对应的布局的ViewHolder
     */
    public interface ViewHolder{}
}
