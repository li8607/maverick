package com.maverick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maverick.base.BaseActivity;
import com.maverick.bean.CollectTabInfo;
import com.maverick.fragment.CollectFragment;
import com.maverick.fragment.CollectItemFragment;
import com.maverick.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limingfei on 2017/9/29.
 */
public class CollectActivity extends BaseActivity implements View.OnClickListener {

    private TextView edit;
    private CollectFragment mCollectFragment;
    private View btn_root;
    private Button btn_delete;
    private Button btn_check_or_cancel;

    public static void launch(Context context) {
        Intent intent = new Intent(context, CollectActivity.class);
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
        back.setOnClickListener(this);
        TextView title = findView(R.id.title);
        title.setText("收藏");

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
        List<CollectTabInfo> list = new ArrayList<>();
        list.add(getCollectTabInfo("笑话", 1));
        list.add(getCollectTabInfo("美女", 2));
        mCollectFragment = CollectFragment.newInstance(list);
        replaceFragment(R.id.collect_content, mCollectFragment);

        mCollectFragment.setOnCollectFragmentListener(new CollectFragment.OnCollectFragmentListener() {
            @Override
            public void onPageSelected() {
                closeEdit();
            }

            @Override
            public void onCheckState(int checkState) {
                switch (checkState) {
                    case CollectItemFragment.STATE_ALL_CHECK:
                        btn_delete.setAlpha(1.0f);
                        btn_delete.setClickable(true);
                        btn_check_or_cancel.setText("取消全选");
                        break;
                    case CollectItemFragment.STATE_NO_ALL_CHECK:
                        btn_delete.setAlpha(0.5f);
                        btn_delete.setClickable(false);
                        btn_check_or_cancel.setText("全选");
                        break;
                    case CollectItemFragment.STATE_CHECK:
                        btn_delete.setAlpha(1.0f);
                        btn_delete.setClickable(true);
                        btn_check_or_cancel.setText("全选");
                        break;
                }
            }
        });
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
                int edit_state = mCollectFragment.getStateEdit();
                switch (edit_state) {
                    case CollectItemFragment.STATE_EDIT:
                        closeEdit();
                        break;
                    case CollectItemFragment.STATE_NO_EDIT:
                        openEdit();
                        break;
                }
                break;
            case R.id.btn_delete:
                mCollectFragment.delete();
                break;
            case R.id.btn_check_or_cancel:
                int check_state = mCollectFragment.getCheckState();
                switch (check_state) {
                    case CollectItemFragment.STATE_ALL_CHECK:
                        mCollectFragment.setCheckState(CollectItemFragment.STATE_NO_ALL_CHECK);
                        break;
                    case CollectItemFragment.STATE_NO_ALL_CHECK:
                    case CollectItemFragment.STATE_CHECK:
                        mCollectFragment.setCheckState(CollectItemFragment.STATE_ALL_CHECK);
                        break;
                }
                break;
        }
    }

    private void openEdit() {
        mCollectFragment.setStateEdit(CollectItemFragment.STATE_EDIT);
        edit.setText("取消编辑");
        btn_root.setVisibility(View.VISIBLE);
        btn_check_or_cancel.setText("全选");

        btn_delete.setAlpha(0.5f);
        btn_delete.setClickable(false);
    }

    private void closeEdit() {
        mCollectFragment.setStateEdit(CollectItemFragment.STATE_NO_EDIT);
        edit.setText("编辑");
        btn_root.setVisibility(View.GONE);
        btn_check_or_cancel.setText("全选");

        btn_delete.setAlpha(0.5f);
        btn_delete.setClickable(false);
    }
}
