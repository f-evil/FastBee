package com.fyj.easylinkingview.commentview;

import android.content.Context;
import android.text.TextPaint;
import android.view.View;

import com.fyj.easylinkingutils.utils.SizeUtils;
import com.fyj.easylinkingview.commentview.bean.DateOwner;
import com.fyj.easylinkingview.commentview.span.ClickableSpanEx;


/**
 * Created by 大灯泡 on 2016/2/23.
 * 评论点击事件
 */
public class CommentClick extends ClickableSpanEx {
    private Context mContext;
    private int textSize;
    private DateOwner mUserInfo;

    private CommentClick() {}

    private CommentClick(Builder builder) {
        super(builder.color,builder.clickEventColor);
        mContext = builder.mContext;
        mUserInfo = builder.mUserInfo;
        this.textSize = builder.textSize;
    }

    @Override
    public void onClick(View widget) {
        if (mUserInfo!=null){
            // TODO: 2016/8/15 点击事件
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setTextSize(textSize);
    }

    public static class Builder {
        private int color;
        private Context mContext;
        private int textSize=16;
        private DateOwner mUserInfo;
        private int clickEventColor;

        public Builder(Context context, DateOwner info) {
            mContext = context;
            mUserInfo=info;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = SizeUtils.sp2px(mContext,textSize);
            return this;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setClickEventColor(int color){
            this.clickEventColor=color;
            return this;
        }

        public CommentClick build() {
            return new CommentClick(this);
        }
    }
}
