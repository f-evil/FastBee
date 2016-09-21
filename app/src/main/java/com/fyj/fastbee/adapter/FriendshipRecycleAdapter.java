package com.fyj.fastbee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.fyj.easylinkingimageloader.ImageLoaderHelper;
import com.fyj.easylinkingutils.listener.OnRecyclerViewListener;
import com.fyj.easylinkingview.roundimageview.RoundImageView;
import com.fyj.fastbee.R;
import com.fyj.fastbee.bean.PersonInfoBean;

import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/26<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class FriendshipRecycleAdapter extends RecyclerView.Adapter implements SectionIndexer {

    private Context mContext;
    private List<PersonInfoBean> list;
    private OnRecyclerViewListener<PersonInfoBean> onRecyclerViewListener;

    public FriendshipRecycleAdapter(Context context, List<PersonInfoBean> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.relationship_list_adapter_rolodex, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        convertView.setLayoutParams(lp);
        return new PersonViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PersonInfoBean bean = list.get(position);
        PersonViewHolder personViewHolder = (PersonViewHolder) holder;
        personViewHolder.manager.setText(bean.getAliasName());
        personViewHolder.name.setText(bean.getRegName());
        personViewHolder.position = position;
        personViewHolder.companyName.setText(bean.getCompanyName());
        ImageLoaderHelper.displayHeadImage("http://attach.easylinking.net/" + bean.getImgUrl(), personViewHolder.headImage);

        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            personViewHolder.mtag.setVisibility(View.VISIBLE);
            personViewHolder.mtag.setText(bean.getSortFirstName());
        } else {
            personViewHolder.mtag.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = list.get(i).getSortFirstName();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortFirstName().charAt(0);
    }

    public void updateListView(List<PersonInfoBean> filterDateList) {
        this.list = filterDateList;
        notifyDataSetChanged();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RoundImageView headImage;
        TextView name;
        TextView manager;
        TextView companyName;
        TextView mtag;
        int position;

        public PersonViewHolder(View convertView) {
            super(convertView);

            headImage = (RoundImageView) convertView.findViewById(R.id.ll_cimg_head);
            name = (TextView) convertView.findViewById(R.id.ll_ll_ll_tv_name);
            manager = (TextView) convertView.findViewById(R.id.ll_ll_ll_tv_position);
            companyName = (TextView) convertView.findViewById(R.id.ll_ll_tv_companyName);
            mtag = (TextView) convertView.findViewById(R.id.ll_tv_tag);

            convertView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onRecyclerViewListener != null) {
                onRecyclerViewListener.onClick(v, list.get(position), position);
            }
        }
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener<PersonInfoBean> onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }
}
