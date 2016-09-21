package com.fyj.easylinkingutils.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

/**
 * <pre>
 *     当前作者: EasyLinking
 *     修改时间: 2016/8/15
 *     修改次数: 2
 *     描述:  未归类的工具类
 * </pre>
 */
public class UnclassifiedUtils {

    private UnclassifiedUtils() {
        throw new UnsupportedOperationException("You do not need to instantiate!");
    }

    /**
     * 获取服务是否开启
     *
     * @param context   上下文
     * @param className 完整包名的服务类名
     * @return true: 是<br>false: 否
     */
    public static boolean isRunningService(Context context, String className) {
        // 进程的管理者,活动的管理者
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取正在运行的服务，最多获取1000个
        List<RunningServiceInfo> runningServices = activityManager.getRunningServices(1000);
        // 遍历集合
        for (RunningServiceInfo runningServiceInfo : runningServices) {
            ComponentName service = runningServiceInfo.service;
            if (className.equals(service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}