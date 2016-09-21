package com.fyj.fastbee.ui.activity.qr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyj.easylinkingutils.utils.EncodeUtils;
import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.fastbee.R;
import com.fyj.fastbee.base.BaseAppCompatActivity;
import com.fyj.fastbee.utils.Bmp2YUV;
import com.fyj.fastbee.utils.QRCodeEncodeHelper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;

public class QrMangerActivity extends BaseAppCompatActivity {

    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.textView)
    EditText textView;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView2)
    TextView textView2;

    public static void goTo(Context context) {
        context.startActivity(new Intent(context, QrMangerActivity.class));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_qr_manger;
    }

    @Override
    protected void destoryPre() {

    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void getDate() {

    }

    @Override
    protected void initView() {
//        textView.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.:/?&"));
    }

    @Override
    protected void initCustomFunction() {

    }

    @Override
    protected void bindEvent() {
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pressGetQRInfo((ImageView) v);
                return false;
            }
        });
    }

    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                QRActivity.goTo(this);
                break;
            case R.id.button2:
                BitMatrix b = null;
                try {
                    String s = textView.getText().toString();
                    b = QRCodeEncodeHelper.encode(EncodeUtils.urlEncode(s), BarcodeFormat.QR_CODE, 300, 300);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                if (b == null) {
                    XLog.e("GenQRCode failed!");
                    return;
                }
                Bitmap bp = QRCodeEncodeHelper.createBitmap(b);
                imageView.setImageBitmap(bp);
                break;
        }
    }

    private void pressGetQRInfo(ImageView v) {
        BitmapDrawable background = (BitmapDrawable) v.getDrawable();
        if (background == null) {
            return;
        }
        Bitmap bMap = background.getBitmap();
        if (bMap == null) {
            return;
        }

        int[] argb = new int[bMap.getWidth() * bMap.getHeight()];

        bMap.getPixels(argb, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

        byte[] yuv = new byte[bMap.getWidth() * bMap.getHeight() * 3 / 2];
        Bmp2YUV.encodeYUV420SP(yuv, argb, bMap.getWidth(), bMap.getHeight());
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new PlanarYUVLuminanceSource(yuv, bMap.getWidth(),
                bMap.getHeight(), 0, 0, bMap.getWidth(), bMap.getHeight(), false)));
        QRCodeReader mQRCodeReader = new QRCodeReader();
        try {
            Result e = mQRCodeReader.decode(bitmap);
            String text = e.getText();
            textView2.setText(EncodeUtils.urlDecode(text));
            XLog.e("Result", e.getText());
        } catch (ChecksumException var1) {
            XLog.e("var1", var1.getLocalizedMessage());
        } catch (NotFoundException var2) {
            XLog.e("var2", var2.getLocalizedMessage());
        } catch (FormatException var3) {
            XLog.e("var3", var3.getLocalizedMessage());
        }
        finally {
            mQRCodeReader.reset();
        }
    }
}
