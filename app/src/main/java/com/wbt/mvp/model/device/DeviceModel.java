package com.wbt.mvp.model.device;

import android.util.Log;

import com.wbt.bean.DeviceBean;
import com.wbt.http.retrofitrxjava.loader.DeviceLoader;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/1/26.
 *
 */

public class DeviceModel implements IDeviceModel{
    private DeviceLoader deviceLoader;
    private String ErrorMsg;

    @Override
    public void getDevice(String userid, String username, final IDeviceListener iDeviceListener) {
        deviceLoader = new DeviceLoader();
        ErrorMsg = "";
        Map<String,Object> params = new HashMap<>();
        params.put("action","getAllDevice");
        params.put("userid",userid);
        params.put("username",username);

        deviceLoader.getDevice(params).subscribe(new Observer<DeviceBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(DeviceBean deviceBean) {
                if(deviceBean.getCode().equals("200")&&deviceBean.getDevices().size()>0){
                    iDeviceListener.onDeviceSuccess(deviceBean.getDevices());
                }else{
                    iDeviceListener.onDeviceFail("获取设备数据失败！");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","error message:"+e.getMessage());
                iDeviceListener.onDeviceError("网络错误!");
            }

            @Override
            public void onComplete() {

            }
        });

    }


}
