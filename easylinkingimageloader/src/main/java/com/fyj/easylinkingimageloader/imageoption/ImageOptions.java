package com.fyj.easylinkingimageloader.imageoption;

import android.graphics.Bitmap;

import com.fyj.easylinkingimageloader.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by Fyj on 2016/6/18.
 */
public class ImageOptions {

    public static final int HEAD_TYPE=0;
    public static final int BANNER_TYPE=1;
    public static final int INTEREST_HOR_TYPE=2;
    public static final int INTEREST_VER_TYPE=3;
    public static final int CIRCLR_TYPE=4;
    public static final int YUE_TYPE=5;
    public static final int DEMAND_HEAD_TYPE=6;

    private int errorImage;
    private int emptyImage;

    public ImageOptions() {
    }

    public ImageOptions(int errorImage, int emptyImage) {
        this.errorImage = errorImage;
        this.emptyImage = emptyImage;
    }

    public DisplayImageOptions build() {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(emptyImage)
                .showImageOnFail(errorImage)
                .resetViewBeforeLoading(true)
                .cacheOnDisc(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public DisplayImageOptions build(int type) {

        switch (type){
            case HEAD_TYPE:
                errorImage= R.drawable.icon_person_head_default;
                emptyImage= R.drawable.icon_person_head_default;
                break;

            case BANNER_TYPE:
                errorImage= R.drawable.icon_loading;
                emptyImage= R.drawable.icon_loading;
                break;

            case INTEREST_HOR_TYPE:
                errorImage= R.drawable.icon_bg_hf_ho_load;
                emptyImage= R.drawable.icon_bg_hf_ho_load;
                break;

            case INTEREST_VER_TYPE:
                errorImage= R.drawable.icon_bg_hf_ver_load;
                emptyImage= R.drawable.icon_bg_hf_ver_load;
                break;

            case CIRCLR_TYPE:
                errorImage= R.drawable.icon_defualt_demand_channel;
                emptyImage= R.drawable.icon_defualt_demand_channel;
                break;

            case YUE_TYPE:
                errorImage= R.drawable.icon_pic_rec;
                emptyImage= R.drawable.icon_pic_rec;
                break;

            case DEMAND_HEAD_TYPE:
                errorImage= R.drawable.icon_person_head_default_unknow;
                emptyImage= R.drawable.icon_person_head_default_unknow;
                break;

            default:
                errorImage= R.drawable.icon_person_head_default;
                emptyImage= R.drawable.icon_person_head_default;
                break;
        }

        return build();
    }
}
