package com.wbt.mvp.model.login;

import com.wbt.bean.UserBean;

import java.util.List;

/**
 * Created by rnd on 2018/1/18.
 */

public interface ILoginListener {
    /**
     * 登陆成功
     */
    void onLoginSuccess(UserBean.UserBeanBean userbean);

    /**
     * 登陆失败
     */
    void onLoginFail(String message);

    /**
     * 登陆错误
     */
    void onLoginError(String message);
}
