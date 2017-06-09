package com.maverick.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.maverick.R;
import com.maverick.adapter.GifAdapter;
import com.maverick.bean.GifInfo;
import com.maverick.bean.GifInfoObj;
import com.maverick.global.UrlData;
import com.maverick.util.JsonUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ll on 2017/5/18.
 */
public class GifFragment extends Fragment {

    private GifAdapter mGifAdapter;
    private PullLoadMoreRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static GifFragment newInstance() {
        GifFragment fragment = new GifFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_gif, null);
        mRecyclerView = (PullLoadMoreRecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLinearLayout();
        mGifAdapter = new GifAdapter();
        mRecyclerView.setAdapter(mGifAdapter);

        final int bottom = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getContext().getResources().getDisplayMetrics()));

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

        mRecyclerView.setHasMore(true);

        mRecyclerView.setPullRefreshEnable(false);

        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    Toast.makeText(getContext(), "正在刷新，请稍后加载更多", Toast.LENGTH_SHORT).show();
                    mRecyclerView.setPullLoadMoreCompleted();
                    return;
                }

                mPage++;
                postAsynHttp(mPage, false);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mRecyclerView.isLoadMore()) {
                    Toast.makeText(getContext(), "正在加载更多，请稍后刷新", Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }

                mPage = 1;
                postAsynHttp(mPage, true);
            }
        });

        return view;
    }

    private List<GifInfo> mGifInfoList = new ArrayList<>();

    private void updateAdapter(GifInfoObj gifInfoObj, boolean clean) {

        if (mSwipeRefreshLayout.isRefreshing()) {
            Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();

        }
        mSwipeRefreshLayout.setRefreshing(false);
        if (mRecyclerView.isLoadMore()) {
            mRecyclerView.setPullLoadMoreCompleted();
        }

        List<GifInfo> list = gifInfoObj.showapi_res_body.contentlist;
        if (clean) {
            mGifInfoList.clear();
        }

        mGifInfoList.addAll(list);
        mGifAdapter.setList(mGifInfoList);
        mGifAdapter.notifyDataSetChanged();
    }

    private int mPage = 1;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postAsynHttp(mPage, true);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void postAsynHttp(int page, final boolean clean) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add(UrlData.APPID_KEY, UrlData.APPID_VALUE)
                .add(UrlData.SIGN_KEY, UrlData.SIGN_VALUE)
                .add("page", page + "")
                .add("maxResult", "20")
                .build();
        Request request = new Request.Builder()
                .url(UrlData.GIF)
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                final GifInfoObj mGifInfoObj = (GifInfoObj) JsonUtil.stringToObject(str, GifInfoObj.class);

                if (getActivity() == null || getContext() == null) {
                    return;
                }

                if (mGifInfoObj == null || mGifInfoObj.showapi_res_body == null || mGifInfoObj.showapi_res_body.contentlist == null) {
                    Toast.makeText(getContext(), "网络异常", Toast.LENGTH_SHORT).show();
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateAdapter(mGifInfoObj, clean);
                    }
                });
            }
        });
    }
}
