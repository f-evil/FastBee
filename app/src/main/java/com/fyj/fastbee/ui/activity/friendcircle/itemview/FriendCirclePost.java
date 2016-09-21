package com.fyj.fastbee.ui.activity.friendcircle.itemview;

import android.content.Intent;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fyj.easylinkingimageloader.ImageLoaderHelper;
import com.fyj.easylinkingutils.utils.SizeUtils;
import com.fyj.easylinkingutils.utils.StringUtil;
import com.fyj.easylinkingutils.utils.TimeUtils;
import com.fyj.easylinkingview.commentview.bean.DateCommentList;
import com.fyj.easylinkingview.ninephotoview.NinePhotoView;
import com.fyj.easylinkingview.richtext.RichText;
import com.fyj.fastbee.R;
import com.fyj.fastbee.bean.ActivitysBean;
import com.fyj.fastbee.bean.OwnerBean;
import com.fyj.fastbee.globel.GlobalVar;
import com.fyj.fastbee.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/6<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class FriendCirclePost extends FriendCircleCommentViewItem<ActivitysBean> {

    @Override
    protected int getLayout() {
        return R.layout.activity_friend_list_type_person;
    }

    @Override
    protected ViewHolder initView(View convertView) {
        setCommentRightPadding(SizeUtils.dp2px(getContext(), 8));
        PostViewHolder mPostViewHolder = new PostViewHolder();
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
        return mPostViewHolder;
    }

    @Override
    public void bindData(View convertView, final int pos, final ActivitysBean data) {

        PostViewHolder mPostViewHolder = (PostViewHolder) convertView.getTag();

        final OwnerBean owner = data.getOwner();
        String ownerId = data.getRefOwnerId();
        String updatedAt = data.getUpdatedAt() + "";
        String regName = owner.getRegName();
        String aliasName = owner.getAliasName();
        String companyName = owner.getCompanyName();
        String head_img = owner.getImgUrl();

        mPostViewHolder.tv_name.setText(regName);

        if (!StringUtil.isEmpty(aliasName) && !StringUtil.isEmpty(companyName)) {
            mPostViewHolder.tv_alias.setText(aliasName + "|" + companyName);
            mPostViewHolder.tv_alias.setVisibility(View.VISIBLE);
        } else if (!StringUtil.isEmpty(aliasName) && StringUtil.isEmpty(companyName)) {
            mPostViewHolder.tv_alias.setText(aliasName);
            mPostViewHolder.tv_alias.setVisibility(View.VISIBLE);
        } else if (StringUtil.isEmpty(aliasName) && !StringUtil.isEmpty(companyName)) {
            mPostViewHolder.tv_alias.setText(companyName);
            mPostViewHolder.tv_alias.setVisibility(View.VISIBLE);
        } else {
            mPostViewHolder.tv_alias.setVisibility(View.GONE);
        }

        if (ownerId.equals(GlobalVar.getGlobalUserInfo().getRefBusinessId())) {
            mPostViewHolder.tv_delete.setVisibility(View.VISIBLE);
            mPostViewHolder.tv_ding.setVisibility(View.VISIBLE);
        } else {
            mPostViewHolder.tv_delete.setVisibility(View.GONE);
            mPostViewHolder.tv_ding.setVisibility(View.GONE);
        }

        mPostViewHolder.tv_time.setText(TimeUtils.refreshUpdatedAtValue(updatedAt));

        if (mPostViewHolder.tv_like_num != null) {
            if (data.getIsLike().equals("0")) {
                mPostViewHolder.iv_like.setImageResource(R.drawable.icon_date_love_no);
            } else {
                mPostViewHolder.iv_like.setImageResource(R.drawable.icon_date_love_yes);
            }
            mPostViewHolder.tv_like_num.setText(data.getLikeNum()+"");
        }

        ImageLoaderHelper.displayHeadImage("http://attach.easylinking.net/" + head_img, mPostViewHolder.iv_person_head);

        List<String> urls = null;
        String ninePic;
        try {
            ninePic = data.getThumbnails();
            urls = Arrays.asList(ninePic.split(","));
            for (String url : urls) {
                url = "http://t2.easylinking.net:10010" + url;
            }
        } catch (Exception e) {
            ninePic = "";
        }

        if (!ninePic.equals("")) {
            mPostViewHolder.npv_photo.setVisibility(View.VISIBLE);
            mPostViewHolder.npv_photo.isPicClick(false).setUrls(urls).display();
        } else {
            mPostViewHolder.npv_photo.setVisibility(View.GONE);
        }

        String content = cutHtmlLength(data.getDesc());
        if (null != content && !content.isEmpty()) {
            mPostViewHolder.rt_comment.setText(getClickableHtml(content));
            mPostViewHolder.rt_comment.setVisibility(View.VISIBLE);
        } else {
            mPostViewHolder.rt_comment.setVisibility(View.GONE);
        }

        List<DateCommentList> commentList = data.getCommentList();

        if (commentList.size()>0){
            addComment(mPostViewHolder.ll_comment,pos,commentList);
            mPostViewHolder.ll_comment.setVisibility(View.VISIBLE);
        }else {
            mPostViewHolder.ll_comment.setVisibility(View.GONE);
        }

        mPostViewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (impl!=null){
                    impl.onNameOrHeadClick(owner);
                }
            }
        });

        mPostViewHolder.iv_person_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (impl!=null){
                    impl.onNameOrHeadClick(owner);
                }
            }
        });

        mPostViewHolder.iv_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (impl!=null){
                    impl.onControllerClick(v, pos,data);
                }
            }
        });
    }

    private class PostViewHolder implements ViewHolder {
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

                Intent wb1 = new Intent(getContext(), MainActivity.class);
                wb1.putExtra("Url", urlSpan.getURL());
                wb1.putExtra("Title", "");
                getContext().startActivity(wb1);
            }
        };
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
    }

}
