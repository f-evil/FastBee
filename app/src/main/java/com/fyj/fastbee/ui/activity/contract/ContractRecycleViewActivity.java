package com.fyj.fastbee.ui.activity.contract;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.fyj.easylinkingutils.listener.OnRecyclerViewListener;
import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.easylinkingview.popwindow.menupopwindow.MenuPopWindow;
import com.fyj.easylinkingview.relationsliderbar.RelationshipSideBar;
import com.fyj.fastbee.R;
import com.fyj.fastbee.adapter.FriendshipRecycleAdapter;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.bean.PersonInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: <br>
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数:
 */
public class ContractRecycleViewActivity extends BaseAppCompatActivity implements RelationContract.View {

    @BindView(R.id.lv_friend)
    RecyclerView lvFriend;
    @BindView(R.id.tv_nofriend)
    TextView tvNofriend;
    @BindView(R.id.fl_sidebar)
    RelationshipSideBar flSidebar;
    @BindView(R.id.tv_dialog)
    TextView tvDialog;

    private FriendshipRecycleAdapter friendShipAdapter;

    private RelationPreImpl relationPre;
    private String[] itemSelectTitle;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, ContractRecycleViewActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_contract_recycleview;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {
        friendShipAdapter = new FriendshipRecycleAdapter(this, new ArrayList<PersonInfoBean>());
    }

    @Override
    protected void getDate() {

    }

    @Override
    protected void initView() {
        relationPre = new RelationPreImpl();
        relationPre.setVM(this, new RelationModel(relationPre));
        relationPre.onStart();

        flSidebar.setTextView(tvDialog);

        lvFriend.setHasFixedSize(true);

//        lvFriend.addItemDecoration(new LineDecoration(this, LineDecoration.VERTICAL_LIST));

        lvFriend.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        lvFriend.setLayoutManager(layoutManager);

    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {
        flSidebar.setOnTouchingLetterChangedListener(new RelationshipSideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (friendShipAdapter != null) {
                    int position = friendShipAdapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        lvFriend.smoothScrollToPosition(position);
                    }
                }
            }
        });

        friendShipAdapter.setOnRecyclerViewListener(new OnRecyclerViewListener<PersonInfoBean>() {
            @Override
            public void onClick(View view, PersonInfoBean bean, int position) {
                showItemSelectPop(bean);
            }
        });

    }

    @Override
    public void updateView(List<PersonInfoBean> data) {
        friendShipAdapter = new FriendshipRecycleAdapter(this, data);
        lvFriend.setAdapter(friendShipAdapter);
    }

    @Override
    public void getDateError(String msg) {
        ToastUtil.makeText(msg);
    }

    /**
     * item选择PopWindow
     *
     * @param user user
     */
    private void showItemSelectPop(final PersonInfoBean user) {

        if (itemSelectTitle == null) {
            itemSelectTitle = new String[]{
                    "发消息",
                    "视频通话",
                    "音频通话",
                    "拨打电话",
                    "删除",
                    "",
            };
        }
        itemSelectTitle[5] = String.format("查看%s的名片", user.getRegName());

        MenuPopWindow
                .FactoryMenuPopWindow(ContractRecycleViewActivity.this)
                .setItemName(itemSelectTitle)
                .setItemStyle(new MenuPopWindow.MenuStyle[]{null, null, null, null, MenuPopWindow.MenuStyle.DANGER, null})
                .setItemClickListener(new MenuPopWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ToastUtil.makeText(itemSelectTitle[position]);
                        switch (position) {
                            case 0:

                                break;
                            case 1:

                                break;
                            case 2:

                                break;
                            case 3:

                                Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + user.getRegMobile()));
                                startActivity(tel);
                                break;
                            case 4:

                                break;
                            case 5:

                                break;
                        }
                    }

                    @Override
                    public void onCancelClick(View view) {

                    }
                })
                .Builder()
                .showPopWindow(findViewById(R.id.lv_friend), Gravity.BOTTOM);

    }
}
