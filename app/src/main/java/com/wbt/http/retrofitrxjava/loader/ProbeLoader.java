package com.wbt.http.retrofitrxjava.loader;

import com.wbt.bean.ProbeBean;
import com.wbt.bean.TempBean;
import com.wbt.http.retrofitrxjava.httputil.RetrofitServiceManager;
import com.wbt.http.retrofitrxjava.service.IProbeService;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2018/2/1.
 *
 */

public class ProbeLoader extends ObjectLoader{
    private IProbeService iProbeService;

    public ProbeLoader(){
        iProbeService = RetrofitServiceManager.getInstance().create(IProbeService.class);
    }

    public Observable<ProbeBean> geProbe(Map<String,Object> params){
        return observe(iProbeService.getProbe(params)).map(new Function<ProbeBean, ProbeBean>() {
            @Override
            public ProbeBean apply(ProbeBean probeBean) throws Exception {
                return probeBean;
            }
        });
    }
}
