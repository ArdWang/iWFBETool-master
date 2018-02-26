package com.wbt.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.wbt.R;
import com.wbt.bean.UserBean;
import com.wbt.mvp.model.login.ILoginListener;
import com.wbt.mvp.presenter.LoginPresenter;
import com.wbt.mvp.view.ILoginView;
import com.wbt.ui.base.BaseActivity;
import com.wbt.util.ActivityCollector;
import com.wbt.util.HandlerUtils;
import com.wbt.util.NetworkUtil;
import com.wbt.util.config.StatusConfig;
import com.wbt.util.customview.CustomProgress;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by rnd on 2018/1/18.
 *
 * 需要增加的功能 账户密码存储  以及第一次登录下次可以免除登录 记住用户登录的状态
 *
 */

public class LoginActivity extends AppCompatActivity implements ILoginView,HandlerUtils.OnReceiveMessageListener,View.OnClickListener {
    private EditText txt_user;

    private EditText txt_pwd;

    private Button login;
    /**
     * 登陆presenter接口
     */
    private LoginPresenter loginPresenter;

    //List<UserBean.UsersBean> userlist;
    private UserBean.UserBeanBean userbeans;

    private HandlerUtils.HandlerHolder handlerHolder;

    private String ErrorMsg;

    //private ProgressDialog dialog;

    private String userid,username,password;

    private SharedPreferences spuser;

    private CustomProgress customProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initStatusBar();

        initView();

        initData();
    }

    private void initView() {
        txt_user = findViewById(R.id.username);
        txt_pwd = findViewById(R.id.password);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    private void initData(){
        spuser = getSharedPreferences(StatusConfig.PREFS_USER_KEY,0);
        userbeans = new UserBean.UserBeanBean();
        handlerHolder = new HandlerUtils.HandlerHolder(this);
        loginPresenter = new LoginPresenter(this);

        String userid = spuser.getString(StatusConfig.PREFS_USERID_KEY,"");
        String username=spuser.getString(StatusConfig.PREFS_USERNAME_KEY, "");
        String password =spuser.getString(StatusConfig.PREFS_PASSWORD_KEY, "");

        //如果不为空的时候直接跳转到主页面
        if(!userid.isEmpty()&&!username.isEmpty()&&!password.isEmpty()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    /**
     * 初始化沉浸式状态栏
     */
    private void initStatusBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                if(NetworkUtil.isNetworkAvailable(this)) {
                    if(customProgress==null){
                        customProgress = CustomProgress.show(this,"Loading...",true,null);
                    }
                    loginPresenter.Login(iLoginListener);
                }else{
                    ErrorMsg = "网络错误！";
                    handlerHolder.sendEmptyMessage(0);
                }
            break;
        }
    }

    private ILoginListener iLoginListener = new ILoginListener() {
        @Override
        public void onLoginSuccess(UserBean.UserBeanBean userbean) {
            userbeans = userbean;
            if(userbeans!=null) {
                handlerHolder.sendEmptyMessage(200);
            }

        }

        @Override
        public void onLoginFail(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(1);
        }

        @Override
        public void onLoginError(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(0);
        }
    };

    @Override
    public void handlerMessage(Message msg) {
        switch (msg.what){
            case 0:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(this,"登录失败! 原因："+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 1:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(this,"登录失败! 原因："+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 200:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }

                if(userbeans!=null) {
                    saveUserData(userbeans);
                }

                Intent intent = new Intent(this, MainActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(StatusConfig.USER_KEY,userbeans);
                intent.putExtras(mBundle);
                startActivity(intent);
                finish();
                break;
        }
    }

    // userBean : {"userid":12,"username":"yuan","password":"123456","age":18,"sex":"男",
    // "phone":"435353","email":"4535435","hoby":"这家伙很懒","note":"没有备注","admin":0}
    private void saveUserData(UserBean.UserBeanBean userbeans){
        //存储提交
        SharedPreferences.Editor editor=spuser.edit();
        editor.putString(StatusConfig.PREFS_USERID_KEY,userbeans.getUserid()+"");
        editor.putString(StatusConfig.PREFS_USERNAME_KEY, userbeans.getUsername());
        editor.putString(StatusConfig.PREFS_PASSWORD_KEY, userbeans.getPassword());
        editor.putInt(StatusConfig.PREFS_AGE_KEY, userbeans.getAge());
        editor.putString(StatusConfig.PREFS_SEX_KEY, userbeans.getSex());
        editor.putString(StatusConfig.PREFS_PHONE_KEY, userbeans.getPhone());
        editor.putString(StatusConfig.PREFS_EMAIL_KEY, userbeans.getEmail());
        editor.putString(StatusConfig.PREFS_HOBY_KEY, userbeans.getHoby());
        editor.putString(StatusConfig.PREFS_NOTE_KEY, userbeans.getNote());
        editor.apply();
    }


    @Override
    public String getUserName() {
        return txt_user.getText().toString();
    }

    @Override
    public String getPassWord() {
        return txt_pwd.getText().toString();
    }

    /**
     * 退出程序显示提示
     */
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }
            else {
                ActivityCollector.finishAll();
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 隐藏键盘代码
     * @param ev
     * @return
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
