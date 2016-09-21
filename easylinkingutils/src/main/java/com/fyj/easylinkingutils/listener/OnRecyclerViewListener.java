package com.fyj.easylinkingutils.listener;

import android.view.View;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/26<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public interface OnRecyclerViewListener<T> {

    void onClick(View view, T bean, int position);

}
