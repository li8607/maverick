package com.maverick.weight;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maverick.R;


/**
 * Created by limingfei on 2017/10/3.
 */
public class ToolbarView extends RelativeLayout {

    private TextView title;

    public ToolbarView(Context context) {
        this(context, null);
    }

    public ToolbarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
       View.inflate(getContext(), R.layout.view_toolbar, this);

        title = (TextView) findViewById(R.id.title);
    }

    public void setText(CharSequence text) {
        if(!TextUtils.isEmpty(text)) {
            title.setText(text);
        }
    }
}
