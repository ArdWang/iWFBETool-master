package com.wbt.http.retrofitrxjava.loader;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rnd on 2018/1/18.
 * 公共线程处理
 */

public class ObjectLoader {
    protected <T> Observable<T> observe(Observable<T> observable){
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()); //指定在主线程中
    }
}
