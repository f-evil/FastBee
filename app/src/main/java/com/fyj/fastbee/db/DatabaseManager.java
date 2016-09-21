package com.fyj.fastbee.db;

import android.database.Cursor;

import com.fyj.easylinkingutils.utils.XLog;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class DatabaseManager {
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    private void DatabaseManager() {

    }


    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            initializeInstance(helper);
        }
        return instance;
    }

    public synchronized SQLiteDatabase getWritableDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            mDatabase = mDatabaseHelper.getWritableDatabase(DBCipherHelper.DB_PWD);
        }
        return mDatabase;
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            mDatabase = mDatabaseHelper.getReadableDatabase(DBCipherHelper.DB_PWD);
        }
        return mDatabase;
    }

    private synchronized void closeDatabase() {

        if (mOpenCounter.decrementAndGet() == 0) {
            mDatabase.close();
        }
    }

    /**
     * 批量处理(很多数据一次性写入时,推荐使用)
     *
     * @param sql        sql
     * @param paramsList 参数
     */
    public void batchOperator(String sql, List<Object[]> paramsList) {

        try {
            mDatabase = getReadableDatabase();

            mDatabase.beginTransaction();

            for (Object[] params : paramsList) {
                mDatabase.execSQL(sql, params);
            }

            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            XLog.e(e.toString());
        } finally {
            if (mDatabase != null) {
                mDatabase.endTransaction();
                closeDatabase();
            }
        }
    }

    /**
     * 该方法用于执行insert,delete,update操作
     *
     * @param sql    sql
     * @param params 参数
     */
    public void operator(String sql, Object[] params) {

        try {
            mDatabase = getReadableDatabase();
            if (params != null) {
                mDatabase.execSQL(sql, params);
            } else {
                mDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            XLog.e(e.toString());
        } finally {
            if (mDatabase != null) {
                closeDatabase();
            }
        }

    }

    /**
     * 查询函数
     *
     * @param sql     sql
     * @param params  params
     * @param columns columns
     * @return 一个Map表示一条数据, key为列名
     */
    public List<Map> query(String sql, String[] params, String[] columns) {
        List<Map> resList = new ArrayList<>();

        Cursor c = null;

        try {
            mDatabase = getReadableDatabase();
            c = mDatabase.rawQuery(sql, params);
            Map<String, Object> map = null;

            while (c.moveToNext()) {
                map = new HashMap<String, Object>();

                for (String colName : columns) {
                    map.put(colName, c.getString(c.getColumnIndex(colName)));
                }
                resList.add(map);
            }
        } catch (Exception e) {
            XLog.e(e.toString());
        } finally {
            if (c != null) {
                c.close();
            }

            if (mDatabase != null) {
                closeDatabase();
            }
        }

        return resList;
    }

    /**
     * 获得行数
     *
     * @param sql    sql
     * @param params param
     * @return 行数
     */
    public int getCount(String sql, String[] params) {
        int count = 0;
        Cursor c = null;
        try {
            mDatabase = getReadableDatabase();
            c = mDatabase.rawQuery(sql, params);

            count = c.getCount();

        } catch (Exception e) {
            XLog.e(e.toString());
        } finally {
            if (c != null) {
                c.close();
            }

            if (mDatabase != null) {
                closeDatabase();
            }
        }

        return count;
    }
}
