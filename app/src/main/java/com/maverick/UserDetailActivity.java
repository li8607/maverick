package com.maverick;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.avos.avoscloud.AVUser;
import com.maverick.adapter.UserDetailActivityAdapter;
import com.maverick.base.BaseActivity;
import com.maverick.bean.UserItemInfo;
import com.maverick.leancloud.User;
import com.maverick.presenter.BasePresenter;
import com.maverick.type.UserType;
import com.maverick.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public class UserDetailActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private UserDetailActivityAdapter mAdapter;
    private List<UserItemInfo> mList;

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_user;
    }

    @Override
    protected void onInitView() {

        Toolbar toolbar = findView(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("个人信息");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView = findView(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (mAdapter.getItemViewType(parent.getChildAdapterPosition(view)) == UserType.EXITLOGIN) {
                    outRect.top = DensityUtil.dip2px(UserDetailActivity.this, 14);
                }
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        if (AVUser.getCurrentUser() == null) {
            finish();
            return;
        }

        String headUrl = AVUser.getCurrentUser().getString(User.headUrl);
        String nickname = AVUser.getCurrentUser().getString(User.nickname);
        String username = AVUser.getCurrentUser().getUsername();
        String email = AVUser.getCurrentUser().getEmail();
        String mobilePhoneNumber = AVUser.getCurrentUser().getMobilePhoneNumber();

        mList = new ArrayList<>();

        UserItemInfo headInfo = new UserItemInfo();
        headInfo.setType(UserType.HEAD);
        headInfo.setTitle("头像");
        headInfo.setHeadUrl(headUrl);
        mList.add(headInfo);

        UserItemInfo nicknameInfo = new UserItemInfo();
        nicknameInfo.setType(UserType.NICKNAME);
        nicknameInfo.setTitle("昵称");
        nicknameInfo.setNickname(nickname);
        mList.add(nicknameInfo);

        UserItemInfo usernameInfo = new UserItemInfo();
        usernameInfo.setType(UserType.USERNAME);
        usernameInfo.setTitle("账号");
        usernameInfo.setUsername(username);
        mList.add(usernameInfo);

        UserItemInfo emailInfo = new UserItemInfo();
        emailInfo.setType(UserType.EMAIL);
        emailInfo.setTitle("邮箱");
        emailInfo.setEmail(email);
        mList.add(emailInfo);

        UserItemInfo mobilePhoneNumberInfo = new UserItemInfo();
        mobilePhoneNumberInfo.setType(UserType.MOBILEPHONENUMBER);
        mobilePhoneNumberInfo.setTitle("手机号");
        mobilePhoneNumberInfo.setMobilePhoneNumber(mobilePhoneNumber);
        mList.add(mobilePhoneNumberInfo);

        UserItemInfo exitLoginInfo = new UserItemInfo();
        exitLoginInfo.setType(UserType.EXITLOGIN);
        exitLoginInfo.setTitle("退出登录");
        mList.add(exitLoginInfo);

        mAdapter = new UserDetailActivityAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new UserDetailActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mList.get(position).getType() == UserType.EXITLOGIN) {
                    AVUser.logOut();
                    setResult(1);
                    finish();
                } else if (mList.get(position).getType() == UserType.HEAD) {

                } else if (mList.get(position).getType() == UserType.NICKNAME
                        || mList.get(position).getType() == UserType.USERNAME
                        || mList.get(position).getType() == UserType.EMAIL
                        || mList.get(position).getType() == UserType.MOBILEPHONENUMBER) {

                    Intent intent = new Intent(UserDetailActivity.this, UserChangeActivity.class);
                    intent.putExtra(UserChangeActivity.USERITEMINFO, mList.get(position));
                    startActivityForResult(intent, 0);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == UserType.NICKNAME) {
            setResult(UserType.NICKNAME);
        }

        if (requestCode == 0 && mList != null && AVUser.getCurrentUser() != null && mAdapter != null) {
            for (int i = 0; i < mList.size(); i++) {
                UserItemInfo userItemInfo = mList.get(i);
                if (userItemInfo.getType() == resultCode) {
                    userItemInfo.setEmail(AVUser.getCurrentUser().getEmail());
                    userItemInfo.setMobilePhoneNumber(AVUser.getCurrentUser().getMobilePhoneNumber());
                    userItemInfo.setUsername(AVUser.getCurrentUser().getUsername());
                    userItemInfo.setHeadUrl((String) AVUser.getCurrentUser().get(User.headUrl));
                    userItemInfo.setNickname((String) AVUser.getCurrentUser().get(User.nickname));
                    mAdapter.notifyItemChanged(i);
                }
            }
        }
    }
}
