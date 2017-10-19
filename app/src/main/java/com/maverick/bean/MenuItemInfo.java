package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2017/10/17.
 */
public class MenuItemInfo implements Serializable {

    private int menuType; //微信、微信朋友圈、新浪微博、QQ空间等类型

    private boolean collect;

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }
}
