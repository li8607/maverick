package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by limingfei on 2017/9/29.
 */
public class CollectTabInfo implements Serializable {

    private String title;

    private int type;

    private List<CollectTabInfo> itemList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<CollectTabInfo> getItemList() {
        return itemList;
    }

    public void setItemList(List<CollectTabInfo> itemList) {
        this.itemList = itemList;
    }
}