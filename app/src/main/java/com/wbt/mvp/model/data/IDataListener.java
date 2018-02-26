package com.wbt.mvp.model.data;

import com.wbt.bean.DataBean;
import com.wbt.bean.DeviceBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/27.
 *
 */

public interface IDataListener {
    /**
     * 获取设备成功
     */
    void onDataSuccess(DataBean dataBean);

    /**
     * 获取设备失败
     */
    void onDataFail(String message);

    /**
     * 获取设备错误
     */
    void onDataError(String message);
}
