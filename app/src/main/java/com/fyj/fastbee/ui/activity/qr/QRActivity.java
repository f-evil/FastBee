package com.fyj.fastbee.ui.activity.qr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dtr.zbar.build.ZBarDecoder;
import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.view.qrscaner.CameraManager;
import com.fyj.fastbee.view.qrscaner.CameraPreview;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.OnClick;

public class QRActivity extends BaseAppCompatActivity {


    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    private CameraManager mCameraManager;

    @BindView(R.id.capture_preview)
    FrameLayout capturePreview;
    @BindView(R.id.capture_mask_top)
    ImageView captureMaskTop;
    @BindView(R.id.capture_scan_line)
    ImageView captureScanLine;
    @BindView(R.id.capture_crop_view)
    RelativeLayout captureCropView;
    @BindView(R.id.capture_mask_bottom)
    ImageView captureMaskBottom;
    @BindView(R.id.capture_mask_left)
    ImageView captureMaskLeft;
    @BindView(R.id.capture_mask_right)
    ImageView captureMaskRight;
    @BindView(R.id.capture_container)
    RelativeLayout captureContainer;
    @BindView(R.id.capture_restart_scan)
    Button captureRestartScan;
    @BindView(R.id.rl_qr)
    RelativeLayout rlQr;

    private Rect mCropRect = null;
    private boolean barcodeScanned = false;
    private boolean previewing = true;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, QRActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_qr;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void finish() {
        releaseCamera();
        super.finish();
    }

    @Override
    protected void onDestroy() {
        releaseCamera();
        super.onDestroy();
    }

    private void releaseCamera() {
        try {
            if (mCamera != null) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.release();
                mCamera = null;
            }

            if (captureScanLine != null) {
                captureScanLine.clearAnimation();
            }
        } catch (Exception e) {

        }

    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void getDate() {

    }

    @Override
    protected void initView() {
        autoFocusHandler = new Handler();
        mCameraManager = new CameraManager(this);
        mCameraManager.setCameraOpenListener(new CameraManager.OnCameraOpenListener() {
            @Override
            public void result(boolean result) {
                if (!result) {
                    ToastUtil.makeText("无照相机权限");
                    QRActivity.this.finish();
                }
            }
        });
        try {
            mCameraManager.openDriver();

            mCamera = mCameraManager.getCamera();
            mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
            capturePreview.addView(mPreview);
        } catch (Error e) {
            e.printStackTrace();
            ToastUtil.makeText("无照相机权限");
            QRActivity.this.finish();
        }

        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation
                .RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.85f);
        animation.setDuration(3000);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.REVERSE);
        captureScanLine.startAnimation(animation);
    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {

    }

    @OnClick(R.id.capture_restart_scan)
    public void onClick() {
        if (barcodeScanned) {
            barcodeScanned = false;
            mCamera.setPreviewCallback(previewCb);
            mCamera.startPreview();
            previewing = true;
            mCamera.autoFocus(autoFocusCB);
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Size size = camera.getParameters().getPreviewSize();

            // 这里需要将获取的data翻转一下，因为相机默认拿的的横屏的数据
            byte[] rotatedData = new byte[data.length];
            for (int y = 0; y < size.height; y++) {
                for (int x = 0; x < size.width; x++)
                    rotatedData[x * size.height + size.height - y - 1] = data[x + y * size.width];
            }

            // 宽高也要调整
            int tmp = size.width;
            size.width = size.height;
            size.height = tmp;

            initCrop();
            ZBarDecoder zBarDecoder = new ZBarDecoder();
            String result = zBarDecoder.decodeCrop(rotatedData, size.width, size.height, mCropRect.left, mCropRect
                    .top, mCropRect.width(), mCropRect.height());

            if (!TextUtils.isEmpty(result)) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                barcodeScanned = true;
                judgeQRResult(result);
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = mCameraManager.getCameraResolution().y;
        int cameraHeight = mCameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        captureCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = captureCropView.getWidth();
        int cropHeight = captureCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = captureContainer.getWidth();
        int containerHeight = captureContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void judgeQRResult(String result) {

        ToastUtil.makeText(result);

        if (result.startsWith("http")) {
            // TODO: 2016/8/31 网页
            return;
        }

        if (!(result.startsWith("X"))) {
            return;
        }

        result = contentDecode(result);
        if (!(result.matches("^[0-9]+$") && result.length() > 2)) {
            return;
        }

        // TODO: 2016/8/31 特殊

    }

    public static String contentDecode(String content) {
        content = content.substring(1);
        content = new String(Base64.decode(content, Base64.DEFAULT));
        content = content.substring(content.indexOf('-') + 1);
        return content;
    }
}
