package com.maverick;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.maverick.base.BaseActivity;
import com.maverick.bean.MenuDetailInfo;
import com.maverick.bean.WebDetailInfo;
import com.maverick.dialog.MenuDialog;
import com.maverick.presenter.BasePresenter;
import com.maverick.type.ShareType;

/**
 * Created by limingfei on 2017/10/20.
 */
public class WebActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = getClass().getSimpleName();

    public static final String INFO = "WebActivity";
    private WebView web;
    private WebDetailInfo mInfo;
    private Toolbar mToolbar;
    private TextView mTitle;

    public static void launch(Context context, WebDetailInfo info) {

        if (context == null || info == null || TextUtils.isEmpty(info.getWebUrl())) {
            return;
        }

        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(INFO, info);
        context.startActivity(intent);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void onInitView() {
        web = findView(R.id.web);
        setCommonWebView(web);

//        View image_share = findViewById(R.id.image_share);
//        image_share.setOnClickListener(this);

        mToolbar = findView(R.id.toolbar_actionbar);
        mTitle = new TextView(this);
        mTitle.setLines(1);
        mTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.y10));

        mTitle.setTextColor(getResources().getColor(R.color.colorWhite));
        Toolbar.LayoutParams mTitleLP = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTitleLP.gravity = Gravity.CENTER;
        mTitleLP.rightMargin = getResources().getDimensionPixelSize(R.dimen.y10);
        mTitle.setVisibility(View.GONE);
        mToolbar.addView(mTitle, mTitleLP);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        mInfo = (WebDetailInfo) getIntent().getSerializableExtra(INFO);
        if (mInfo == null || TextUtils.isEmpty(mInfo.getWebUrl())) {
            return;
        }

        web.setWebViewClient(mWebViewClient);
        web.setWebChromeClient(mWebChromeClient);
        web.loadUrl(mInfo.getWebUrl());


        Log.d(TAG, "HtmlUrl = " + mInfo.getWebUrl());
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!TextUtils.isEmpty(title)) {
                mTitle.setText(title);
                mTitle.setVisibility(View.VISIBLE);
            }
        }
    };

    private WebViewClient mWebViewClient = new WebViewClient() {

    };

    public static void setCommonWebView(WebView webView) {

        if (webView == null) {
            return;
        }

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setSupportZoom(true);  //支持缩放
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadsImagesAutomatically(true);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // 用于判断是否为Android 3.0系统,隐藏缩放控件
            webView.getSettings().setDisplayZoomControls(false);
            webView.getSettings().setAllowContentAccess(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.image_share:
                openMenuDialog(v, mInfo);
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if (web.canGoBack()) {
            web.goBack();
            return;
        }

        super.onBackPressed();
    }

    private void openMenuDialog(final View v, WebDetailInfo info) {
        if (info == null) {
            return;
        }

        MenuDetailInfo menuDetailInfo = new MenuDetailInfo();
        menuDetailInfo.setShareType(ShareType.IMAGE_TEXT);
        menuDetailInfo.setTitle(info.getTitle());
        menuDetailInfo.setImageurl(info.getImageUrl());
        menuDetailInfo.setCollect(info.getCollect());
        MenuDialog dialog = MenuDialog.newInstance(menuDetailInfo);
        dialog.setOnDismissListener(new MenuDialog.OnShareDialogListener() {
            @Override
            public void onDismiss() {
                if (v != null) {
                    v.setSelected(false);
                }
            }
        });
        showDialogFragment(dialog);
        if (v != null) {
            v.setSelected(true);
        }
    }

    @Override
    public void updateUiElements() {
        super.updateUiElements();
        mTitle.setTextColor(getTextColor());
        mToolbar.setBackgroundColor(getPrimaryColor());
        setStatusBarColor();
    }
}
