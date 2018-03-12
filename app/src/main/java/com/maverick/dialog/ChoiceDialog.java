package com.maverick.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.adapter.ChoiceDialogAdapter;
import com.maverick.base.BaseDialogFragment;
import com.maverick.presenter.BasePresenter;

/**
 * Created by maver on 2018/3/8.
 */

public class ChoiceDialog extends BaseDialogFragment {

    public static final int SINGLE = 1;
    public static final int MULTI = 2;
    private TextView mTvTitle;
    private RecyclerView mRecyclerView;
    private TextView mSure;
    private TextView mCancel;
    private View mBtn_root;
    private Toolbar mToolbar;
    private View mLlLineTopBtnRoot;

    public static ChoiceDialog newInstance(int type, String title, String[] items, String[] item, String btn1, String btn2) {
        ChoiceDialog dialog = new ChoiceDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("title", title);
        bundle.putStringArray("items", items);
        bundle.putStringArray("item", item);
        bundle.putString("btn1", btn1);
        bundle.putString("btn2", btn2);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.dialog_choice;
    }

    @Override
    protected void onInitView(View view) {

        mToolbar = findView(R.id.tb_dialog_theme);

        mTvTitle = view.findViewById(R.id.tv_title);
        mRecyclerView = view.findViewById(R.id.rv_choice);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

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

        int type = getArguments().getInt("type", 0);
        String title = getArguments().getString("title");
        String[] items = getArguments().getStringArray("items");
        String[] item = getArguments().getStringArray("item");
        String btn1 = getArguments().getString("btn1");
        String btn2 = getArguments().getString("btn2");

        if (!TextUtils.isEmpty(title)) {
            mToolbar.setTitle(title);
            mToolbar.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(btn1)) {
            mSure.setText(btn1);
            mSure.setVisibility(View.VISIBLE);
            mCancel.setVisibility(View.GONE);
            mBtn_root.setVisibility(View.VISIBLE);
            mLlLineTopBtnRoot.setVisibility(View.VISIBLE);

            mSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnSureListener != null) {
                        mOnSureListener.onSureClick(ChoiceDialog.this);
                    }
                }
            });
        }

        if (!TextUtils.isEmpty(btn2)) {
            mCancel.setText(btn2);
            mSure.setVisibility(View.GONE);
            mCancel.setVisibility(View.VISIBLE);
            mBtn_root.setVisibility(View.VISIBLE);
            mLlLineTopBtnRoot.setVisibility(View.VISIBLE);

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnCancelListener != null) {
                        mOnCancelListener.onCancelClick(ChoiceDialog.this);
                    }
                }
            });
        }

        if (items != null) {
            mRecyclerView.setVisibility(View.VISIBLE);
            ChoiceDialogAdapter mChoiceDialogAdapter = new ChoiceDialogAdapter(getContext(), items, item);
            mChoiceDialogAdapter.setOnItemClickListener(new ChoiceDialogAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView.ViewHolder viewHolder, int position, boolean isChecked) {
                    if (mOnMultiChoiceClickListener != null) {
                        mOnMultiChoiceClickListener.onClick(ChoiceDialog.this, position, isChecked);
                    }
                }
            });
            mChoiceDialogAdapter.setType(type);
            mRecyclerView.setAdapter(mChoiceDialogAdapter);
        }
    }

    private OnMultiChoiceClickListener mOnMultiChoiceClickListener;

    public void setOnMultiChoiceClickListener(OnMultiChoiceClickListener listener) {
        this.mOnMultiChoiceClickListener = listener;
    }

    public interface OnMultiChoiceClickListener {
        void onClick(DialogFragment dialogFragment, int position, boolean isChecked);
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
}
