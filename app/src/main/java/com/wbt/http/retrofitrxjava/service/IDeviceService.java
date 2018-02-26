package com.wbt.http.retrofitrxjava.service;

import com.wbt.bean.DeleteBean;
import com.wbt.bean.DeviceBean;
import com.wbt.bean.DevicesBean;
import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/1/26.
 *
 */

public interface IDeviceService {
    /**
     * 获取得到设备数据
     * @param params
     * @return
     */
    @POST("getData")
    Observable<DeviceBean> getDevice(@QueryMap Map<String,Object> params);

    @POST("getData")
    Observable<DevicesBean> addDevice(@QueryMap Map<String,Object> params);

    @POST("getData")
    Observable<DevicesBean> updateDevice(@QueryMap Map<String,Object> params);

    @POST("getData")
    Observable<DeleteBean> deleteDevice(@QueryMap Map<String,Object> params);
}
