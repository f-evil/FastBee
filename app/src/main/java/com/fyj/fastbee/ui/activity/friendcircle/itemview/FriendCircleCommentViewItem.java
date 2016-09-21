package com.fyj.fastbee.ui.activity.friendcircle.itemview;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fyj.easylinkingadapter.AbsMViewItem;
import com.fyj.easylinkingutils.utils.SizeUtils;
import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.easylinkingview.commentview.CommentWidget;
import com.fyj.easylinkingview.commentview.bean.DateCommentList;
import com.fyj.fastbee.bean.ActivitysBean;
import com.fyj.fastbee.ui.activity.friendcircle.FriendCircleCommonPreImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/6<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public abstract class FriendCircleCommentViewItem<T> extends AbsMViewItem<T> implements ViewGroup.OnHierarchyChangeListener {

    private static final CommentPool COMMENT_TEXT_POOL = new CommentPool(35);

    private int commentPaddintRight=0;
    protected FriendCircleCommonPreImpl impl;

    protected void setCommentRightPadding(int commentPaddintRight){
        this.commentPaddintRight=commentPaddintRight;
    };

    protected void setPreImpl(FriendCircleCommonPreImpl impl){
        this.impl=impl;
    };

    public void addComment(LinearLayout comment_ll, final int pos, final List<DateCommentList> commentList) {
        if (commentList == null || commentList.size() == 0) return;

//        if (commentList.size() > 3) {
//            List<DateCommentList> commentListTemp = new ArrayList<>();
//            commentListTemp.add(commentList.get(0));
//            commentListTemp.add(commentList.get(1));
//            commentListTemp.add(commentList.get(2));
//            commentList.clear();
//            commentList.addAll(commentListTemp);
//        }

        final int childCount = comment_ll.getChildCount();
        comment_ll.setOnHierarchyChangeListener(this);
        if (childCount < commentList.size()) {
            //当前的view少于list的长度，则补充相差的view
            int subCount = commentList.size() - childCount;
            for (int i = 0; i < subCount; i++) {
                CommentWidget commentWidget = COMMENT_TEXT_POOL.get();
                if (commentWidget == null) {
                    commentWidget = new CommentWidget(getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.topMargin = 1;
                    params.bottomMargin = 1;
                    commentWidget.setLayoutParams(params);
                    commentWidget.setPadding(0, 0, commentPaddintRight, 0);
                    commentWidget.setLineSpacing(4, 1);
                    commentWidget.setMaxLines(2);
                    commentWidget.setEllipsize(TextUtils.TruncateAt.END);
                }
                comment_ll.addView(commentWidget);
            }
        } else if (childCount > commentList.size()) {
            //当前的view的数目比list的长度大，则减去对应的view
            comment_ll.removeViews(commentList.size(), childCount - commentList.size());
        }
        //绑定数据
        for (int n = 0; n < commentList.size(); n++) {
            final CommentWidget commentWidget = (CommentWidget) comment_ll.getChildAt(n);
            if (commentWidget != null) {
                final DateCommentList dateCommentList = commentList.get(n);
                commentWidget.setCommentText(dateCommentList);
                commentWidget.setOnClickListener(null);
                final int finalN = n;
                commentWidget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (impl!=null){
                            impl.onCommentClick(commentWidget,dateCommentList,pos, finalN);
                        }
                    }
                });
            }
        }
    }

    static class CommentPool {
        private CommentWidget[] CommentPool;
        private int size;
        private int curPointer = -1;

        public CommentPool(int size) {
            this.size = size;
            CommentPool = new CommentWidget[size];
        }

        public synchronized CommentWidget get() {
            if (curPointer == -1 || curPointer > CommentPool.length) return null;
            CommentWidget commentTextView = CommentPool[curPointer];
            CommentPool[curPointer] = null;
            //Log.d("itemDelegate","复用成功---- 当前的游标为： "+curPointer);
            curPointer--;
            return commentTextView;
        }

        public synchronized boolean put(CommentWidget commentTextView) {
            if (curPointer == -1 || curPointer < CommentPool.length - 1) {
                curPointer++;
                CommentPool[curPointer] = commentTextView;
                //Log.d("itemDelegate","入池成功---- 当前的游标为： "+curPointer);
                return true;
            }
            return false;
        }

        public void clearPool() {
            for (int i = 0; i < CommentPool.length; i++) {
                CommentPool[i] = null;
            }
            curPointer = -1;
        }
    }

    @Override
    public void onChildViewAdded(View parent, View child) {

    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
        if (child instanceof CommentWidget) COMMENT_TEXT_POOL.put((CommentWidget) child);
    }
}
