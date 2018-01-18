package com.maverick.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maverick.bean.MultipleItem;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */

public class PearBottomAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PearBottomAdapter(List<MultipleItem> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {

    }
}
