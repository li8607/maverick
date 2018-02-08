package com.maverick.dialog;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.maverick.R;
import com.maverick.adapter.MenuDialogAdapter;
import com.maverick.base.BaseDialogFragment;
import com.maverick.bean.MenuDetailInfo;
import com.maverick.bean.MenuItemInfo;
import com.maverick.global.Tag;
import com.maverick.model.CollectModel;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.MenuDialogPresenter;
import com.maverick.presenter.implView.IMenuDialogView;
import com.maverick.type.MenuType;
import com.maverick.util.DensityUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */
public class MenuDialog extends BaseDialogFragment implements DialogInterface.OnKeyListener, View.OnClickListener, IMenuDialogView {

    private String TAG = getClass().getSimpleName();
    private MenuDialogPresenter mPresenter;
    private MenuDetailInfo mMenuDetailInfo;
    private ProgressDialog mProgressDialog;
    private RecyclerView share_list, send_list;
    private MenuItemInfo mMenuItemInfo;
    private static final int MY_REQUEST_CODE = 100;

    public static MenuDialog newInstance(MenuDetailInfo menuDetailInfo) {
        MenuDialog dialog = new MenuDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag.KEY_INFO, menuDetailInfo);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_FullScreen);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new MenuDialogPresenter(getActivity(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.dialog_share;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mPresenter != null) {
            mPresenter.loadShareData(mMenuDetailInfo);
        }
    }

    @Override
    protected void onInitView(View view) {
        getDialog().setOnKeyListener(this);
        getDialog().setCanceledOnTouchOutside(true);
        Window window = getDialog().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        window.getAttributes().gravity = Gravity.BOTTOM;
        window.getAttributes().height = DensityUtil.dip2px(getContext(), 360);

        share_list = findView(R.id.share_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        share_list.setLayoutManager(layoutManager);

        share_list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = DensityUtil.dip2px(getContext(), 8);
                outRect.top = DensityUtil.dip2px(getContext(), 12);
                outRect.bottom = DensityUtil.dip2px(getContext(), 12);
            }
        });

        send_list = findView(R.id.send_list);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        send_list.setLayoutManager(layoutManager2);
        send_list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = DensityUtil.dip2px(getContext(), 8);
                outRect.top = DensityUtil.dip2px(getContext(), 12);
                outRect.bottom = DensityUtil.dip2px(getContext(), 12);
            }
        });

        View cancel = findView(R.id.cancel);
        cancel.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(getContext());
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mMenuDetailInfo = (MenuDetailInfo) getArguments().getSerializable(Tag.KEY_INFO);
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
//                dismiss();
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
    public void onShowShareView(List<MenuItemInfo> menuItemInfos) {
        MenuDialogAdapter mMenuDialogAdapter = new MenuDialogAdapter(getContext(), menuItemInfos);
        share_list.setAdapter(mMenuDialogAdapter);
        mMenuDialogAdapter.setOnOnListener(new MenuDialogAdapter.OnListener() {
            @Override
            public void onItemClick(View view, int position, MenuItemInfo menuItemInfo) {
                mMenuItemInfo = menuItemInfo;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//被拒绝过
//                            ExplainFragment.getInstance().show(getFragmentManager(), "");
                        } else {//第一次请求
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_REQUEST_CODE);
                            return;
                        }
                    }
                }
                share(menuItemInfo);
            }
        });
    }

    @Override
    public void onShowSendView(List<MenuItemInfo> send) {
        final MenuDialogAdapter mMenuDialogAdapter = new MenuDialogAdapter(getContext(), send);
        send_list.setAdapter(mMenuDialogAdapter);
        mMenuDialogAdapter.setOnOnListener(new MenuDialogAdapter.OnListener() {
            @Override
            public void onItemClick(View view, int position, MenuItemInfo menuItemInfo) {

                if (menuItemInfo == null) {
                    return;
                }

                switch (menuItemInfo.getMenuType()) {
                    case MenuType.SEND_COLLENT:
                        if (mMenuDetailInfo == null) {
                            return;
                        }

                        menuItemInfo.setCollect(!menuItemInfo.isCollect());
                        if (menuItemInfo.isCollect()) {
                            CollectModel.newInstance().insertCollectDB(mMenuDetailInfo.getCollect());
                        } else {
                            CollectModel.newInstance().deleteCollectDB(mMenuDetailInfo.getCollect());
                        }

                        RecyclerView.ViewHolder viewHolder = send_list.findViewHolderForAdapterPosition(position);

                        if (viewHolder != null && viewHolder instanceof MenuDialogAdapter.ShareViewHolder) {
                            MenuDialogAdapter.ShareViewHolder shareViewHolder = (MenuDialogAdapter.ShareViewHolder) viewHolder;
                            shareViewHolder.updateCollectUI(menuItemInfo);
                        } else {
                            mMenuDialogAdapter.notifyItemChanged(position);
                        }
                        break;
                }
            }
        });
    }

    private void share(MenuItemInfo menuItemInfo) {
        if (menuItemInfo == null) {
            return;
        }
        switch (menuItemInfo.getMenuType()) {
            case MenuType.SHARE_WEIXIN:
                mPresenter.shareWechat(mMenuDetailInfo);
                break;
            case MenuType.SHARE_WEIXIN_CIRCLE:
                mPresenter.shareWxcircle(mMenuDetailInfo);
                break;
            case MenuType.SHARE_SINA:
                mPresenter.shareSina(mMenuDetailInfo);
                break;
            case MenuType.SHARE_QZONE:
                mPresenter.shareQzone(mMenuDetailInfo);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_REQUEST_CODE:
                share(mMenuItemInfo);
                break;
        }
    }

    public interface OnShareDialogListener {
        void onDismiss();
    }
}
