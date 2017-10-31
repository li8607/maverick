package com.maverick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.maverick.base.BaseActivity;
import com.maverick.bean.PearVideoDetailBean;
import com.maverick.fragment.PearBottomFragment;
import com.maverick.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/10/31.
 */

public class PearActivity extends BaseActivity {

    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";

    public static void launch(Activity activity, PearVideoDetailBean info) {

        if (activity == null || info == null) {
            return;
        }

        Intent intent = new Intent(activity, PearActivity.class);
        intent.putExtra(EXTRA_IMAGE, info);
        activity.startActivity(intent);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //TODO 隐藏状态栏
        }
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_pear;
    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        PearVideoDetailBean mInfo = (PearVideoDetailBean) getIntent().getSerializableExtra(EXTRA_IMAGE);
        Toast.makeText(this, mInfo.getName(), Toast.LENGTH_SHORT).show();
        replaceFragment(R.id.bottom, PearBottomFragment.newInstance(mInfo));
    }
}
