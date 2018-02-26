package com.wbt.mvp.model.probe;

import com.wbt.bean.ProbeBean;
import com.wbt.http.retrofitrxjava.loader.ProbeLoader;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/2/1.
 *
 */

public class ProbeModel implements IPobeModel{
    private ProbeLoader probeLoader;



    @Override
    public void getProbe(String deviceid, String probename, Long begintime, Long endtime, final IPorbeListener iPorbeListener) {
        probeLoader = new ProbeLoader();


        Map<String,Object> params = new HashMap<>();
        params.put("action","getProbe");
        params.put("deviceid",deviceid);
        params.put("probename",probename);
        params.put("begintime",begintime);
        params.put("endtime",endtime);


        probeLoader.geProbe(params).subscribe(new Observer<ProbeBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ProbeBean probeBean) {
                if(probeBean.getCode().equals("200")){
                    if(probeBean.getProbes().size()>0){
                        iPorbeListener.onProbeSuccess(probeBean.getProbes());
                    }
                }else{
                    iPorbeListener.onProbeFail("没有数据");
                }
            }

            @Override
            public void onError(Throwable e) {
                iPorbeListener.onProbeError("网络错误");
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
