package com.fyj.fastbee.db;

import android.content.Context;

import com.fyj.easylinkingutils.utils.XLog;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class DBCipherHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String DB_NAME = "FastBeeDB";//数据库名字
    public static final String DB_PWD = "whoislcj";//数据库密码
    private static final int DB_VERSION = 2;   // 数据库版本

    /**
     * 用于储存配置
     * 包括登录用户信息
     * 配置信息
     */
    private static final String CREATE_SETTING = "CREATE table IF NOT EXISTS tblSettings"
            + " (_S_id INTEGER PRIMARY KEY AUTOINCREMENT, _Name TEXT, _Value TEXT)";

    private static final String CREATE_FRIENDLIST = "CREATE TABLE IF NOT EXISTS tblFriendList(" +
            "_f_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "aliasName text," +
            "companyName text," +
            "groupId text," +
            "groupName text," +
            "easyId text," +
            "imgUrl text," +
            "refBusinessId text," +
            "refCompanyId text," +
            "regMobile text," +
            "regName text," +
            "userGrade text)";

    private static final String CREATE_FRIENDLIST_IDX = "CREATE INDEX idx_friendlist_refbusinessid ON tblFriendList(refBusinessId);";

    public DBCipherHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        //不可忽略的 进行so库加载
        SQLiteDatabase.loadLibs(context);
    }

    public DBCipherHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_SETTING);
            db.execSQL(CREATE_FRIENDLIST);
            db.execSQL(CREATE_FRIENDLIST_IDX);
        } catch (Exception e) {
            XLog.e(TAG, e.getLocalizedMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(CREATE_FRIENDLIST);
                db.execSQL(CREATE_FRIENDLIST_IDX);
        }
    }

}
