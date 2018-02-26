package com.wbt.http.retrofitrxjava.loader;

import com.wbt.bean.DeleteBean;
import com.wbt.bean.DeviceBean;
import com.wbt.bean.DevicesBean;
import com.wbt.http.retrofitrxjava.httputil.RetrofitServiceManager;
import com.wbt.http.retrofitrxjava.service.IDeviceService;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2018/1/26.
 *
 */

public class DeviceLoader extends ObjectLoader{
    private IDeviceService iDeviceService;

    public DeviceLoader(){
        iDeviceService = RetrofitServiceManager.getInstance().create(IDeviceService.class);
    }

    /**
     * 得到设备
     * @param params
     * @return
     */
    public Observable<DeviceBean> getDevice(Map<String,Object> params){
        return observe(iDeviceService.getDevice(params)).map(new Function<DeviceBean, DeviceBean>() {
            @Override
            public DeviceBean apply(DeviceBean devicesBean) throws Exception {
                return devicesBean;
            }
        });
    }

    /**
     * 添加设备
     * @param parasm
     * @return
     */
    public Observable<DevicesBean> addDevice(Map<String,Object> parasm){
        return observe(iDeviceService.addDevice(parasm)).map(new Function<DevicesBean, DevicesBean>() {
            @Override
            public DevicesBean apply(DevicesBean deviceBean) throws Exception {
                return deviceBean;
            }
        });
    }

    /**
     * 更新设备
     * @param params
     * @return
     */
    public Observable<DevicesBean> updateDevice(Map<String,Object> params){
        return observe(iDeviceService.updateDevice(params)).map(new Function<DevicesBean, DevicesBean>() {
            @Override
            public DevicesBean apply(DevicesBean devicesBean) throws Exception {
                return devicesBean;
            }
        });
    }

    /**
     * 删除设备
     * @param params
     * @return
     */
    public Observable<DeleteBean> deleteDevice(Map<String,Object> params){
        return observe(iDeviceService.deleteDevice(params)).map(new Function<DeleteBean, DeleteBean>() {
            @Override
            public DeleteBean apply(DeleteBean deleteBean) throws Exception {
                return deleteBean;
            }
        });
    }


}
