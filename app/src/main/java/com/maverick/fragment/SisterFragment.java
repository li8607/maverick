package com.maverick.fragment;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.maverick.R;
import com.maverick.adapter.SisterFragmentAdapter;
import com.maverick.adapter.holder.SisterTextHolder;
import com.maverick.adapter.holder.SisterVideoHolder;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.ShareInfo;
import com.maverick.bean.SisterDetailInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.dialog.ShareDialog;
import com.maverick.global.Tag;
import com.maverick.model.CollectModel;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.SisterFragmentPresenter;
import com.maverick.presenter.implView.ISisterFragmentView;
import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SisterFragment extends BaseFragment2 implements ISisterFragmentView {

    private SisterFragmentAdapter mSisterFragmentAdapter;
    private SisterFragmentPresenter mPresenter;
    private SisterDetailInfo mSisterDetailInfo;
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private ListVideoUtil listVideoUtil;

    int lastVisibleItem;
    int firstVisibleItem;
    private ViewGroup root;

    public static SisterFragment newInstance(SisterDetailInfo sisterDetailInfo) {
        SisterFragment fragment = new SisterFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag.KEY_INFO, sisterDetailInfo);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new SisterFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_sister;
    }

    @Override
    protected void onInitView(View view) {

        root = findView(R.id.root);

        pullLoadMoreRecyclerView = findView(R.id.recyclerView);
        RecyclerView recyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mSisterFragmentAdapter = new SisterFragmentAdapter(getContext());
        recyclerView.setAdapter(mSisterFragmentAdapter);

        mSisterFragmentAdapter.setOnSisterTextHolderListener(new SisterTextHolder.OnSisterTextHolderListener() {
            @Override
            public void onShareClick(final View view, SisterInfo sisterInfo) {

                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setImageurl(sisterInfo.getImage2());
                shareInfo.setTitle(sisterInfo.getText());

                ShareDialog shareDialog = ShareDialog.newInstance(shareInfo);
                shareDialog.setOnDismissListener(new ShareDialog.OnShareDialogListener() {

                    @Override
                    public void onDismiss() {
                        view.setSelected(false);
                    }
                });

                showDialogFragment(shareDialog);
                view.setSelected(true);
            }
        });

        listVideoUtil = new ListVideoUtil(getContext());
        listVideoUtil.setFullViewContainer((ViewGroup) getActivity().findViewById(R.id.video_full_container));
        listVideoUtil.setHideStatusBar(true);
        mSisterFragmentAdapter.setListVideoUtil(listVideoUtil);

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData(mSisterDetailInfo);
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMoreData();
            }
        });

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = getResources().getDimensionPixelSize(R.dimen.x1);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                Debuger.printfLog("firstVisibleItem " + firstVisibleItem + " lastVisibleItem " + lastVisibleItem);
                //大于0说明有播放,//对应的播放列表TAG
                if (listVideoUtil.getPlayPosition() >= 0 && listVideoUtil.getPlayTAG().equals(SisterVideoHolder.TAG)) {
                    //当前播放的位置
                    int position = listVideoUtil.getPlayPosition();
                    //不可视的是时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果是小窗口就不需要处理
                        if (!listVideoUtil.isSmall() && !listVideoUtil.isFull()) {
                            //小窗口
                            int size = CommonUtil.dip2px(getContext(), 150);
                            //actionbar为true才不会掉下面去
                            listVideoUtil.showSmallVideo(new Point(size, size), true, true);
                        }
                    } else {
                        if (listVideoUtil.isSmall()) {
                            listVideoUtil.smallVideoToNormal();
                        }
                    }
                }
            }
        });

        listVideoUtil.setVideoAllCallBack(new StandardVideoAllCallBack() {
            @Override
            public void onPrepared(String url, Object... objects) {

            }

            @Override
            public void onClickStartIcon(String url, Object... objects) {

            }

            @Override
            public void onClickStartError(String url, Object... objects) {

            }

            @Override
            public void onClickStop(String url, Object... objects) {

            }

            @Override
            public void onClickStopFullscreen(String url, Object... objects) {

            }

            @Override
            public void onClickResume(String url, Object... objects) {

            }

            @Override
            public void onClickResumeFullscreen(String url, Object... objects) {

            }

            @Override
            public void onClickSeekbar(String url, Object... objects) {

            }

            @Override
            public void onClickSeekbarFullscreen(String url, Object... objects) {

            }

            @Override
            public void onAutoComplete(String url, Object... objects) {

            }

            @Override
            public void onEnterFullscreen(String url, Object... objects) {

            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {

            }

            @Override
            public void onQuitSmallWidget(String url, Object... objects) {
                //大于0说明有播放,//对应的播放列表TAG
                if (listVideoUtil.getPlayPosition() >= 0 && listVideoUtil.getPlayTAG().equals(SisterVideoHolder.TAG)) {
                    //当前播放的位置
                    int position = listVideoUtil.getPlayPosition();
                    //不可视的是时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //释放掉视频
                        listVideoUtil.releaseVideoPlayer();
                        mSisterFragmentAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onEnterSmallWidget(String url, Object... objects) {

            }

            @Override
            public void onTouchScreenSeekVolume(String url, Object... objects) {

            }

            @Override
            public void onTouchScreenSeekPosition(String url, Object... objects) {

            }

            @Override
            public void onTouchScreenSeekLight(String url, Object... objects) {

            }

            @Override
            public void onPlayError(String url, Object... objects) {

            }

            @Override
            public void onClickStartThumb(String url, Object... objects) {

            }

            @Override
            public void onClickBlank(String url, Object... objects) {

            }

            @Override
            public void onClickBlankFullscreen(String url, Object... objects) {

            }
        });
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mSisterDetailInfo = (SisterDetailInfo) getArguments().getSerializable(Tag.KEY_INFO);
//        mPresenter.refreshData(mSisterDetailInfo);
        pullLoadMoreRecyclerView.setRefreshing(true);
        pullLoadMoreRecyclerView.refresh();
    }

    @Override
    public void onShowSuccessView(List<SisterInfo> sisterInfos) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(true);
        mSisterFragmentAdapter.setData(sisterInfos);
        mSisterFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowEmptyView() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();

        View view = View.inflate(root.getContext(), R.layout.view_empty, null);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        root.addView(view, layoutParams);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.removeView(v);
                pullLoadMoreRecyclerView.setRefreshing(true);
                pullLoadMoreRecyclerView.refresh();
            }
        });
    }

    @Override
    public void onShowErrorView() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();

        View view = View.inflate(root.getContext(), R.layout.view_error, null);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;

        root.addView(view, layoutParams);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.removeView(v);
                pullLoadMoreRecyclerView.setRefreshing(true);
                pullLoadMoreRecyclerView.refresh();
            }
        });
    }

    @Override
    public void onLoadMoreSuccess(List<SisterInfo> list, boolean isHasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(isHasMore);
        int positionStart = mSisterFragmentAdapter.getItemCount();
        mSisterFragmentAdapter.setMoreData(list);
        mSisterFragmentAdapter.notifyItemRangeInserted(positionStart, list.size());
    }

    @Override
    public void onLoadMoreFail() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (listVideoUtil != null && listVideoUtil.getGsyVideoPlayer() != null) {
            if (isVisibleToUser) {
                listVideoUtil.getGsyVideoPlayer().onVideoResume();
            } else {
                listVideoUtil.getGsyVideoPlayer().onVideoPause();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (listVideoUtil.getGsyVideoPlayer() != null) {
                if (listVideoUtil.isSmall()) {
                    listVideoUtil.getGsyVideoPlayer().hideSmallVideo();
                } else {
                    listVideoUtil.getGsyVideoPlayer().onVideoPause();
                }
            }
        } else {
            if (listVideoUtil.getGsyVideoPlayer() != null) {
                listVideoUtil.getGsyVideoPlayer().onVideoResume();
            }
        }
    }

    @Override
    public boolean onBackPressed() {

        if (listVideoUtil.backFromFull()) {
            return true;
        }

        return super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (listVideoUtil.getGsyVideoPlayer() != null) {
            listVideoUtil.getGsyVideoPlayer().onVideoPause();
        }
        CollectModel.newInstance().addOnCollectListener(mListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listVideoUtil.getGsyVideoPlayer() != null) {
            listVideoUtil.getGsyVideoPlayer().onVideoResume();
        }

        CollectModel.newInstance().removeOnCollectListener(mListener);
    }

    private CollectModel.OnCollectListener mListener = new CollectModel.OnCollectListener() {

        @Override
        public void onChange() {
            if (mSisterFragmentAdapter != null && mSisterFragmentAdapter.getData() != null && mSisterFragmentAdapter.getData().size() > 0) {
                for (int i = 0; i < mSisterFragmentAdapter.getData().size(); i++) {
                    SisterInfo sisterInfo = mSisterFragmentAdapter.getData().get(i);
                    Collect collect = new Collect();
                    collect.setCollectMajorKey(sisterInfo.getId());
                    sisterInfo.setCollect(CollectModel.newInstance().hasCollectDB(collect));
                }
                mSisterFragmentAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listVideoUtil.releaseVideoPlayer();
        GSYVideoPlayer.releaseAllVideos();
    }
}
