package com.wbt.mvp.model.device;

import android.util.Log;

import com.wbt.bean.DeleteBean;
import com.wbt.bean.DeviceBean;
import com.wbt.bean.DevicesBean;
import com.wbt.http.retrofitrxjava.loader.DeviceLoader;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/1/29.
 */

public class DevicesModel implements IDevicesModel{
    private DeviceLoader deviceLoader;
    private String ErrorMsg;


    @Override
    public void addDevice(String userid, String username, String devicename, String deviceaddre, Long addtime,final IDevicesListener iDevicesListener) {
        deviceLoader = new DeviceLoader();
        ErrorMsg = "";

        Map<String,Object> params = new HashMap<>();
        params.put("action","addDevice");
        params.put("userid",userid);
        params.put("username",username);
        params.put("devicename",devicename);
        params.put("deviceaddre",deviceaddre);
        params.put("addtime",addtime);

        /**
         * action = addDevice
         userid = 12
         username = yuan
         devicename = demo
         deviceaddre = 00:00:00:00
         添加设备的时间是由app/其它端上传上来 传入是Long型的参数 addtime = 152355660000
         */
        deviceLoader.addDevice(params).subscribe(new Observer<DevicesBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(DevicesBean devicesBean) {
                if(devicesBean.getCode().equals("200")&&devicesBean.getDeviceBean()!=null){
                    iDevicesListener.onDevicesSuccess(devicesBean.getDeviceBean());
                }else{
                    iDevicesListener.onDevicesFail("获取网络数据错误或者设备地址已经被注册！");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","error message:"+e.getMessage());
                iDevicesListener.onDevicesError("网络错误!");
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void updateDevice(String deviceid, String userid, String username, String devicename,
                             String deviceaddre, Long addtime, final IDevicesListener iDevicesListener) {
        deviceLoader = new DeviceLoader();

        Map<String,Object> params = new HashMap<>();
        params.put("action","updateDevice");
        params.put("deviceid",deviceid);
        params.put("userid",userid);
        params.put("username",username);
        params.put("devicename",devicename);
        params.put("deviceaddre",deviceaddre);
        params.put("addtime",addtime);

        deviceLoader.updateDevice(params).subscribe(new Observer<DevicesBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(DevicesBean devicesBean) {
                if(devicesBean.getCode().equals("200")&&devicesBean.getDeviceBean()!=null){
                    iDevicesListener.onDevicesSuccess(devicesBean.getDeviceBean());
                }else{
                    iDevicesListener.onDevicesFail("修改设备失败！");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","error message:"+e.getMessage());
                iDevicesListener.onDevicesError("网络错误!");
            }

            @Override
            public void onComplete() {

            }
        });


    }

    @Override
    public void deleteDevice(String deviceid, String userid, String username, final IDeleteDeviceListener iDeleteDeviceListener) {
        deviceLoader = new DeviceLoader();

        Map<String,Object> params = new HashMap<>();
        params.put("action","deleteDevice");
        params.put("deviceid",deviceid);
        params.put("userid",userid);
        params.put("username",username);


        deviceLoader.deleteDevice(params).subscribe(new Observer<DeleteBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(DeleteBean deleteBean) {
                if(deleteBean.getCode().equals("200")){
                    iDeleteDeviceListener.onDelDevicesSuccess("删除成功");
                }else{
                    iDeleteDeviceListener.onDelDevicesFail("删除失败");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","error message:"+e.getMessage());
                iDeleteDeviceListener.onDelDevicesError("网络错误");
            }

            @Override
            public void onComplete() {

            }
        });
    }


}
