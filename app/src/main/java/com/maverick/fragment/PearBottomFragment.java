package com.maverick.fragment;

import android.os.Bundle;
import android.view.View;

import com.maverick.R;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.PearVideoDetailBean;
import com.maverick.bean.PearVideoDetailInfoVideo;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.PearBottomFragmentPresenter;
import com.maverick.presenter.implView.IPearBottomFragmentView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class PearBottomFragment extends BaseFragment2 implements IPearBottomFragmentView {

    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";

    private PearBottomFragmentPresenter mPresenter;

    public static PearBottomFragment newInstance(PearVideoDetailBean info) {
        PearBottomFragment fragment = new PearBottomFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_IMAGE, info);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new PearBottomFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_pear_bottom;
    }

    @Override
    protected void onInitView(View view) {

    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mPresenter.loadData((PearVideoDetailBean) getArguments().getSerializable(EXTRA_IMAGE));
    }

    @Override
    public void onShowSuccessView() {

    }

    @Override
    public void onShowEmptyView() {

    }

    @Override
    public void onShowErrorView() {

    }

    @Override
    public void onShowVideoView(List<PearVideoDetailInfoVideo> list) {
        if (getActivity() != null && getActivity() instanceof OnListener) {
            OnListener listener = (OnListener) getActivity();
            listener.playVideo(list);
        }
    }

    public interface OnListener {
        void playVideo(List<PearVideoDetailInfoVideo> list);
    }
}
