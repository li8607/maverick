package com.maverick.imodel;

import java.util.List;

import cntv.greendaolibrary.dbbean.Collect;

/**
 * Created by limingfei on 2017/9/29.
 */
public interface ICollectModel {

    List<Collect> getBeautyData();

    List<Collect> getJokeTextData();

    List<Collect> getJokeImgData();

    List<Collect> getJokeGifData();

    List<Collect> getSisterData();

    List<Collect> getCaricatureData();

    List<Collect> getSinaData();

    List<Collect> getData(Collect collect);

    boolean insertCollectDB(Collect collect);

    boolean deleteCollectDB(Collect collect);

    boolean deleteCollectDBList(List<Collect> collects);

    boolean hasCollectDB(Collect collect);
}
