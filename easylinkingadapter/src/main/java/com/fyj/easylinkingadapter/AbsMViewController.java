package com.fyj.easylinkingadapter;

/**
 * 默认的ItemViewController 返回类型个数为1,type类型为0
 * 即如果只有一个View的话可以不用重写{@link #getItemViewType(Object, int)}
 * 和{@link #getViewTypeCount()}方法
 */
public abstract class AbsMViewController<T> implements MViewController<T> {

    /**
     *
     * @param data item所匹配的数据
     * @param position item所在的点
     * @return 0 当View只有一个类型的时候不用重写该方法，直接返回0
     */
    @Override
    public int getItemViewType(T data, int position) {
        return 0;
    }

    /**
     *
     * @return 1 当只有一个类型的View的时候不用重写该方法，直接返回1
     */
    @Override
    public int getViewTypeCount() {
        return 1;
    }

}
