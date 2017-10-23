package com.maverick.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.CollectTabInfo;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;

/**
 * Created by limingfei on 2017/9/29.
 */
public class BaseEditFragment extends BaseFragment2 {

    public static final int STATE_EDIT = 1;
    public static final int STATE_NO_EDIT = 2;
    public int stateEdit = STATE_NO_EDIT;

    public static final int STATE_CHECK = 1;
    public static final int STATE_NO_ALL_CHECK = 2;
    public static final int STATE_ALL_CHECK = 3;
    public int checkState = STATE_NO_ALL_CHECK;

    public static BaseEditFragment newInstance(CollectTabInfo collectTabInfo) {
        BaseEditFragment fragment = new BaseEditFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag.KEY_INFO, collectTabInfo);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return 0;
    }

    @Override
    protected void onInitView(View view) {
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
    }

    public int getStateEdit() {
        return stateEdit;
    }

    public void setStateEdit(int stateEdit) {
        this.stateEdit = stateEdit;
        switch (stateEdit) {
            case STATE_EDIT:
                openEditState();
                break;
            case STATE_NO_EDIT:
                closeEditState();
                break;
        }
    }

    protected void closeEditState() {

    }

    protected void openEditState() {

    }

    public void delete() {

    }

    public int getCheckState() {
        return checkState;
    }

    public void setCheckState(int checkState) {
        this.checkState = checkState;
        switch (checkState) {
            case STATE_ALL_CHECK:
                if (isSelectorAll()) {
                    selectorAll();
                } else {
                    this.checkState = STATE_NO_ALL_CHECK;
                }
                break;
            case STATE_NO_ALL_CHECK:
                cancelAll();
                break;
        }

        if (mOnBaseEditFragmentListener != null) {
            mOnBaseEditFragmentListener.onCheckState(this.checkState);
        }
    }

    /**
     * 是否可以全选，默认不可以
     */
    public boolean isSelectorAll() {
        return false;
    }

    public void selectorAll() {
    }

    public void cancelAll() {
    }

    protected OnBaseEditFragmentListener mOnBaseEditFragmentListener;

    public void setOnBaseEditFragmentListener(OnBaseEditFragmentListener listener) {
        this.mOnBaseEditFragmentListener = listener;
    }

    public interface OnBaseEditFragmentListener {
        void onCheckState(int checkState);
    }
}
