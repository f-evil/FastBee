package com.fyj.easylinkingkeyboard.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fyj.easylinkingkeyboard.R;
import com.fyj.easylinkingkeyboard.adapter.EmotionGridViewAdapter;
import com.fyj.easylinkingkeyboard.adapter.FunctionGridViewAdapter;
import com.fyj.easylinkingkeyboard.adapter.HorizontalRecyclerviewAdapter;
import com.fyj.easylinkingkeyboard.adapter.NoHorizontalScrollerVPAdapter;
import com.fyj.easylinkingkeyboard.emotionkeyboardview.EmotionKeyboard;
import com.fyj.easylinkingkeyboard.emotionkeyboardview.NoHorizontalScrollerViewPager;
import com.fyj.easylinkingkeyboard.model.ImageModel;
import com.fyj.easylinkingkeyboard.model.KeyboardFunctionBean;
import com.fyj.easylinkingkeyboard.utils.EmotionGlobalOnItemClickManagerUtils;
import com.fyj.easylinkingkeyboard.utils.EmotionUtils;
import com.fyj.easylinkingkeyboard.utils.FunctionGlobalOnItemClickManagerUtils;
import com.fyj.easylinkingkeyboard.utils.KeyBoardSpanStringUtils;
import com.fyj.easylinkingutils.utils.KeyboardUtils;
import com.fyj.easylinkingutils.utils.SPUtils;
import com.fyj.easylinkingutils.utils.XLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zejian
 * Time  16/1/6 下午5:26
 * Email shinezejian@163.com
 * Description:表情主界面
 */
public class EmotionMainFragment extends EmotionBaseFragment {

    //键盘上显示的类型
    public static final String VIEW_SHOW_TYPE_KEY = "view_show_type_key";
    //消息页
    public static final int VIEW_SHOW_TYPE_PARAM_MSG = 0x000000001;
    //Web页
    public static final int VIEW_SHOW_TYPE_PARAM_WEB = 0x000000002;
    //朋友圈页
    public static final int VIEW_SHOW_TYPE_PARAM_FRIEND_CIRCLE = 0x000000003;

    //是否绑定当前Bar的编辑框的flag
    public static final String BIND_TO_EDITTEXT = "bind_to_edittext";

    //当前被选中底部tab
    private static final String CURRENT_POSITION_FLAG = "CURRENT_POSITION_FLAG";
    private int CurrentPosition = 0;
    //底部水平tab
    private RecyclerView recyclerview_horizontal;
    private HorizontalRecyclerviewAdapter horizontalRecyclerviewAdapter;
    //表情面板
    private EmotionKeyboard mEmotionKeyboard;

    private EditText bar_edit_text;
    private ImageView iv_function;
    private LinearLayout rl_editbar_bg;
    private ImageView emotion_button;
    private TextView tv_emotion_send;
    private ImageView iv_vedio_toggle;
    private ImageView iv_vedio_press;
    private EditText mEditView;

    //需要绑定的内容view
    private View contentView;

    //不可横向滚动的ViewPager
    private NoHorizontalScrollerViewPager mEmotionViewPager;
    private NoHorizontalScrollerViewPager mFunctionPager;

    //是否绑定当前Bar的编辑框,默认true,即绑定。
    //false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
    private boolean isBindToBarEditText = true;

    //布局类型
    private int showType = VIEW_SHOW_TYPE_PARAM_MSG;

    private List<Fragment> mEmotionFragments = new ArrayList<>();

    private List<Fragment> mFunctionFragments = new ArrayList<>();

    private ArrayList<KeyboardFunctionBean> functionBeans = new ArrayList<>();

    private OnEmotionKeyboardEventsListener mOnEmotionKeyboardEventsListener;

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
        View rootView = inflater.inflate(R.layout.fragment_main_emotion, container, false);
        showType = args.getInt(EmotionMainFragment.VIEW_SHOW_TYPE_KEY);
        //获取判断绑定对象的参数
        isBindToBarEditText = args.getBoolean(EmotionMainFragment.BIND_TO_EDITTEXT);
        initView(rootView);
        mEmotionKeyboard = EmotionKeyboard.with(getActivity())
                .setEmotionView(rootView.findViewById(R.id.ll_emotion_layout))//绑定表情面板
                .setFunctionView(rootView.findViewById(R.id.ll_function_layout))//绑定功能面板
                .setVedioView(rootView.findViewById(R.id.ll_vedio_layout))//绑定录音面板
                .bindToContent(contentView)//绑定内容view
                .bindToEditText(!isBindToBarEditText ? ((EditText) contentView) : ((EditText) rootView.findViewById(R.id.bar_edit_text)))//判断绑定那种EditView
                .bindToEmotionButton(rootView.findViewById(R.id.emotion_button))//绑定表情按钮
                .bindToFunctionButton(rootView.findViewById(R.id.iv_function))//绑定功能按钮
                .bindToVedioButton(rootView.findViewById(R.id.iv_vedio))//绑定功能按钮
                .build();
        initListener();
        initDatas();
        //创建全局监听
        EmotionGlobalOnItemClickManagerUtils globalOnItemClickManager = EmotionGlobalOnItemClickManagerUtils.getInstance();
        FunctionGlobalOnItemClickManagerUtils functionGlobalOnItemClickManagerUtils = FunctionGlobalOnItemClickManagerUtils.getInstance();

        mEditView = null;

        if (isBindToBarEditText) {
            mEditView = bar_edit_text;
        } else {
            mEditView = (EditText) contentView;
            mEmotionKeyboard.bindToEditText((EditText) contentView);
        }

        mEditView.setImeOptions(EditorInfo.IME_ACTION_SEND);
        mEditView.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        mEditView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1500)});
        mEditView.setMaxLines(3);

        mEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (mOnEmotionKeyboardEventsListener != null) {
                        mOnEmotionKeyboardEventsListener.onEmotionSendEventKey(mEditView.getText().toString());
                        mEditView.setText("");
                    }
                }
                return false;
            }
        });

        globalOnItemClickManager.setOnEmotionClickListener(new EmotionGlobalOnItemClickManagerUtils.OnEmotionClickListener() {
            @Override
            public void onEmotionItemClick(AdapterView<?> parent, int emotion_map_type, int position) {

                try {

                    Object itemAdapter = parent.getAdapter();

                    if (itemAdapter instanceof EmotionGridViewAdapter) {
                        // 点击的是表情
                        EmotionGridViewAdapter emotionGvAdapter = (EmotionGridViewAdapter) itemAdapter;

                        if (position == emotionGvAdapter.getCount() - 1) {
                            // 如果点击了最后一个回退按钮,则调用删除键事件
                            mEditView.dispatchKeyEvent(new KeyEvent(
                                    KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                        } else {
                            // 如果点击了表情,则添加到输入框中
                            String emotionName = emotionGvAdapter.getItem(position);

                            // 获取当前光标位置,在指定位置上添加表情图片文本
                            int curPosition = mEditView.getSelectionStart();
                            StringBuilder sb = new StringBuilder(mEditView.getText().toString());
                            sb.insert(curPosition, emotionName);

                            // 特殊文字处理,将表情等转换一下
                            mEditView.setText(KeyBoardSpanStringUtils.getEmotionContent(emotion_map_type,
                                    getActivity(), mEditView, sb.toString()));

                            // 将光标设置到新增完表情的右侧
                            mEditView.setSelection(curPosition + emotionName.length());
                        }

                    }
                } catch (Exception e) {
                    XLog.e("EmotionMainFragment.setOnEmotionClickListener", e.getLocalizedMessage());
                }
            }
        });

        functionGlobalOnItemClickManagerUtils.setOnFunctiomClickListener(new FunctionGlobalOnItemClickManagerUtils.OnFunctiomClickListener() {
            @Override
            public void onEmotionItemClick(AdapterView<?> parent, int position) {
                try {

                    Object itemAdapter = parent.getAdapter();

                    if (itemAdapter instanceof FunctionGridViewAdapter) {
                        // 点击的是表情
                        FunctionGridViewAdapter functionGvAdapter = (FunctionGridViewAdapter) itemAdapter;

                        KeyboardFunctionBean item = functionGvAdapter.getItem(position);

                        if (mOnEmotionKeyboardEventsListener != null) {
                            mOnEmotionKeyboardEventsListener.onFunctionEventKey(item);
                        }

                    }
                } catch (Exception e) {
                    XLog.e("EmotionMainFragment.setOnFunctiomClickListener", e.getLocalizedMessage());
                }
            }
        });

        return rootView;
    }

    /**
     * 绑定内容view
     *
     * @param contentView
     * @return
     */
    public void bindToContentView(View contentView) {
        this.contentView = contentView;
    }

    /**
     * 绑定功能面板
     *
     * @param functionBeans
     * @return
     */
    public void bindToFunctionView(ArrayList<KeyboardFunctionBean> functionBeans) {
        this.functionBeans = functionBeans;
    }

    /**
     * 初始化view控件
     */
    protected void initView(View rootView) {
        mEmotionViewPager = (NoHorizontalScrollerViewPager) rootView.findViewById(R.id.vp_emotionview_layout);
        mFunctionPager = (NoHorizontalScrollerViewPager) rootView.findViewById(R.id.vp_function_layout);
        recyclerview_horizontal = (RecyclerView) rootView.findViewById(R.id.recyclerview_horizontal);
        bar_edit_text = (EditText) rootView.findViewById(R.id.bar_edit_text);
        tv_emotion_send = (TextView) rootView.findViewById(R.id.tv_emotion_send);
        emotion_button = (ImageView) rootView.findViewById(R.id.emotion_button);
        iv_function = (ImageView) rootView.findViewById(R.id.iv_function);
        iv_vedio_press = (ImageView) rootView.findViewById(R.id.iv_vedio_press);
        iv_vedio_toggle = (ImageView) rootView.findViewById(R.id.iv_vedio);
        rl_editbar_bg = (LinearLayout) rootView.findViewById(R.id.rl_editbar_bg);
        rl_editbar_bg.setBackgroundResource(R.drawable.shape_bg_reply_edittext);

        switch (showType) {
            case VIEW_SHOW_TYPE_PARAM_MSG:
                emotion_button.setVisibility(View.VISIBLE);
                iv_function.setVisibility(View.VISIBLE);
                iv_vedio_toggle.setVisibility(View.VISIBLE);
                break;

            case VIEW_SHOW_TYPE_PARAM_WEB:
                emotion_button.setVisibility(View.GONE);
                iv_function.setVisibility(View.GONE);
                iv_vedio_toggle.setVisibility(View.GONE);
                break;

            case VIEW_SHOW_TYPE_PARAM_FRIEND_CIRCLE:
                emotion_button.setVisibility(View.VISIBLE);
                iv_function.setVisibility(View.GONE);
                iv_vedio_toggle.setVisibility(View.GONE);
                break;

            default:
                emotion_button.setVisibility(View.GONE);
                iv_function.setVisibility(View.GONE);
                iv_vedio_toggle.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {

        tv_emotion_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnEmotionKeyboardEventsListener != null) {
                    mOnEmotionKeyboardEventsListener.onEmotionSendEventKey(mEditView.getText().toString());
                    mEditView.setText("");
                }
            }
        });

        tv_emotion_send.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnEmotionKeyboardEventsListener != null) {
                    mOnEmotionKeyboardEventsListener.onEmotionSendEventKey(mEditView.getText().toString());
                    mEditView.setText("");
                }
                return false;
            }
        });

        iv_vedio_press.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mOnEmotionKeyboardEventsListener != null) {
                    mOnEmotionKeyboardEventsListener.onSoundEventKey(event);
                }
                return false;
            }
        });
    }

    /**
     * 初始化各种面板
     */
    protected void initDatas() {
        replaceEmotionFragment();
        initEmotionIndicatior();
        initFunctionBoard();
    }

    /**
     * 初始化功能面板
     */
    private void initFunctionBoard() {
        FragmentFactory factory = FragmentFactory.getSingleFactoryInstance();
        //创建修改实例
        EmotionFunctionFragment f1 = (EmotionFunctionFragment) factory.getFunctionFragment(functionBeans);
        mFunctionFragments.add(f1);

        NoHorizontalScrollerVPAdapter adapter = new NoHorizontalScrollerVPAdapter(getActivity().getSupportFragmentManager(), mFunctionFragments);
        mFunctionPager.setAdapter(adapter);
    }

    /**
     * 表情面板指示器
     */
    private void initEmotionIndicatior() {
        List<ImageModel> list = new ArrayList<>();
        for (int i = 0; i < mEmotionFragments.size(); i++) {
            if (i == 0) {
                ImageModel model1 = new ImageModel();
                model1.icon = getResources().getDrawable(R.drawable.ic_emotion);
                model1.flag = "经典笑脸";
                model1.isSelected = true;
                list.add(model1);
            } else {
                ImageModel model = new ImageModel();
                model.icon = getResources().getDrawable(R.drawable.ic_plus);
                model.flag = "其他笑脸" + i;
                model.isSelected = false;
                list.add(model);
            }
        }

        //记录底部默认选中第一个
        CurrentPosition = 0;
        SPUtils.putInt(getActivity(), CURRENT_POSITION_FLAG, CurrentPosition);

        //底部tab
        horizontalRecyclerviewAdapter = new HorizontalRecyclerviewAdapter(getActivity(), list);
        recyclerview_horizontal.setHasFixedSize(true);//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        recyclerview_horizontal.setAdapter(horizontalRecyclerviewAdapter);
        recyclerview_horizontal.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
        //初始化recyclerview_horizontal监听器
        horizontalRecyclerviewAdapter.setOnClickItemListener(new HorizontalRecyclerviewAdapter.OnClickItemListener() {
            @Override
            public void onItemClick(View view, int position, List<ImageModel> datas) {
                //获取先前被点击tab
                int oldPosition = SPUtils.getInt(getActivity(), CURRENT_POSITION_FLAG, 0);
                //修改背景颜色的标记
                datas.get(oldPosition).isSelected = false;
                //记录当前被选中tab下标
                CurrentPosition = position;
                datas.get(CurrentPosition).isSelected = true;
                SPUtils.putInt(getActivity(), CURRENT_POSITION_FLAG, CurrentPosition);
                //通知更新，这里我们选择性更新就行了
                horizontalRecyclerviewAdapter.notifyItemChanged(oldPosition);
                horizontalRecyclerviewAdapter.notifyItemChanged(CurrentPosition);
                //viewpager界面切换
                mEmotionViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onItemLongClick(View view, int position, List<ImageModel> datas) {
            }
        });
    }

    /**
     * 表情面板
     */
    private void replaceEmotionFragment() {
        //创建fragment的工厂类
        FragmentFactory factory = FragmentFactory.getSingleFactoryInstance();
        //创建修改实例
        EmotiomComplateFragment f1 = (EmotiomComplateFragment) factory.getFragment(EmotionUtils.EMOTION_CLASSIC_TYPE);
        mEmotionFragments.add(f1);

//        Bundle b = null;
//        for (int i = 0; i < 7; i++) {
//            b = new Bundle();
//            b.putString("Interge", "Fragment-" + i);
//            Fragment1 fg = Fragment1.newInstance(Fragment1.class, b);
//            mEmotionFragments.add(fg);
//        }

        NoHorizontalScrollerVPAdapter adapter = new NoHorizontalScrollerVPAdapter(getActivity().getSupportFragmentManager(), mEmotionFragments);
        mEmotionViewPager.setAdapter(adapter);
    }


    /**
     * 是否拦截返回键操作，如果此时表情布局未隐藏，先隐藏表情布局
     *
     * @return true则隐藏表情布局，拦截返回键操作
     * false 则不拦截返回键操作
     */
    public boolean isInterceptBackPress() {
        return mEmotionKeyboard.interceptBackPress();
    }

    public void showKeyBorad(){
        KeyboardUtils.showSoftInput(getActivity(),mEditView);
    }

    public void hideKeyBorad(){
//        KeyboardUtils.hideSoftInput(getActivity(),mEditView);
        mEmotionKeyboard.interceptBackPress();
    }

    public interface OnEmotionKeyboardEventsListener {
        void onEmotionSendEventKey(String msg);

        void onFunctionEventKey(KeyboardFunctionBean bean);

        void onSoundEventKey(MotionEvent event);
    }

    public void setOnEmotionKeyboardEventsListener(OnEmotionKeyboardEventsListener mOnEmotionKeyboardEventsListener) {
        this.mOnEmotionKeyboardEventsListener = mOnEmotionKeyboardEventsListener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EmotionGlobalOnItemClickManagerUtils.getInstance().destoryUtils();
        FunctionGlobalOnItemClickManagerUtils.getInstance().destoryUtils();
    }
}


