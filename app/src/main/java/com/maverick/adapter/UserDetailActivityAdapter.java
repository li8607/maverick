package com.maverick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maverick.R;
import com.maverick.adapter.holder.UserImageViewHolder;
import com.maverick.adapter.holder.UserTextViewHolder;
import com.maverick.adapter.holder.UserViewHolder;
import com.maverick.bean.UserItemInfo;
import com.maverick.type.UserType;
import com.maverick.util.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public class UserDetailActivityAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<UserItemInfo> mList;
    private final LayoutInflater mLayoutInflater;

    public UserDetailActivityAdapter(Context context, List<UserItemInfo> list) {
        this.mContext = context;
        this.mList = list;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case UserType.HEAD:
                holder = new UserImageViewHolder(mLayoutInflater.inflate(R.layout.item_user_img, parent, false));
                break;
            case UserType.EXITLOGIN:
                holder = new UserViewHolder(mLayoutInflater.inflate(R.layout.item_user, parent, false));
                break;
            default:
                holder = new UserTextViewHolder(mLayoutInflater.inflate(R.layout.item_user_text, parent, false));
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof UserImageViewHolder) {
            UserImageViewHolder userImageViewHolder = (UserImageViewHolder) holder;
            userImageViewHolder.title.setText(TextUtils.isEmpty(mList.get(position).getTitle()) ? "" : mList.get(position).getTitle());
            GlideUtil.loadImage(mContext, mList.get(position).getHeadUrl(), userImageViewHolder.image);
            userImageViewHolder.line.setVisibility(View.VISIBLE);
        } else if (holder instanceof UserTextViewHolder) {
            UserTextViewHolder userTextViewHolder = (UserTextViewHolder) holder;
            userTextViewHolder.title.setText(TextUtils.isEmpty(mList.get(position).getTitle()) ? "" : mList.get(position).getTitle());
            switch (mList.get(position).getType()) {
                case UserType.NICKNAME:
                    userTextViewHolder.value.setText(TextUtils.isEmpty(mList.get(position).getNickname()) ? "" : mList.get(position).getNickname());
                    userTextViewHolder.line.setVisibility(View.VISIBLE);
                    break;
                case UserType.USERNAME:
                    userTextViewHolder.value.setText(TextUtils.isEmpty(mList.get(position).getUsername()) ? "" : mList.get(position).getUsername());
                    userTextViewHolder.line.setVisibility(View.VISIBLE);
                    break;
                case UserType.EMAIL:
                    userTextViewHolder.value.setText(TextUtils.isEmpty(mList.get(position).getEmail()) ? "" : mList.get(position).getEmail());
                    userTextViewHolder.line.setVisibility(View.VISIBLE);
                    break;
                case UserType.MOBILEPHONENUMBER:
                    userTextViewHolder.value.setText(TextUtils.isEmpty(mList.get(position).getMobilePhoneNumber()) ? "" : mList.get(position).getMobilePhoneNumber());
                    userTextViewHolder.line.setVisibility(View.INVISIBLE);
                    break;
                default:
                    userTextViewHolder.value.setText("");
                    break;
            }
        } else if (holder instanceof UserViewHolder) {
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.title.setText(TextUtils.isEmpty(mList.get(position).getTitle()) ? "" : mList.get(position).getTitle());

            switch (mList.get(position).getType()) {
                case UserType.NICKNAME:
                    userViewHolder.line.setVisibility(View.INVISIBLE);
                    break;
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
