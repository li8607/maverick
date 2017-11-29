package com.maverick;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestEmailVerifyCallback;
import com.avos.avoscloud.SignUpCallback;
import com.maverick.base.BaseActivity;
import com.maverick.bean.UserItemInfo;
import com.maverick.leancloud.User;
import com.maverick.presenter.BasePresenter;
import com.maverick.type.UserType;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/28.
 */

public class UserChangeActivity extends BaseActivity {

    public static final String USERITEMINFO = "UserItemInfo";
    private EditText editText;
    private TextView text;
    private View edit_root;
    private View save_btn;
    private UserItemInfo mInfo;

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_user_change;
    }

    @Override
    protected void onInitView() {

        Toolbar toolbar = findView(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit_root = findView(R.id.edit_root);
        editText = findView(R.id.editText);
        text = findView(R.id.text);

        save_btn = findView(R.id.save_btn);
//        save_btn.setAlpha(0.5f);
//        save_btn.setClickable(false);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInfo == null || AVUser.getCurrentUser() == null) {
                    return;
                }

                String text = editText.getText().toString().trim();

                switch (mInfo.getType()) {
                    case UserType.NICKNAME:
                        break;
                    case UserType.USERNAME:
                        break;
                    case UserType.EMAIL:
                        AVUser.getCurrentUser().setEmail(text);
                        AVUser.getCurrentUser().signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(AVException e) {

                                if (e != null) {
                                    Log.e("lmf", "e = " + e.toString());
                                } else {

                                }
                            }
                        });
                        break;
                    case UserType.MOBILEPHONENUMBER:
                        break;
                    case UserType.HEAD:
                        break;
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!save_btn.isClickable()) {
                    save_btn.setAlpha(1.0f);
                    save_btn.setClickable(true);
                }
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        if (getIntent() == null) {
            return;
        }

        Serializable serializable = getIntent().getSerializableExtra(USERITEMINFO);

        if (serializable == null || !(serializable instanceof UserItemInfo)) {
            return;
        }

        mInfo = (UserItemInfo) serializable;

        switch (mInfo.getType()) {
            case UserType.NICKNAME:
                edit_root.setVisibility(View.VISIBLE);
                setSupportActionBarTitle((TextUtils.isEmpty(mInfo.getNickname()) ? "设置" : "更改") + mInfo.getTitle());
                setEditText(mInfo.getNickname());
                setText("好名字可以让你的朋友更容易记住你。");
                break;
            case UserType.USERNAME:
                edit_root.setVisibility(View.VISIBLE);
                setSupportActionBarTitle((TextUtils.isEmpty(mInfo.getUsername()) ? "设置" : "更改") + mInfo.getTitle());
                setEditText(mInfo.getUsername());
                setText("用户名是账号的唯一凭证。");
                break;
            case UserType.EMAIL:
                edit_root.setVisibility(View.VISIBLE);
                setSupportActionBarTitle((TextUtils.isEmpty(mInfo.getEmail()) ? "设置" : "更改") + mInfo.getTitle());
                setEditText(mInfo.getEmail());
                setText("邮箱是账号的辅助凭证。");
                break;
            case UserType.MOBILEPHONENUMBER:
                edit_root.setVisibility(View.VISIBLE);
                setSupportActionBarTitle((TextUtils.isEmpty(mInfo.getMobilePhoneNumber()) ? "设置" : "更改") + mInfo.getTitle());
                setEditText(mInfo.getEmail());
                setText("手机号是账号的辅助凭证。");
                break;
            case UserType.HEAD:
                edit_root.setVisibility(View.GONE);
                break;
        }
    }

    public void setEditText(String text) {
        editText.setText(TextUtils.isEmpty(text) ? "" : text);
    }

    public void setText(String text) {
        this.text.setText(TextUtils.isEmpty(text) ? "" : text);
    }

    public void setSupportActionBarTitle(String title) {
        if (getSupportActionBar() == null || TextUtils.isEmpty(title)) {
            return;
        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
    }
}
