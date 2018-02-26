package com.wbt.mvp.presenter;

import com.wbt.mvp.model.device.DeviceModel;
import com.wbt.mvp.model.device.IDeviceListener;
import com.wbt.mvp.model.device.IDeviceModel;
import com.wbt.mvp.model.device.IDevicesModel;

/**
 * Created by Administrator on 2018/1/26.
 *
 */

public class DevicePresenter {
    private IDeviceModel iDeviceModel;

    public DevicePresenter(){
        iDeviceModel = new DeviceModel();

    }

    public void getDevice(String userid, String username, IDeviceListener iDeviceListener){
        iDeviceModel.getDevice(userid,username,iDeviceListener);
    }
}
