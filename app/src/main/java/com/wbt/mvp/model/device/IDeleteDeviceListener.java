package com.wbt.mvp.model.device;


/**
 * Created by Administrator on 2018/1/30.
 *
 */

public interface IDeleteDeviceListener {
    /**
     * 获取设备成功
     */
    void onDelDevicesSuccess(String message);

    /**
     * 获取设备失败
     */
    void onDelDevicesFail(String message);

    /**
     * 获取设备错误
     */
    void onDelDevicesError(String message);
}
