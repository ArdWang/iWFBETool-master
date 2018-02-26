package com.wbt.mvp.model.temp;

import com.wbt.bean.TempBean;
import com.wbt.bean.UserBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public interface ITempListener {
    /**
     * 登陆成功
     */
    public void onTempSuccess(List<TempBean.TempsBean> list);

    /**
     * 登陆失败
     */
    public void onTempFail(String message);

    /**
     * 登陆错误
     */
    public void onTempError(String message);
}
