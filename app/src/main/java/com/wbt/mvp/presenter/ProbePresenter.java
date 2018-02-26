package com.wbt.mvp.presenter;


import com.wbt.mvp.model.probe.IPobeModel;
import com.wbt.mvp.model.probe.IPorbeListener;
import com.wbt.mvp.model.probe.ProbeModel;

/**
 * Created by Administrator on 2018/2/1.
 *
 */

public class ProbePresenter {
    private IPobeModel iPobeModel;


    public ProbePresenter(){
        iPobeModel = new ProbeModel();
    }

    /**
     * 得到探头的数据
     * @param deviceid
     * @param probename
     * @param begintime
     * @param endtime
     * @param iPorbeListener
     */
    public void getProbe(String deviceid,String probename,Long begintime,Long endtime,IPorbeListener iPorbeListener){
        iPobeModel.getProbe(deviceid,probename,begintime,endtime,iPorbeListener);
    }
}
