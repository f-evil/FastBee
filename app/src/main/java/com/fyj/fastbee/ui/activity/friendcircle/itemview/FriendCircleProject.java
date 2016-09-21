package com.fyj.fastbee.ui.activity.friendcircle.itemview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fyj.easylinkingutils.utils.SizeUtils;
import com.fyj.easylinkingutils.utils.StringUtil;
import com.fyj.easylinkingutils.utils.TimeUtils;
import com.fyj.easylinkingview.commentview.bean.DateCommentList;
import com.fyj.fastbee.R;
import com.fyj.fastbee.bean.ActivitysBean;
import com.fyj.fastbee.bean.OwnerBean;
import com.fyj.fastbee.globel.GlobalVar;

import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/6<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class FriendCircleProject extends FriendCircleCommentViewItem<ActivitysBean> {


    @Override
    protected int getLayout() {
        return R.layout.activity_friend_list_type_vip;
    }

    @Override
    protected ViewHolder initView(View convertView) {
        setCommentRightPadding(SizeUtils.dp2px(getContext(), 8));
        ProjectViewHolder mProjectViewHolder = new ProjectViewHolder();
        mProjectViewHolder.iv_person_head = (ImageView) convertView.findViewById(R.id.iv_person_head);
        mProjectViewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        mProjectViewHolder.tv_alias = (TextView) convertView.findViewById(R.id.tv_alias);
        mProjectViewHolder.iv_poster = (ImageView) convertView.findViewById(R.id.iv_poster);
        mProjectViewHolder.tv_activity_name = (TextView) convertView.findViewById(R.id.tv_activity_name);
        mProjectViewHolder.tv_activity_time = (TextView) convertView.findViewById(R.id.tv_activity_time);
        mProjectViewHolder.tv_activity_type = (TextView) convertView.findViewById(R.id.tv_activity_type);
        mProjectViewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        mProjectViewHolder.iv_controller = (ImageView) convertView.findViewById(R.id.iv_controller);
        mProjectViewHolder.tv_like_num = (TextView) convertView.findViewById(R.id.tv_like_num);
        mProjectViewHolder.iv_like = (ImageView) convertView.findViewById(R.id.iv_like);
        mProjectViewHolder.tv_menber_num = (TextView) convertView.findViewById(R.id.tv_menber_num);
        mProjectViewHolder.iv_menber = (ImageView) convertView.findViewById(R.id.iv_menber);
        mProjectViewHolder.ll_comment = (LinearLayout) convertView.findViewById(R.id.ll_comment);
        mProjectViewHolder.tv_msg_num = (TextView) convertView.findViewById(R.id.tv_msg_num);
        mProjectViewHolder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
        mProjectViewHolder.tv_ding = (TextView) convertView.findViewById(R.id.tv_ding);
        return mProjectViewHolder;
    }

    @Override
    public void bindData(View convertView, final int pos, final ActivitysBean data) {
        ProjectViewHolder mProjectViewHolder = (ProjectViewHolder) convertView.getTag();

        final OwnerBean owner = data.getOwner();
        String ownerId = data.getRefOwnerId();
        String updatedAt = data.getUpdatedAt() + "";
        String regName = owner.getRegName();
        String aliasName = owner.getAliasName();
        String companyName = owner.getCompanyName();
        String head_img = owner.getImgUrl();

        mProjectViewHolder.tv_name.setText(regName);

        if (!StringUtil.isEmpty(aliasName) && !StringUtil.isEmpty(companyName)) {
            mProjectViewHolder.tv_alias.setText(aliasName + "|" + companyName);
            mProjectViewHolder.tv_alias.setVisibility(View.VISIBLE);
        } else if (!StringUtil.isEmpty(aliasName) && StringUtil.isEmpty(companyName)) {
            mProjectViewHolder.tv_alias.setText(aliasName);
            mProjectViewHolder.tv_alias.setVisibility(View.VISIBLE);
        } else if (StringUtil.isEmpty(aliasName) && !StringUtil.isEmpty(companyName)) {
            mProjectViewHolder.tv_alias.setText(companyName);
            mProjectViewHolder.tv_alias.setVisibility(View.VISIBLE);
        } else {
            mProjectViewHolder.tv_alias.setVisibility(View.GONE);
        }

        if (ownerId.equals(GlobalVar.getGlobalUserInfo().getRefBusinessId())) {
            mProjectViewHolder.tv_delete.setVisibility(View.VISIBLE);
            mProjectViewHolder.tv_ding.setVisibility(View.VISIBLE);
        } else {
            mProjectViewHolder.tv_delete.setVisibility(View.GONE);
            mProjectViewHolder.tv_ding.setVisibility(View.GONE);
        }

        mProjectViewHolder.tv_time.setText(TimeUtils.refreshUpdatedAtValue(updatedAt));

        if (mProjectViewHolder.tv_like_num != null) {
            if (data.getIsLike().equals("0")) {
                mProjectViewHolder.iv_like.setImageResource(R.drawable.icon_date_love_no);
            } else {
                mProjectViewHolder.iv_like.setImageResource(R.drawable.icon_date_love_yes);
            }
            mProjectViewHolder.tv_like_num.setText(data.getLikeNum()+"");
        }

        if (data.getIsApplyOrJoin().equals("0")) {
            mProjectViewHolder.iv_menber.setImageResource(R.drawable.icon_date_number_out);
        } else {
            mProjectViewHolder.iv_menber.setImageResource(R.drawable.icon_date_number_in);
        }
        mProjectViewHolder.tv_menber_num.setText(data.getMemberNum()+"");

        String type = data.getType();

        if (type.equals("Project_eLinkLive")) {
            mProjectViewHolder.iv_person_head.setImageResource(R.drawable.icon_date_activity_type_elink);
        } else if (type.equals("Project_eLinkReception")) {
            mProjectViewHolder.iv_person_head.setImageResource(R.drawable.icon_date_activity_type_visitor);
        } else if (type.equals("Project_SalePromotion")) {
            mProjectViewHolder.iv_person_head.setImageResource(R.drawable.icon_date_activity_type_final_sale);
        } else if (type.equals("Project_Auction")) {
            mProjectViewHolder.iv_person_head.setImageResource(R.drawable.icon_date_activity_type_pm);
        }

        List<DateCommentList> commentList = data.getCommentList();

        if (commentList.size()>0){
            addComment(mProjectViewHolder.ll_comment,pos,commentList);
            mProjectViewHolder.ll_comment.setVisibility(View.VISIBLE);
        }else {
            mProjectViewHolder.ll_comment.setVisibility(View.GONE);
        }

        mProjectViewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (impl!=null){
                    impl.onNameOrHeadClick(owner);
                }
            }
        });

        mProjectViewHolder.iv_person_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (impl!=null){
                    impl.onNameOrHeadClick(owner);
                }
            }
        });

        mProjectViewHolder.iv_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (impl!=null){
                    impl.onControllerClick(v, pos,data);
                }
            }
        });

    }

    private class ProjectViewHolder implements ViewHolder {
        ImageView iv_person_head;
        TextView tv_activity_name;
        TextView tv_ding;
        TextView tv_name;
        TextView tv_alias;
        TextView tv_activity_time;
        TextView tv_activity_type;
        ImageView iv_poster;
        TextView tv_time;
        TextView tv_delete;
        ImageView iv_controller;
        TextView tv_like_num;
        ImageView iv_like;
        TextView tv_menber_num;
        ImageView iv_menber;
        LinearLayout ll_comment;
        TextView tv_msg_num;
    }
}
