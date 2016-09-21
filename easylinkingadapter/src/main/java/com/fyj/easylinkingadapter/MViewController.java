package com.fyj.easylinkingadapter;

import android.widget.AbsListView;
import android.widget.ListAdapter;

/**
 * 这个类主要用来获取显示的item的类型个数和获得某个item的类型
 */
public interface MViewController<T> {
    /**
     * 需要注意到是Item类型int是从0开始依次增加的且不能超过{@link #getViewTypeCount()}
     * 即{@link #getItemViewType(Object, int)}返回的int type < {@link #getViewTypeCount()}
     * 原因是Android源码中
     *     public void setViewTypeCount(int viewTypeCount) {
     *     if (viewTypeCount < 1) {
     *     throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
     *     }
     *     //noinspection unchecked
     *     ArrayList<View>[] scrapViews = new ArrayList[viewTypeCount];
     *     for (int i = 0; i < viewTypeCount; i++) {
     *     scrapViews[i] = new ArrayList<View>();
     *     }
     *     mViewTypeCount = viewTypeCount;
     *     mCurrentScrap = scrapViews[0];
     *     mScrapViews = scrapViews;
     *     }
     * 源码位置
     *  {@link android.widget.ListView#setAdapter(ListAdapter)}中的
     *  mRecycler.setViewTypeCount(mAdapter.getViewTypeCount());
     *  mRecycler为 {@link AbsListView}中的内部类RecycleBin
     *  mRecycler.setViewTypeCount的实现就是上面代码他创建了大小为viewTypeCount的ArrayList<View>
     *  用于存储每种类型的缓存View
     *  而取ItemViewType的时候
     *
     *   View getScrapView(int position) {
     *      final int whichScrap = mAdapter.getItemViewType(position);
     *      if (whichScrap < 0) {
     *          return null;
     *      }
     *      if (mViewTypeCount == 1) {
     *          return retrieveFromScrap(mCurrentScrap, position);
     *      } else if (whichScrap < mScrapViews.length) {
     *          return retrieveFromScrap(mScrapViews[whichScrap], position);
     *      }
     *      return null;
     *   }
     *  直接用mScrapViews[whichScrap]下标来取所以type类型不能超过{@link #getViewTypeCount()}
     */


    /**
     * 获取某个Item的显示类型
     * @param data item所匹配的数据
     * @param position item所在的点
     * @return 返回view的类型
     */
    int getItemViewType(T data, int position);

    /**
     * 获取View的类型个数
     * @return 类型个数
     */
    int getViewTypeCount();

    /**
     *
     * 根具item的类型来获取一个ViewItem对象
     * @param type item的类型
     * @return 返回一个ItemView对象
     */
    MViewItem<T> newViewItem(int type);
}
