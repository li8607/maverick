package com.maverick.factory;

import com.maverick.base.BaseFragment2;
import com.maverick.bean.ButtonInfo;
import com.maverick.fragment.BeautyFragment;
import com.maverick.fragment.CaricatureFragment;
import com.maverick.fragment.JokeFragment;
import com.maverick.fragment.MyFragment;
import com.maverick.fragment.SisterFragment;
import com.maverick.type.FragmentType;

/**
 * Created by Administrator on 2017/10/17.
 */
public class FragmentFactory {

    public static BaseFragment2 getMainFragment(ButtonInfo buttonInfo) {
        if (buttonInfo == null) {
            return BeautyFragment.newInstance();
        }
        BaseFragment2 fragment;
        switch (buttonInfo.getType()) {
            case FragmentType.SISTER:
                fragment = SisterFragment.newInstance();
                break;
            case FragmentType.JOKE:
                fragment = JokeFragment.newInstance();
                break;
            case FragmentType.BEAUTY:
                fragment = BeautyFragment.newInstance();
                break;
            case FragmentType.MY:
                fragment = MyFragment.newInstance();
                break;
            case FragmentType.CARICATURE:
                fragment = CaricatureFragment.newInstance();
                break;
            default:
                fragment = BeautyFragment.newInstance();
                break;
        }
        return fragment;
    }

}
