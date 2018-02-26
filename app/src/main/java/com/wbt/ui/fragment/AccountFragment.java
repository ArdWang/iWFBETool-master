package com.wbt.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wbt.R;
import com.wbt.ui.activity.AcountActivity;
import com.wbt.ui.activity.MainActivity;
import com.wbt.ui.base.BaseFragment;
import com.wbt.util.config.StatusConfig;

/**
 * Created by Administrator on 2018/1/18.
 *
 */

public class AccountFragment extends BaseFragment{
    private MainActivity ma;
    private LinearLayout lluser;
    private RelativeLayout rlmanage;
    private RelativeLayout rlversion;
    private RelativeLayout rlfunction;
    private RelativeLayout rlabount;
    private RelativeLayout rlset;

    private SharedPreferences spuser;

    private String username,single;

    private TextView setusername,setqianming;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ma = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.account_fg_layout,container,false);
        initView(view);
        onCickEvent();
        initData();
        return view;

    }

    private void initView(View view){
        lluser = view.findViewById(R.id.ll_user);
        rlmanage = view.findViewById(R.id.rl_manage);
        rlversion = view.findViewById(R.id.rl_version);
        rlfunction = view.findViewById(R.id.rl_function);
        rlabount = view.findViewById(R.id.rl_abount);
        rlset = view.findViewById(R.id.rl_set);

        setusername = view.findViewById(R.id.set_username);
        setqianming = view.findViewById(R.id.set_qianming);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();

    }

    private void initData(){
        spuser = getActivity().getSharedPreferences(StatusConfig.PREFS_USER_KEY,0);
    }

    private void getData(){
        username=spuser.getString(StatusConfig.PREFS_USERNAME_KEY, "");
        single = spuser.getString(StatusConfig.PREFS_NOTE_KEY,"");
        if(!username.isEmpty()&&!single.isEmpty()){
            setusername.setText(username);
            setqianming.setText(single);
        }

    }



    private void onCickEvent(){
        lluser.setOnClickListener(this);
        rlmanage.setOnClickListener(this);
        rlversion.setOnClickListener(this);
        rlfunction.setOnClickListener(this);
        rlabount.setOnClickListener(this);
        rlset.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){

            case R.id.ll_user:
                intent = new Intent(getActivity(), AcountActivity.class);
                intent.putExtra("value", 0);
                intent.putExtra("toptxt", "User Info");
                startActivity(intent);
                break;

            case R.id.rl_manage:
                intent = new Intent(getActivity(), AcountActivity.class);
                intent.putExtra("value", 1);
                intent.putExtra("toptxt", "Manage");
                startActivity(intent);
                break;

            case R.id.rl_version:
                intent = new Intent(getActivity(), AcountActivity.class);
                intent.putExtra("value", 2);
                intent.putExtra("toptxt", "Version");
                startActivity(intent);
                break;

            case R.id.rl_function:
                intent = new Intent(getActivity(), AcountActivity.class);
                intent.putExtra("value", 3);
                intent.putExtra("toptxt", "Function");
                startActivity(intent);
                break;

            case R.id.rl_abount:
                intent = new Intent(getActivity(), AcountActivity.class);
                intent.putExtra("value", 4);
                intent.putExtra("toptxt", "About Me");
                startActivity(intent);
                break;

            case R.id.rl_set:
                intent = new Intent(getActivity(), AcountActivity.class);
                intent.putExtra("value", 5);
                intent.putExtra("toptxt", "Settings");
                startActivity(intent);
                break;
        }
    }
}
