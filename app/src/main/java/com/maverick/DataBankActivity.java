package com.maverick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfei on 2017/9/29.
 */
public class DataBankActivity extends BaseActivity implements View.OnClickListener {

    private TextView edit;
    private BaseEditFragment mBaseEditFragment;
    private View btn_root;
    private Button btn_delete;
    private Button btn_check_or_cancel;
    private TextView title;

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
        View back = findView(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

        title = findView(R.id.title);
        title.setVisibility(View.VISIBLE);

        edit = findView(R.id.edit);
        edit.setOnClickListener(this);
        //TODO 等数据成功之后再显示
        edit.setVisibility(View.VISIBLE);

        btn_root = findView(R.id.btn_root);
        btn_delete = findView(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        btn_check_or_cancel = findView(R.id.btn_check_or_cancel);
        btn_check_or_cancel.setOnClickListener(this);
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

        if (TextUtils.equals(myInfo.getType(), "0")) {
            //浏览记录
            baseEditFragment = BrowsingHistoryFragment.newInstance();
            if (!TextUtils.isEmpty(myInfo.getTitle())) {
                title.setText(myInfo.getTitle());
            }
        } else if (TextUtils.equals(myInfo.getType(), "1")) {
            //收藏
            List<CollectTabInfo> list = new ArrayList<>();
//            list.add(getCollectTabInfo("笑话", 1));
//            list.add(getCollectTabInfo("美女", 2));
            list.add(getCollectTabInfo("百思不得姐", 6));
            list.add(getCollectTabInfo("漫画", 7));
            CollectFragment collectFragment = CollectFragment.newInstance(list);
            collectFragment.setOnCollectFragmentListener(new CollectFragment.OnCollectFragmentListener() {
                @Override
                public void onPageSelected() {
                    closeEdit();
                }
            });
            baseEditFragment = collectFragment;

            if (!TextUtils.isEmpty(myInfo.getTitle())) {
                title.setText(myInfo.getTitle());
            }
        }


        return baseEditFragment;
    }

    private CollectTabInfo getCollectTabInfo(String title, int type) {
        CollectTabInfo collectTabInfo = new CollectTabInfo();
        collectTabInfo.setTitle(title);
        collectTabInfo.setType(type);
        switch (type) {
            case 1:
                List<CollectTabInfo> list = new ArrayList<>();
                list.add(getCollectTabInfo("文本笑话", 3));
                list.add(getCollectTabInfo("图文笑话", 4));
                list.add(getCollectTabInfo("动图笑话", 5));
                collectTabInfo.setItemList(list);
                break;
        }
        return collectTabInfo;
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
        edit.setText("取消编辑");
        btn_root.setVisibility(View.VISIBLE);
        btn_check_or_cancel.setText("全选");

        btn_delete.setAlpha(0.5f);
        btn_delete.setClickable(false);
    }

    private void closeEdit() {
        mBaseEditFragment.setStateEdit(BaseEditFragment.STATE_NO_EDIT);
        edit.setText("编辑");
        btn_root.setVisibility(View.GONE);
        btn_check_or_cancel.setText("全选");

        btn_delete.setAlpha(0.5f);
        btn_delete.setClickable(false);
    }
}
