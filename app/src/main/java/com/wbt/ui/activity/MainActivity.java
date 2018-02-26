package com.wbt.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.wbt.R;
import com.wbt.bean.UserBean;
import com.wbt.ui.base.BaseFragmentActivity;
import com.wbt.ui.fragment.AccountFragment;
import com.wbt.ui.fragment.AddDeviceFragment;
import com.wbt.ui.fragment.DeviceFragment;
import com.wbt.ui.fragment.SerachFragment;
import com.wbt.util.ActivityCollector;
import com.wbt.util.config.StatusConfig;

public class MainActivity extends BaseFragmentActivity {

    //界面底部的菜单按钮
    private LinearLayout[] bt_menu = new LinearLayout[4];
    //界面底部的菜单id
    private int[] bt_menu_id  ={R.id.data,R.id.only,R.id.home,R.id.set};
    //界面的图片
    private ImageView[] bt_img=new ImageView[4];
    //界面底部的选中图片id
    private int[] bt_img_ids = {R.id.iv_menu_0,R.id.iv_menu_1,R.id.iv_menu_2,R.id.iv_menu_3};
    //界面底部的文字
    private TextView[] bt_txt = new TextView[4];
    //界面底部的文字Id
    private int[] bt_txt_id={R.id.tv_menu_0,R.id.tv_menu_1,R.id.tv_menu_2,R.id.tv_menu_3};
    //界面底部的选中图片id
    //private int[] bt_img_id = {R.id.iv_menu_0,R.id.iv_menu_1,R.id.iv_menu_2,R.id.iv_menu_3};
    //界面底部选中菜单资源
    private int []select_on={R.drawable.only_n,R.drawable.message_n,
            R.drawable.home_n,R.drawable.set_n};

    // 界面底部的未选中菜单按钮资源
    private int []select_off={R.drawable.only_p,R.drawable.message_p,
            R.drawable.home_p,R.drawable.set_p};
    //界面主数组
    private Fragment[] fgtmain;

    private Fragment data,devices,settings,account;

    //private List<UserBean.UsersBean> userlist;

    private UserBean.UserBeanBean userbaen;

    public static String userid;
    public static String username;


    private SharedPreferences spuser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        initView();
        initData();

       // getIntentData();
    }

    private void initView(){

        spuser = getSharedPreferences(StatusConfig.PREFS_USER_KEY,0);

        //找到底部菜单按钮 并设置真听
        for(int i = 0;i<bt_menu.length;i++){
            bt_menu[i] = findViewById(bt_menu_id[i]);
            bt_img[i] = findViewById(bt_img_ids[i]);
            bt_txt[i] = findViewById(bt_txt_id[i]);
            bt_menu[i].setOnClickListener(this);
        }
        //默认的时候是第主页选中
        bt_img[0].setBackgroundResource(select_on[0]);       //选中图片更换
        bt_txt[0].setTextColor(getResources().getColor(R.color.colorSYellow));

        setFgtmain();
    }

    private void setFgtmain() {
        try {
            data = new DeviceFragment();
            devices = new AddDeviceFragment();
            settings = new SerachFragment();
            account = new AccountFragment();
            fgtmain = new Fragment[]{data, devices, settings, account};
            getSupportFragmentManager().beginTransaction().add(R.id.currentFL, data)
                    .add(R.id.currentFL, devices).add(R.id.currentFL, settings)
                    .add(R.id.currentFL, account).show(data).hide(devices)
                    .hide(settings).hide(account).commit();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



    private void initData(){
        try {
            String userids = spuser.getString(StatusConfig.PREFS_USERID_KEY,"");
            String usernames=spuser.getString(StatusConfig.PREFS_USERNAME_KEY, "");

            if(userids.isEmpty()&&usernames.isEmpty()) {
                Intent intent = getIntent();
                // 获取该Intent所携带的数据
                Bundle bundle = intent.getExtras();
                // 从bundle数据包中取出数据
                if (bundle != null) {
                    userbaen = (UserBean.UserBeanBean) bundle.getSerializable(StatusConfig.USER_KEY);
                    if (userbaen != null) {
                        userid = userbaen.getUserid() + "";
                        username = userbaen.getUsername();
                    }
                }
            }else{
                userid = userids;
                username = usernames;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*private void getIntentData(){
        Intent intent = getIntent();
        userlist = (List<UserBean.UsersBean>) intent.getSerializableExtra("userlist");
    }*/


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //底部按钮事件点击切换
            case R.id.data:
                switchFragment(0);
                changeSelector(R.id.data);
                break;
            case R.id.only:
                switchFragment(1);
                changeSelector(R.id.only);
                break;
            case R.id.home:
                switchFragment(2);
                changeSelector(R.id.home);
                break;
            case R.id.set:
                switchFragment(3);
                changeSelector(R.id.set);
                break;
        }
    }

    /**
     * 点击选中的资源的颜色和文字变化
     * @param a
     */
    private void changeSelector(int a){
        for(int i=0;i<bt_menu.length;i++){
            if(a==bt_menu_id[i]){
                bt_img[i].setBackgroundResource(select_on[i]);       //选中图片更换
                bt_txt[i].setTextColor(getResources().getColor(R.color.colorSYellow));
            }else{
                bt_img[i].setBackgroundResource(select_off[i]);

                bt_txt[i].setTextColor(getResources().getColor(R.color.colorGery));  //选中 文字也跟着改变颜色
            }
        }
    }

    private int currentIndex=0;  //当前的选中项为首页
    /**
     * 选中切换Fragment的代码
     * @param index
     */
    private void switchFragment(int index){
        try{
            if(currentIndex!=index){
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.hide(fgtmain[currentIndex]);
                if(!fgtmain[index].isAdded()){  //是否已经添加了
                    ft.add(R.id.currentFL,fgtmain[index]);
                }

                ft.show(fgtmain[index]).commit();
            }
            currentIndex = index;
        }catch(Exception ex){
            ex.printStackTrace();
        }
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





}
