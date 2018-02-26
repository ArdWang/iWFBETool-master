package com.wbt.mvp.model.device;

import com.wbt.bean.DeviceBean;


import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 *
 */

public interface IDeviceListener {
    /**
     * 获取设备成功
     */
    void onDeviceSuccess(List<DeviceBean.DevicesBean> devicesList);

    /**
     * 获取设备失败
     */
    void onDeviceFail(String message);

    /**
     * 获取设备错误
     */
    void onDeviceError(String message);
}
