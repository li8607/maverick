package com.maverick.presenter.implView;

import com.maverick.bean.GifInfo;

import java.util.List;

/**
 * Created by ll on 2017/5/22.
 */
public interface TextFragmentView extends IBaseFragment {

    void refreshAdapter(List<GifInfo> list);
}
