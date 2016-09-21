package com.fyj.fastbee.ui.activity.contract;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fyj.easylinkingutils.utils.ScreenUtils;
import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.easylinkingview.popwindow.menupopwindow.MenuPopWindow;
import com.fyj.easylinkingview.relationsliderbar.RelationshipSideBar;
import com.fyj.fastbee.R;
import com.fyj.fastbee.adapter.FriendShipAdapter;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.bean.PersonInfoBean;

import java.util.List;

import butterknife.BindView;

/**
 * 描述: <br>
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数:
 */
public class ContractActivity extends BaseAppCompatActivity implements RelationContract.View {

    @BindView(R.id.lv_friend)
    ListView lvFriend;
    @BindView(R.id.tv_nofriend)
    TextView tvNofriend;
    @BindView(R.id.fl_sidebar)
    RelationshipSideBar flSidebar;
    @BindView(R.id.tv_dialog)
    TextView tvDialog;

    private FriendShipAdapter friendShipAdapter;

    private RelationPreImpl relationPre;
    private String[] itemSelectTitle;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, ContractActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_contract;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {

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
                        lvFriend.setSelection(position);
                    }
                }
            }
        });

        lvFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showItemSelectPop((PersonInfoBean) parent.getAdapter().getItem(position));
            }
        });

        lvFriend.setOnScrollListener(new AbsListView.OnScrollListener() {

            private SparseArray recordSp = new SparseArray(0);
            private int mCurrentfirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                try {
                    if (friendShipAdapter != null && friendShipAdapter.getCount() > 0) {

                        mCurrentfirstVisibleItem = firstVisibleItem;
                        View firstView = view.getChildAt(0);
                        if (null != firstView) {
                            ItemRecod itemRecord = (ItemRecod) recordSp.get(firstVisibleItem);
                            if (null == itemRecord) {
                                itemRecord = new ItemRecod();
                            }
                            itemRecord.height = firstView.getHeight();
                            itemRecord.top = firstView.getTop();
                            recordSp.append(firstVisibleItem, itemRecord);
                            int h = getScrollY();//滚动距离
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private int getScrollY() {
                int height = 0;
                for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
                    ItemRecod itemRecod = (ItemRecod) recordSp.get(i);
                    height += itemRecod.height;
                }
                ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);
                if (null == itemRecod) {
                    itemRecod = new ItemRecod();
                }
                return height - itemRecod.top;
            }


            class ItemRecod {
                int height = 0;
                int top = 0;
            }
        });


    }

    @Override
    public void updateView(List<PersonInfoBean> data) {
        friendShipAdapter = new FriendShipAdapter(this, data);
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
                .FactoryMenuPopWindow(ContractActivity.this)
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
