package com.wbt.http.retrofitrxjava.service;

import com.wbt.bean.TempBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by rnd on 2018/1/18.
 *
 * 获取温度数据
 */

public interface ITempService {
    /**
     * 获取得到温度数据
     * @param params
     * @return
     */
    @POST("getData")
    Observable<TempBean> getTemp(@QueryMap Map<String,Object> params);
}
