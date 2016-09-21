package com.fyj.fastbee.db;

import com.fyj.easylinkingutils.utils.CharacterParser;
import com.fyj.easylinkingutils.utils.StringUtil;
import com.fyj.fastbee.bean.PersonInfoBean;
import com.fyj.fastbee.ui.application.FastBebApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/26<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class FriendshipDB {

    private DatabaseManager mDBM;
    private CharacterParser characterParser;

    public FriendshipDB() {
        this.mDBM = DatabaseManager.getInstance(new DBCipherHelper(FastBebApplication.getAppContext().get()));
        characterParser = CharacterParser.getInstance();
    }

    public boolean isExistUserId(String userId) {

        if (StringUtil.isEmpty(userId)) {
            return false;
        }

        String sql = "select * from tblFriendList where refBusinessId = ?";

        int count = mDBM.getCount(sql, new String[]{userId});

        return count > 0;

    }

    public void insertFriends(List<PersonInfoBean> data) {

        mDBM.operator("delete from tblFriendList", null);

        String insertSql = "insert into tblFriendList (" +
                "aliasName," +
                "companyName," +
                "groupId," +
                "groupName," +
                "easyId," +
                "imgUrl," +
                "refBusinessId," +
                "refCompanyId," +
                "regMobile," +
                "regName," +
                "userGrade) values(?,?,?,?,?,?,?,?,?,?,?)";

        List<Object[]> list = new ArrayList<>();

        for (PersonInfoBean bean : data) {
            list.add(new Object[]{
                    bean.getAliasName(),
                    bean.getCompanyName(),
                    bean.getGroupId(),
                    bean.getGroupName(),
                    bean.getId(),
                    bean.getImgUrl(),
                    bean.getRefBusinessId(),
                    bean.getRefCompanyId(),
                    bean.getRegMobile(),
                    bean.getRegName(),
                    bean.getUserGrade()});
        }

        mDBM.batchOperator(insertSql, list);
    }

    private void updateSinglePerson(PersonInfoBean data) {

        String updateSql = "update tblFriendList set " +
                "aliasName = ?," +
                "companyName = ?," +
                "groupId = ?," +
                "groupName = ?," +
                "easyId = ?," +
                "imgUrl = ?," +
                "refCompanyId = ?," +
                "regMobile = ?," +
                "regName = ?," +
                "userGrade = ? where refBusinessId=2043";

        if (!isExistUserId(data.getRefBusinessId())) {
            List<PersonInfoBean> list = new ArrayList<>();
            list.add(data);
            insertFriends(list);
        } else {
            mDBM.operator(updateSql, new String[]{
                    data.getAliasName(),
                    data.getCompanyName(),
                    data.getGroupId(),
                    data.getGroupName(),
                    data.getId(),
                    data.getImgUrl(),
                    data.getRefCompanyId(),
                    data.getRegMobile(),
                    data.getRegName(),
                    data.getUserGrade()
            });
        }
    }

    public List<PersonInfoBean> getAllFriendList() {

        String sql = "select * from tblFriendList";

        String[] columns = {
                "aliasName",
                "companyName",
                "groupId",
                "groupName",
                "easyId",
                "imgUrl",
                "refBusinessId",
                "refCompanyId",
                "regMobile",
                "regName",
                "userGrade"};

        List<PersonInfoBean> persons = new ArrayList<>();

        List<Map> list = mDBM.query(sql, null, columns);

        for (Map map : list) {
            PersonInfoBean bean = new PersonInfoBean();

            bean.setAliasName(String.valueOf(map.get("aliasName")));
            bean.setCompanyName(String.valueOf(map.get("companyName")));
            bean.setGroupId(String.valueOf(map.get("groupId")));
            bean.setGroupName(String.valueOf(map.get("groupName")));
            bean.setImgUrl(String.valueOf(map.get("imgUrl")));
            bean.setRefBusinessId(String.valueOf(map.get("refBusinessId")));
            bean.setRefCompanyId(String.valueOf(map.get("refCompanyId")));
            bean.setRegMobile(String.valueOf(map.get("regMobile")));
            bean.setRegName(String.valueOf(map.get("regName")));
            bean.setUserGrade(String.valueOf(map.get("userGrade")));

            //转拼音
            String pinyin = characterParser.getSelling(String.valueOf(map.get("regName")));
            String sortString = pinyin.substring(0, 1).toUpperCase();

            String pinyin2 = characterParser.getSelling(String.valueOf(map.get("regName")).charAt(0) + "");
            String sortString2 = pinyin2.toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                bean.setSortFirstName(sortString.toUpperCase());
                bean.setSortSecondName(sortString2.toUpperCase());
            } else {
                bean.setSortFirstName("#");
                bean.setSortSecondName("#");
            }
            persons.add(bean);
        }

        return persons;
    }
}
