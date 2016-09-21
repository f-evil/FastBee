package com.fyj.fastbee.bean;

import java.io.Serializable;

/**
 * 当前作者: Fyj<br>
 * 时间: 2016/8/24<br>
 * 邮箱: f279259625@gmail.com<br>
 * 修改次数: <br>
 * 描述:
 */
public class FastBeeSettingsBean implements Serializable {

    private String _Name;
    private String _Value;

    public FastBeeSettingsBean(String _Name, String _Value) {
        this._Name = _Name;
        this._Value = _Value;
    }

    public String get_Name() {
        return _Name;
    }

    public void set_Name(String _Name) {
        this._Name = _Name;
    }

    public String get_Value() {
        return _Value;
    }

    public void set_Value(String _Value) {
        this._Value = _Value;
    }
}
