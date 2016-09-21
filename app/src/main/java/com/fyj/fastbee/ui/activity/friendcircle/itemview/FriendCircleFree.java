package com.fyj.fastbee.ui.activity.friendcircle.itemview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fyj.easylinkingadapter.AbsMViewItem;
import com.fyj.easylinkingimageloader.ImageLoaderHelper;
import com.fyj.easylinkingutils.utils.SizeUtils;
import com.fyj.easylinkingutils.utils.StringUtil;
import com.fyj.easylinkingutils.utils.TimeUtils;
import com.fyj.easylinkingview.commentview.bean.DateCommentList;
import com.fyj.fastbee.R;
import com.fyj.fastbee.bean.ActivitysBean;
import com.fyj.fastbee.bean.OwnerBean;
import com.fyj.fastbee.globel.GlobalVar;
import com.fyj.fastbee.ui.activity.friendcircle.FriendCircleCommonPreImpl;

import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/6<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class FriendCircleFree extends FriendCircleCommentViewItem<ActivitysBean> {



    @Override
    protected int getLayout() {
        return R.layout.activity_friend_list_type_free;
    }

    @Override
    protected ViewHolder initView(View convertView) {
        setCommentRightPadding(SizeUtils.dp2px(getContext(), 8));
        FreeViewHolder mFreeViewHolder = new FreeViewHolder();
        mFreeViewHolder.iv_person_head = (ImageView) convertView.findViewById(R.id.iv_person_head);
        mFreeViewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        mFreeViewHolder.tv_alias = (TextView) convertView.findViewById(R.id.tv_alias);
        mFreeViewHolder.iv_poster = (ImageView) convertView.findViewById(R.id.iv_poster);
        mFreeViewHolder.tv_activity_name = (TextView) convertView.findViewById(R.id.tv_activity_name);
        mFreeViewHolder.tv_activity_addr = (TextView) convertView.findViewById(R.id.tv_activity_addr);
        mFreeViewHolder.tv_activity_time = (TextView) convertView.findViewById(R.id.tv_activity_time);
        mFreeViewHolder.tv_activity_type = (TextView) convertView.findViewById(R.id.tv_activity_type);
        mFreeViewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        mFreeViewHolder.iv_controller = (ImageView) convertView.findViewById(R.id.iv_controller);
        mFreeViewHolder.tv_like_num = (TextView) convertView.findViewById(R.id.tv_like_num);
        mFreeViewHolder.iv_like = (ImageView) convertView.findViewById(R.id.iv_like);
        mFreeViewHolder.tv_menber_num = (TextView) convertView.findViewById(R.id.tv_menber_num);
        mFreeViewHolder.iv_menber = (ImageView) convertView.findViewById(R.id.iv_menber);
        mFreeViewHolder.ll_comment = (LinearLayout) convertView.findViewById(R.id.ll_comment);
        mFreeViewHolder.tv_msg_num = (TextView) convertView.findViewById(R.id.tv_msg_num);
        mFreeViewHolder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
        mFreeViewHolder.tv_ding = (TextView) convertView.findViewById(R.id.tv_ding);
        return mFreeViewHolder;
    }

    @Override
    public void bindData(View convertView, final int pos, final ActivitysBean data) {

        FreeViewHolder mFreeViewHolder = (FreeViewHolder) convertView.getTag();

        final OwnerBean owner = data.getOwner();
        String ownerId = data.getRefOwnerId();
        String updatedAt = data.getUpdatedAt() + "";
        String regName = owner.getRegName();
        String aliasName = owner.getAliasName();
        String companyName = owner.getCompanyName();
        String head_img = owner.getImgUrl();

        mFreeViewHolder.tv_name.setText(regName);

        if (!StringUtil.isEmpty(aliasName) && !StringUtil.isEmpty(companyName)) {
            mFreeViewHolder.tv_alias.setText(aliasName + "|" + companyName);
            mFreeViewHolder.tv_alias.setVisibility(View.VISIBLE);
        } else if (!StringUtil.isEmpty(aliasName) && StringUtil.isEmpty(companyName)) {
            mFreeViewHolder.tv_alias.setText(aliasName);
            mFreeViewHolder.tv_alias.setVisibility(View.VISIBLE);
        } else if (StringUtil.isEmpty(aliasName) && !StringUtil.isEmpty(companyName)) {
            mFreeViewHolder.tv_alias.setText(companyName);
            mFreeViewHolder.tv_alias.setVisibility(View.VISIBLE);
        } else {
            mFreeViewHolder.tv_alias.setVisibility(View.GONE);
        }

        if (ownerId.equals(GlobalVar.getGlobalUserInfo().getRefBusinessId())) {
            mFreeViewHolder.tv_delete.setVisibility(View.VISIBLE);
            mFreeViewHolder.tv_ding.setVisibility(View.VISIBLE);
        } else {
            mFreeViewHolder.tv_delete.setVisibility(View.GONE);
            mFreeViewHolder.tv_ding.setVisibility(View.GONE);
        }

        mFreeViewHolder.tv_time.setText(TimeUtils.refreshUpdatedAtValue(updatedAt));

        if (mFreeViewHolder.tv_like_num != null) {
            if (data.getIsLike().equals("0")) {
                mFreeViewHolder.iv_like.setImageResource(R.drawable.icon_date_love_no);
            } else {
                mFreeViewHolder.iv_like.setImageResource(R.drawable.icon_date_love_yes);
            }
            mFreeViewHolder.tv_like_num.setText(data.getLikeNum()+"");
        }

        if (data.getIsApplyOrJoin().equals("0")) {
            mFreeViewHolder.iv_menber.setImageResource(R.drawable.icon_date_number_out);
        } else {
            mFreeViewHolder.iv_menber.setImageResource(R.drawable.icon_date_number_in);
        }
        mFreeViewHolder.tv_menber_num.setText(data.getMemberNum()+"");

        ImageLoaderHelper.displayHeadImage("http://attach.easylinking.net/" + head_img, mFreeViewHolder.iv_person_head);

        List<DateCommentList> commentList = data.getCommentList();

        if (commentList.size()>0){
            addComment(mFreeViewHolder.ll_comment,pos,commentList);
            mFreeViewHolder.ll_comment.setVisibility(View.VISIBLE);
        }else {
            mFreeViewHolder.ll_comment.setVisibility(View.GONE);
        }

        mFreeViewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (impl!=null){
                    impl.onNameOrHeadClick(owner);
                }
            }
        });

        mFreeViewHolder.iv_person_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (impl!=null){
                    impl.onNameOrHeadClick(owner);
                }
            }
        });

        mFreeViewHolder.iv_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (impl!=null){
                    impl.onControllerClick(v,pos,data);
                }
            }
        });

    }

    private class FreeViewHolder implements ViewHolder {
        ImageView iv_person_head;
        TextView tv_name;
        TextView tv_alias;
        ImageView iv_poster;
        TextView tv_activity_name;
        TextView tv_activity_addr;
        TextView tv_activity_time;
        TextView tv_activity_type;
        TextView tv_time;
        ImageView iv_controller;
        TextView tv_like_num;
        ImageView iv_like;
        TextView tv_menber_num;
        ImageView iv_menber;
        LinearLayout ll_comment;
        TextView tv_msg_num;
        TextView tv_delete;
        TextView tv_ding;
    }
}
