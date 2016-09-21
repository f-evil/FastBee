package com.fyj.easylinkingutils.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * <pre>
 *     当前作者: EasyLinking
 *     修改时间: 2016/8/15
 *     修改次数: 2
 *     描述:  网络相关的工具类
 * </pre>
 */
public class NetworkUtils {

    private NetworkUtils() {
        throw new UnsupportedOperationException("You do not need to instantiate!");
    }

    public static final int NETWORK_WIFI = 1;    // wifi network
    public static final int NETWORK_4G = 4;    // "4G" networks
    public static final int NETWORK_3G = 3;    // "3G" networks
    public static final int NETWORK_2G = 2;    // "2G" networks
    public static final int NETWORK_UNKNOWN = 5;    // unknown network
    public static final int NETWORK_NO = -1;   // no network

    private static final int NETWORK_TYPE_GSM = 16;
    private static final int NETWORK_TYPE_TD_SCDMA = 17;
    private static final int NETWORK_TYPE_IWLAN = 18;

    /**
     * 获取活动网路信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * 判断网络是否可用
     * <p>需添加权限 android.permission.ACCESS_NETWORK_STATE</p>
     */
    public static boolean isAvailable(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    /**
     * 判断网络是否连接
     * <p>需添加权限 android.permission.ACCESS_NETWORK_STATE</p>
     *
     * @param context 上下文
     * @return true: 是<br>false: 否
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isConnected();
    }

    /**
     * 判断网络是否是4G
     * <p>需添加权限 android.permission.ACCESS_NETWORK_STATE</p>
     *
     * @param context 上下文
     * @return true: 是<br>false: 不是
     */
    public static boolean is4G(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
    }

    /**
     * 判断wifi是否连接状态
     * <p>需添加权限 android.permission.ACCESS_NETWORK_STATE</p>
     *
     * @param context 上下文
     * @return true: 连接<br>false: 未连接
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取当前的网络类型(WIFI,2G,3G,4G)
     * <p>需添加权限 android.permission.ACCESS_NETWORK_STATE</p>
     *
     * @param context 上下文
     * @return 网络类型
     * <ul>
     * <li>NETWORK_WIFI    = 1;</li>
     * <li>NETWORK_4G      = 4;</li>
     * <li>NETWORK_3G      = 3;</li>
     * <li>NETWORK_2G      = 2;</li>
     * <li>NETWORK_UNKNOWN = 5;</li>
     * <li>NETWORK_NO      = -1;</li>
     * </ul>
     */
    public static int getNetWorkType(Context context) {
        int netType = NETWORK_NO;
        NetworkInfo info = getActiveNetworkInfo(context);
        if (info != null && info.isAvailable()) {

            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                netType = NETWORK_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {

                    case NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        netType = NETWORK_2G;
                        break;

                    case NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        netType = NETWORK_3G;
                        break;

                    case NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        netType = NETWORK_4G;
                        break;
                    default:

                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            netType = NETWORK_3G;
                        } else {
                            netType = NETWORK_UNKNOWN;
                        }
                        break;
                }
            } else {
                netType = NETWORK_UNKNOWN;
            }
        }
        return netType;
    }

    /**
     * 获取当前的网络类型(WIFI,2G,3G,4G)
     * <p>依赖上面的方法</p>
     *
     * @param context 上下文
     * @return 网络类型名称
     * <ul>
     * <li>NETWORK_WIFI   </li>
     * <li>NETWORK_4G     </li>
     * <li>NETWORK_3G     </li>
     * <li>NETWORK_2G     </li>
     * <li>NETWORK_UNKNOWN</li>
     * <li>NETWORK_NO     </li>
     * </ul>
     */
    public static String getNetWorkTypeName(Context context) {
        switch (getNetWorkType(context)) {
            case NETWORK_WIFI:
                return "NETWORK_WIFI";
            case NETWORK_4G:
                return "NETWORK_4G";
            case NETWORK_3G:
                return "NETWORK_3G";
            case NETWORK_2G:
                return "NETWORK_2G";
            case NETWORK_NO:
                return "NETWORK_NO";
            default:
                return "NETWORK_UNKNOWN";
        }
    }
}