package com.maverick.bean;

import java.io.Serializable;

/**
 * Created by limingfei on 2018/3/7.
 */

public class LeanCloudMusic implements Serializable {

    private LeanCloudMusicMid mid;

    public LeanCloudMusicMid getMid() {
        return mid;
    }

    public void setMid(LeanCloudMusicMid mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "LeanCloudMusic{" +
                "mid=" + mid +
                '}';
    }
}
