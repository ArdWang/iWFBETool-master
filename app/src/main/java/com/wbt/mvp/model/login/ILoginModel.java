package com.wbt.mvp.model.login;

import com.wbt.bean.UserBean;

/**
 * Created by rnd on 2018/1/18.
 *
 */

public interface ILoginModel {
    /**
     * 提取的一个登陆方法，当然还可以有其它方法，比如获取数据，保存用户信息之类
     * @param username  用户名
     * @param password   密码
     * @param loginListener 登陆监听
     */
    void Login(String username,String password,ILoginListener loginListener);

    void updateUser(UserBean.UserBeanBean userBeanBean,ILoginListener loginListener);


}
