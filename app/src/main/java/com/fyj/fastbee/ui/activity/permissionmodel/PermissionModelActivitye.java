package com.fyj.fastbee.ui.activity.permissionmodel;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.fyj.easylinkingutils.utils.ToastUtil;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PermissionModelActivitye extends BaseAppCompatActivity {

    @BindView(R.id.btn_camera)
    Button btnCamera;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, PermissionModelActivitye.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_permission_mode;
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

    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {

    }

    @OnClick({R.id.btn_camera})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                PermissionModelActivityePermissionsDispatcher.needsCameraActionWithCheck(this);
                break;
        }
    }

    private void showRationaleDialog(String messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionModelActivityePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    //允许了权限请求,或者api低于23,6.0以下
    @NeedsPermission(Manifest.permission.CAMERA)
    void needsCameraAction() {
        ToastUtil.makeText("打开了照相机");
    }

    //onClick调用时调用,阐述为什么要调用这个接口
    //api低于23,6.0以下直接调用@NeedsPermission标记方法
    @OnShowRationale(Manifest.permission.CAMERA)
    void showsCameraAction(PermissionRequest request) {
        showRationaleDialog("拍照需要使用到照相机", request);
    }

    //照相机被拒绝是调用
    @OnPermissionDenied(Manifest.permission.CAMERA)
    void deniedsCameraAction() {
        ToastUtil.makeText("照相机权限被拒绝,无法打开照相机");
    }

    //选中不在询问并拒绝后
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void neverAskCameraAction() {
        ToastUtil.makeText("照相机权限被拒绝,并不在询问");
    }
}
