package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.bean.MenuItemInfo;
import com.maverick.type.MenuType;

import java.util.List;

/**
 * Created by limingfei on 2017/10/17.
 */
public class MenuDialogAdapter extends RecyclerView.Adapter {

    private List<MenuItemInfo> mList;
    private Context mContext;

    public MenuDialogAdapter(Context context, List<MenuItemInfo> list) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShareViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShareViewHolder shareViewHolder = (ShareViewHolder) holder;
        shareViewHolder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ShareViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView image;
        private final TextView title;
        private MenuItemInfo mInfo;

        public ShareViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        public void bindData(MenuItemInfo menuItemInfo) {
            this.mInfo = menuItemInfo;
            switch (menuItemInfo.getMenuType()) {
                case MenuType.SHARE_WEIXIN:
                    image.setImageResource(R.drawable.umeng_socialize_wechat);
                    title.setText(itemView.getContext().getString(R.string.share_wechat));
                    break;
                case MenuType.SHARE_WEIXIN_CIRCLE:
                    image.setImageResource(R.drawable.umeng_socialize_wxcircle);
                    title.setText(itemView.getContext().getString(R.string.share_wxcircle));
                    break;
                case MenuType.SHARE_SINA:
                    image.setImageResource(R.drawable.umeng_socialize_sina);
                    title.setText(itemView.getContext().getString(R.string.share_sina));
                    break;
                case MenuType.SHARE_QZONE:
                    image.setImageResource(R.drawable.umeng_socialize_qzone);
                    title.setText(itemView.getContext().getString(R.string.share_qzone));
                    break;
                case MenuType.SEND_COLLENT:
                    image.setImageResource(R.drawable.collect_selector);
                    updateCollectUI(menuItemInfo);
                    break;
            }


        }

        @Override
        public void onClick(View v) {
            if (mOnListener != null) {
                mOnListener.onItemClick(v, getAdapterPosition(), mInfo);
            }
        }

        public void updateCollectUI(MenuItemInfo menuItemInfo) {

            if (menuItemInfo == null) {
                return;
            }

            if (menuItemInfo.getMenuType() == MenuType.SEND_COLLENT && menuItemInfo.isCollect()) {
                itemView.setSelected(true);
                title.setText(itemView.getResources().getString(R.string.collect_select));
            } else {
                itemView.setSelected(false);
                title.setText(itemView.getResources().getString(R.string.collect));
            }
        }
    }

    private OnListener mOnListener;

    public void setOnOnListener(OnListener listener) {
        this.mOnListener = listener;
    }

    public interface OnListener {
        void onItemClick(View view, int position, MenuItemInfo menuItemInfo);
    }
}
