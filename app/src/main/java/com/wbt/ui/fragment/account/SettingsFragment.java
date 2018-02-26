package com.wbt.ui.fragment.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.wbt.R;
import com.wbt.ui.activity.AcountActivity;
import com.wbt.ui.activity.LoginActivity;
import com.wbt.ui.base.BaseFragment;
import com.wbt.util.ActivityCollector;
import com.wbt.util.config.StatusConfig;

/**
 * Created by rnd on 2018/1/31.
 *
 */

public class SettingsFragment extends BaseFragment{

    private AcountActivity aa;

    private Button exitproject;

    private SharedPreferences spuser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        aa = (AcountActivity) getActivity();
        View view = inflater.inflate(R.layout.settings_fg_layout,container,false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        exitproject = view.findViewById(R.id.exit_project);

        exitproject.setOnClickListener(this);
    }

    private void initData(){
        spuser = getActivity().getSharedPreferences(StatusConfig.PREFS_USER_KEY,0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.exit_project:
                saveUserData();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                ActivityCollector.finishAll();
                break;
        }
    }

    private void saveUserData(){
        try {
            //存储提交
            SharedPreferences.Editor editor = spuser.edit();
            editor.putString(StatusConfig.PREFS_USERID_KEY, "");
            editor.putString(StatusConfig.PREFS_USERNAME_KEY, "");
            editor.putString(StatusConfig.PREFS_PASSWORD_KEY, "");
            editor.putInt(StatusConfig.PREFS_AGE_KEY, 0);
            editor.putString(StatusConfig.PREFS_SEX_KEY, "");
            editor.putString(StatusConfig.PREFS_PHONE_KEY, "");
            editor.putString(StatusConfig.PREFS_EMAIL_KEY, "");
            editor.putString(StatusConfig.PREFS_HOBY_KEY, "");
            editor.putString(StatusConfig.PREFS_NOTE_KEY, "");
            editor.apply();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
