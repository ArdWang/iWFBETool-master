package com.wbt.http.retrofitrxjava.service;

import com.wbt.bean.UserBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by rnd on 2018/1/18.
 * 获取登录数据
 */

public interface ILoginService {

    /**
     * 登录
     * @param params
     * @return
     */
    @POST("getData")
    Observable<UserBean> getLogin(@QueryMap Map<String,Object> params);


    @POST("getData")
    Observable<UserBean> updateUser(@QueryMap Map<String,Object> params);
}
