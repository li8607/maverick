package com.maverick.adapter.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.maverick.R;
import com.maverick.adapter.SisterItemFragmentAdapter;
import com.maverick.bean.SisterInfo;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;


/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterVideoHolder extends SisterTextHolder {

    public final static String TAG = "RecyclerView2List";

    private SisterInfo mSisterInfo;
    private final ImageView image;
    private final ViewGroup video_root;
    private ListVideoUtil listVideoUtil;
    private final View list_item_btn;
    private final WebView webView;

    public SisterVideoHolder(View itemView) {
        super(itemView);
        image = new ImageView(itemView.getContext());
        list_item_btn = itemView.findViewById(R.id.list_item_btn);

        list_item_btn.setOnClickListener(this);

        video_root = (ViewGroup) itemView.findViewById(R.id.video_root);

        webView = (WebView) itemView.findViewById(R.id.webView);
    }

    public void bindData(Context context, SisterInfo sisterInfo) {
        super.bindData(context, sisterInfo);
        this.mSisterInfo = sisterInfo;
        listVideoUtil.addVideoPlayer(getAdapterPosition(), image, TAG, video_root, list_item_btn);

        if (TextUtils.isEmpty(mSisterInfo.getText()) && !TextUtils.isEmpty(mSisterInfo.getWeixin_url())) {
            webView.loadUrl(mSisterInfo.getWeixin_url());
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (!TextUtils.isEmpty(view.getTitle()) && mSisterInfo.getWeixin_url().contains(url)) {
                        String temp = view.getTitle();
                        String title = temp.split("-")[0];
                        content.setText(title);
                        mSisterInfo.setText(title);
                    }
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.list_item_btn:
                openVideo(v);
                break;
        }
    }

    private void openVideo(View v) {

        if (mSisterInfo.getVideo_uri() == null) {
            return;
        }

        listVideoUtil.setPlayPositionAndTag(getAdapterPosition(), TAG);
        listVideoUtil.setTitle(mSisterInfo.getText());
        listVideoUtil.startPlay(mSisterInfo.getVideo_uri());

        sisterItemFragmentAdapter.notifyDataSetChanged();
    }

    public void setListVideoUtil(ListVideoUtil listVideoUtil) {
        this.listVideoUtil = listVideoUtil;
    }

    private SisterItemFragmentAdapter sisterItemFragmentAdapter;

    public void setRecyclerAdapter(SisterItemFragmentAdapter sisterItemFragmentAdapter) {
        this.sisterItemFragmentAdapter = sisterItemFragmentAdapter;
    }
}
