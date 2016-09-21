package com.fyj.fastbee.adapter;

import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fyj.easylinkingimageloader.ImageLoaderHelper;
import com.fyj.easylinkingimageloader.imageoption.ImageOptions;
import com.fyj.easylinkingutils.utils.SizeUtils;
import com.fyj.easylinkingutils.utils.StringUtil;
import com.fyj.easylinkingutils.utils.TimeUtils;
import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.easylinkingview.commentview.CommentWidget;
import com.fyj.easylinkingview.commentview.bean.DateCommentList;
import com.fyj.easylinkingview.ninephotoview.NinePhotoView;
import com.fyj.easylinkingview.richtext.RichText;
import com.fyj.fastbee.R;
import com.fyj.fastbee.bean.ActivitysBean;
import com.fyj.fastbee.bean.OwnerBean;
import com.fyj.fastbee.globel.GlobalVar;
import com.fyj.fastbee.ui.application.FastBebApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Fyj on 2016/7/13.
 */
public class FriendListAdapter extends BaseAdapter implements ViewGroup.OnHierarchyChangeListener {

    private static final int TYPE_POST = 0;
    private static final int TYPE_FREE = 1;
    private static final int TYPE_FROM_PROJECT = 2;

    //评论区的view对象池
    private static final CommentPool COMMENT_TEXT_POOL = new CommentPool(35);

    private int commentPaddintRight = 0;

    private Context mContext;
    private List<ActivitysBean> newsAndActivity;
    private final LayoutInflater layoutInflater;

    private OnControllerClickListener mOnControllerClickListener;
    private OnDeleteClickListener mOnDeleteClickListener;
    private OnDingClickListener mOnDingClickListener;
    private OnContentClickListener mOnContentClickListener;
    private OnCommentClickListener mOnCommentClickListener;

    public FriendListAdapter(Context mContext, List<ActivitysBean> newsAndActivity) {
        this.mContext = mContext;
        this.newsAndActivity = newsAndActivity;
        this.layoutInflater = LayoutInflater.from(mContext);
        if (this.commentPaddintRight == 0) this.commentPaddintRight = SizeUtils.dp2px(mContext, 8);
    }

    @Override
    public int getCount() {
        return newsAndActivity.size();
    }

    @Override
    public ActivitysBean getItem(int position) {
        return newsAndActivity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        String type = getItem(position).getType();

        if (type.toLowerCase().contains("post")) {
            return TYPE_POST;
        } else if (type.toLowerCase().contains("free")) {
            return TYPE_FREE;
        } else {
            return TYPE_FROM_PROJECT;
        }

    }

    @Override
    public int getViewTypeCount() {
        return 10;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ActivitysBean bean = getItem(position);

        int typeView = getItemViewType(position);
        String type = bean.getType();
        final String activityId = bean.getId();

        PostViewHolder mPostViewHolder = null;
        FreeViewHolder mFreeViewHolder = null;
        ProjectViewHolder mProjectViewHolder = null;

        if (convertView == null) {
            if (typeView == TYPE_POST) {
                mPostViewHolder = new PostViewHolder();
                convertView = layoutInflater.inflate(R.layout.activity_friend_list_type_person, null, false);
                mPostViewHolder.iv_person_head = (ImageView) convertView.findViewById(R.id.iv_person_head);
                mPostViewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                mPostViewHolder.tv_alias = (TextView) convertView.findViewById(R.id.tv_alias);
                mPostViewHolder.rt_comment = (RichText) convertView.findViewById(R.id.rt_comment);
                mPostViewHolder.npv_photo = (NinePhotoView) convertView.findViewById(R.id.npv_photo);
                mPostViewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                mPostViewHolder.iv_controller = (ImageView) convertView.findViewById(R.id.iv_controller);
                mPostViewHolder.tv_like_num = (TextView) convertView.findViewById(R.id.tv_like_num);
                mPostViewHolder.iv_like = (ImageView) convertView.findViewById(R.id.iv_like);
                mPostViewHolder.ll_comment = (LinearLayout) convertView.findViewById(R.id.ll_comment);
                mPostViewHolder.tv_msg_num = (TextView) convertView.findViewById(R.id.tv_msg_num);
                mPostViewHolder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
                mPostViewHolder.tv_ding = (TextView) convertView.findViewById(R.id.tv_ding);
                convertView.setTag(mPostViewHolder);
            } else if (typeView == TYPE_FREE) {
                mFreeViewHolder = new FreeViewHolder();
                convertView = layoutInflater.inflate(R.layout.activity_friend_list_type_free, null, false);
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
                convertView.setTag(mFreeViewHolder);
            } else {
                mProjectViewHolder = new ProjectViewHolder();
                convertView = layoutInflater.inflate(R.layout.activity_friend_list_type_vip, null, false);
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
                convertView.setTag(mProjectViewHolder);
            }
        } else {
            if (typeView == TYPE_POST) {
                mPostViewHolder = (PostViewHolder) convertView.getTag();
            } else if (typeView == TYPE_FREE) {
                mFreeViewHolder = (FreeViewHolder) convertView.getTag();
            } else {
                mProjectViewHolder = (ProjectViewHolder) convertView.getTag();
            }
        }

        ImageView head_img;
        TextView name_tx;
        TextView alias_tx;
        TextView ge2_tx;
        TextView company_tx;
        TextView time_tx;
        ImageView controller_iv;
        TextView like_num_tx;
        ImageView like_iv;
        TextView menber_num_tx;
        ImageView menber_iv;
        LinearLayout comment_ll;
        TextView msg_num_tx;
        TextView delete_tx;
        TextView ding_tx;
        TextView activity_name_tv;
        TextView activity_addr_tv;
        TextView activity_type_tv;
        TextView activity_time_tv;
        NinePhotoView nine_pics;
        RichText comment_tv;
        ImageView post_iv;

        if (typeView == 0) {
            head_img = mPostViewHolder.iv_person_head;
            name_tx = mPostViewHolder.tv_name;
            alias_tx = mPostViewHolder.tv_alias;
            time_tx = mPostViewHolder.tv_time;
            controller_iv = mPostViewHolder.iv_controller;
            like_num_tx = mPostViewHolder.tv_like_num;
            like_iv = mPostViewHolder.iv_like;
            menber_num_tx = null;
            menber_iv = null;
            comment_ll = mPostViewHolder.ll_comment;
            msg_num_tx = mPostViewHolder.tv_msg_num;
            delete_tx = mPostViewHolder.tv_delete;
            ding_tx = mPostViewHolder.tv_ding;
            nine_pics = mPostViewHolder.npv_photo;
            activity_name_tv = null;
            activity_addr_tv = null;
            activity_type_tv = null;
            activity_time_tv = null;
            comment_tv = mPostViewHolder.rt_comment;
            post_iv = null;
        } else if (typeView == 1) {
            head_img = mFreeViewHolder.iv_person_head;
            name_tx = mFreeViewHolder.tv_name;
            alias_tx = mFreeViewHolder.tv_alias;
            time_tx = mFreeViewHolder.tv_time;
            controller_iv = mFreeViewHolder.iv_controller;
            like_num_tx = mFreeViewHolder.tv_like_num;
            like_iv = mFreeViewHolder.iv_like;
            menber_num_tx = mFreeViewHolder.tv_menber_num;
            menber_iv = mFreeViewHolder.iv_menber;
            comment_ll = mFreeViewHolder.ll_comment;
            msg_num_tx = mFreeViewHolder.tv_msg_num;
            delete_tx = mFreeViewHolder.tv_delete;
            ding_tx = mFreeViewHolder.tv_ding;
            activity_name_tv = mFreeViewHolder.tv_activity_name;
            activity_addr_tv = mFreeViewHolder.tv_activity_addr;
            activity_type_tv = mFreeViewHolder.tv_activity_type;
            activity_time_tv = mFreeViewHolder.tv_activity_time;
            nine_pics = null;
            comment_tv = null;
            post_iv = mFreeViewHolder.iv_poster;
        } else {
            head_img = mProjectViewHolder.iv_person_head;
            name_tx = mProjectViewHolder.tv_name;
            alias_tx = mProjectViewHolder.tv_alias;
            time_tx = mProjectViewHolder.tv_time;
            controller_iv = mProjectViewHolder.iv_controller;
            like_num_tx = mProjectViewHolder.tv_like_num;
            like_iv = mProjectViewHolder.iv_like;
            menber_num_tx = mProjectViewHolder.tv_menber_num;
            menber_iv = mProjectViewHolder.iv_menber;
            comment_ll = mProjectViewHolder.ll_comment;
            msg_num_tx = mProjectViewHolder.tv_msg_num;
            delete_tx = mProjectViewHolder.tv_delete;
            ding_tx = mProjectViewHolder.tv_ding;
            activity_name_tv = mProjectViewHolder.tv_activity_name;
            activity_addr_tv = null;
            activity_type_tv = mProjectViewHolder.tv_activity_type;
            activity_time_tv = mProjectViewHolder.tv_activity_time;
            nine_pics = null;
            comment_tv = null;
            post_iv = mProjectViewHolder.iv_poster;
        }

        OwnerBean owner = bean.getOwner();
        final String ownerId = bean.getRefOwnerId();
        String regName = owner.getRegName();
        String aliasName = owner.getAliasName();
        String companyName = owner.getCompanyName();

        name_tx.setText(regName);

        if (typeView == 0 || typeView == 1) {
            if (!StringUtil.isEmpty(aliasName) && !StringUtil.isEmpty(companyName)) {
                alias_tx.setText(aliasName + "|" + companyName);
                alias_tx.setVisibility(View.VISIBLE);
            } else if (!StringUtil.isEmpty(aliasName) && StringUtil.isEmpty(companyName)) {
                alias_tx.setText(aliasName);
                alias_tx.setVisibility(View.VISIBLE);
            } else if (StringUtil.isEmpty(aliasName) && !StringUtil.isEmpty(companyName)) {
                alias_tx.setText(companyName);
                alias_tx.setVisibility(View.VISIBLE);
            } else {
                alias_tx.setVisibility(View.GONE);
            }

        } else {
            if (!StringUtil.isEmpty(aliasName) && !StringUtil.isEmpty(companyName)) {
                alias_tx.setText("|" + aliasName + "|" + companyName);
                alias_tx.setVisibility(View.VISIBLE);
            } else if (!StringUtil.isEmpty(aliasName) && StringUtil.isEmpty(companyName)) {
                alias_tx.setText("|" + aliasName);
                alias_tx.setVisibility(View.VISIBLE);
            } else if (StringUtil.isEmpty(aliasName) && !StringUtil.isEmpty(companyName)) {
                alias_tx.setText("|" + companyName);
                alias_tx.setVisibility(View.VISIBLE);
            } else {
                alias_tx.setVisibility(View.GONE);
            }
        }

        if (bean.getRefOwnerId().equals(GlobalVar.getGlobalUserInfo().getRefBusinessId())) {
            delete_tx.setVisibility(View.VISIBLE);
            ding_tx.setVisibility(View.VISIBLE);
        } else {
            delete_tx.setVisibility(View.GONE);
            ding_tx.setVisibility(View.GONE);
        }

        time_tx.setText(TimeUtils.refreshUpdatedAtValue(bean.getUpdatedAt() + ""));

        if (menber_num_tx != null) {
            if (bean.getIsApplyOrJoin().equals("0")) {
                menber_iv.setImageResource(R.drawable.icon_date_number_out);
            } else {
                menber_iv.setImageResource(R.drawable.icon_date_number_in);
            }
            menber_num_tx.setText(bean.getMemberNum() + "");
        }

        if (like_num_tx != null) {
            if (bean.getIsLike().equals("0")) {
                like_iv.setImageResource(R.drawable.icon_date_love_no);
            } else {
                like_iv.setImageResource(R.drawable.icon_date_love_yes);
            }
            like_num_tx.setText(bean.getLikeNum() + "");
        }

        if (typeView == 0) {
            ImageLoaderHelper.displayHeadImage(owner.getImgUrl(), head_img);
        } else if (typeView == 1) {
            ImageLoaderHelper.displayHeadImage(owner.getImgUrl(), head_img);
        } else {
            if (type.equals("Project_eLinkLive")) {
                head_img.setImageResource(R.drawable.icon_date_activity_type_elink);
            } else if (type.equals("Project_eLinkReception")) {
                head_img.setImageResource(R.drawable.icon_date_activity_type_visitor);
            } else if (type.equals("Project_SalePromotion")) {
                head_img.setImageResource(R.drawable.icon_date_activity_type_final_sale);
            } else if (type.equals("Project_Auction")) {
                head_img.setImageResource(R.drawable.icon_date_activity_type_pm);
            }
        }

        if (activity_name_tv != null) {

            if (type.equals("Project_eLinkLive")) {
                activity_name_tv.setTextColor(FastBebApplication.getResourse().getColor(R.color.line_blue));
            } else if (type.equals("Project_eLinkReception")) {
                activity_name_tv.setTextColor(FastBebApplication.getResourse().getColor(R.color.line_yellow));
            } else if (type.equals("Project_SalePromotion")) {
                activity_name_tv.setTextColor(FastBebApplication.getResourse().getColor(R.color.line_origin));
            } else if (type.equals("Project_Auction")) {
                activity_name_tv.setTextColor(FastBebApplication.getResourse().getColor(R.color.line_purple));
            }

            activity_name_tv.setText(bean.getTitle());

        }

        if (activity_addr_tv != null) {
            activity_addr_tv.setText(bean.getLocationName());
        }

        if (activity_type_tv != null) {
            activity_type_tv.setText(bean.getCategoryNote());
        }

        if (activity_time_tv != null) {
            activity_time_tv.setText(TimeUtils.milliseconds2String(bean.getStartTime()));
        }


        if (nine_pics != null) {
            List<String> urls = null;
            String ninePic;
            try {
                ninePic = bean.getThumbnails();
                urls = Arrays.asList(ninePic.split(","));
            } catch (Exception e) {
                ninePic = "";
            }

            if (ninePic != null && !ninePic.equals("")) {
                nine_pics.setVisibility(View.VISIBLE);
                nine_pics.isPicClick(false);
                nine_pics.setUrls(urls);
                nine_pics.display();
            } else {
                nine_pics.setVisibility(View.GONE);
            }

        }

        if (comment_tv != null) {
            String content = cutHtmlLength(bean.getDesc());
            if (null != content && !content.isEmpty()) {
                comment_tv.setText(getClickableHtml(content));
                comment_tv.setVisibility(View.VISIBLE);
            } else {
                comment_tv.setVisibility(View.GONE);
            }
        }

        if (post_iv != null) {
            String thumbnails = bean.getThumbnails();
            String post_url;
            if (thumbnails != null && !thumbnails.isEmpty()) {
                post_url = "http://t2.easylinking.net:10010" + thumbnails;
            } else {
                post_url = "http://t2.easylinking.net:10010" + bean.getPoster();
            }
            ImageLoaderHelper.display(post_url, post_iv, ImageOptions.YUE_TYPE);
        }

        String commentNum = bean.getCommentNum() + "";
        List<DateCommentList> commentList = bean.getCommentList();
        if (commentList != null && commentList.size() > 0) {
            if (msg_num_tx != null) {
                if (commentNum != null && !commentNum.equals("0")) {
                    comment_ll.setVisibility(View.VISIBLE);
                    addCommentWidget(comment_ll, bean);
                    if (Integer.valueOf(commentNum) > 3) {
                        msg_num_tx.setText("查看全部" + commentNum + "条评论");
                        msg_num_tx.setVisibility(View.VISIBLE);
                    } else {
                        msg_num_tx.setVisibility(View.GONE);
                    }
                } else {
                    msg_num_tx.setVisibility(View.GONE);
                    comment_ll.setVisibility(View.GONE);
                }
            }
        } else {
            if (msg_num_tx != null) {
                msg_num_tx.setVisibility(View.GONE);
                comment_ll.setVisibility(View.GONE);
            }
        }


        head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ownerId.equals(GlobalVar.getGlobalUserInfo().getRefBusinessId())) {

                    ToastUtil.makeText(ownerId);

                }
            }
        });

        name_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ownerId.equals(GlobalVar.getGlobalUserInfo().getRefBusinessId())) {

                    ToastUtil.makeText(ownerId);

                }
            }
        });

        controller_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnControllerClickListener != null) {
                    mOnControllerClickListener.onClick(v, bean);
                }
            }
        });

        delete_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDeleteClickListener != null) {
                    mOnDeleteClickListener.onClick(v, bean, position);
                }
            }
        });

        ding_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDingClickListener != null) {
                    mOnDingClickListener.onClick(v, bean, position);
                }
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnContentClickListener != null) {
                    mOnContentClickListener.onClick(activityId);
                }
            }
        });


        return convertView;
    }

    public void updataList(List<ActivitysBean> result) {
        this.newsAndActivity = result;
        notifyDataSetChanged();
    }

    @Override
    public void onChildViewAdded(View parent, View child) {

    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
        if (child instanceof CommentWidget) COMMENT_TEXT_POOL.put((CommentWidget) child);
    }

    private class PostViewHolder {
        ImageView iv_person_head;
        TextView tv_name;
        TextView tv_alias;
        RichText rt_comment;
        NinePhotoView npv_photo;
        TextView tv_time;
        ImageView iv_controller;
        TextView tv_like_num;
        ImageView iv_like;
        LinearLayout ll_comment;
        TextView tv_msg_num;
        TextView tv_delete;
        TextView tv_ding;
    }

    private class FreeViewHolder {
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

    private class ProjectViewHolder {
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

    private String cutHtmlLength(String html) {
        String content = "";
        try {
            int firstIndex = html.indexOf("<p>");
            int lastIndex = html.indexOf("</p>");
            content = html.substring(firstIndex + 3, lastIndex);

            try {
                content = content.replaceAll("\\\\\"", "\"");
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (content.contains("\"</a><br>\"")) {
                int oneIndex = content.indexOf("</a><br>");
                String xx = content.substring(oneIndex + 8, content.length());
                if (xx.length() > 100) {
                    xx = xx.substring(0, 99);
                    content = content.substring(0, oneIndex + 8) + xx + "&nbsp;" + "..." + "<p></p>" + "<font " +
                            "color=\"#506591\">全文</font>";
                }
            } else {
                if (!content.contains("</a>") && content.length() > 100) {
                    content = content.substring(0, 99) + "&nbsp;" + "..." + "<p></p>" + "<font " +
                            "color=\"#506591\">全文</font>";
                }
            }

            try {
                String[] num = content.split("<br>");
                if (num.length > 7) {

                    content = cutBr(content);

                    if (content.length() > 100) {
                        content = content.substring(0, 99) + "&nbsp;" + "..." + "<p></p>" + "<font " +
                                "color=\"#506591\">全文</font>";
                    } else {
                        content = content.replaceAll("<br><br>", "<br>") + "&nbsp;" + "..." + "<p></p>" + "<font " +
                                "color=\"#506591\">全文</font>";
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    private String cutBr(String content) {
        ArrayList<Integer> bar_index = StringUtil.searchTextFromString("<br>", content);
        content = content.substring(0, bar_index.get(6 * 2 - 2));
        return content;
    }

    private CharSequence getClickableHtml(String html) {
        Spanned spannedHtml = Html.fromHtml(html);
        SpannableStringBuilder clickableHtmlBuilder = new SpannableStringBuilder(spannedHtml);
        URLSpan[] urls = clickableHtmlBuilder.getSpans(0, spannedHtml.length(), URLSpan.class);
        for (final URLSpan span : urls) {
            setLinkClickable(clickableHtmlBuilder, span);
        }
        return clickableHtmlBuilder;
    }

    /**
     * textview加载html文件捕获url的点击事件
     */
    private void setLinkClickable(final SpannableStringBuilder clickableHtmlBuilder,
                                  final URLSpan urlSpan) {
        int start = clickableHtmlBuilder.getSpanStart(urlSpan);
        int end = clickableHtmlBuilder.getSpanEnd(urlSpan);
        int flags = clickableHtmlBuilder.getSpanFlags(urlSpan);
        ClickableSpan clickableSpan = new ClickableSpan() {
            public void onClick(View view) {

                ToastUtil.makeText(urlSpan.getURL());
            }
        };
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
    }

    public interface OnControllerClickListener {
        void onClick(View v, ActivitysBean bean);
    }

    public void setOnControllerClickListener(OnControllerClickListener mOnControllerClickListener) {
        this.mOnControllerClickListener = mOnControllerClickListener;
    }

    public interface OnDeleteClickListener {
        void onClick(View v, ActivitysBean bean, int position);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener mOnDeleteClickListener) {
        this.mOnDeleteClickListener = mOnDeleteClickListener;
    }

    public interface OnDingClickListener {
        void onClick(View v, ActivitysBean bean, int position);
    }

    public void setOnDingClickListener(OnDingClickListener mOnDingClickListener) {
        this.mOnDingClickListener = mOnDingClickListener;
    }

    public interface OnContentClickListener {
        void onClick(String activityId);
    }

    public void setOnContentClickListener(OnContentClickListener mOnContentClickListener) {
        this.mOnContentClickListener = mOnContentClickListener;
    }

    public interface OnCommentClickListener {
        void onCommentClick(View v, ActivitysBean bean);

        void onTotalCommentNumClick(View v, ActivitysBean bean);
    }

    public void setOnCommentClickListener(OnCommentClickListener mOnCommentClickListener) {
        this.mOnCommentClickListener = mOnCommentClickListener;
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

    private void addCommentWidget(LinearLayout comment_ll, final ActivitysBean bean) {
        List<DateCommentList> commentList = bean.getCommentList();
        if (commentList == null || commentList.size() == 0) return;
        /**
         * 优化方案：
         * 因为是在listview里面，那么复用肯定有，意味着滑动的时候必须要removeView或者addView
         * 但为了性能提高，不可以直接removeAllViews
         * 于是采取以下方案：
         *    根据现有的view进行remove/add差额
         *    然后统一设置
         * */

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
                    commentWidget = new CommentWidget(mContext);
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
            CommentWidget commentWidget = (CommentWidget) comment_ll.getChildAt(n);
            if (commentWidget != null) {
                commentWidget.setCommentText(commentList.get(n));
                commentWidget.setOnClickListener(null);
                commentWidget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnCommentClickListener != null) {
                            mOnCommentClickListener.onCommentClick(v, bean);
                        }
                    }
                });
            }
        }
    }

}
