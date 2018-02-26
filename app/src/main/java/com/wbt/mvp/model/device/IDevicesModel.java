package com.wbt.mvp.model.device;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/29.
 *
 */

public interface IDevicesModel {
    /**
     * action = addDevice
        userid = 12
        username = yuan
        devicename = demo
        deviceaddre = 00:00:00:00
        addtime = 152355660000
     *  @param iDevicesListener
     */
    void addDevice(String userid,String username,String devicename,String deviceaddre,Long addtime,
                   IDevicesListener iDevicesListener);

    void updateDevice(String deviceid,String userid,String username,String devicename,
                      String deviceaddre,Long addtime,IDevicesListener iDevicesListener);

    void deleteDevice(String deviceid,String userid,String username,IDeleteDeviceListener iDeleteDeviceListener);
}
