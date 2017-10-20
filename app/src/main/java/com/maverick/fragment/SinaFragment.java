package com.maverick.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.maverick.R;
import com.maverick.adapter.SinaFragmentAdapter;
import com.maverick.base.BaseFragment2;
import com.maverick.bean.SinaTabInfo;
import com.maverick.global.Tag;
import com.maverick.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class SinaFragment extends BaseFragment2 {

    private TabLayout tab_layout;
    private ViewPager viewpager;
    private SinaFragmentAdapter mAdapter;

    public static SinaFragment newInstance(SinaTabInfo info) {
        SinaFragment fragment = new SinaFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag.KEY_INFO, info);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static SinaFragment newInstance() {
        SinaFragment fragment = new SinaFragment();
        return fragment;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_sina;
    }

    @Override
    protected void onInitView(View view) {
        tab_layout = findView(R.id.tab_layout);
        viewpager = findView(R.id.viewpager);

        mAdapter = new SinaFragmentAdapter(getChildFragmentManager());
        viewpager.setAdapter(mAdapter);
        tab_layout.setupWithViewPager(viewpager);
    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {


        if (getArguments() == null || getArguments().getSerializable(Tag.KEY_INFO) == null || TextUtils.isEmpty(((SinaTabInfo) getArguments().getSerializable(Tag.KEY_INFO)).getType())) {
            List<SinaTabInfo> list = new ArrayList<>();
            list.add(getSinaTabInfo("互联网", Tag.SINA_INTERNET));
            list.add(getSinaTabInfo("科学", Tag.SINA_KEXUE));
            list.add(getSinaTabInfo("历史", Tag.SINA_LISHI));
            list.add(getSinaTabInfo("军事", Tag.SINA_JUNSHI));
            list.add(getSinaTabInfo("数码", Tag.SINA_SHUMA));
            list.add(getSinaTabInfo("人文", Tag.SINA_RENWEN));
            list.add(getSinaTabInfo("宠物", Tag.SINA_CHONGWU));
            list.add(getSinaTabInfo("星座", Tag.SINA_XINGZUO));
            list.add(getSinaTabInfo("搞笑", Tag.SINA_GAOXIAO));
            list.add(getSinaTabInfo("情感", Tag.SINA_QINGGAN));
            list.add(getSinaTabInfo("媒体", Tag.SINA_MEITI));
            list.add(getSinaTabInfo("养生", Tag.SINA_YANGSHENG));
            list.add(getSinaTabInfo("音乐", Tag.SINA_YINYUE));
            list.add(getSinaTabInfo("电视剧", Tag.SINA_DIANSHIJU));
            list.add(getSinaTabInfo("电影", Tag.SINA_DIANYING));
            list.add(getSinaTabInfo("综艺", Tag.SINA_ZONGYI));
            list.add(getSinaTabInfo("摄影", Tag.SINA_SHEYING));
            list.add(getSinaTabInfo("健身", Tag.SINA_JIANSHEN));
            list.add(getSinaTabInfo("体育", Tag.SINA_TIYU));
            list.add(getSinaTabInfo("美食", Tag.SINA_MEISHI));
            list.add(getSinaTabInfo("旅游", Tag.SINA_LVYOU));
            list.add(getSinaTabInfo("财经", Tag.SINA_CAIJING));
            list.add(getSinaTabInfo("医疗", Tag.SINA_YILIAO));
            list.add(getSinaTabInfo("读书", Tag.SINA_DUSHU));
            list.add(getSinaTabInfo("房地产", Tag.SINA_FANGDICHAN));
            list.add(getSinaTabInfo("汽车", Tag.SINA_QICHE));
            list.add(getSinaTabInfo("时尚", Tag.SINA_SHISHANG));
            list.add(getSinaTabInfo("美妆", Tag.SINA_MEIZHUANG));
            list.add(getSinaTabInfo("教育", Tag.SINA_JIAOYU));
            list.add(getSinaTabInfo("动漫", Tag.SINA_DONGMAN));
            list.add(getSinaTabInfo("游戏", Tag.SINA_YOUXI));
            list.add(getSinaTabInfo("法律", Tag.SINA_FALV));
            list.add(getSinaTabInfo("母婴", Tag.SINA_MUYING));
            list.add(getSinaTabInfo("娱乐", Tag.SINA_YULE));
            list.add(getSinaTabInfo("收藏", Tag.SINA_SHOUCANG));
            list.add(getSinaTabInfo("科技观察", Tag.SINA_KEJIGUANCHA));
            mAdapter.setData(list);

            tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);

        } else {
            SinaTabInfo info = (SinaTabInfo) getArguments().getSerializable(Tag.KEY_INFO);
            List<SinaTabInfo> list = new ArrayList<>();
            SinaTabInfo day = info.clone();
            if (day != null) {
                day.setTitle("日榜");
                day.setSpace(Tag.SINA_DAY_SPACE);
                list.add(day);
            }

            SinaTabInfo week = info.clone();
            if (week != null) {
                week.setTitle("周榜");
                week.setSpace(Tag.SINA_WEEK_SPACE);
                list.add(week);
            }

            SinaTabInfo month = info.clone();
            if (month != null) {
                month.setTitle("月榜");
                month.setSpace(Tag.SINA_MONTH_SPACE);
                list.add(month);
            }

            mAdapter.setData(list);

            tab_layout.setTabMode(TabLayout.MODE_FIXED);

            if (list.size() < 1) {
                return;
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    private SinaTabInfo getSinaTabInfo(String title, String type) {
        SinaTabInfo info = new SinaTabInfo();
        info.setTitle(title);
        info.setType(type);
        return info;
    }
}
