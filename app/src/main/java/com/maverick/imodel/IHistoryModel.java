package com.maverick.imodel;

import java.util.List;

import cntv.greendaolibrary.dbbean.History;

/**
 * Created by limingfei on 2017/9/27.
 */
public interface IHistoryModel {

    List<History> getHistory();

    List<History> getBeautyHistory();

    List<History> getJokeHistory();

    List<History> getTextHistory();

    List<History> getImgHistory();

    List<History> getGifHistory();

    boolean insertHistoryDB(History history);

    boolean deleteHistoryDB(History history);

    boolean deleteHistoryDBList(List<History> history);

    boolean deleteHistoryDBAll();

    boolean containsHistoryDB(History history);
}
