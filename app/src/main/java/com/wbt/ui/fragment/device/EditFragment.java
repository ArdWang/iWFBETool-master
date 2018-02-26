package com.wbt.ui.fragment.device;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wbt.R;
import com.wbt.bean.DeviceBean;
import com.wbt.bean.DevicesBean;
import com.wbt.data.EditDvAdapter;
import com.wbt.mvp.model.device.IDeleteDeviceListener;
import com.wbt.mvp.model.device.IDeviceListener;
import com.wbt.mvp.model.device.IDevicesListener;
import com.wbt.mvp.presenter.DevicePresenter;
import com.wbt.mvp.presenter.DevicesPresenter;
import com.wbt.ui.activity.MainActivity;
import com.wbt.ui.base.BaseFragment;
import com.wbt.util.DateUtils;
import com.wbt.util.DividerItemDecoration;
import com.wbt.util.HandlerUtils;
import com.wbt.util.customview.dialog.ActionSheetDialog;
import com.wbt.util.customview.CHRecyerView;
import com.wbt.util.customview.CustomProgress;
import com.wbt.util.customview.dialog.EditDialog;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by rnd on 2018/1/30.
 *
 */

public class EditFragment extends BaseFragment implements HandlerUtils.OnReceiveMessageListener,
        EditDvAdapter.OnDragClickListener,OnRefreshListener, OnLoadMoreListener {

    private MainActivity ma;

    private SwipeToLoadLayout swipeToLoadLayout;

    private CHRecyerView swipetarget;

    private EditDvAdapter editDvAdapter;

    private DevicePresenter devicePresenter;
    private List<DeviceBean.DevicesBean> deviceslist;
    private HandlerUtils.HandlerHolder handlerHolder;
    private String ErrorMsg;

    private EditDialog editDialog;

    private DevicesPresenter devicesPresenter;

    private Date date;

    private DateFormat dateFormat;

    private int Poss;

    private CustomProgress customProgress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ma = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.edit_dc_fg_layout,container,false);
        initView(view);
        initData();

        if(customProgress==null){
            customProgress = CustomProgress.show(getActivity(),"Loading...",true,null);
        }

        getDevice();
        //autoRefresh();
        return view;

    }

    private void initView(View view) {
        swipeToLoadLayout = view.findViewById(R.id.swipeToLoadLayout);
        swipetarget = view.findViewById(R.id.swipe_target);

        swipetarget.setLayoutManager(new LinearLayoutManager(ma));
        swipetarget.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));

        swipeToLoadLayout.setRefreshHeaderView(LayoutInflater.from(ma).inflate(R.layout.layout_twitter_header, swipeToLoadLayout, false));
        /*设置上拉加载更多布局*/
        swipeToLoadLayout.setLoadMoreFooterView(LayoutInflater.from(ma).inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false));

        // 设置监听器
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);//上拉;

        //swipeToLoadLayout.setDefaultToRefreshingScrollingDuration(60);
        //swipeToLoadLayout.s
    }


    private void initData(){
        Poss = -1;
        devicePresenter = new DevicePresenter();
        devicesPresenter = new DevicesPresenter();
        deviceslist = new ArrayList<>();
        handlerHolder = new HandlerUtils.HandlerHolder(this);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    private void getDevice(){
        if(MainActivity.userid!=null&&MainActivity.username!=null) {
            devicePresenter.getDevice(MainActivity.userid,MainActivity.username, iDeviceListener);
        }else{
            handlerHolder.sendEmptyMessage(2);
        }
    }

    IDeviceListener iDeviceListener = new IDeviceListener() {
        @Override
        public void onDeviceSuccess(List<DeviceBean.DevicesBean> devicesList) {
            deviceslist = devicesList;
            if(devicesList.size()>0){
                handlerHolder.sendEmptyMessage(200);
            }else{
                ErrorMsg = "获取数据错误";
                handlerHolder.sendEmptyMessage(2);
            }
        }

        @Override
        public void onDeviceFail(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(1);
        }

        @Override
        public void onDeviceError(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(0);
        }
    };


    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDevice();
                swipeToLoadLayout.setLoadingMore(false);
            }
        },2000);
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDevice();
                swipeToLoadLayout.setRefreshing(false);
            }
        },2000);
    }

    @Override
    public void handlerMessage(Message msg) {
        switch (msg.what){
            case 0:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),"获取数据失败:"+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 1:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),"获取数据失败:"+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 2:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),"获取数据失败:"+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;
            case 3:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),"获取数据失败:"+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 4:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),"获取数据失败:"+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 5:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),"获取数据失败:"+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;
            case 6:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),ErrorMsg,Toast.LENGTH_SHORT).show();
                break;
            case 7:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),"网络错误:"+ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 200:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }

                if(editDvAdapter==null){
                    editDvAdapter = new EditDvAdapter(getActivity(),deviceslist,this);
                    swipetarget.setAdapter(editDvAdapter);
                }else{
                    editDvAdapter.deviceslist = deviceslist;
                    editDvAdapter.notifyDataSetChanged();
                }

                break;

            case 202:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                //重新刷新
                getDevice();
                break;

            case 204:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }

                if(Poss>0) {
                    deviceslist.remove(Poss);
                    editDvAdapter.notifyItemRemoved(Poss);//推荐用这个
                    Toast.makeText(getActivity(),ErrorMsg,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onEditClick(View view, int pos) {
        if(deviceslist.size()>0) {
            DeviceBean.DevicesBean devicesBean = deviceslist.get(pos);
            final String devicename = devicesBean.getDevicename();
            String deviceaddre = devicesBean.getDeviceaddre();
            final String deviceid = devicesBean.getDeviceid()+"";
            final String userid = devicesBean.getUserid()+"";
            final String username = devicesBean.getUsername();

            final EditDialog.Builder builder = new EditDialog.Builder(getActivity());
            builder.setDeviceName(devicename);
            builder.setDeviceAddre(deviceaddre);

            builder.setPositiveButton(R.string.edit_dialog_ok , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    String name = builder.getEdname().getText().toString();
                    String addre = builder.getEdaddre().getText().toString();
                    if(!name.isEmpty()&&!addre.isEmpty()){
                        if(customProgress==null){
                            customProgress = CustomProgress.show(getActivity(),"Loading...",true,null);
                        }
                        devicesPresenter.updateDevice(deviceid,userid,username,name,addre,getCurrentDataTime(),iDevicesListener);
                    }
                }
            }).setnegativeButton(R.string.edit_dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                }
            });

            editDialog = builder.create();
            editDialog.show();
            editDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

            //dialog.setCancelable(true);
            editDialog.setCanceledOnTouchOutside(true);
        }
    }

    IDevicesListener iDevicesListener = new IDevicesListener() {
        @Override
        public void onDevicesSuccess(DevicesBean.DeviceBeanBean deviceBeanBean) {
            if(deviceBeanBean!=null){
                handlerHolder.sendEmptyMessage(202);
            }else{
                handlerHolder.sendEmptyMessage(5);
                ErrorMsg = "添加失败";
            }
        }

        @Override
        public void onDevicesFail(String message) {
            ErrorMsg = "修改失败";
            handlerHolder.sendEmptyMessage(4);
        }

        @Override
        public void onDevicesError(String message) {
            ErrorMsg = "网络错误";
            handlerHolder.sendEmptyMessage(3);
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

    @Override
    public void onDeleteClick(View view, final int pos) {
        Poss = pos;
        new ActionSheetDialog(getActivity())
                .builder()
                .setTitle("Do you sure delete device？")
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("Delete", ActionSheetDialog.SheetItemColor.Red
                        , new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //填写事件
                                if(deviceslist.size()>0){
                                    if(customProgress==null){
                                        customProgress = CustomProgress.show(getActivity(),"Loading...",true,null);
                                    }

                                    DeviceBean.DevicesBean devicesBean = deviceslist.get(pos);

                                    String username = devicesBean.getUsername();
                                    String deviceid = devicesBean.getDeviceid()+"";
                                    String userid = devicesBean.getUserid()+"";

                                    if(username!=null&&!deviceid.isEmpty()&&!userid.isEmpty()){
                                        devicesPresenter.deleteDevice(deviceid,userid,username,iDeleteDeviceListener);
                                    }
                                }
                            }
                        }).show();



    }

    IDeleteDeviceListener iDeleteDeviceListener = new IDeleteDeviceListener() {
        @Override
        public void onDelDevicesSuccess(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(204);
        }

        @Override
        public void onDelDevicesFail(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(7);
        }

        @Override
        public void onDelDevicesError(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(6);

        }
    };

    /**
     * 自动执行刷新
     */
    public void autoRefresh(){
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDevice();
                swipeToLoadLayout.setRefreshing(false);
            }
        },2000);
    }
}
