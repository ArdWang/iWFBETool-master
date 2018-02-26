package com.wbt.mvp.presenter;

import com.wbt.mvp.model.device.DeviceModel;
import com.wbt.mvp.model.device.DevicesModel;
import com.wbt.mvp.model.device.IDeleteDeviceListener;
import com.wbt.mvp.model.device.IDeviceListener;
import com.wbt.mvp.model.device.IDeviceModel;
import com.wbt.mvp.model.device.IDevicesListener;
import com.wbt.mvp.model.device.IDevicesModel;
import com.wbt.mvp.view.IDeviceView;

/**
 * Created by Administrator on 2018/1/29.
 *
 */

public class DevicesPresenter {
    private IDevicesModel iDevicesModel;
    private IDeviceView iDeviceView;


    public DevicesPresenter(){
        iDevicesModel = new DevicesModel();
    }



    public DevicesPresenter(IDeviceView iDeviceView){
        this.iDeviceView = iDeviceView;
        iDevicesModel = new DevicesModel();

    }

    public void addDevice(String userid, String username,Long addtime, IDevicesListener iDevicesListener){
        iDevicesModel.addDevice(userid,username,iDeviceView.getDeviceName(),iDeviceView.getDeviceAddre(),addtime,iDevicesListener);
    }


    public void updateDevice(String deviceid,String userid,String username,String devicename,
                             String deviceaddre,Long addtime,IDevicesListener iDevicesListener){
        iDevicesModel.updateDevice(deviceid,userid,username,devicename,deviceaddre,addtime,iDevicesListener);
    }

    public void deleteDevice(String deviceid, String userid, String username,IDeleteDeviceListener iDeleteDeviceListener){
        iDevicesModel.deleteDevice(deviceid,userid,username,iDeleteDeviceListener);
    }
}
