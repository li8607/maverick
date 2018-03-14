package com.maverick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.maverick.base.BaseActivity;
import com.maverick.factory.FragmentFactory;
import com.maverick.presenter.BasePresenter;

/**
 * Created by limingfei on 2018/3/14.
 */

public class FragmentActivity extends BaseActivity {

    public static void launch(Activity activity, CharSequence title, int type) {
        Intent intent = new Intent(activity, FragmentActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onInitView() {
        Toolbar mToolbar = findView(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        if (getIntent() != null) {
            CharSequence title = getIntent().getCharSequenceExtra("title");
            getSupportActionBar().setTitle(title);
            replaceFragment(R.id.fl_content, FragmentFactory.getFragment(getIntent().getIntExtra("type", 0)));
        }
    }
}
