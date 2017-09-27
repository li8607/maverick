package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.bean.MyInfo;

import java.util.List;

/**
 * Created by limingfei on 2017/9/27.
 */
public class MyFragmentAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MyInfo> mList;

    public MyFragmentAdapter(Context context, List<MyInfo> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView imageView;
        private final TextView title;
        private MyInfo mMyInfo;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        public void bindData(MyInfo myInfo) {
            this.mMyInfo = myInfo;
            imageView.setImageResource(myInfo.getIcon());
            title.setText(myInfo.getTitle());
        }

        @Override
        public void onClick(View v) {
            if (mMyInfo == null || mOnItemClickListener == null) {
                return;
            }

            mOnItemClickListener.onItemClick(getAdapterPosition(), mMyInfo);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, MyInfo myInfo);
    }
}
