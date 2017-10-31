package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.maverick.R;
import com.maverick.bean.PearVideoInfo;
import com.maverick.util.GlideUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class PearBannerViewHolder extends RecyclerView.ViewHolder {

    private final Banner banner;

    public PearBannerViewHolder(View itemView) {
        super(itemView);
        banner = (Banner) itemView.findViewById(R.id.banner);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置指示器位置（当banner模式中有指示器时）
//        banner.setIndicatorGravity();
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
    }

    public void bindData(Context context, List<PearVideoInfo> list) {
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list);
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            titles.add(list.get(i).getName());
        }
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    public void onStart() {
        //开始轮播
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    public void onStop() {
        //结束轮播
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            if (path != null && path instanceof PearVideoInfo) {
                PearVideoInfo info = (PearVideoInfo) path;
                GlideUtil.loadImage(context, info.getPic(), imageView);
            }
        }
    }
}
