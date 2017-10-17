package com.maverick.dialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.maverick.R;
import com.maverick.adapter.ShareDialogAdapter;
import com.maverick.base.BaseDialogFragment;
import com.maverick.bean.ShareInfo;
import com.maverick.bean.ShareItemInfo;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.ShareDialogPresenter;
import com.maverick.presenter.implView.IShareDialogView;
import com.maverick.type.ShareType;

import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */
public class ShareDialog extends BaseDialogFragment implements DialogInterface.OnKeyListener, View.OnClickListener, IShareDialogView {

    private String TAG = getClass().getSimpleName();
    private ShareDialogPresenter mPresenter;
    private ShareInfo mShareInfo;
    private ProgressDialog mProgressDialog;
    private RecyclerView share_list;

    public static ShareDialog newInstance(ShareInfo shareInfo) {
        ShareDialog dialog = new ShareDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag.KEY_INFO, shareInfo);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new ShareDialogPresenter(getActivity(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.dialog_share;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0xff000000));
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }

    @Override
    protected void onInitView(View view) {
        getDialog().setOnKeyListener(this);
        getDialog().setCanceledOnTouchOutside(true);
        Window window = getDialog().getWindow();
        window.getAttributes().windowAnimations = R.style.dialogAnim;
        window.getAttributes().gravity = Gravity.BOTTOM;

        share_list = findView(R.id.share_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        share_list.setLayoutManager(layoutManager);

        share_list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = view.getResources().getDimensionPixelOffset(R.dimen.y6);
                outRect.top = view.getResources().getDimensionPixelOffset(R.dimen.y10);
                outRect.bottom = view.getResources().getDimensionPixelOffset(R.dimen.y10);
            }
        });


        View cancel = findView(R.id.cancel);
        cancel.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(getContext());
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mShareInfo = (ShareInfo) getArguments().getSerializable(Tag.KEY_INFO);
        mPresenter.loadShareData();
    }

    private OnShareDialogListener mOnShareDialogListener;

    public void setOnDismissListener(OnShareDialogListener listener) {
        this.mOnShareDialogListener = listener;
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mOnShareDialogListener != null) {
            mOnShareDialogListener.onDismiss();
        }
        super.onDismiss(dialog);
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                dismiss();
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onShowShareView(List<ShareItemInfo> shareItemInfos) {
        ShareDialogAdapter mShareDialogAdapter = new ShareDialogAdapter(getContext(), shareItemInfos);
        share_list.setAdapter(mShareDialogAdapter);
        mShareDialogAdapter.setOnOnListener(new ShareDialogAdapter.OnListener() {
            @Override
            public void onItemClick(ShareItemInfo shareItemInfo) {
                switch (shareItemInfo.getShareType()) {
                    case ShareType.WEIXIN:
                        mPresenter.shareWechat(mShareInfo);
                        break;
                    case ShareType.WEIXIN_CIRCLE:
                        mPresenter.shareWxcircle(mShareInfo);
                        break;
                    case ShareType.SINA:
                        mPresenter.shareSina(mShareInfo);
                        break;
                    case ShareType.QZONE:
                        mPresenter.shareQzone(mShareInfo);
                        break;
                }
            }
        });
    }

    public interface OnShareDialogListener {
        void onDismiss();
    }
}
