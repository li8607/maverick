package com.maverick.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.TextAdapter;
import com.maverick.bean.GifInfo;
import com.maverick.presenter.implPresenter.TextFragmentPresenterImpl;
import com.maverick.presenter.implView.TextFragmentView;

import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public class TextFragment extends Fragment implements TextFragmentView {

    private TextFragmentPresenterImpl mTextFragmentPresenterImpl;
    private TextAdapter mTextAdapter;

    public static TextFragment newInstance() {
        TextFragment fragment = new TextFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mTextFragmentPresenterImpl = new TextFragmentPresenterImpl(getContext(), this);

        View view = inflater.inflate(R.layout.fragment_text, null);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mTextAdapter = new TextAdapter(getContext());
        mRecyclerView.setAdapter(mTextAdapter);


        final int bottom = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getContext().getResources().getDisplayMetrics()));

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = bottom;
                }
                outRect.bottom = bottom;
                outRect.left = bottom;
                outRect.right = bottom;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTextFragmentPresenterImpl.getImgList(1, true);
    }

    @Override
    public void refreshAdapter(List<GifInfo> list) {
        mTextAdapter.setData(list);
        mTextAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }
}
