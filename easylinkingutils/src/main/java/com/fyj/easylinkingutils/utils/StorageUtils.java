package com.fyj.easylinkingutils.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Fyj on 2016/8/22.
 */
public class StorageUtils {

    private StorageUtils() {
    }

    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    public static File getCacheDirectory(Context context,String name) {
        return getCacheDirectory(context, name,true);
    }

    public static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        if (preferExternal && "mounted".equals(Environment.getExternalStorageState())) {
            appCacheDir = getExternalCacheDir(context);
        }

        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }

        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            XLog.w("Can\'t define system cache directory! \'%s\' will be used.", cacheDirPath);
            appCacheDir = new File(cacheDirPath);
        }

        return appCacheDir;
    }

    public static File getCacheDirectory(Context context, String name,boolean preferExternal) {
        File appCacheDir = null;
        if (preferExternal && "mounted".equals(Environment.getExternalStorageState())) {
            appCacheDir = getExternalCacheDir(context,name);
        }

        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }

        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/"+name;
            XLog.w("Can\'t define system cache directory! \'%s\' will be used.", cacheDirPath);
            appCacheDir = new File(cacheDirPath);
        }

        return appCacheDir;
    }

    public static File getIndividualCacheDirectory(Context context, String dirNmae) {
        File cacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(cacheDir, dirNmae);
        if (!individualCacheDir.exists() && !individualCacheDir.mkdir()) {
            individualCacheDir = cacheDir;
        }

        return individualCacheDir;
    }

    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }

        if (appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }

        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                XLog.w("Unable to create external cache directory");
                return null;
            }

            try {
                (new File(appCacheDir, ".nomedia")).createNewFile();
            } catch (IOException var4) {
                XLog.i("Can\'t create \".nomedia\" file in application external cache directory");
            }
        }

        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context,String name) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "name");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                XLog.w("Unable to create external cache directory");
                return null;
            }

            try {
                (new File(appCacheDir, ".nomedia")).createNewFile();
            } catch (IOException var4) {
                XLog.i("Can\'t create \".nomedia\" file in application external cache directory");
            }
        }

        return appCacheDir;
    }

//    private static boolean hasExternalStoragePermission(Context context) {
//        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
//        return perm == 0;
//    }

}
