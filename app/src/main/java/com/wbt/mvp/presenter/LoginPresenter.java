package com.wbt.mvp.presenter;

import com.wbt.bean.UserBean;
import com.wbt.mvp.model.login.ILoginListener;
import com.wbt.mvp.model.login.ILoginModel;
import com.wbt.mvp.model.login.LoginModel;
import com.wbt.mvp.view.ILoginView;

/**
 * Created by rnd on 2018/1/18.
 *
 */

public class LoginPresenter {
    private ILoginView iLoginView;

    private ILoginModel iLoginModel;

    public LoginPresenter(ILoginView iLoginView){
        this.iLoginView = iLoginView;
        iLoginModel = new LoginModel();
    }

    public LoginPresenter(){
        iLoginModel = new LoginModel();
    }

    public void Login(ILoginListener iLoginListener){
        iLoginModel.Login(iLoginView.getUserName(),iLoginView.getPassWord(),iLoginListener);
    }

    public void updateUser(UserBean.UserBeanBean userBeanBean,ILoginListener iLoginListener){
        iLoginModel.updateUser(userBeanBean,iLoginListener);
    }

}
