package com.fyj.fastbee.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Map;

/**
 * Helper class for encoding barcodes as a Bitmap.
 *
 * Adapted from QRCodeEncoder, from the zxing project:
 * https://github.com/zxing/zxing
 *
 * Licensed under the Apache License, Version 2.0.
 */
public class QRCodeEncodeHelper {
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;


    private QRCodeEncodeHelper() {
    }

    public static Bitmap createBitmap(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static BitMatrix encode(String contents, BarcodeFormat format, int width, int height) throws WriterException {
        try {
            return new MultiFormatWriter().encode(contents, format, width, height);
        } catch (WriterException e) {
            throw e;
        } catch (Exception e) {
            // ZXing sometimes throws an IllegalArgumentException
            throw new WriterException(e);
        }
    }

    public static BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        try {
            return new MultiFormatWriter().encode(contents, format, width, height, hints);
        } catch (WriterException e) {
            throw e;
        } catch (Exception e) {
            throw new WriterException(e);
        }
    }

    public static Bitmap encodeBitmap(String contents, BarcodeFormat format, int width, int height) throws WriterException {
        return createBitmap(encode(contents, format, width, height));
    }

    public static Bitmap encodeBitmap(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        return createBitmap(encode(contents, format, width, height, hints));
    }

    /**
     * 使用 AsyncTask 生成图片，会产生新线程。
     */
    @Deprecated
    public static final class GenQRCodeTask extends AsyncTask<String, Integer, Long> {
        private ImageView img;
        private Bitmap b;

        public GenQRCodeTask(ImageView img) {
            this.img = img;
        }

        @Override
        protected Long doInBackground(String... content) {
            BitMatrix bq = null;
            try {
                bq = QRCodeEncodeHelper.encode(content[0], BarcodeFormat.QR_CODE, 500, 500);
            } catch (WriterException e) {
                e.printStackTrace();
            }

            b = QRCodeEncodeHelper.createBitmap(bq);
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            img.setImageBitmap(b);
        }
    }

    /**
     * 简易的 id 加密方法，主要防止误识别。
     * e.g., content = 100002
     *       return "X" + base64(easylinking-100002);
     *
     * 头部 "X" 可用于快速检错。
     *
     * @param content 用户 id
     * @return 编码后的用户 id， 二维码图片中的内容。
     */
    public static String contentEncode(String content) {
        content = String.format("easylinking-%s", content);
        content = Base64.encodeToString(content.getBytes(), Base64.DEFAULT);
        content = String.format("X%s", content);

        return content;
    }

    /**
     * 相应的解码方法
     *
     * @param content 编码后的用户 id， 二维码图片中的内容。
     * @return 用户 id
     */
    public static String contentDecode(String content) {
        content = content.substring(1);
        content = new String(Base64.decode(content, Base64.DEFAULT));
        content = content.substring(content.indexOf('-') + 1);
        return content;
    }
}

