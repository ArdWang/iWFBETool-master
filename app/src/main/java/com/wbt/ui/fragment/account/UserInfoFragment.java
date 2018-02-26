package com.wbt.ui.fragment.account;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wbt.R;
import com.wbt.bean.UserBean;
import com.wbt.mvp.model.login.ILoginListener;
import com.wbt.mvp.presenter.LoginPresenter;
import com.wbt.ui.activity.AcountActivity;
import com.wbt.ui.base.BaseFragment;
import com.wbt.util.HandlerUtils;
import com.wbt.util.config.StatusConfig;
import com.wbt.util.customview.dialog.UserDialog;


/**
 * Created by rnd on 2018/1/31.
 *
 */

public class UserInfoFragment extends BaseFragment implements HandlerUtils.OnReceiveMessageListener{

    private AcountActivity aa;

    private LinearLayout llsingle,llphone,llemail;

    private TextView txtsigle,txtphone,txtemail,txtusername;

    private UserDialog userDialog;

    private SharedPreferences spuser;

    private String userid,username,password,sex,phone,email,hoby,note;

    private int age;

    private LoginPresenter loginPresenter;

    private HandlerUtils.HandlerHolder handlerHolder;

    private String ErrorMsg;

    private UserBean.UserBeanBean userBeanBean;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        aa = (AcountActivity) getActivity();
        View view = inflater.inflate(R.layout.user_info_fg_layout,container,false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        llsingle = view.findViewById(R.id.ll_single);
        llphone = view.findViewById(R.id.ll_phone);
        llemail = view.findViewById(R.id.ll_email);

        txtsigle = view.findViewById(R.id.txt_single);
        txtphone = view.findViewById(R.id.txt_phone);
        txtemail = view.findViewById(R.id.txt_email);

        txtusername = view.findViewById(R.id.txt_name);

        llsingle.setOnClickListener(this);
        llphone.setOnClickListener(this);
        llemail.setOnClickListener(this);
    }

    private void initData(){
        handlerHolder = new HandlerUtils.HandlerHolder(this);
        loginPresenter = new LoginPresenter();
        spuser = getActivity().getSharedPreferences(StatusConfig.PREFS_USER_KEY,0);
        userid = spuser.getString(StatusConfig.PREFS_USERID_KEY,"");
        username=spuser.getString(StatusConfig.PREFS_USERNAME_KEY, "");
        password =spuser.getString(StatusConfig.PREFS_PASSWORD_KEY, "");
        age = spuser.getInt(StatusConfig.PREFS_AGE_KEY,0);
        sex = spuser.getString(StatusConfig.PREFS_SEX_KEY,"");
        phone = spuser.getString(StatusConfig.PREFS_PHONE_KEY,"");
        email = spuser.getString(StatusConfig.PREFS_EMAIL_KEY,"");
        hoby = spuser.getString(StatusConfig.PREFS_HOBY_KEY,"");
        note = spuser.getString(StatusConfig.PREFS_NOTE_KEY,"");

        if(!phone.isEmpty()&&!email.isEmpty()&&!note.isEmpty()&&!username.isEmpty()) {
            txtsigle.setText(note);
            txtphone.setText(phone);
            txtemail.setText(email);
            txtusername.setText(username);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_single:
                getUpdateUser("Single",note);
                break;

            case R.id.ll_phone:
                getUpdateUser("Phone",phone);
                break;

            case R.id.ll_email:
                getUpdateUser("Email",email);
                break;
        }
    }


    private void getUpdateUser(final String txt,String edit){
        final UserDialog.Builder builder = new UserDialog.Builder(getActivity());
        builder.setName(txt);
        builder.setEdit(edit);

        builder.setPositiveButton(R.string.edit_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                String editt = builder.getEditText().getText().toString().trim();
                //进行数据操作
                if(txt.equals("Phone")) {
                    updatePhone(editt);
                }else if(txt.equals("Email")){
                    updateEmail(editt);
                }else if(txt.equals("Single")){
                    updateSingle(editt);
                }

                if(userBeanBean!=null){
                    loginPresenter.updateUser(userBeanBean,iLoginListener);
                }
            }
        }).setnegativeButton(R.string.edit_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        userDialog = builder.cerate();
        userDialog.show();
        userDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        //dialog.setCancelable(true);
        userDialog.setCanceledOnTouchOutside(true);
    }

    private void updatePhone(String edit){
        userBeanBean = new UserBean.UserBeanBean();
        int id = Integer.parseInt(userid);
        userBeanBean.setUserid(id);
        userBeanBean.setUsername(username);
        userBeanBean.setPassword(password);
        userBeanBean.setAge(age);
        userBeanBean.setSex(sex);
        userBeanBean.setPhone(edit);
        userBeanBean.setEmail(email);
        userBeanBean.setHoby(hoby);
        userBeanBean.setNote(note);
    }

    private void updateEmail(String edit){
        userBeanBean = new UserBean.UserBeanBean();
        int id = Integer.parseInt(userid);
        userBeanBean.setUserid(id);
        userBeanBean.setUsername(username);
        userBeanBean.setPassword(password);
        userBeanBean.setAge(age);
        userBeanBean.setSex(sex);
        userBeanBean.setPhone(phone);
        userBeanBean.setEmail(edit);
        userBeanBean.setHoby(hoby);
        userBeanBean.setNote(note);
    }

    private void updateSingle(String edit){
        userBeanBean = new UserBean.UserBeanBean();
        int id = Integer.parseInt(userid);
        userBeanBean.setUserid(id);
        userBeanBean.setUsername(username);
        userBeanBean.setPassword(password);
        userBeanBean.setAge(age);
        userBeanBean.setSex(sex);
        userBeanBean.setPhone(phone);
        userBeanBean.setEmail(email);
        userBeanBean.setHoby(hoby);
        userBeanBean.setNote(edit);
    }


    ILoginListener iLoginListener = new ILoginListener() {
        @Override
        public void onLoginSuccess(UserBean.UserBeanBean userbean) {
            userBeanBean = userbean;
            if(userBeanBean!=null){
                handlerHolder.sendEmptyMessage(200);
            }else{
                handlerHolder.sendEmptyMessage(2);
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
                Toast.makeText(getActivity(),ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 1:
                Toast.makeText(getActivity(),ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 2:
                Toast.makeText(getActivity(),ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 200:
                 if(userBeanBean!=null){
                     note = userBeanBean.getNote();
                     email = userBeanBean.getEmail();
                     phone = userBeanBean.getPhone();

                     txtsigle.setText(note);
                     txtemail.setText(email);
                     txtphone.setText(phone);

                     //存储新的数据
                     saveUserData(userBeanBean);
                 }

                break;
        }
    }



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
}
