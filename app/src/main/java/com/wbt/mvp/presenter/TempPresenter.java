package com.wbt.mvp.presenter;

import com.wbt.mvp.model.temp.ITempListener;
import com.wbt.mvp.model.temp.ITempModel;
import com.wbt.mvp.model.temp.TempModel;

/**
 * Created by Administrator on 2018/1/18.
 */

public class TempPresenter {

    private ITempModel iTempModel;

    public TempPresenter(){
        iTempModel = new TempModel();
    }

    public void getTemp(String tpdevid,ITempListener iTempListener){
        iTempModel.getTemp(tpdevid,iTempListener);
    }
}
