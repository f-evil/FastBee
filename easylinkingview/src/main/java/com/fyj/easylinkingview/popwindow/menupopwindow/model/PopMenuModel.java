package com.fyj.easylinkingview.popwindow.menupopwindow.model;

import com.fyj.easylinkingview.popwindow.menupopwindow.MenuPopWindow;

import java.io.Serializable;

/**
 * Created by SSNB on 2016/4/9.
 */
public class PopMenuModel implements Serializable{
    private String name;
    private String id;
    private MenuPopWindow.MenuStyle style = MenuPopWindow.MenuStyle.NORMAL;

    public MenuPopWindow.MenuStyle getStyle() {
        return style;
    }

    public void setStyle(MenuPopWindow.MenuStyle style) {
        if(style==null){
            style = MenuPopWindow.MenuStyle.NORMAL;
        }
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
