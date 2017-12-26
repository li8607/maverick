package com.maverick;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.maverick.base.BaseActivity;
import com.maverick.bean.CollectTabInfo;
import com.maverick.bean.MyInfo;
import com.maverick.fragment.BaseEditFragment;
import com.maverick.fragment.BrowsingHistoryFragment;
import com.maverick.fragment.CollectFragment;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;
import com.maverick.type.FragmentType;
import com.maverick.type.MyType;

/**
 * Created by limingfei on 2017/9/29.
 */
public class DataBankActivity extends BaseActivity implements View.OnClickListener {

    private BaseEditFragment mBaseEditFragment;
    private View btn_root;
    private Button btn_delete;
    private Button btn_check_or_cancel;

    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;
    private TextView mEdit;
    private View collect_content;

    public static void launch(Context context, MyInfo myInfo) {
        Intent intent = new Intent(context, DataBankActivity.class);
        intent.putExtra(Tag.KEY_INFO, myInfo);
        context.startActivity(intent);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void onInitView() {

        mAppBarLayout = findView(R.id.appbar);

        mToolbar = findView(R.id.toolbar);

        mEdit = new TextView(this);

        mEdit.setId(R.id.edit);
        mEdit.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.y10));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mEdit.setTextAppearance(android.R.style.TextAppearance);
        } else {
            mEdit.setTextAppearance(this, android.R.style.TextAppearance);
        }
        mEdit.setText("编辑");
        Toolbar.LayoutParams mTitleLP = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTitleLP.gravity = Gravity.RIGHT;
        mTitleLP.rightMargin = getResources().getDimensionPixelOffset(R.dimen.y10);
        mToolbar.addView(mEdit, mTitleLP);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEdit.setOnClickListener(this);

        btn_root = findView(R.id.btn_root);
        btn_delete = findView(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        btn_check_or_cancel = findView(R.id.btn_check_or_cancel);
        btn_check_or_cancel.setOnClickListener(this);

        collect_content = findView(R.id.collect_content);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

        MyInfo myInfo = (MyInfo) getIntent().getSerializableExtra(Tag.KEY_INFO);

        mBaseEditFragment = getBaseEditFragment(myInfo);

        if (mBaseEditFragment == null) {
            return;
        }

        replaceFragment(R.id.collect_content, mBaseEditFragment);

        mBaseEditFragment.setOnBaseEditFragmentListener(new BaseEditFragment.OnBaseEditFragmentListener() {

            @Override
            public void onCheckState(int checkState) {
                switch (checkState) {
                    case BaseEditFragment.STATE_ALL_CHECK:
                        btn_delete.setAlpha(1.0f);
                        btn_delete.setClickable(true);
                        btn_check_or_cancel.setText("取消全选");
                        break;
                    case BaseEditFragment.STATE_NO_ALL_CHECK:
                        btn_delete.setAlpha(0.5f);
                        btn_delete.setClickable(false);
                        btn_check_or_cancel.setText("全选");
                        break;
                    case BaseEditFragment.STATE_CHECK:
                        btn_delete.setAlpha(1.0f);
                        btn_delete.setClickable(true);
                        btn_check_or_cancel.setText("全选");
                        break;
                }
            }
        });
    }

    public BaseEditFragment getBaseEditFragment(MyInfo myInfo) {

        BaseEditFragment baseEditFragment = null;

        if (myInfo.getType() == MyType.HISTORY) {
            //浏览记录
            baseEditFragment = BrowsingHistoryFragment.newInstance();
            if (!TextUtils.isEmpty(myInfo.getTitle())) {
                getSupportActionBar().setTitle(myInfo.getTitle());
            }
        } else if (myInfo.getType() == MyType.COLLECT) {
            CollectTabInfo collectTabInfo = new CollectTabInfo();
            collectTabInfo.setType(FragmentType.COLLECT);
            CollectFragment collectFragment = CollectFragment.newInstance(collectTabInfo);
            collectFragment.setOnCollectFragmentListener(new CollectFragment.OnCollectFragmentListener() {
                @Override
                public void onPageSelected() {
                    closeEdit();
                }
            });
            baseEditFragment = collectFragment;

            if (!TextUtils.isEmpty(myInfo.getTitle())) {
                getSupportActionBar().setTitle(myInfo.getTitle());
            }
        }


        return baseEditFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.edit:
                int edit_state = mBaseEditFragment.getStateEdit();
                switch (edit_state) {
                    case BaseEditFragment.STATE_EDIT:
                        closeEdit();
                        break;
                    case BaseEditFragment.STATE_NO_EDIT:
                        openEdit();
                        break;
                }
                break;
            case R.id.btn_delete:
                mBaseEditFragment.delete();
                break;
            case R.id.btn_check_or_cancel:
                int check_state = mBaseEditFragment.getCheckState();
                switch (check_state) {
                    case BaseEditFragment.STATE_ALL_CHECK:
                        mBaseEditFragment.setCheckState(BaseEditFragment.STATE_NO_ALL_CHECK);
                        break;
                    case BaseEditFragment.STATE_NO_ALL_CHECK:
                    case BaseEditFragment.STATE_CHECK:
                        mBaseEditFragment.setCheckState(BaseEditFragment.STATE_ALL_CHECK);
                        break;
                }
                break;
        }
    }

    private void openEdit() {
        mBaseEditFragment.setStateEdit(BaseEditFragment.STATE_EDIT);
        mEdit.setText("取消编辑");
        btn_root.setVisibility(View.VISIBLE);
        btn_check_or_cancel.setText("全选");

        btn_delete.setAlpha(0.5f);
        btn_delete.setClickable(false);
    }

    private void closeEdit() {
        mBaseEditFragment.setStateEdit(BaseEditFragment.STATE_NO_EDIT);
        mEdit.setText("编辑");
        btn_root.setVisibility(View.GONE);
        btn_check_or_cancel.setText("全选");

        btn_delete.setAlpha(0.5f);
        btn_delete.setClickable(false);
    }

    @Override
    public void updateUiElements() {
        super.updateUiElements();

        setStatusBarColor();
        mToolbar.setBackgroundColor(getPrimaryColor());
        collect_content.setBackgroundColor(getBackgroundColor());
        btn_check_or_cancel.setTextColor(getTextColor());
        btn_delete.setTextColor(getTextColor());
        btn_root.setBackgroundColor(getCardBackgroundColor());
        mEdit.setTextColor(getTextColor());

        switch (getBaseTheme()) {
            case DARK:
            case AMOLED:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mAppBarLayout.setElevation(getResources().getDimension(R.dimen.card_elevation));
                }
                break;
            case LIGHT:
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mAppBarLayout.setElevation(0);
                }
                break;
        }
    }
}
