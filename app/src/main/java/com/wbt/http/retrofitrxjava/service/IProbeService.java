package com.wbt.http.retrofitrxjava.service;

import com.wbt.bean.ProbeBean;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/2/1.
 *
 */

public interface IProbeService {

    @POST("getData")
    Observable<ProbeBean> getProbe(@QueryMap Map<String,Object> params);
}
