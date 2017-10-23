package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.maverick.CaricatureActivity;
import com.maverick.R;
import com.maverick.WebActivity;
import com.maverick.hepler.BeanHelper;
import com.maverick.type.CollectType;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/10/5.
 */
public class CollectJokeTextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private final TextView mTitle;
    private final TextView time;
    private final CheckBox checkbox;
    private Collect mCollect;
    private boolean editState;
    private Context mContext;

    public CollectJokeTextViewHolder(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.title);
        time = (TextView) itemView.findViewById(R.id.time);

        checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(this);
        itemView.setOnClickListener(this);
    }

    public void bindData(Context context, Collect collect, boolean editState) {
        this.mContext = context;
        this.mCollect = collect;
        this.editState = editState;
        String text = collect.getCollectName();
        if (!TextUtils.isEmpty(text)) {
            mTitle.setText(text.trim());
        } else {
            mTitle.setText("");
        }

        String time = collect.getCollectCT();
        if (!TextUtils.isEmpty(time)) {
            this.time.setText(time.trim());
        } else {
            this.time.setText("");
        }

        checkbox.setVisibility(editState ? View.VISIBLE : View.INVISIBLE);
        checkbox.setChecked(collect.getCheck());
    }

    @Override
    public void onClick(View v) {
        if (editState) {
            checkbox.setChecked(!checkbox.isChecked());
            if (mOnCollectJokeTextViewHolderListener != null) {
                mOnCollectJokeTextViewHolderListener.onItemClick(getAdapterPosition(), mCollect);
            }
        } else {

            if (TextUtils.equals(mCollect.getCollectType(), CollectType.CARICATURE)) {
                CaricatureActivity.launch(mContext, BeanHelper.getCaricatureDetailInfo(mCollect));
            } else {
                WebActivity.launch(mContext, BeanHelper.getWebDetailInfo(mCollect));
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.mCollect.setCheck(isChecked);
    }

    /**
     * @param visible   编辑框可见参数
     * @param check     编辑框是否选中
     * @param editState 是否为编辑状态
     */
    public void setCheckViewVisible(int visible, boolean check, boolean editState) {
        this.editState = editState;
        this.checkbox.setVisibility(visible);
        this.checkbox.setChecked(check);
    }

    public void setCheck(boolean check) {
        this.checkbox.setChecked(check);
    }

    private OnCollectJokeTextViewHolderListener mOnCollectJokeTextViewHolderListener;

    public void setOnCollectJokeTextViewHolderListener(OnCollectJokeTextViewHolderListener listener) {
        this.mOnCollectJokeTextViewHolderListener = listener;
    }

    public interface OnCollectJokeTextViewHolderListener {
        void onItemClick(int position, Collect collect);
    }
}
