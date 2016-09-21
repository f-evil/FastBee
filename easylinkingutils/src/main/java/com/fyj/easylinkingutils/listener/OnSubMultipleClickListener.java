package com.fyj.easylinkingutils.listener;

import android.view.View;

/**
 * 防爆点击
 * Created by Fyj on 2016/5/30.
 */
public abstract class OnSubMultipleClickListener implements View.OnClickListener {

    private final static float DELAY_TIME=1800;

    private long lastClickTime;

    public OnSubMultipleClickListener() {
        lastClickTime=0;
    }

    @Override
    public void onClick(View v) {
        if (!canTransmit()){
            return;
        }
        onSubClick(v);
    }

    public void onSubClick(View v){

    }

    protected boolean canTransmit(){

        long temTime=System.currentTimeMillis();

        float ttTime=Math.abs(temTime-lastClickTime);

        if (ttTime<DELAY_TIME&&ttTime>0){
            return false;
        }

        lastClickTime=System.currentTimeMillis();
        return true;
    }
}
