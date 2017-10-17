package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.bean.ShareItemInfo;

import java.util.List;

/**
 * Created by limingfei on 2017/10/17.
 */
public class ShareDialogAdapter extends RecyclerView.Adapter {

    private List<ShareItemInfo> mList;

    public ShareDialogAdapter(Context context, List<ShareItemInfo> list) {
        this.mList = list;
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
        return mList == null? 0:mList.size();
    }


    public class ShareViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView image;
        private final TextView title;
        private ShareItemInfo mInfo;

        public ShareViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        public void bindData(ShareItemInfo shareItemInfo) {
            this.mInfo = shareItemInfo;
            image.setImageResource(shareItemInfo.getId());
            title.setText(shareItemInfo.getTitle());
        }

        @Override
        public void onClick(View v) {
            if(mOnListener != null) {
                mOnListener.onItemClick(mInfo);
            }
        }
    }

    private OnListener mOnListener;

    public void setOnOnListener(OnListener listener) {
        this.mOnListener = listener;
    }

    public interface OnListener {
        void onItemClick(ShareItemInfo shareItemInfo);
    }
}
