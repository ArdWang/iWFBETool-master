package com.wbt.mvp.model.probe;

/**
 * Created by Administrator on 2018/2/1.
 */

public interface IPobeModel {

    void getProbe(String deviceid,String probename,Long begintime,Long endtime,IPorbeListener iPorbeListener);

}
