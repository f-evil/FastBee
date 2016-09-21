package com.fyj.easylinkingkeyboard.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.fyj.easylinkingkeyboard.R;
import com.fyj.easylinkingkeyboard.adapter.EmotionAndFunctionPagerAdapter;
import com.fyj.easylinkingkeyboard.adapter.FunctionGridViewAdapter;
import com.fyj.easylinkingkeyboard.emotionkeyboardview.EmojiIndicatorView;
import com.fyj.easylinkingkeyboard.model.KeyboardFunctionBean;
import com.fyj.easylinkingkeyboard.utils.FunctionGlobalOnItemClickManagerUtils;
import com.fyj.easylinkingutils.utils.ScreenUtils;
import com.fyj.easylinkingutils.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/2<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class EmotionFunctionFragment extends EmotionBaseFragment {

    public static final String FUNCTION_CONTENT_KEY = "function_content_key";

    private EmotionAndFunctionPagerAdapter emotionPagerGvAdapter;
    private ViewPager vp_complate_emotion_layout;
    private EmojiIndicatorView ll_point_group;//表情面板对应的点列表
    private ArrayList<KeyboardFunctionBean> functionBeans;

    /**
     * 创建与Fragment对象关联的View视图时调用
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_complate_emotion, container, false);
        initView(rootView);
        initListener();
        return rootView;
    }

    /**
     * 初始化view控件
     */
    protected void initView(View rootView) {
        vp_complate_emotion_layout = (ViewPager) rootView.findViewById(R.id.vp_complate_emotion_layout);
        ll_point_group = (EmojiIndicatorView) rootView.findViewById(R.id.ll_point_group);
        //获取map的类型
        functionBeans = args.getParcelableArrayList(EmotionFunctionFragment.FUNCTION_CONTENT_KEY);
        initFunctiom();
    }

    private void initFunctiom() {

        // 获取屏幕宽度
        int screenWidth = ScreenUtils.getScreenWidth(getActivity());
        // item的间距
        int spacing = SizeUtils.dp2px(getActivity(), 12);
        // 动态计算item的宽度和高度
        int itemWidth = (screenWidth - spacing * 5) / 4;
        //动态计算gridview的总高度
        int gvHeight = itemWidth * 3 + spacing * 3;

        List<GridView> functionViews = new ArrayList<>();
        ArrayList<KeyboardFunctionBean> tempBeans = new ArrayList<>();

        for (KeyboardFunctionBean bean : functionBeans) {
            tempBeans.add(bean);
            if (tempBeans.size() == 8) {
                GridView functionGridView = createFunctionGridView(tempBeans, screenWidth, spacing, itemWidth, gvHeight);
                functionViews.add(functionGridView);
                tempBeans = new ArrayList<>();
            }
        }

        if (tempBeans.size() > 0) {
            GridView emotionGridView = createFunctionGridView(tempBeans, screenWidth, spacing, itemWidth, gvHeight);
            functionViews.add(emotionGridView);
            tempBeans = new ArrayList<>();
        }

        //初始化指示器
        ll_point_group.initIndicator(functionViews.size());
        // 将多个GridView添加显示到ViewPager中
        emotionPagerGvAdapter = new EmotionAndFunctionPagerAdapter(functionViews);
        vp_complate_emotion_layout.setAdapter(emotionPagerGvAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        vp_complate_emotion_layout.setLayoutParams(params);
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {

        vp_complate_emotion_layout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ll_point_group.playByStartPointToNext(oldPagerPos, position);
                oldPagerPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 创建显示表情的GridView
     */
    private GridView createFunctionGridView(List<KeyboardFunctionBean> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        // 创建GridView
        GridView gv = new GridView(getActivity());
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent);
        //设置7列
        gv.setNumColumns(4);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding*2);
        //设置GridView的宽高
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);

        FunctionGridViewAdapter adapter = new FunctionGridViewAdapter(getActivity(), emotionNames, itemWidth);
        gv.setAdapter(adapter);

        //设置全局点击事件
        gv.setOnItemClickListener(FunctionGlobalOnItemClickManagerUtils.getInstance().getOnItemClickListener());
        return gv;
    }
}
