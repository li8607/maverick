package com.maverick.dialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.maverick.R;
import com.maverick.base.BaseDialogFragment;
import com.maverick.bean.ShareInfo;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.ShareDialogPresenter;
import com.maverick.presenter.implView.IShareDialogView;

/**
 * Created by Administrator on 2017/10/15.
 */
public class ShareDialog extends BaseDialogFragment implements DialogInterface.OnKeyListener, View.OnClickListener, IShareDialogView {

    private String TAG = getClass().getSimpleName();
    private ShareDialogPresenter mPresenter;
    private ShareInfo mShareInfo;
    private ProgressDialog mProgressDialog;

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

        View wechat = findView(R.id.wechat);
        View wxcircle = findView(R.id.wxcircle);
        View sina = findView(R.id.sina);
        View qzone = findView(R.id.qzone);

        wechat.setOnClickListener(this);
        wxcircle.setOnClickListener(this);
        sina.setOnClickListener(this);
        qzone.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(getContext());
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mShareInfo = (ShareInfo) getArguments().getSerializable(Tag.KEY_INFO);
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
            case R.id.wechat:
                mPresenter.shareWechat(mShareInfo);
                break;
            case R.id.wxcircle:
                mPresenter.shareWxcircle(mShareInfo);
                break;
            case R.id.sina:
                mPresenter.shareSina(mShareInfo);
                break;
            case R.id.qzone:
                mPresenter.shareQzone(mShareInfo);
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

    public interface OnShareDialogListener {
        void onDismiss();
    }
}
