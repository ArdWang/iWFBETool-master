package com.wbt.mvp.model.device;


import com.wbt.bean.DevicesBean;



/**
 * Created by Administrator on 2018/1/29.
 *
 */

public interface IDevicesListener {
    /**
     * 获取设备成功
     */
    void onDevicesSuccess(DevicesBean.DeviceBeanBean deviceBeanBean);

    /**
     * 获取设备失败
     */
    void onDevicesFail(String message);

    /**
     * 获取设备错误
     */
    void onDevicesError(String message);
}
