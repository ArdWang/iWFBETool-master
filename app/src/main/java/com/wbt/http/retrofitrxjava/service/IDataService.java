package com.wbt.http.retrofitrxjava.service;

import com.wbt.bean.DataBean;
import com.wbt.bean.DeviceBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/1/27.
 *
 */

public interface IDataService {
    /**
     * 获取得到设备数据
     * @param params
     * @return
     */
    @POST("getData")
    Observable<DataBean> getData(@QueryMap Map<String,Object> params);
}
