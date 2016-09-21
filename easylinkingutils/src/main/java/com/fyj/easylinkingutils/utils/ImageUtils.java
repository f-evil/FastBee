package com.fyj.easylinkingutils.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * <pre>
 *     当前作者: EasyLinking
 *     修改时间: 2016/8/15
 *     修改次数: 2
 *     描述:  图片相关工具类
 * </pre>
 */
public class ImageUtils {

    /**
     * 将Bitmap转换成字符串
     *
     * @param bitmap
     * @return
     */
    public static String bitmaptoString(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * 把byte数组转化成 bitmap对象
     *
     * @param b
     * @return
     */
    public static Bitmap bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
}
