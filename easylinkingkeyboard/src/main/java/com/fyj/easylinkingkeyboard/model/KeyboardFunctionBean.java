package com.fyj.easylinkingkeyboard.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/9/2<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class KeyboardFunctionBean implements Parcelable {

    private String name;

    private int drawable;

    private int type;

    private Class className;

    public KeyboardFunctionBean(String name, int drawable, int type, Class className) {
        this.name = name;
        this.drawable = drawable;
        this.type = type;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Class getClassName() {
        return className;
    }

    public void setClassName(Class className) {
        this.className = className;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.drawable);
        dest.writeInt(this.type);
        dest.writeSerializable(this.className);
    }

    protected KeyboardFunctionBean(Parcel in) {
        this.name = in.readString();
        this.drawable = in.readInt();
        this.type = in.readInt();
        this.className = (Class) in.readSerializable();
    }

    public static final Creator<KeyboardFunctionBean> CREATOR = new Creator<KeyboardFunctionBean>() {
        @Override
        public KeyboardFunctionBean createFromParcel(Parcel source) {
            return new KeyboardFunctionBean(source);
        }

        @Override
        public KeyboardFunctionBean[] newArray(int size) {
            return new KeyboardFunctionBean[size];
        }
    };
}
