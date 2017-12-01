package com.maverick;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestEmailVerifyCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.maverick.base.BaseActivity;
import com.maverick.bean.UserItemInfo;
import com.maverick.leancloud.User;
import com.maverick.presenter.BasePresenter;
import com.maverick.type.UserType;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import java.io.Serializable;

import cntv.themelibrary.ThemeHelper;

/**
 * Created by Administrator on 2017/11/28.
 */

public class UserChangeActivity extends BaseActivity {

    public static final String USERITEMINFO = "UserItemInfo";
    private EditText editText;
    private TextView text;
    private View edit_root;
    private UserItemInfo mInfo;
    private Button send_btn;
    private MenuItem mMenuItem;

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
        send_btn = findView(R.id.send_btn);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AVUser.getCurrentUser() == null || TextUtils.isEmpty(editText.getText())) {
                    return;
                }

                AVUser.getCurrentUser().requestEmailVerifyInBackground(editText.getText().toString().trim(), new RequestEmailVerifyCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Toast.makeText(UserChangeActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserChangeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                if (mMenuItem != null) {
                    mMenuItem.setVisible(true);
                }
            }
        });

        editText.post(new Runnable() {
            @Override
            public void run() {
                if (mMenuItem != null) {
                    mMenuItem.setVisible(false);
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
                if (AVUser.getCurrentUser() != null && !TextUtils.isEmpty(mInfo.getEmail())) {
                    send_btn.setVisibility(AVUser.getCurrentUser().getBoolean(User.emailVerified) ? View.GONE : View.VISIBLE);
                    send_btn.setText("认证邮箱");
                }
                break;
            case UserType.MOBILEPHONENUMBER:
                edit_root.setVisibility(View.VISIBLE);
                setSupportActionBarTitle((TextUtils.isEmpty(mInfo.getMobilePhoneNumber()) ? "设置" : "更改") + mInfo.getTitle());
                setEditText(mInfo.getMobilePhoneNumber());
                setText("手机号是账号的辅助凭证。");
                if (AVUser.getCurrentUser() != null && !TextUtils.isEmpty(mInfo.getMobilePhoneNumber())) {
                    send_btn.setVisibility(AVUser.getCurrentUser().isMobilePhoneVerified() ? View.GONE : View.VISIBLE);
                    send_btn.setText("认证手机号");
                }
                break;
            case UserType.HEAD:
                edit_root.setVisibility(View.GONE);
                break;
        }
    }

    public void setEditText(String text) {
        editText.setText(TextUtils.isEmpty(text) ? "" : text);
        if (!TextUtils.isEmpty(text)) {
            editText.setSelection(text.length());
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_albums, menu);
        mMenuItem = menu.findItem(R.id.save_action);
        mMenuItem.setIcon(getToolbarIcon(GoogleMaterial.Icon.gmd_done));
        mMenuItem.setVisible(true);
        return true;
    }

    public IconicsDrawable getToolbarIcon(IIcon icon) {
        return new ThemeHelper(getApplicationContext()).getToolbarIcon(icon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_action:
                save();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void save() {

        if (mInfo == null || AVUser.getCurrentUser() == null) {
            return;
        }

        final ProgressDialog mProgressDialog = new ProgressDialog(UserChangeActivity.this);
        mProgressDialog.show();

        final String text = editText.getText().toString().trim();

        switch (mInfo.getType()) {
            case UserType.NICKNAME:
                AVUser.getCurrentUser().put(User.nickname, text);
                AVUser.getCurrentUser().signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        mProgressDialog.dismiss();
                        if (e == null) {
                            setResult(mInfo.getType());
                            finish();
                        } else {
                            if (!TextUtils.isEmpty(e.getMessage())) {
                                Toast.makeText(UserChangeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserChangeActivity.this, "更改昵称失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                break;
            case UserType.USERNAME:
                AVUser.getCurrentUser().setUsername(text);
                AVUser.getCurrentUser().signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        mProgressDialog.dismiss();
                        if (e == null) {
                            setResult(mInfo.getType());
                            finish();
                        } else {
                            if (!TextUtils.isEmpty(e.getMessage())) {
                                Toast.makeText(UserChangeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserChangeActivity.this, "更改用户名失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                break;
            case UserType.EMAIL:
                AVUser.getCurrentUser().setEmail(text);
                AVUser.getCurrentUser().signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        mProgressDialog.dismiss();
                        if (e == null) {
                            AVUser.getCurrentUser().requestEmailVerify(text); //认证
                            AVUser.getCurrentUser().put(User.emailVerified, false);

                            setResult(mInfo.getType());
                            finish();
                        } else {
                            if (!TextUtils.isEmpty(e.getMessage())) {
                                Toast.makeText(UserChangeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserChangeActivity.this, "更改邮箱失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                break;
            case UserType.MOBILEPHONENUMBER:
                AVUser.getCurrentUser().setMobilePhoneNumber(text);
                AVUser.getCurrentUser().signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        mProgressDialog.dismiss();
                        if (e == null) {
                            setResult(mInfo.getType());
                            AVUser.getCurrentUser().requestMobilePhoneVerifyInBackground(text, new RequestMobileCodeCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e != null) {
                                        Toast.makeText(UserChangeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(UserChangeActivity.this, "验证手机号成功！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            if (!TextUtils.isEmpty(e.getMessage())) {
                                Toast.makeText(UserChangeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserChangeActivity.this, "更改手机号失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                break;
            case UserType.HEAD:
                break;
        }
    }
}
