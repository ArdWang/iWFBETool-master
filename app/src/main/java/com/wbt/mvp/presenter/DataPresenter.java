package com.wbt.mvp.presenter;

import com.wbt.mvp.model.data.DataModel;
import com.wbt.mvp.model.data.IDataListener;
import com.wbt.mvp.model.data.IDataModel;

/**
 * Created by Administrator on 2018/1/27.
 *
 */
public class DataPresenter {

    private IDataModel iDataModel;

    public DataPresenter(){
        iDataModel = new DataModel();
    }

    public void getData(String dataaddre, IDataListener iDataListener){
        iDataModel.getData(dataaddre,iDataListener);
    }
}
