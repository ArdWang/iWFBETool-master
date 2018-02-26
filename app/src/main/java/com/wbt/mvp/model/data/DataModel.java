package com.wbt.mvp.model.data;

import android.util.Log;
import com.wbt.bean.DataBean;
import com.wbt.http.retrofitrxjava.loader.DataLoader;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/1/27.
 *
 */

public class DataModel implements IDataModel{
    private DataLoader dataLoader;
    private String ErrorMsg;


    @Override
    public void getData(String dataaddre, final IDataListener iDataListener) {
        dataLoader = new DataLoader();
        ErrorMsg = "";

        Map<String,Object> params = new HashMap<>();
        params.put("action","getCurrent");
        params.put("dataaddre",dataaddre);

        dataLoader.getData(params).subscribe(new Observer<DataBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(DataBean dataBean) {
                if(dataBean.getCode().equals("200")&&dataBean.getDatas().size()>0){
                    iDataListener.onDataSuccess(dataBean);
                }else{
                    iDataListener.onDataFail("获取数据失败");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","error message:"+e.getMessage());
                iDataListener.onDataError("网络错误!");
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
