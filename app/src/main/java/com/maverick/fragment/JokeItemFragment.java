package com.maverick.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.adapter.JokeItemFragmentAdapter;
import com.maverick.adapter.holder.JokeTextViewHolder;
import com.maverick.base.BaseFragment;
import com.maverick.bean.BigImgInfo;
import com.maverick.bean.GifInfo;
import com.maverick.bean.JokeTabInfo;
import com.maverick.global.SPKey;
import com.maverick.global.Tag;
import com.maverick.hepler.BeanHelper;
import com.maverick.hepler.SpeechHelper;
import com.maverick.model.CollectModel;
import com.maverick.presenter.BasePresenter;
import com.maverick.presenter.JokeItemFragmentPresenter;
import com.maverick.presenter.implView.IJokeItemFragmentView;
import com.maverick.util.PreferenceUtil;
import com.maverick.weight.ControlSpeechView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.maverick.adapter.JokeItemFragmentAdapter.JOKE_TEXT;

/**
 * Created by ll on 2017/5/22.
 */
public class JokeItemFragment extends BaseFragment implements IJokeItemFragmentView, SpeechHelper.OnSpeechInitListener, SpeechHelper.OnSpeechListener {

    private JokeItemFragmentPresenter mPresenter;
    private JokeItemFragmentAdapter mJokeItemFragmentAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private FrameLayout root;
    private JokeTabInfo mJokeTabInfo;
    private GifInfo mSpeechInfo;
    private SpeechHelper mSpeechHelper;
    private RecyclerView mRecyclerView;
    private boolean pause;
    private ControlSpeechView mControlSpeechView;
    private int bottom;
    private int childHeight;

    public static JokeItemFragment newInstance(JokeTabInfo jokeTabInfo) {
        JokeItemFragment fragment = new JokeItemFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag.KEY_INFO, jokeTabInfo);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        mPresenter = new JokeItemFragmentPresenter(getContext(), this);
        return mPresenter;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_joke_item;
    }

    @Override
    protected void onInitView(final View view) {
        bottom = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getContext().getResources().getDisplayMetrics()));
        childHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 42, getContext().getResources().getDisplayMetrics()));

        root = findView(R.id.root);

        mControlSpeechView = findView(R.id.controlSpeechView);
        mControlSpeechView.setOnItemClickListener(new ControlSpeechView.OnItemClickListener() {
            @Override
            public void onPlayClick() {
                if (mSpeechInfo != null && mSpeechHelper.isSpeaking()) {
                    if (!mSpeechHelper.isPause()) {
                        mSpeechHelper.pauseSpeaking();
                    } else {
                        mSpeechHelper.resumeSpeaking();
                    }
                }
            }

            @Override
            public void onStopClick() {
                if (!mSpeechHelper.isSpeaking()) {
                    return;
                }
                updateUI(STOP);
            }
        });

        pullLoadMoreRecyclerView = findView(R.id.recyclerView);

        mRecyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(mSpeechInfo == null) {
                    return;
                }

                View stickview = recyclerView.findChildViewUnder(bottom, 0);
                if(stickview != null) {
                    int position = recyclerView.getChildAdapterPosition(stickview);
                    if(position == mPresenter.getData().indexOf(mSpeechInfo)) {
                        int height = stickview.getMeasuredHeight();
                        int top = stickview.getTop();
                        if(top + height <= childHeight){
                            mControlSpeechView.setVisibility(View.VISIBLE);
                        }else {
                            mControlSpeechView.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        });

        mJokeItemFragmentAdapter = new JokeItemFragmentAdapter(getContext());
        mRecyclerView.setAdapter(mJokeItemFragmentAdapter);

        mJokeItemFragmentAdapter.setOnItemChildClickListener(new JokeItemFragmentAdapter.OnItemChildClickListener() {
            @Override
            public void onImageClick(View view, int position, GifInfo gifInfo) {
                if (getActivity() != null && gifInfo != null) {
                    List<GifInfo> list = mPresenter.getData();
                    if (list != null && list.size() > 0) {

                        List<GifInfo> tempList = new ArrayList<>();

                        int index = list.indexOf(gifInfo);

                        for (int i = 0; i < list.size(); i++) {
                            if (Math.abs(index - i) < 5) {
                                tempList.add(list.get(i));
                            }
                        }

                        List<BigImgInfo> bigImgInfos = BeanHelper.getBigImgInfo(tempList);

                        BigImgInfo bigImgInfo = new BigImgInfo();
                        bigImgInfo.setImg(gifInfo.img);

                        DetailActivity.launch(getActivity(), view, bigImgInfos, bigImgInfo);
                    }
                }
            }

            @Override
            public void onPlayClick(final RecyclerView.ViewHolder viewHolder, int position, final GifInfo gifInfo) {
                if (mSpeechHelper == null) {
                    mSpeechHelper = SpeechHelper.newInstance(getContext());
                    mSpeechHelper.setOnSpeechInitListener(JokeItemFragment.this);
                    mSpeechHelper.setOnSpeechListener(JokeItemFragment.this);
                }

                if (gifInfo != null && gifInfo.equals(mSpeechInfo) && mSpeechHelper.isSpeaking()) {
                    if (!mSpeechHelper.isPause()) {
                        mSpeechHelper.pauseSpeaking();
                    } else {
                        mSpeechHelper.resumeSpeaking();
                    }
                    return;
                }

                if (viewHolder.getItemViewType() == JOKE_TEXT) {
                    JokeTextViewHolder jokeTextViewHolder = (JokeTextViewHolder) viewHolder;
                    jokeTextViewHolder.pb_loading.setVisibility(View.VISIBLE);
                }

                mSpeechInfo = gifInfo;
                updateUI(START);
            }

            @Override
            public void onStopClick(RecyclerView.ViewHolder viewHolder, int position, GifInfo gifInfo) {
                if (!mSpeechHelper.isSpeaking()) {
                    return;
                }
                updateUI(STOP);
            }
        });


        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = bottom;
                }
                outRect.bottom = bottom;
                outRect.left = bottom;
                outRect.right = bottom;
            }
        });

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                updateUI(INIT);
                mPresenter.refreshData(mJokeTabInfo);
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMoreData();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (mPresenter != null && mPresenter.getData() != null) {
            outState.putInt("page", mPresenter.getPage());
            outState.putSerializable("data", (Serializable) mPresenter.getData());
            outState.putParcelable("state", pullLoadMoreRecyclerView.getLayoutManager().onSaveInstanceState());
            outState.putBoolean("hasMore", pullLoadMoreRecyclerView.isHasMore());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {
        mJokeTabInfo = (JokeTabInfo) getArguments().getSerializable(Tag.KEY_INFO);
        mPresenter.setJokeTabInfo(mJokeTabInfo);
        if (savedInstanceState != null) {
            mPresenter.setPage(savedInstanceState.getInt("page", 1));
            List<GifInfo> list = (List<GifInfo>) savedInstanceState.getSerializable("data");
            if (list != null && list.size() > 0) {
                Parcelable parcelable = savedInstanceState.getParcelable("state");
                if (parcelable != null) {
                    pullLoadMoreRecyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
                }
                mPresenter.setData(list);
                onShowSuccessView(list, savedInstanceState.getBoolean("hasMore", false));
                return;
            }
        }

        pullLoadMoreRecyclerView.setRefreshing(true);
        pullLoadMoreRecyclerView.refresh();
    }

//    public void randomReFresh() {
//        if (!isLoading) {
//            mPage = (int) (Math.random() * 223 + 1);
//            mPresenter.getImgList(1, true);
//        }
//    }

    @Override
    public void onShowSuccessView(List<GifInfo> gifInfos, boolean hasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(hasMore);
        mJokeItemFragmentAdapter.setData(gifInfos);
        mJokeItemFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowEmptyView() {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();

        View view = View.inflate(root.getContext(), R.layout.view_empty, null);

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
    public void onLoadMoreSuccess(List<GifInfo> list, int positionStart, int count, boolean isHasMore) {
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        pullLoadMoreRecyclerView.setHasMore(isHasMore);
        int startPosition = mJokeItemFragmentAdapter.getItemCount();
        mJokeItemFragmentAdapter.setData(list);
        mJokeItemFragmentAdapter.notifyItemRangeInserted(startPosition, count);
    }

    @Override
    public void onLoadMoreFail() {
        pullLoadMoreRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
            }
        });
    }

    public void setUserVisibleHintSuper(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (mSpeechHelper != null && pause) {
                mSpeechHelper.resumeSpeaking();
                pause = false;
            }
        } else {
            if (mSpeechHelper != null && !mSpeechHelper.isPause()) {
                this.pause = true;
                mSpeechHelper.pauseSpeaking();
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mSpeechHelper != null && pause) {
                mSpeechHelper.resumeSpeaking();
                pause = false;
            }
        } else {
            if (mSpeechHelper != null && !mSpeechHelper.isPause()) {
                this.pause = true;
                mSpeechHelper.pauseSpeaking();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        CollectModel.newInstance().removeOnCollectListener(mListener);
        if (mSpeechHelper != null && pause) {
            mSpeechHelper.resumeSpeaking();
            pause = false;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mSpeechHelper != null && !mSpeechHelper.isPause()) {
            this.pause = true;
            mSpeechHelper.pauseSpeaking();
        }
        CollectModel.newInstance().addOnCollectListener(mListener);
    }

    private CollectModel.OnCollectListener mListener = new CollectModel.OnCollectListener() {
        @Override
        public void onChange() {
            if (mPresenter != null && mJokeItemFragmentAdapter != null) {
                mPresenter.checkCollect(mJokeItemFragmentAdapter.getData());
                mJokeItemFragmentAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onInit() {
        if (mSpeechInfo != null) {
            mSpeechHelper.startSpeaking(mSpeechInfo.getText());
        }
    }

    @Override
    public void onSpeakBegin() {
        mJokeItemFragmentAdapter.setSpeaking(true);
        mJokeItemFragmentAdapter.notifyDataSetChanged();
        mControlSpeechView.start();
    }

    @Override
    public void onSpeakPaused() {
        mJokeItemFragmentAdapter.setSpeaking(false);
        mJokeItemFragmentAdapter.notifyDataSetChanged();
        mControlSpeechView.pause();
    }

    @Override
    public void onSpeakResumed() {
        mJokeItemFragmentAdapter.setSpeaking(true);
        mJokeItemFragmentAdapter.notifyDataSetChanged();
        mControlSpeechView.start();
    }

    @Override
    public void onBufferProgress(int percent, int beginPos, int endPos) {

        RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForAdapterPosition(mPresenter.getData().indexOf(mSpeechInfo));
        if(viewHolder != null && viewHolder.getItemViewType() == JOKE_TEXT) {
            JokeTextViewHolder jokeTextViewHolder = (JokeTextViewHolder) viewHolder;
            jokeTextViewHolder.pb_progress.setSecondaryProgress(percent);
        }

        mControlSpeechView.setSecondaryProgress(percent);
    }

    @Override
    public void onSpeakProgress(int percent, int beginPos, int endPos) {
        RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForAdapterPosition(mPresenter.getData().indexOf(mSpeechInfo));
        if(viewHolder != null && viewHolder.getItemViewType() == JOKE_TEXT) {
            JokeTextViewHolder jokeTextViewHolder = (JokeTextViewHolder) viewHolder;
            jokeTextViewHolder.pb_progress.setProgress(percent);
        }

        mControlSpeechView.setProgress(percent);
    }

    @Override
    public void onCompleted() {

        if (getContext() == null) {
            return;
        }

        boolean next = PreferenceUtil.getInstance(getContext()).getBoolean(SPKey.JOKE_NEXT, false);
        if (next) {
            int index = mPresenter.getData().indexOf(mSpeechInfo);
            if (index != mPresenter.getData().size() - 1) {
                mSpeechInfo = mPresenter.getData().get(++index);

                View stickview = mRecyclerView.findChildViewUnder(bottom, 0);
                if(stickview != null) {
                    int position = mRecyclerView.getChildAdapterPosition(stickview);
                    if(position == mPresenter.getData().indexOf(mSpeechInfo)) {
                        int height = stickview.getMeasuredHeight();
                        int top = stickview.getTop();
                        if(top + height <= childHeight){
                            mControlSpeechView.setVisibility(View.VISIBLE);
                        }else {
                            mControlSpeechView.setVisibility(View.INVISIBLE);
                        }
                    }
                }

                updateUI(START);
                return;
            }
        }
        updateUI(STOP);
    }

    public static final int STOP = 0;
    public static final int INIT = 1;
    public static final int START = 2;

    public void updateUI(int state) {
        switch (state) {
            case INIT:
            case STOP:
                if (mSpeechHelper != null) {
                    mSpeechHelper.stopSpeaking();
                }
                mJokeItemFragmentAdapter.setSpeaking(false);
                mSpeechInfo = null;
                mJokeItemFragmentAdapter.setSpeechData(mSpeechInfo);
                mJokeItemFragmentAdapter.notifyDataSetChanged();
                mControlSpeechView.stop();
                mControlSpeechView.setVisibility(View.INVISIBLE);
                break;
            case START:
                mSpeechHelper.startSpeaking(mSpeechInfo.getText());
                mJokeItemFragmentAdapter.setSpeaking(true);
                mJokeItemFragmentAdapter.setSpeechData(mSpeechInfo);
                mJokeItemFragmentAdapter.notifyDataSetChanged();
                mControlSpeechView.start();
                break;
        }
    }
}
