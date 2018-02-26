package com.wbt.http.retrofitrxjava.loader;

import com.wbt.bean.TempBean;
import com.wbt.http.retrofitrxjava.httputil.RetrofitServiceManager;
import com.wbt.http.retrofitrxjava.service.ITempService;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by rnd on 2018/1/18.
 * 获取TempLoader
 */

public class TempLoader extends ObjectLoader{
    private ITempService iTempService;

    public TempLoader(){
        iTempService = RetrofitServiceManager.getInstance().create(ITempService.class);
    }

    public Observable<TempBean> getTemp(Map<String,Object> params){
        return observe(iTempService.getTemp(params)).map(new Function<TempBean, TempBean>() {
            @Override
            public TempBean apply(TempBean tempBean) throws Exception {
                return tempBean;
            }
        });
    }

}
