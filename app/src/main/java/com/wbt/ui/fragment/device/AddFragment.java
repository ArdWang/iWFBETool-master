package com.wbt.ui.fragment.device;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.wbt.R;
import com.wbt.bean.DevicesBean;
import com.wbt.mvp.model.device.IDevicesListener;
import com.wbt.mvp.presenter.DevicesPresenter;
import com.wbt.mvp.view.IDeviceView;
import com.wbt.ui.activity.MainActivity;
import com.wbt.ui.base.BaseFragment;
import com.wbt.util.DateUtils;
import com.wbt.util.HandlerUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/18.
 *
 */

public class AddFragment extends BaseFragment implements IDeviceView,HandlerUtils.OnReceiveMessageListener{
    private MainActivity ma;

    private EditText addname,addaddre;

    private Button addok,addcancel;

    private DevicesPresenter devicesPresenter;

    private HandlerUtils.HandlerHolder handlerHolder;

    private String ErrorMsg;

    DevicesBean.DeviceBeanBean deviceBeanBeans;

    private String userid,username;

    private Date date;

    private DateFormat dateFormat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ma = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.add_fg_layout,container,false);
        initView(view);
        initData();
        return view;

    }

    private void initView(View view) {
        addname = view.findViewById(R.id.add_name);
        addaddre = view.findViewById(R.id.add_addre);
        addcancel = view.findViewById(R.id.add_cancel);
        addok = view.findViewById(R.id.add_ok);

        addok.setOnClickListener(this);
        addcancel.setOnClickListener(this);
        addaddre.addTextChangedListener(textWatcher);
    }

    private void initData(){
        handlerHolder = new HandlerUtils.HandlerHolder(this);
        devicesPresenter = new DevicesPresenter(this);
        if(MainActivity.userid!=null&&MainActivity.username!=null){
            userid = MainActivity.userid;
            username = MainActivity.username;
        }

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    }



    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s == null || s.length() == 0) return;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (i != 2&&s.charAt(i) == ':') {
                    continue;
                } else {
                    sb.append(s.charAt(i));
                    if ((sb.length() == 3 || sb.length() == 6||sb.length()== 9||sb.length()==12||sb.length()==15
                            ||sb.length()==18) && sb.charAt(sb.length() - 1) != ':') {
                        sb.insert(sb.length() - 1, ':');
                    }
                }
            }

            if (!sb.toString().equals(s.toString())) {
                try {
                    int index = start + 1;
                    if (sb.charAt(start) == ':') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    //ax = sb.toString();
                    addaddre.setText(sb.toString());
                    addaddre.setSelection(index);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


            /*else{
                if(sb.length()>16) {
                    //setcal.setText(ax);
                }
            }*/

            //setcal.setText(sb.toString());
            //setcal.setSelection(index);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    @Override
    public String getDeviceName() {
        return addname.getText().toString().trim();
    }

    @Override
    public String getDeviceAddre() {
        //转为大写
        return addaddre.getText().toString().toUpperCase().trim();
    }

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
                Toast.makeText(getActivity(),"添加成功！",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_ok:
                devicesPresenter.addDevice(userid,username,getCurrentDataTime(),iDevicesListener);
                break;
        }
    }

    IDevicesListener iDevicesListener = new IDevicesListener() {
        @Override
        public void onDevicesSuccess(DevicesBean.DeviceBeanBean deviceBeanBean) {
            deviceBeanBeans = deviceBeanBean;
            if(deviceBeanBeans!=null){
                handlerHolder.sendEmptyMessage(200);
            }else{
                ErrorMsg = "错误";
                handlerHolder.sendEmptyMessage(2);
            }
        }

        @Override
        public void onDevicesFail(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(1);
        }

        @Override
        public void onDevicesError(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(0);
        }
    };

    /**
     * 转换时间格式
     * @return
     */
    private Long getCurrentDataTime(){
        date = new Date(System.currentTimeMillis());
        String times = dateFormat.format(date);
        return DateUtils.getStringToDate(times);
    }
}
