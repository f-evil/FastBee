package com.fyj.fastbee.db;

import com.fyj.fastbee.bean.LoginBean;
import com.fyj.fastbee.ui.application.FastBebApplication;
import com.fyj.fastbee.utils.JsonByGsonUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class UserAndSettingsDB {

    private DatabaseManager mDBM;

    public UserAndSettingsDB() {
        this.mDBM = DatabaseManager.getInstance(new DBCipherHelper(FastBebApplication.getAppContext().get()));
    }

    private void deleteAllSettings() {

        String sql = "delete from tblSettings";

        mDBM.operator(sql, null);
    }

    public void insertSettings(String _Name, String _Value) {

        String sql = "insert into tblSettings (_Name,_Value) values(?,?)";

        String temp = selectValueByName(_Name);

        if (temp == null) {
            mDBM.operator(sql, new String[]{_Name, _Value});
        } else {
            sql = "update tblSettings set _Value = ? where _Name = ?";
            mDBM.operator(sql, new String[]{_Name, _Value});
        }
    }

    public void insertLoginSettings(LoginBean bean) {

        String sql = "insert into tblSettings (_Name,_Value) values(?,?)";

        deleteAllSettings();

        Gson gson = new Gson();
        String beanJson = gson.toJson(bean);

        Map<String, Object> objectMap = JsonByGsonUtils.toMap(beanJson);

        List<Object[]> list = new ArrayList<>();

        Iterator it = objectMap.keySet().iterator();
        while (it.hasNext()) {

            String key;
            String value;

            key = it.next().toString();

            value = objectMap.get(key).toString();

            list.add(new String[]{key, value});

        }

        mDBM.batchOperator(sql, list);
    }

    public List<Map> getList() {

        String sql = "select * from tblSettings";
        String[] columns = {"_Name", "_Value"};
        return mDBM.query(sql, new String[]{}, columns);
    }

    public String selectValueByName(String _Name) {
        String _Value = null;
        List<Map> queryList = mDBM.query("select * from tblSettings where _Name=?", new String[]{_Name}, new String[]{"_Name", "_Value"});

        if (queryList != null && queryList.size() > 0) {
            _Value = (String) queryList.get(0).get("_Value");
        }

        return _Value;
    }
}
