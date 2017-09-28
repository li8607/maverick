package com.maverick.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.maverick.DetailActivity;
import com.maverick.R;
import com.maverick.bean.BigImgInfo;
import com.maverick.model.HistoryModel;
import com.maverick.util.GlideUtil;
import com.maverick.weight.RatioImageView;

import java.util.List;

import cntv.greendaolibrary.dbbean.History;

/**
 * Created by Administrator on 2017/9/27.
 */
public class BrowsingHistoryActivityAdapter extends RecyclerView.Adapter {

    private String TAG = getClass().getSimpleName();

    private List<Object> mList;
    private Context mContext;
    public static final int TITLE = 1;
    public static final int IMAGE = 2;

    public static final int STATE_EDIT = 1;
    public static final int STATE_NO_EDIT = 2;
    public int stateEdit = STATE_NO_EDIT;

    public BrowsingHistoryActivityAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case TITLE:
                holder = new HistoryTitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_title, parent, false));
                break;
            case IMAGE:
                holder = new HistoryImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_image, parent, false));
                break;
            default:
                holder = new RecyclerView.ViewHolder(new View(parent.getContext())) {
                };
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HistoryImageHolder && mList.get(position) instanceof History) {
            HistoryImageHolder historyImageHolder = (HistoryImageHolder) holder;
            historyImageHolder.bindData((History) mList.get(position));
        } else if (holder instanceof HistoryTitleHolder && mList.get(position) instanceof String) {
            HistoryTitleHolder historyTitleHolder = (HistoryTitleHolder) holder;
            historyTitleHolder.bindData((String) mList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position) instanceof String) {
            return TITLE;
        } else if (mList.get(position) instanceof History) {
            return IMAGE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<Object> histories) {
        this.mList = histories;
    }

    public List<Object> getData() {
        return mList;
    }

    public int getStateEdit() {
        return stateEdit;
    }

    public void setStateEdit(int stateEdit) {
        this.stateEdit = stateEdit;
    }

    public class HistoryImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        private final RatioImageView image;
        private final TextView type;
        private History mHistory;
        private final CheckBox checkbox;

        public HistoryImageHolder(View itemView) {
            super(itemView);
            image = (RatioImageView) itemView.findViewById(R.id.image);
            type = (TextView) itemView.findViewById(R.id.type);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);


            image.setOriginalSize(1, 1);
            itemView.setOnClickListener(this);
            checkbox.setOnCheckedChangeListener(this);
        }

        public void bindData(History history) {
            this.mHistory = history;
            GlideUtil.loadImage(mContext, mHistory.getHistoryimage(), image);

            String type = history.getHistoryType();

            if (TextUtils.equals(type, "1")) {
                this.type.setText("笑话");
            } else if (TextUtils.equals(type, "2")) {
                this.type.setText("美女");
            }

            checkbox.setVisibility(stateEdit == STATE_EDIT ? View.VISIBLE : View.INVISIBLE);
            checkbox.setChecked(mHistory.isCheck());
        }

        @Override
        public void onClick(View v) {
            if (mHistory == null) {
                return;
            }

            if (stateEdit == STATE_EDIT) {
                checkbox.setChecked(!checkbox.isChecked());
                return;
            }


            mHistory.setHistoryTime(System.currentTimeMillis());
            HistoryModel.newInstance().insertHistoryDB(mHistory);

            BigImgInfo bigImgInfo = new BigImgInfo();
            bigImgInfo.setImg(mHistory.getHistoryimage());
            DetailActivity.launch((Activity) mContext, image, bigImgInfo);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.e(TAG, "onCheckedChanged = " + isChecked);
            mHistory.setCheck(isChecked);
        }
    }

    public class HistoryTitleHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        public HistoryTitleHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }

        public void bindData(String title) {
            if (!TextUtils.isEmpty(title)) {
                this.title.setText(title);
            }
        }
    }

    private OnAdapterListener mOnAdapterListener;

    public void setOnAdapterListener(OnAdapterListener listener) {
        this.mOnAdapterListener = listener;
    }

    public interface OnAdapterListener {
        void onCheck();
    }
}
