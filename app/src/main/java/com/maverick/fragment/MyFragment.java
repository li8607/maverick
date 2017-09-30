package com.maverick.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.maverick.DataBankActivity;
import com.maverick.R;
import com.maverick.adapter.MyFragmentAdapter;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.MyInfo;
import com.maverick.presenter.BasePresenter;
import com.maverick.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class MyFragment extends BaseFragment2 {

    private RecyclerView recyclerView;

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onInitView(View view) {
        recyclerView = findView(view, R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                int position = parent.getChildAdapterPosition(view);

                if (position == 0) {
                    outRect.top = DensityUtil.dip2px(getContext(), 100);
                } else {
                    outRect.top = DensityUtil.dip2px(getContext(), 10);
                }
                outRect.bottom = DensityUtil.dip2px(getContext(), 10);
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        List<MyInfo> list = new ArrayList<>();
        list.add(getMyInfo("浏览记录", R.drawable.ic_menu_gallery, "0"));
        list.add(getMyInfo("收藏", R.drawable.ic_menu_camera, "1"));
        list.add(getMyInfo("系统设置", R.drawable.ic_menu_send, "2"));
        MyFragmentAdapter mMyFragmentAdapter = new MyFragmentAdapter(getContext(), list);
        recyclerView.setAdapter(mMyFragmentAdapter);

        mMyFragmentAdapter.setOnItemClickListener(new MyFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, MyInfo myInfo) {
                Toast.makeText(getContext(), myInfo.getTitle(), Toast.LENGTH_SHORT).show();
                if (TextUtils.equals(myInfo.getType(), "0")) {
                    DataBankActivity.launch(getContext(), myInfo);
                } else if (TextUtils.equals(myInfo.getType(), "1")) {
                    DataBankActivity.launch(getContext(), myInfo);
                } else if (TextUtils.equals(myInfo.getType(), "2")) {

                }
            }
        });
    }

    private MyInfo getMyInfo(String title, int icon, String type) {
        MyInfo myInfo = new MyInfo();
        myInfo.setTitle(title);
        myInfo.setIcon(icon);
        myInfo.setType(type);
        return myInfo;
    }
}
