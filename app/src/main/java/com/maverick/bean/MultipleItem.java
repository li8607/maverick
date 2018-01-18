package com.maverick.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Administrator on 2018/1/17.
 */

public class MultipleItem<T> implements MultiItemEntity {

    public static final int PEAR_DETAIL = 0;
    public static final int PEAR_IMAGE_TEXT = 1;

    public T t;
    private int viewType;

    public MultipleItem(int viewType, T t) {
        this.t = t;
        this.viewType = viewType;
    }

    @Override
    public int getItemType() {
        return viewType;
    }
}
