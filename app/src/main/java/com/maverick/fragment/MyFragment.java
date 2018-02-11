package com.maverick.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avos.avoscloud.AVUser;
import com.maverick.DataBankActivity;
import com.maverick.LoginActivity;
import com.maverick.R;
import com.maverick.SettingActivity;
import com.maverick.UserDetailActivity;
import com.maverick.adapter.MyFragmentAdapter;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.MyInfo;
import com.maverick.global.ActivityCode;
import com.maverick.leancloud.User;
import com.maverick.presenter.BasePresenter;
import com.maverick.type.MyType;
import com.maverick.type.UserType;
import com.maverick.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class MyFragment extends BaseFragment2 {

    private RecyclerView recyclerView;
    private List<MyInfo> mList;
    private MyFragmentAdapter mMyFragmentAdapter;

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
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);

                if (position == 0) {
                    outRect.top = DensityUtil.dip2px(getContext(), 14);
                }

                if (mList.get(position).getType() == MyType.LOGIN_REGISTER
                        || mList.get(position).getType() == MyType.USER
                        || mList.get(position).getType() == MyType.COLLECT) {
                    outRect.bottom = DensityUtil.dip2px(getContext(), 14);
                }
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mList = new ArrayList<>();

        if (AVUser.getCurrentUser() != null) {
            mList.add(getMyInfo(AVUser.getCurrentUser().getString(User.nickname), R.drawable.ic_account_box_black_24dp, MyType.USER));
        } else {
            mList.add(getMyInfo("登录/注册", R.drawable.ic_account_box_black_24dp, MyType.LOGIN_REGISTER));
        }
        mList.add(getMyInfo("浏览记录", R.drawable.ic_timelapse_black_24dp, MyType.HISTORY));
        mList.add(getMyInfo("收藏", R.drawable.ic_favorite_black_24dp, MyType.COLLECT));
        mList.add(getMyInfo("设置", R.drawable.ic_brightness_low_black_24dp, MyType.SETTING));
        mMyFragmentAdapter = new MyFragmentAdapter(getContext(), mList);
        recyclerView.setAdapter(mMyFragmentAdapter);

        mMyFragmentAdapter.setOnItemClickListener(new MyFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, MyInfo myInfo) {
                if (myInfo.getType() == MyType.HISTORY || myInfo.getType() == MyType.COLLECT) {

                    if (AVUser.getCurrentUser() == null) {
                        if (getContext() == null) {
                            return;
                        }
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivityForResult(intent, 0);
                        return;
                    }

                    DataBankActivity.launch(getContext(), myInfo);
                } else if (myInfo.getType() == MyType.LOGIN_REGISTER) {
                    if (getContext() == null) {
                        return;
                    }
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                } else if (myInfo.getType() == MyType.USER) {
                    if (getContext() == null) {
                        return;
                    }
                    Intent intent = new Intent(getContext(), UserDetailActivity.class);
                    startActivityForResult(intent, 1);
                } else if (myInfo.getType() == MyType.SETTING) {
                    if (getContext() == null) {
                        return;
                    }
                    Intent intent = new Intent(getContext(), SettingActivity.class);
                    getActivity().startActivityForResult(intent, ActivityCode.REQUEST_CODE_THEME);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            //登录成功
            if (mList != null && mList.size() > 0) {
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getType() == MyType.LOGIN_REGISTER) {
                        mList.get(i).setType(MyType.USER);
                        mMyFragmentAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }
        } else if (requestCode == 1 && resultCode == 1) {
            //退出登录
            if (mList != null && mList.size() > 0) {
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getType() == MyType.USER) {
                        mList.get(i).setType(MyType.LOGIN_REGISTER);
                        mList.get(i).setTitle("登录/注册");
                        mMyFragmentAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }
        } else if (requestCode == 1 && resultCode == UserType.NICKNAME) {
            //昵称改变
            if (mList != null && mList.size() > 0) {
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getType() == MyType.LOGIN_REGISTER || mList.get(i).getType() == MyType.USER) {
                        mList.get(i).setType(MyType.USER);
                        mMyFragmentAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }
        }
    }

    private MyInfo getMyInfo(String title, int icon, int type) {
        MyInfo myInfo = new MyInfo();
        myInfo.setTitle(title);
        myInfo.setIcon(icon);
        myInfo.setType(type);
        return myInfo;
    }
}
