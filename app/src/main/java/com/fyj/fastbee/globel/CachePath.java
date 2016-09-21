package com.fyj.fastbee.globel;

import android.content.Context;

import com.fyj.easylinkingutils.utils.SDCardUtils;
import com.fyj.easylinkingutils.utils.StorageUtils;
import com.fyj.easylinkingutils.utils.StringUtil;

import java.io.File;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/8<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class CachePath {

    private static String dirName = "FastBee";
    private static final String CACHE_EXTRAL_FILE_NAME_IAMGE = "image";
    private static final String CACHE_EXTRAL_FILE_NAME_RADIO = "radio";
    private static final String CACHE_EXTRAL_FILE_NAME_MEDIA = "media";
    private static final String CACHE_EXTRAL_FILE_NAME_IMAGE_COMPRESS = "imgcompress";
    private static final String CACHE_EXTRAL_FILE_NAME_CRASH = "crash";

    public static void initDirName(String name) {
        if (!StringUtil.isEmpty(name)) {
            dirName = name;
        }
    }

    private static String CreateCachePath(Context context, String extralPath, String name) {

        String path = "";

        if (SDCardUtils.isSDCardEnable()) {
            String tempPath = (StringUtil.isEmpty(extralPath) ? SDCardUtils.getSDCardPath() : extralPath) + name;
            File dir = new File(tempPath);
            if (!dir.exists()) {
                boolean mkdir = dir.mkdirs();
                if (mkdir) {
                    path = tempPath;
                } else {
                    path = StorageUtils.getCacheDirectory(context, name).getAbsolutePath();
                }
            } else {
                path = tempPath;
            }
        } else {
            path = StorageUtils.getCacheDirectory(context, name).getAbsolutePath();
        }

        path = path + File.separator;

        return path;
    }

    private static String getCachePath(Context context, String name) {

        String pathTotal = CreateCachePath(context, "", dirName);

        String path = "";

        path = CreateCachePath(context, pathTotal, name);

        return path;
    }

    public static String getImageCachePath(Context context) {

        return getCachePath(context, CACHE_EXTRAL_FILE_NAME_IAMGE);
    }

    public static String getRadioCachePath(Context context) {

        return getCachePath(context, CACHE_EXTRAL_FILE_NAME_RADIO);
    }

    public static String getMediaCachePath(Context context) {

        return getCachePath(context, CACHE_EXTRAL_FILE_NAME_MEDIA);
    }

    public static String getImageCompressCachePath(Context context) {

        return getCachePath(context, CACHE_EXTRAL_FILE_NAME_IMAGE_COMPRESS);
    }

    public static String getImageCrashCachePath(Context context) {

        return getCachePath(context, CACHE_EXTRAL_FILE_NAME_CRASH);
    }

    public static File getRetrofitCachePath(Context context) {

        return context.getCacheDir();
    }

}
