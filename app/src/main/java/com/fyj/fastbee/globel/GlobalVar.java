package com.fyj.fastbee.globel;

import com.fyj.easylinkingutils.utils.StringUtil;
import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.fastbee.BuildConfig;
import com.fyj.fastbee.bean.LoginBean;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/1<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class GlobalVar {

    /**
     * DEVELOP: 开发环境
     * TESTING: 测试环境
     * RELEASE: 集成环境
     */
    public enum SvrAddrType {
        DEVELOP,
        TESTING,
        RELEASE
    }

    /**
     * 初始化允许环境
     */
    private static SvrAddrType svrAddrType = BuildConfig.LOG_DEBUG ? SvrAddrType.DEVELOP : SvrAddrType.RELEASE;

    /**
     * 事例:切换运行环境
     */
    public static String HTPPADDR;

    /**
     * 改变运行环境
     *
     * @param svrType serType
     */
    public static void toggleSvrAddrType(SvrAddrType svrType) {
        svrAddrType = svrType;
        toggleSerAddr(svrAddrType);
    }

    static {
        toggleSerAddr(svrAddrType);
    }

    /**
     * 切换运行环境
     *
     * @param svrAddrType 运行环境
     */
    private static void toggleSerAddr(SvrAddrType svrAddrType) {
        switch (svrAddrType) {
            case TESTING:
                HTPPADDR = "TESTING";
                break;

            case DEVELOP:
                HTPPADDR = "DEVELOP";
                break;

            case RELEASE:
                HTPPADDR = "RELEASE";
                break;
        }
    }

    /**
     * 全局人物信息
     */
    private static LoginBean GLOBAL_USER_INFO;

    /**
     * 获得登录人员信息
     *
     * @return 信息
     */
    public static LoginBean getGlobalUserInfo() {

        if (GLOBAL_USER_INFO != null && !StringUtil.isEmpty(GLOBAL_USER_INFO.getRefBusinessId())) {
            return GLOBAL_USER_INFO;
        }
        setGlobalUserInfo(UserInfoManager.getLoginBean());
        XLog.e("getLoginBean");
        return GLOBAL_USER_INFO;
    }

    public static void setGlobalUserInfo(LoginBean globalUserInfo) {
        GLOBAL_USER_INFO = globalUserInfo;
    }

}
