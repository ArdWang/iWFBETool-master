package com.wbt.mvp.model.login;

import android.text.TextUtils;
import android.util.Log;
import com.wbt.bean.UserBean;
import com.wbt.http.retrofitrxjava.loader.LoginLoader;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by rnd on 2018/1/18.
 *
 */

public class LoginModel implements ILoginModel{
    private LoginLoader loginLoader;
    private String ErrorMsg;

    @Override
    public void Login(String username, String password,final ILoginListener loginListener) {
        loginLoader = new LoginLoader();
        ErrorMsg = "";


        //判断不能为空
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            loginListener.onLoginError("输入不能为空！");
            return;
        }

        Map<String,Object> params = new HashMap<>();
        params.put("action","getUser");
        params.put("username",username);
        params.put("password",password);

        loginLoader.getLogin(params).subscribe(new Observer<UserBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                /**
                 * Disposable是1.x的Subscription改名的，因为Reactive-Streams规范用这个名称，为了避免重复
                 * 这个回调方法是在2.0之后新添加的
                 * 可以使用d.dispose()方法来取消订阅
                 */


            }

            @Override
            public void onNext(UserBean userBean) {
                if(userBean.getCode().equals("200")&&userBean.getUserBean()!=null){
                    loginListener.onLoginSuccess(userBean.getUserBean());
                }else{
                    loginListener.onLoginFail("账号或者密码不正确！");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","error message:"+e.getMessage());

                loginListener.onLoginError(ErrorMsg);
            }

            @Override
            public void onComplete() {
                //可以干一些别的事情
                Log.e("onComplete", "complete");
            }
        });

    }

    @Override
    public void updateUser(UserBean.UserBeanBean userBeanBean, final ILoginListener loginListener) {
        loginLoader = new LoginLoader();

        if(userBeanBean==null){
            loginListener.onLoginError("空值错误！");
            return;
        }

        Map<String,Object> params = new HashMap<>();
        params.put("action","updateUser");
        params.put("userid",userBeanBean.getUserid());
        params.put("username",userBeanBean.getUsername());
        params.put("password",userBeanBean.getPassword());
        params.put("age",userBeanBean.getAge());
        params.put("sex",userBeanBean.getSex());
        params.put("phone",userBeanBean.getPhone());
        params.put("email",userBeanBean.getEmail());
        params.put("hoby",userBeanBean.getHoby());
        params.put("note",userBeanBean.getNote());

        loginLoader.updateUser(params).subscribe(new Observer<UserBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserBean userBean) {
                if(userBean.getCode().equals("200")&&userBean.getUserBean()!=null){
                    loginListener.onLoginSuccess(userBean.getUserBean());
                }else{
                    loginListener.onLoginFail("修改失败！");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","error message:"+e.getMessage());
                loginListener.onLoginError(ErrorMsg);
            }

            @Override
            public void onComplete() {

            }
        });


    }
}
