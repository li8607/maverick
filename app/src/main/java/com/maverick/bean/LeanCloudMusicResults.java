package com.maverick.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by limingfei on 2018/3/7.
 */

public class LeanCloudMusicResults implements Serializable {

    private List<LeanCloudMusic> results;

    public List<LeanCloudMusic> getResults() {
        return results;
    }

    public void setResults(List<LeanCloudMusic> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "LeanCloudMusicResults{" +
                "results=" + results +
                '}';
    }
}
