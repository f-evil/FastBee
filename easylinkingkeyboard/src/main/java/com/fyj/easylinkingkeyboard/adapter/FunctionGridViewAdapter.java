package com.fyj.easylinkingkeyboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fyj.easylinkingkeyboard.R;
import com.fyj.easylinkingkeyboard.model.KeyboardFunctionBean;

import java.util.List;

/**
 * Created by zejian
 * Time  16/1/7 下午4:46
 * Email shinezejian@163.com
 * Description:
 */
public class FunctionGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<KeyboardFunctionBean> keyboardFunctionBeen;
    private int itemWidth;

    public FunctionGridViewAdapter(Context context, List<KeyboardFunctionBean> keyboardFunctionBeen, int itemWidth) {
        this.context = context;
        this.keyboardFunctionBeen = keyboardFunctionBeen;
        this.itemWidth = itemWidth;
    }

    @Override
    public int getCount() {
        return keyboardFunctionBeen.size();
    }

    @Override
    public KeyboardFunctionBean getItem(int position) {
        return keyboardFunctionBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View function_view = LayoutInflater.from(context).inflate(R.layout.layout_item_function, null);

        ImageView iv_view = (ImageView) function_view.findViewById(R.id.iv_view);
        TextView tv_name = (TextView) function_view.findViewById(R.id.tv_name);

        KeyboardFunctionBean item = getItem(position);

        tv_name.setText(item.getName());

        // 设置内边距
        iv_view.setPadding(itemWidth / 8, itemWidth / 8, itemWidth / 8, itemWidth / 8);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, itemWidth);
        iv_view.setLayoutParams(params);


        return function_view;
    }

}
