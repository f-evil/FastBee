package com.fyj.fastbee.globel;

import com.fyj.fastbee.bean.LoginBean;
import com.fyj.fastbee.db.UserAndSettingsDB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/1<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class UserInfoManager {

    public static LoginBean getLoginBean() {

        UserAndSettingsDB db = new UserAndSettingsDB();

        List<Map> list = db.getList();

        LoginBean bean = new LoginBean();

        Map<String, String> tempMap = new HashMap<>();

        if (list.size() > 0) {

            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);
                tempMap.put(String.valueOf(map.get("_Name")), String.valueOf(map.get("_Value")));

            }

            bean.setAliasName(String.valueOf(tempMap.get("AliasName")));
            bean.setCompanyName(String.valueOf(tempMap.get("CompanyId")));
            bean.setCompanyId(String.valueOf(tempMap.get("CompanyName")));
            bean.setId(String.valueOf(tempMap.get("Id")));
            bean.setImgUrl(String.valueOf(tempMap.get("ImgUrl")));
            bean.setManagerId(String.valueOf(tempMap.get("ManagerId")));
            bean.setPassWordSalt(String.valueOf(tempMap.get("PassWordSalt")));
            bean.setRefBusinessId(String.valueOf(tempMap.get("RefBusinessId")));
            bean.setRefDefaultBoardId(String.valueOf(tempMap.get("RefDefaultBoardId")));
            bean.setRegName(String.valueOf(tempMap.get("RegName")));
            bean.setUserGrade(String.valueOf(tempMap.get("UserGrade")));
            bean.setCommunityNames(String.valueOf(tempMap.get("communityNames")));
            bean.setRefBoardIds(String.valueOf(tempMap.get("refBoardIds")));
            bean.setRefCommunityIds(String.valueOf(tempMap.get("refCommunityIds")));

        }

        return bean;
    }
}
