package com.fyj.easylinkingutils.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 模仿微信
 * <p>
 * 一档直接压缩;
 * 二挡模糊处理压缩;
 * (默认)三挡,判定更为严格;
 * <p>
 * 1.判断图片比例值，是否处于以下区间内；
 * <p>
 * [1, 0.5625) 即图片处于 [1:1 ~ 9:16) 比例范围内
 * <p>
 * [0.5625, 0.5) 即图片处于 [9:16 ~ 1:2) 比例范围内
 * <p>
 * [0.5, 0) 即图片处于 [1:2 ~ 1:∞) 比例范围内
 * <p>
 * 2.判断图片最长边是否过边界值；
 * <p>
 * [1, 0.5625) 边界值为：1664 * n（n=1）, 4990 * n（n=2）, 1280 * pow(2, n-1)（n≥3）
 * <p>
 * [0.5625, 0.5) 边界值为：1280 * pow(2, n-1)（n≥1）
 * <p>
 * [0.5, 0) 边界值为：1280 * pow(2, n-1)（n≥1）
 * <p>
 * 3.计算压缩图片实际边长值，以第2步计算结果为准，超过某个边界值则：width / pow(2, n-1)，height/pow(2, n-1)
 * <p>
 * 4.计算压缩图片的实际文件大小，以第2、3步结果为准，图片比例越大则文件越大。
 * <p>
 * size = (newW * newH) / (width * height) * m；
 * <p>
 * [1, 0.5625) 则 width & height 对应 1664，4990，1280 * n（n≥3），m 对应 150，300，300；
 * <p>
 * [0.5625, 0.5) 则 width = 1440，height = 2560, m = 200；
 * <p>
 * [0.5, 0) 则 width = 1280，height = 1280 / scale，m = 500；注：scale为比例值
 * <p>
 * 5.判断第4步的size是否过小
 * <p>
 * [1, 0.5625) 则最小 size 对应 60，60，100
 * <p>
 * [0.5625, 0.5) 则最小 size 都为 100
 * <p>
 * [0.5, 0) 则最小 size 都为 100
 * <p>
 * 6.将前面求到的值压缩图片 width, height, size 传入压缩流程，压缩图片直到满足以上数值
 * <p>
 * Created by Fyj on 2016/8/2.
 */
public class ImageCompressUtils {

    public static final int FIRST_GRADE = 1;
    public static final int SECEND_GRADE = 2;
    public static final int THRID_GRADE = 3;

    private static final double ONETOONE = 1.0;
    //    private static final double THREETOFOUR = 0.75;
    private static final double NINETONINETEEN = 0.5625;
    private static final double ONETOTWO = 0.5;

//    private static final String TAG = "ImageCompressUtils";

    private static final String DEFAULT_DISK_DIR = "image_cache";

    private static volatile ImageCompressUtils INSTANCE;

    private final File mCacheFile;

    private File mFile;

    private int grade = THRID_GRADE;

    private OnCompressListener mOnCompressListener;

    public ImageCompressUtils(File mCacheFile) {
        this.mCacheFile = mCacheFile;
    }

    /**
     * 获得本体
     *
     * @return 本体
     */
    public static ImageCompressUtils init(File file) {
        if (INSTANCE == null) {
            INSTANCE = new ImageCompressUtils(file);
        }
        return INSTANCE;
    }

    /**
     * 设置带压缩的文件
     *
     * @param mFile 文件
     * @return 本体
     */
    public ImageCompressUtils loadFile(File mFile) {
        this.mFile = mFile;
        return this;
    }

    /**
     * 设置压缩等级
     *
     * @param grade 压缩等级
     * @return 本体
     */
    public ImageCompressUtils setGrade(int grade) {
        this.grade = grade;
        return this;
    }

    /**
     * 设置回调
     *
     * @param mOnCompressListener 回调
     * @return 本体
     */
    public ImageCompressUtils setListener(OnCompressListener mOnCompressListener) {
        this.mOnCompressListener = mOnCompressListener;
        return this;
    }

    /**
     * 开始执行压缩
     */
    public void start() {

        if (mFile == null) {
            if (mOnCompressListener != null) {
                mOnCompressListener.onFailed("待压缩文件不存在");
            }
            return;
        }

        if (grade == FIRST_GRADE) {
            firstGradeCompress(mFile);
        } else if (grade == SECEND_GRADE) {
            secondGradeCompress(mFile);
        } else if (grade == THRID_GRADE) {
            thridGradeCompress(mFile);
        }

    }

    /**
     * 一级压缩
     * 最简单
     * 限定最大100K
     *
     * @param file 文件
     */
    private void firstGradeCompress(File file) {
//        int minSize = 60;
        int longSide = 720;
        int shortSide = 1280;

        String filePath = file.getAbsolutePath();
        String thumbFilePath = mCacheFile.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
//        long size = 0;
//        long maxSize = file.length() / 5;

        int angle = getImageAngle(filePath);
        int[] imgSize = getImageSize(filePath);
        int width = 0, height = 0;
        if (imgSize[0] <= imgSize[1]) {
            double scale = (double) imgSize[0] / (double) imgSize[1];
            if (scale <= 1.0 && scale > 0.5625) {
                width = imgSize[0] > shortSide ? shortSide : imgSize[0];
                height = width * imgSize[1] / imgSize[0];
//                size = minSize;
            } else if (scale <= 0.5625) {
                height = imgSize[1] > longSide ? longSide : imgSize[1];
                width = height * imgSize[0] / imgSize[1];
//                size = maxSize;
            }
        } else {
            double scale = (double) imgSize[1] / (double) imgSize[0];
            if (scale <= 1.0 && scale > 0.5625) {
                height = imgSize[1] > shortSide ? shortSide : imgSize[1];
                width = height * imgSize[0] / imgSize[1];
//                size = minSize;
            } else if (scale <= 0.5625) {
                width = imgSize[0] > longSide ? longSide : imgSize[0];
                height = width * imgSize[1] / imgSize[0];
//                size = maxSize;
            }
        }

        compress(filePath, thumbFilePath, width, height, angle, 100);
    }

    /**
     * 二级压缩
     * 判断范围比较大
     * 对于长图支持略逊色
     *
     * @param file 文件
     */
    private void secondGradeCompress(File file) {
        int minSize = 60;
        int longSide = 720;
        int shortSide = 1280;

        String filePath = file.getAbsolutePath();
        String thumbFilePath = mCacheFile.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
        long size = 0;
        long maxSize = file.length() / 5;

        int angle = getImageAngle(filePath);
        int[] imgSize = getImageSize(filePath);
        int width = 0, height = 0;
        if (imgSize[0] <= imgSize[1]) {
            double scale = (double) imgSize[0] / (double) imgSize[1];
            if (scale <= ONETOONE && scale > NINETONINETEEN) {
                width = imgSize[0] > shortSide ? shortSide : imgSize[0];
                height = width * imgSize[1] / imgSize[0];
                size = minSize;
            } else if (scale <= NINETONINETEEN) {
                height = imgSize[1] > longSide ? longSide : imgSize[1];
                width = height * imgSize[0] / imgSize[1];
                size = maxSize;
            }
        } else {
            double scale = (double) imgSize[1] / (double) imgSize[0];
            if (scale <= ONETOONE && scale > NINETONINETEEN) {
                height = imgSize[1] > shortSide ? shortSide : imgSize[1];
                width = height * imgSize[0] / imgSize[1];
                size = minSize;
            } else if (scale <= NINETONINETEEN) {
                width = imgSize[0] > longSide ? longSide : imgSize[0];
                height = width * imgSize[1] / imgSize[0];
                size = maxSize;
            }
        }

        compress(filePath, thumbFilePath, width, height, angle, size);
    }

    /**
     * 三级压缩
     * 判断范围较细致
     *
     * @param file file
     */
    private void thridGradeCompress(File file) {

        String thumb = mCacheFile.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";

        double size;
        String filePath = file.getAbsolutePath();

        int angle = getImageAngle(filePath);
        int width = getImageSize(filePath)[0];
        int height = getImageSize(filePath)[1];
        int thumbW = width % 2 == 1 ? width + 1 : width;
        int thumbH = height % 2 == 1 ? height + 1 : height;

        width = thumbW > thumbH ? thumbH : thumbW;
        height = thumbW > thumbH ? thumbW : thumbH;

        double scale = ((double) width / height);

        if (scale <= 1 && scale > 0.5625) {
            if (height < 1664) {
                if (file.length() / 1024 < 150) {
                    if (mOnCompressListener != null) {
                        mOnCompressListener.onSuccess(file);
                    }
                }

                size = (width * height) / Math.pow(1664, 2) * 150;
                size = size < 60 ? 60 : size;
            } else if (height >= 1664 && height < 4990) {
                thumbW = width / 2;
                thumbH = height / 2;
                size = (thumbW * thumbH) / Math.pow(2495, 2) * 300;
                size = size < 60 ? 60 : size;
            } else if (height >= 4990 && height < 10240) {
                thumbW = width / 4;
                thumbH = height / 4;
                size = (thumbW * thumbH) / Math.pow(2560, 2) * 300;
                size = size < 100 ? 100 : size;
            } else {
                int multiple = height / 1280 == 0 ? 1 : height / 1280;
                thumbW = width / multiple;
                thumbH = height / multiple;
                size = (thumbW * thumbH) / Math.pow(2560, 2) * 300;
                size = size < 100 ? 100 : size;
            }
        } else if (scale <= 0.5625 && scale > 0.5) {
            if (height < 1280 && file.length() / 1024 < 200) {
                if (mOnCompressListener != null) {
                    mOnCompressListener.onSuccess(file);
                }
            }

            int multiple = height / 1280 == 0 ? 1 : height / 1280;
            thumbW = width / multiple;
            thumbH = height / multiple;
            size = (thumbW * thumbH) / (1440.0 * 2560.0) * 400;
            size = size < 100 ? 100 : size;
        } else {
            int multiple = (int) Math.ceil(height / (1280.0 / scale));
            thumbW = width / multiple;
            thumbH = height / multiple;
            size = ((thumbW * thumbH) / (1280.0 * (1280 / scale))) * 500;
            size = size < 100 ? 100 : size;
        }

        compress(filePath, thumb, thumbW, thumbH, angle, (long) size);

    }

    /**
     * 获得图片的长宽
     *
     * @param filePath 文件地址
     * @return 宽度[0]长度[1]的数组
     */
    public int[] getImageSize(String filePath) {

        int[] wh = new int[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(filePath, options);

        wh[0] = options.outWidth;
        wh[1] = options.outHeight;

        options.inJustDecodeBounds = false;

        return wh;
    }

    /**
     * 获得图片的角度
     *
     * @param filePath 文件地址
     * @return 角度
     */
    private int getImageAngle(String filePath) {

        int angle = 0;

        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int a = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (a) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return angle;

    }

    /**
     * 旋转图片
     *
     * @param angle  角度
     * @param bitmap 传进来的bitmap
     * @return 旋转后的bitmap
     */
    private Bitmap rotateImage(int angle, Bitmap bitmap) {

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }

    /**
     * 指定属性的压缩文件并保存到指定目录
     *
     * @param originPath 原始地址
     * @param thumbpath  压缩待存放地址
     * @param w          宽度
     * @param h          高度
     * @param a          角度
     * @param size       期望大小
     */
    private void compress(String originPath, String thumbpath, int w, int h, int a, long size) {
        Bitmap thumbBtp = compress(originPath, w, h);
        thumbBtp = rotateImage(a, thumbBtp);
        saveImage(thumbpath, thumbBtp, size);
    }

    /**
     * 压缩图片置顶大小
     *
     * @param filePath 文件地址
     * @param w        宽度
     * @param h        长度
     * @return 压缩后的bitmap
     */
    private Bitmap compress(String filePath, int w, int h) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        int ow = options.outWidth;
        int oh = options.outHeight;

        int sampleSize = 1;

        if (ow > w || oh > h) {
            int hw = ow / 2;
            int hh = oh / 2;

            while ((hw / sampleSize > w) && (hh / sampleSize > h)) {
                sampleSize *= 2;
            }

        }

        options.inSampleSize = sampleSize;

        options.inJustDecodeBounds = false;

        //向上取整
        int wScale = (int) Math.ceil(options.outWidth / w);
        int hSacle = (int) Math.ceil(options.outHeight / h);

        if (wScale > 1 || hSacle > 1) {
            if (wScale < hSacle) {
                options.inSampleSize = hSacle;
            } else {
                options.inSampleSize = wScale;
            }
        }

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);

    }

    /**
     * 保存图片
     *
     * @param thumbpath 带保存的地址
     * @param thumbBtp  压缩后的bitmap
     * @param size      期望大小
     */
    private void saveImage(String thumbpath, Bitmap thumbBtp, long size) {

        File result = new File(thumbpath.substring(0, thumbpath.lastIndexOf("/")));

        if (!result.exists() &&
                !result.mkdirs()) {
            if (mOnCompressListener != null) {
                mOnCompressListener.onFailed("目录不存在");
            }
            return;
        }

        ByteArrayOutputStream bopt = new ByteArrayOutputStream();
        int quality = 100;
        thumbBtp.compress(Bitmap.CompressFormat.JPEG, quality, bopt);

        while (bopt.toByteArray().length / 1024 > size) {
            quality -= 6;
            if (quality <= 0) {
                break;
            }
            bopt.reset();
            thumbBtp.compress(Bitmap.CompressFormat.JPEG, quality, bopt);
        }

        FileOutputStream fopt = null;

        try {
            fopt = new FileOutputStream(thumbpath);
            fopt.write(bopt.toByteArray());
            fopt.flush();
            fopt.close();

            if (mOnCompressListener != null) {
                mOnCompressListener.onSuccess(new File(thumbpath));
            }
        } catch (Exception e) {

            try {
                if (fopt != null) {
                    fopt.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if (mOnCompressListener != null) {
                mOnCompressListener.onFailed("文件保存失败");
            }
        }
    }

    private interface OnCompressListener {

        void onSuccess(File mFile);

        void onFailed(String msg);
    }
}
