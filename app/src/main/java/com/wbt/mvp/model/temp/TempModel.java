package com.wbt.mvp.model.temp;

import android.util.Log;

import com.wbt.bean.TempBean;
import com.wbt.http.retrofitrxjava.loader.TempLoader;
import com.wbt.mvp.model.login.ILoginListener;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/1/18.
 */

public class TempModel implements ITempModel{
    private TempLoader tempLoader;
    private String ErrorMsg;
    @Override
    public void getTemp(String tpdvid, final ITempListener tempListener) {
        tempLoader = new TempLoader();
        ErrorMsg = "";

        Map<String,Object> params = new HashMap<>();
        params.put("action","getTempOneData");
        params.put("tpdvid",tpdvid);

        tempLoader.getTemp(params).subscribe(new Observer<TempBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TempBean tempBean) {
                if(tempBean.getCode().equals("200")&&tempBean.getTemps().size()>0){
                    tempListener.onTempSuccess(tempBean.getTemps());
                }else{
                    tempListener.onTempFail("账号或者密码不正确！");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","error message:"+e.getMessage());
                tempListener.onTempError(ErrorMsg);
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
