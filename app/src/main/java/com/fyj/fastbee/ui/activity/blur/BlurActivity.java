package com.fyj.fastbee.ui.activity.blur;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.utils.BlurBitmapUtils;

import butterknife.BindView;

public class BlurActivity extends BaseAppCompatActivity {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.rl_image)
    RelativeLayout rlImage;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, BlurActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_blur;
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

        ivImage.setBackgroundResource(R.drawable.pic6);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic6);

        Bitmap blurBitmap = BlurBitmapUtils.getBlurBitmap(this, bitmap, 15);

        BitmapDrawable drawable=new BitmapDrawable(blurBitmap);

        rlImage.setBackground(drawable);

    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {

    }

}
