package com.wbt.http.retrofitrxjava.loader;

import com.wbt.bean.DataBean;
import com.wbt.http.retrofitrxjava.httputil.RetrofitServiceManager;
import com.wbt.http.retrofitrxjava.service.IDataService;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2018/1/27.
 *
 */

public class DataLoader extends ObjectLoader{
    private IDataService iDataService;

    public DataLoader(){
        iDataService = RetrofitServiceManager.getInstance().create(IDataService.class);
    }

    public Observable<DataBean> getData(Map<String,Object> params){
        return observe(iDataService.getData(params)).map(new Function<DataBean, DataBean>() {
            @Override
            public DataBean apply(DataBean dataBean) throws Exception {
                return dataBean;
            }
        });
    }

}
