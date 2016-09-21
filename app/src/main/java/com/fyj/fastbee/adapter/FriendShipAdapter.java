package com.fyj.fastbee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.fyj.easylinkingimageloader.ImageLoaderHelper;
import com.fyj.easylinkingview.roundimageview.RoundImageView;
import com.fyj.fastbee.R;
import com.fyj.fastbee.bean.PersonInfoBean;
import com.fyj.fastbee.ui.activity.contract.ContractActivity;

import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/26<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class FriendShipAdapter extends BaseAdapter implements SectionIndexer {

    private Context mContext;
    private List<PersonInfoBean> list;

    public FriendShipAdapter(Context context, List<PersonInfoBean> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.relationship_list_adapter_rolodex, null);
            viewHolder.headImage = (RoundImageView) convertView.findViewById(R.id.ll_cimg_head);
            viewHolder.name = (TextView) convertView.findViewById(R.id.ll_ll_ll_tv_name);
            viewHolder.position = (TextView) convertView.findViewById(R.id.ll_ll_ll_tv_position);
            viewHolder.companyName = (TextView) convertView.findViewById(R.id.ll_ll_tv_companyName);
            viewHolder.mtag = (TextView) convertView.findViewById(R.id.ll_tv_tag);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final PersonInfoBean chatMember = (PersonInfoBean) getItem(position);
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.mtag.setVisibility(View.VISIBLE);
            viewHolder.mtag.setText(chatMember.getSortFirstName());
        } else {
            viewHolder.mtag.setVisibility(View.GONE);
        }
        viewHolder.name.setText(chatMember.getRegName());
        viewHolder.position.setText(chatMember.getAliasName());
        viewHolder.companyName.setText(chatMember.getCompanyName());
        ImageLoaderHelper.displayHeadImage("http://attach.easylinking.net/"+chatMember.getImgUrl(), viewHolder.headImage);
        return convertView;
    }

    class ViewHolder {
        RoundImageView headImage;
        TextView name;
        TextView position;
        TextView companyName;
        TextView mtag;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
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
}
