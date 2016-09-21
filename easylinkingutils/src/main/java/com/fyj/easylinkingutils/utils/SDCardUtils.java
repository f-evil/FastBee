package com.fyj.easylinkingutils.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <pre>
 *     当前作者: EasyLinking
 *     修改时间: 2016/8/15
 *     修改次数: 2
 *     描述:  SD卡相关的工具类
 * </pre>
 */
public class SDCardUtils {

    private SDCardUtils() {
        throw new UnsupportedOperationException("You do not need to instantiate!");
    }

    /**
     * 获取设备SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取设备SD卡路径
     * <p>一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }


    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * Check if the file is exists
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static boolean isFileExistsInSDCard(String filePath, String fileName) {
        boolean flag = false;
        if (isSDCardEnable()) {
            File file = new File(filePath, fileName);
            if (file.exists()) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Write file to SD card
     * @param filePath
     * @param filename
     * @param content
     * @return
     * @throws Exception
     */
    public static boolean saveFileToSDCard(String filePath, String filename, String content)
            throws Exception {
        boolean flag = false;
        if (isSDCardEnable()) {
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(filePath, filename);
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(content.getBytes());
            outStream.close();
            flag = true;
        }
        return flag;
    }

    /**
     * Read file as stream from SD card
     *
     * @param fileName
     *            String PATH =
     *            Environment.getExternalStorageDirectory().getAbsolutePath() +
     *            "/dirName";
     * @return
     */
    public static byte[] readFileFromSDCard(String filePath, String fileName) {
        byte[] buffer = null;
        FileInputStream fin = null;
        try {
            if (isSDCardEnable()) {
                String filePaht = filePath + "/" + fileName;
                fin = new FileInputStream(filePaht);
                int length = fin.available();
                buffer = new byte[length];
                fin.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fin != null) fin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer;
    }

    /**
     * Delete file
     *
     * @param filePath
     * @param fileName
     *            filePath =
     *            android.os.Environment.getExternalStorageDirectory().getPath()
     * @return
     */
    public static boolean deleteSDFile(String filePath, String fileName) {
        File file = new File(filePath + "/" + fileName);
        return !(!file.exists() || file.isDirectory()) && file.delete();
    }
}
