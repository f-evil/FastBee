package com.fyj.fastbee.ui.activity.contract;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fyj.fastbee.R;
import com.fyj.fastbee.ui.application.FastBebApplication;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/26<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class LineDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    private Drawable mDivider;
    private int mOrientation;
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
    public static final int[] ATRRS = new int[]{
            android.R.attr.listDivider
    };

    public LineDecoration(Context mContext) {
        this.mContext = mContext;
        final TypedArray ta = mContext.obtainStyledAttributes(ATRRS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.mDivider = FastBebApplication.getResourse().getDrawable(R.drawable.recyclerview_line, mContext.getTheme());
        } else {
            this.mDivider = FastBebApplication.getResourse().getDrawable(R.drawable.recyclerview_line);
        }
        ta.recycle();
    }

    public LineDecoration(Context mContext, int mOrientation) {
        this.mContext = mContext;
        final TypedArray ta = mContext.obtainStyledAttributes(ATRRS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.mDivider = FastBebApplication.getResourse().getDrawable(R.drawable.recyclerview_line, mContext.getTheme());
        } else {
            this.mDivider = FastBebApplication.getResourse().getDrawable(R.drawable.recyclerview_line);
        }
        ta.recycle();
        this.mOrientation = mOrientation;
    }

    //设置屏幕的方向
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    public void setLineDrawable(Drawable drawable) {
        this.mDivider = drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == HORIZONTAL_LIST) {
            drawHorizontalLine(c, parent, state);
        } else {
            drawVerticalLine(c, parent, state);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == HORIZONTAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

    private void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }


    }
}
