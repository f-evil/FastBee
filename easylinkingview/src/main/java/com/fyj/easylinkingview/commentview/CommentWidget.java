package com.fyj.easylinkingview.commentview;

import android.content.Context;
import android.os.Build;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.fyj.easylinkingview.commentview.bean.DateCommentList;
import com.fyj.easylinkingview.commentview.span.ClickableSpanEx;
import com.fyj.easylinkingview.commentview.span.SpannableStringBuilderAllVer;

/**
 * Created by 大灯泡 on 2016/2/23.
 * 评论控件
 */
public class CommentWidget extends TextView {
    private static final String TAG = "CommentWidget";
    private Context mContext;
    //用户名颜色
    private int textColor = 0xff517fae;
    private static final int textSize = 14;
    SpannableStringBuilderAllVer mSpannableStringBuilderAllVer;

    public CommentWidget(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public CommentWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public CommentWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMovementMethod(LinkMovementMethod.getInstance());
        setOnTouchListener(new ClickableSpanEx.ClickableSpanSelector());
        this.setHighlightColor(0x00000000);
        this.mContext = context;
        setTextSize(textSize);
    }

//    public CommentWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        setMovementMethod(LinkMovementMethod.getInstance());
//        this.setHighlightColor(0x00000000);
//        this.mContext = context;
//        setTextSize(textSize);
//    }

    public void setCommentText(DateCommentList info) {
        if (info == null) return;
        try {
            setTag(info);
            createCommentStringBuilder(info);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e(TAG, "虽然在下觉得不可能会有这个情况，但还是捕捉下吧，万一被打脸呢。。。");
        }
    }

    private void createCommentStringBuilder(DateCommentList info) {
        if (mSpannableStringBuilderAllVer == null) {
            mSpannableStringBuilderAllVer = new SpannableStringBuilderAllVer();
        }
        else {
            mSpannableStringBuilderAllVer.clear();
            mSpannableStringBuilderAllVer.clearSpans();
        }
        String content = "： " + info.getComment() + "\0";

        SpannableString spannableString = new SpannableString(content);

        boolean isApply = (info.getToUserId()==null||info.getToUserId().isEmpty());

        if (isApply){
            CommentClick userA = new CommentClick.Builder(getContext(), info.getOwner()).setColor(0xff517fae)
                    .setClickEventColor(0xffc6c6c6)
                    .setTextSize(textSize)
                    .build();
            mSpannableStringBuilderAllVer.append(info.getOwner().getRegName(), userA, 0);
            if (null != spannableString) {
                mSpannableStringBuilderAllVer.append(spannableString);
            } else {
                mSpannableStringBuilderAllVer.append(content);
            }
        }else {
            CommentClick userA = new CommentClick.Builder(getContext(), info.getOwner()).setColor(0xff517fae)
                    .setClickEventColor(0xffc6c6c6)
                    .setTextSize(textSize)
                    .build();
            mSpannableStringBuilderAllVer.append(info.getOwner().getRegName(), userA, 0);
            mSpannableStringBuilderAllVer.append("回复");
            CommentClick userB = new CommentClick.Builder(getContext(), info.getToUser()).setColor(0xff517fae)
                    .setClickEventColor(0xffc6c6c6)
                    .setTextSize(textSize)
                    .build();
            mSpannableStringBuilderAllVer.append(info.getToUser().getRegName(), userB, 0);
            if (null != spannableString) {
                mSpannableStringBuilderAllVer.append(spannableString);
            } else {
                mSpannableStringBuilderAllVer.append(content);
            }
        }

        setText(mSpannableStringBuilderAllVer);

    }

    public DateCommentList getData() throws ClassCastException {
        return (DateCommentList) getTag();
    }
}
