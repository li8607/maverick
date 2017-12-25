package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVUser;
import com.maverick.R;
import com.maverick.adapter.holder.MyItemViewHolder;
import com.maverick.adapter.holder.MyUserViewHolder;
import com.maverick.bean.MyInfo;
import com.maverick.leancloud.User;
import com.maverick.type.MyType;
import com.maverick.util.GlideUtil;

import java.util.List;

import cntv.themelibrary.ThemeHelper;

/**
 * Created by limingfei on 2017/9/27.
 */
public class MyFragmentAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MyInfo> mList;
    private final LayoutInflater mLayoutInflater;

    public MyFragmentAdapter(Context context, List<MyInfo> list) {
        this.mContext = context;
        this.mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case MyType.USER:
                holder = new MyUserViewHolder(mLayoutInflater.inflate(R.layout.item_my_user, parent, false));
                break;
            default:
                holder = new MyItemViewHolder(mLayoutInflater.inflate(R.layout.item_my, parent, false));
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyItemViewHolder) {
            MyItemViewHolder myItemViewHolder = (MyItemViewHolder) holder;
            myItemViewHolder.imageView.setImageResource(mList.get(position).getIcon());
            myItemViewHolder.title.setText(mList.get(position).getTitle());
            myItemViewHolder.line.setVisibility(getItemViewType(position) == MyType.HISTORY ? View.VISIBLE : View.INVISIBLE);
        } else if (holder instanceof MyUserViewHolder) {
            MyUserViewHolder myUserViewHolder = (MyUserViewHolder) holder;
            if (AVUser.getCurrentUser() != null) {
                GlideUtil.loadImage(mContext, (String) AVUser.getCurrentUser().get(User.headUrl), myUserViewHolder.imageView);
                myUserViewHolder.nickname.setText((String) AVUser.getCurrentUser().get(User.nickname));
                myUserViewHolder.username.setText("账号：" + AVUser.getCurrentUser().getUsername());
            }
        }

        holder.itemView.setBackgroundColor(ThemeHelper.getThemeHelper(mContext).getCardBackgroundColor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, mList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, MyInfo myInfo);
    }
}
