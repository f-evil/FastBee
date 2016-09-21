package com.fyj.easylinkingview.popwindow.menupopwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fyj.easylinkingview.R;
import com.fyj.easylinkingview.popwindow.menupopwindow.adapter.PopMenuAdapter;
import com.fyj.easylinkingview.popwindow.menupopwindow.model.PopMenuModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SSNB on 2016/4/8.
 */
public class MenuPopWindow extends PopupWindow implements PopupWindow.OnDismissListener{

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onCancelClick(View view);
    }

    @Override
    public void onDismiss() {
        closePopWindow();
    }

    public enum MenuStyle{
        NORMAL,DANGER,
    }
    private static final String TAG = MenuPopWindow.class.getSimpleName();

    private Context mContext;
    private LinearLayout contentView;
    private LinearLayout popListLayout;
    private ListView popListView;
    private TextView CancelView;

    private OnItemClickListener itemClickListener;
    private String[] itemName;
    private MenuStyle[] itemStyle;
    private List<PopMenuModel> popData;
    private PopMenuAdapter popAdapter;

    //头部空的一个view 用于防止item过多导致的超出屏幕的情况
    private TextView emptyBlock;
    int itemNub;
    int styleNub;


    public static MenuPopWindow FactoryMenuPopWindow(Context context){
        return new MenuPopWindow(context);
    }

    private MenuPopWindow(Context context){
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = (LinearLayout)inflater.inflate(R.layout.pop_window_menu, null);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        emptyBlock = (TextView)contentView.findViewById(R.id.ll_empty_block);
        popListLayout =(LinearLayout)contentView.findViewById(R.id.ll_ll_pop);
        popListView = (ListView)contentView.findViewById(R.id.ll_ll_lv_item);
        CancelView = (TextView)contentView.findViewById(R.id.ll_ll_tv_cancel);

        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable drawable = new ColorDrawable(0);
        this.setBackgroundDrawable(drawable);
    }

    private void initData() {
        popData = new ArrayList<>();
    }

    private void initEvent() {
        CancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener !=null){
                    itemClickListener.onCancelClick(v);
                }
                dismiss();
            }
        });
        emptyBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener !=null){
                    itemClickListener.onCancelClick(v);
                }
                dismiss();
            }
        });
        setOnDismissListener(this);
    }

    public MenuPopWindow Builder(){
        if (itemName != null){
            itemNub = itemName.length;
        }else{
            itemNub = 0;
        }
        if(itemStyle != null){
            styleNub = itemStyle.length;
        }else{
            styleNub = 0;
        }
        for (int i = 0 ;i < itemNub ; i++){
            PopMenuModel model =new PopMenuModel();
            model.setName(itemName[i]);
            if(i<styleNub){
                model.setStyle(itemStyle[i]);
            }
            popData.add(model);
        }
        bindData();
        bindEvent();
//        this.setAnimationStyle(R.style.PopWindow_bottom_animation);
        return this;
    }

    private void bindEvent(){
        if (itemClickListener !=null){
            popListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    itemClickListener.onItemClick(view,position);
                    dismiss();
                }
            });
        }
    }

    private void bindData() {
        if(itemNub>0){
            popListLayout.setVisibility(View.VISIBLE);
        }else{
            popListLayout.setVisibility(View.GONE);
        }
        popAdapter = new PopMenuAdapter(mContext,popData);
        popListView.setAdapter(popAdapter);
    }

    public MenuPopWindow setItemStyle(MenuStyle[] itemStyle) {
        this.itemStyle = itemStyle;
        return this;
    }

    public MenuPopWindow setItemName(String[] itemName) {
        this.itemName = itemName;
        return this;
    }

    public MenuPopWindow setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

    public void showPopWindow(View parent,int gravity){
        if(!this.isShowing()){
            this.showAtLocation(parent, gravity,0,0);
            openPopWindow();
        }else{
            this.dismiss();
        }
    }

    private void closePopWindow(){
        WindowManager.LayoutParams params=((Activity)mContext).getWindow().getAttributes();
        params.alpha=1f;
        ((Activity)mContext).getWindow().setAttributes(params);
    }
    private void openPopWindow(){
        WindowManager.LayoutParams params=((Activity)mContext).getWindow().getAttributes();
        params.alpha=0.5f;
        ((Activity)mContext).getWindow().setAttributes(params);
    }
}
