package com.fyj.fastbee.bean.base;

import java.io.Serializable;

/**
 * 抽象方法
 * T 代表解析类
 * 适用于object
 * Created by Fyj on 2016/6/21.
 */
public class BaseObjectBean<T> implements Serializable {

    private String msg;
    private int dataNum;
    private int status;
    private T data;

    public BaseObjectBean() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getDataNum() {
        return dataNum;
    }

    public void setDataNum(int dataNum) {
        this.dataNum = dataNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
