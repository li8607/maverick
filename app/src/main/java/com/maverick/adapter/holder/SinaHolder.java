package com.maverick.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maverick.R;
import com.maverick.WebActivity;
import com.maverick.bean.SinaInfo;
import com.maverick.bean.SisterInfo;
import com.maverick.hepler.BeanHelper;
import com.maverick.util.GlideUtil;

/**
 * Created by Administrator on 2017/9/30.
 */
public class SinaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected String TAG = getClass().getSimpleName();

    protected SinaInfo mInfo;
    protected final ImageView image_head;
    protected final TextView time, name;
    protected final TextView content;
    private Context mContext;

    public SinaHolder(View itemView) {
        super(itemView);

        image_head = (ImageView) itemView.findViewById(R.id.image_head);
        time = (TextView) itemView.findViewById(R.id.time);
        content = (TextView) itemView.findViewById(R.id.content);


        itemView.setOnClickListener(this);

        name = (TextView) itemView.findViewById(R.id.name);
    }

    public void bindData(Context context, SinaInfo info) {
        this.mContext = context;
        this.mInfo = info;
        GlideUtil.loadCircleImage(context, info.getImg(), image_head);

        if (!TextUtils.isEmpty(info.getName())) {
            name.setText(info.getName().trim());
        } else {
            name.setText("");
        }

        if (!TextUtils.isEmpty(info.getDate())) {
            time.setText(info.getDate().trim());
        } else {
            time.setText("");
        }

        if (!TextUtils.isEmpty(info.getNewinfo())) {
            Log.e(TAG, "sisterInfo.getText() = " + info.getNewinfo());
            content.setText(info.getNewinfo().trim());
        } else {
            content.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        WebActivity.launch(mContext, BeanHelper.getWebDetailInfo(mInfo));
    }

    private OnListener mListener;

    public void setOnListener(OnListener listener) {
        this.mListener = listener;
    }

    public interface OnListener {
        void onShareClick(View view, SisterInfo sisterInfo);
    }
}
