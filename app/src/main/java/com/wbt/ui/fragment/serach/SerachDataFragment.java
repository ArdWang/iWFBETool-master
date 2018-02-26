package com.wbt.ui.fragment.serach;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.wbt.R;
import com.wbt.bean.ProbeBean;
import com.wbt.bean.Temperature;
import com.wbt.data.SearchTempAdapter;
import com.wbt.mvp.model.probe.IPorbeListener;
import com.wbt.mvp.presenter.ProbePresenter;
import com.wbt.ui.activity.SerachAllActivity;
import com.wbt.ui.base.BaseFragment;
import com.wbt.util.DBManager;
import com.wbt.util.DateUtils;
import com.wbt.util.HandlerUtils;
import com.wbt.util.NormalUtil;
import com.wbt.util.customview.CustomProgress;
import com.wbt.util.customview.dialog.CustomDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rnd on 2018/2/1.
 *
 */

public class SerachDataFragment extends BaseFragment implements HandlerUtils.OnReceiveMessageListener{
    private SerachAllActivity sla;

    //控件
    private ListView serachList;
    private ImageView ivDeleteImg;
    private EditText editSearch;

    private CustomDialog dialog;


    private List<Temperature> templist;
    private List<Map<String,String>> listtemp;

    private List<ProbeBean.ProbesBean> probeslist;

    private List<String> tpName;
    private List<String> tpTemp;
    private List<String> tpSymbol;
    private List<String> tpHum;
    private List<String> tpTime;

    private List<String> tpDvid;

    private HandlerUtils.HandlerHolder handlerHolder;

    private Temperature temp;

    private String alltimes;

    //得到当前时间
    private String cuerrnttime;

    private String probename;

    private String addre;

    private int deviceid;

    //当前的时间
    private Date currentdate;

    //时间格式
    private SimpleDateFormat dateFormat;

    private SearchTempAdapter searchAdapter;

    private ProbePresenter probePresenter;

    private String ErrorMsg;

    //加载对话框
    private CustomProgress customProgress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sla = (SerachAllActivity) getActivity();
        View view = inflater.inflate(R.layout.serach_data_fg_layout,container,false);
        initView(view);
        //onCickEvent();
        initData();
        return view;

    }

    private void initView(View view) {
        serachList = view.findViewById(R.id.serach_list);
        ivDeleteImg = view.findViewById(R.id.ivDeleteImg);
        editSearch = view.findViewById(R.id.editSearch);

        //editSearch.setOnClickListener(this);

        ivDeleteImg.setOnClickListener(this);

        // 搜索文本框的事件发生改变的时候
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                /**
                 * 这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
                if (s.length() == 0) {
                    ivDeleteImg.setVisibility(View.GONE);// 当文本框为空时，则叉叉消失
                } else {
                    ivDeleteImg.setVisibility(View.VISIBLE);// 当文本框不为空时，出现叉叉
                }
                handlerHolder.post(eChanged);
            }
        });

    }

    private void initData(){
        probePresenter = new ProbePresenter();
        handlerHolder = new HandlerUtils.HandlerHolder(this);
        templist = new ArrayList<>();
        listtemp = new ArrayList<>();
        tpTemp = new ArrayList<>();
        tpTime = new ArrayList<>();
        tpName = new ArrayList<>();
        tpSymbol = new ArrayList<>();
        tpDvid = new ArrayList<>();
        tpHum = new ArrayList<>();

        //时间格式化
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(!SerachAllActivity.date.isEmpty()){
            //获取时间
            cuerrnttime = SerachAllActivity.date;
        }

        if(!SerachAllActivity.probename.isEmpty()){
            probename = SerachAllActivity.probename;
        }

        if(SerachAllActivity.deviceid!=-1){
            deviceid = SerachAllActivity.deviceid;
        }

        if(!SerachAllActivity.addre.isEmpty()){
            addre = SerachAllActivity.addre;
        }

        /**
         * 判断执行本地还是网络
         */
        if(SerachAllActivity.localornet==0){
            getData(0);
        }else{
            getData(1);
        }

    }

    /**
     * 搜索控件
     */
    Runnable eChanged = new Runnable() {
        @Override
        public void run() {
            String data = editSearch.getText().toString();
            listtemp.clear();// 先要清空，不然会叠加
            getmDataSub(listtemp, data);// 获取更新数据
            searchAdapter.notifyDataSetChanged();// 更新
        }
    };


    private void getData(int value){
        //时间转换
        //得到当前的系统时间
        currentdate = new Date(System.currentTimeMillis());
        //MM/DD/YYYY
        String [] localtime = cuerrnttime.split("/");
        //开始时间
        String bgtime = localtime[2]+"-"+localtime[0]+"-"+localtime[1]+" "+"00:00:00";
        //结束时间
        String edtime = localtime[2]+"-"+localtime[0]+"-"+localtime[1]+" "+"23:59:59";

        long bg = DateUtils.getStringToDate(bgtime);
        long ed = DateUtils.getStringToDate(edtime);

        if(value==0){
            //执行数据库
            if(bg!=0&&ed!=0&&probename!=null){
                templist = DBManager.getInstance(getActivity()).queryTempTimeData(probename,ed,bg);
                if(templist.size()>0){
                    getTempData(listtemp);
                    if(listtemp.size()>0){
                        searchAdapter = new SearchTempAdapter(getActivity(), listtemp);
                        serachList.setAdapter(searchAdapter);
                        searchAdapter.notifyDataSetChanged();
                    }
                }else{
                    createDialog(bgtime+"~"+edtime+" "+"There's no data");
                }
            }

        }else if(value==1){
            if(deviceid!=0&&!probename.isEmpty()&&bg!=0&&ed!=0) {
                if(customProgress==null){
                    customProgress = CustomProgress.show(getActivity(),"Loading...",true,null);
                }
                //执行网络查询
                probePresenter.getProbe(deviceid + "", probename, bg, ed, iPorbeListener);
            }else{
                handlerHolder.sendEmptyMessage(2);
            }
        }
    }

    /**
     * 获得根据搜索框的数据data来从元数据筛选，筛选出来的数据放入mDataSubs里
     * @param mDataSubs
     * @param data
     */
    private void getmDataSub(List<Map<String, String>> mDataSubs, String data) {
        int length = tpTemp.size();
        for (int i = 0; i < length; i++) {
            if (tpTemp.get(i).contains(data) || tpTime.get(i).contains(data)||tpName.get(i).contains(data)||tpDvid.get(i).contains(data)
                    ||tpSymbol.get(i).contains(data)||tpHum.get(i).contains(data)) {
                Map<String, String> item = new HashMap<>();

                item.put("temp", tpTemp.get(i));
                item.put("time", tpTime.get(i));
                item.put("name", tpName.get(i));
                item.put("addre", tpDvid.get(i));
                item.put("symbol", tpSymbol.get(i));
                item.put("hum",tpHum.get(i));

                mDataSubs.add(item);
            }
        }
    }


    /**
     * 添加设备l
     */
    private void getTempData(List<Map<String, String>> mDatas) {
        for (int i = 0; i < templist.size(); i++) {
            temp = templist.get(i);
            String corf = temp.getTpsymbol();
            //修改 必须把时间戳转为string型的
            String times = DateUtils.getDateToString(temp.getTptime());
            String []one = times.split(" ");
            String two = one[0];
            String three = one[1];
            String []all = two.split("-");
            String year = all[0];
            String month = all[1];
            String day = all[2];
            alltimes = month+"/"+day+"/"+year+"/"+" "+three;

            if(corf.equals("°C")){
                tpTemp.add(temp.getTptemp());
                tpSymbol.add("°C");
                tpTime.add(alltimes);
                tpName.add(temp.getTpname());
                tpDvid.add(temp.getTpdvid());
                tpHum.add(temp.getTphum());

                Map<String, String> item = new HashMap<>();
                item.put("temp", temp.getTptemp());
                item.put("time", alltimes);
                item.put("name", temp.getTpname());
                item.put("addre", temp.getTpdvid());
                item.put("symbol", "°C");
                item.put("hum",temp.getTphum());
                mDatas.add(item);

            }else if(corf.equals("°F")){
                String a = temp.getTptemp();
                Float b = Float.parseFloat(a);
                Float bl = NormalUtil.ConvertCelciusToFahren(b);  //温度转为浮度显示
                String bb = bl+"";
                String ab = NormalUtil.saveOneDecimalPoint(bb);
                tpTemp.add(ab);
                tpSymbol.add("°F");
                tpTime.add(alltimes);
                tpName.add(temp.getTpname());
                tpDvid.add(temp.getTpdvid());
                tpHum.add(temp.getTphum());

                Map<String, String> item = new HashMap<>();
                item.put("temp", ab);
                item.put("time", alltimes);
                item.put("name", temp.getTpname());
                item.put("addre", temp.getTpdvid());
                item.put("symbol", "°F");
                item.put("hum",temp.getTphum());
                mDatas.add(item);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivDeleteImg:
                editSearch.setText("");
                break;
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        switch (msg.what){
            case 0:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 1:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 2:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),ErrorMsg,Toast.LENGTH_SHORT).show();
                break;

            case 200:
                try {
                    if(customProgress!=null){
                        customProgress.dismiss();
                        customProgress = null;
                    }

                    for (ProbeBean.ProbesBean probesBean : probeslist) {
                        temp = new Temperature();
                        temp.setTpname(probesBean.getProbename());
                        temp.setTptemp(probesBean.getProbetemp());
                        if (probesBean.getProbesymbol().equals("0")) {
                            temp.setTpsymbol("°C");
                        } else {
                            temp.setTpsymbol("°F");
                        }
                        temp.setTphum(probesBean.getProbehum());
                        temp.setTptime(probesBean.getProbetime());
                        temp.setTpdvid(addre);
                        Long id = Long.parseLong(probesBean.getProbeid() + "");
                        temp.setTpid(id);
                        templist.add(temp);
                    }

                    //获取Net数据
                    if(templist.size()>0){
                        getTempData(listtemp);
                        if(listtemp.size()>0){
                            searchAdapter = new SearchTempAdapter(getActivity(), listtemp);
                            serachList.setAdapter(searchAdapter);
                            searchAdapter.notifyDataSetChanged();
                        }
                    }else{
                        Toast.makeText(getActivity(), "There's no data", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
        }
    }

    IPorbeListener iPorbeListener = new IPorbeListener() {
        @Override
        public void onProbeSuccess(List<ProbeBean.ProbesBean> probesBean) {
            probeslist = probesBean;
            if(probeslist.size()>0){
                handlerHolder.sendEmptyMessage(200);
            }else{
                handlerHolder.sendEmptyMessage(2);
            }
        }

        @Override
        public void onProbeFail(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(1);
        }

        @Override
        public void onProbeError(String message) {
            ErrorMsg = message;
            handlerHolder.sendEmptyMessage(0);
        }
    };

    private void createDialog(String msg){
        CustomDialog.Builder customBuilder = new CustomDialog.Builder(getActivity());
        customBuilder.setTitle("Tips");
        customBuilder.setMessage(msg);
        customBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog = customBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }
}
