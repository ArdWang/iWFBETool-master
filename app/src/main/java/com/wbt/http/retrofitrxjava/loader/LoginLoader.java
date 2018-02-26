package com.wbt.http.retrofitrxjava.loader;

import com.wbt.bean.UserBean;
import com.wbt.http.retrofitrxjava.httputil.RetrofitServiceManager;
import com.wbt.http.retrofitrxjava.service.ILoginService;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by rnd on 2018/1/18.
 *
 */

public class LoginLoader extends ObjectLoader{
    private ILoginService iLoginService;

    public LoginLoader(){
        iLoginService = RetrofitServiceManager.getInstance().create(ILoginService.class);
    }

    /**
     * 登录
     * @param params
     * @return userBean
     */
    public Observable<UserBean> getLogin(Map<String,Object> params){
        return observe(iLoginService.getLogin(params)).map(new Function<UserBean, UserBean>() {
            @Override
            public UserBean apply(UserBean userBean) throws Exception {
                return userBean;
            }
        });
    }

    /**
     * 更新用户信息
     * @param params
     * @return
     */
    public Observable<UserBean> updateUser(Map<String,Object> params){
        return observe(iLoginService.updateUser(params)).map(new Function<UserBean, UserBean>() {
            @Override
            public UserBean apply(UserBean userBean) throws Exception {
                return userBean;
            }
        });
    }


}
