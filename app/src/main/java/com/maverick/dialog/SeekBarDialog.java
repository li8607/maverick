package com.maverick.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.base.BaseDialogFragment;
import com.maverick.presenter.BasePresenter;

/**
 * Created by limingfei on 2018/3/12.
 */

public class SeekBarDialog extends BaseDialogFragment {

    private Toolbar mToolbar;
    private SeekBar mSeekBar;
    private TextView mTvProgress;
    private TextView mSure;
    private TextView mCancel;
    private View mBtn_root;
    private View mLlLineTopBtnRoot;

    public static SeekBarDialog newInstance(String title, int progress, int max, String sure, String cancel) {
        SeekBarDialog dialog = new SeekBarDialog();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("sure", sure);
        bundle.putString("cancel", cancel);
        bundle.putInt("progress", progress);
        bundle.putInt("max", max);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.dialog_seek_bar;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void onInitView(View view) {
        mToolbar = findView(R.id.tb_dialog_theme);
        mSeekBar = findView(R.id.sb_progress);
        mTvProgress = findView(R.id.tv_progress);

        mSure = findView(R.id.tv_sure);
        mCancel = findView(R.id.tv_cancel);
        mBtn_root = findView(R.id.ll_btn_root);

        mLlLineTopBtnRoot = findView(R.id.ll_line_top_btn_root);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        if (getArguments() == null) {
            return;
        }

        String title = getArguments().getString("title", "");
        String sure = getArguments().getString("sure", "");
        String cancel = getArguments().getString("cancel", "");
        int progress = getArguments().getInt("progress", 0);
        int max = getArguments().getInt("max", 100);
        mToolbar.setTitle(title);
        mSeekBar.setProgress(progress);
        mSeekBar.setMax(max);
        mTvProgress.setText(progress + "/" + max);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvProgress.setText(progress + "/" + seekBar.getMax());
                if (mOnProgressChangeListener != null) {
                    mOnProgressChangeListener.onProgressChanged(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if (!TextUtils.isEmpty(sure)) {
            mSure.setText(sure);
            mSure.setVisibility(View.VISIBLE);
            mBtn_root.setVisibility(View.VISIBLE);
            mLlLineTopBtnRoot.setVisibility(View.VISIBLE);

            mSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnSureListener != null) {
                        mOnSureListener.onSureClick(SeekBarDialog.this);
                    }
                }
            });
        }

        if (!TextUtils.isEmpty(cancel)) {
            mCancel.setText(cancel);
            mCancel.setVisibility(View.VISIBLE);
            mBtn_root.setVisibility(View.VISIBLE);
            mLlLineTopBtnRoot.setVisibility(View.VISIBLE);

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnCancelListener != null) {
                        mOnCancelListener.onCancelClick(SeekBarDialog.this);
                    }
                }
            });
        }
    }

    private OnCancelListener mOnCancelListener;

    public void setOnCancelListener(OnCancelListener listener) {
        this.mOnCancelListener = listener;
    }

    public interface OnCancelListener {
        void onCancelClick(DialogFragment dialogFragment);
    }

    private OnSureListener mOnSureListener;

    public void setOnSureListener(OnSureListener listener) {
        this.mOnSureListener = listener;
    }

    public interface OnSureListener {
        void onSureClick(DialogFragment dialogFragment);
    }

    private OnProgressChangeListener mOnProgressChangeListener;

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.mOnProgressChangeListener = listener;
    }

    public interface OnProgressChangeListener {
        void onProgressChanged(int progress);
    }
}
