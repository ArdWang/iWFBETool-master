package com.wbt.ui.fragment.serach;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.LineChart;
import com.wbt.R;
import com.wbt.bean.ProbeBean;
import com.wbt.bean.Temperature;
import com.wbt.manager.chart.ChartManager;
import com.wbt.manager.chart.LineChartManager;
import com.wbt.mvp.model.probe.IPorbeListener;
import com.wbt.mvp.presenter.ProbePresenter;
import com.wbt.ui.activity.SerachAllActivity;
import com.wbt.ui.base.BaseFragment;
import com.wbt.util.DBManager;
import com.wbt.util.DateUtils;
import com.wbt.util.HandlerUtils;
import com.wbt.util.NormalShared;
import com.wbt.util.NormalUtil;
import com.wbt.util.customview.CustomProgress;
import com.wbt.util.customview.dialog.CustomDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rnd on 2018/2/1.
 *
 */

public class SerachChartFragment extends BaseFragment implements HandlerUtils.OnReceiveMessageListener{

    private SerachAllActivity saa;

    private TextView probename,deviceaddre;

    private LineChart tempchart,humchart;

    private ProbePresenter probePresenter;

    private List<Temperature> templist;

    private List<ProbeBean.ProbesBean> probeslist;

    private Temperature temp;

    //图表帮助类
    private LineChartManager lineChartManager;

    private LineChartManager humChartManager;

    private ChartManager chartManager;

    private Date currentdate;

    private SimpleDateFormat dateFormat;

    private SimpleDateFormat dateFormat1;

    //得到当前时间
    private String cuerrnttime;

    private String pbname;

    private String addre;

    private int deviceid;

    //当前的小时
    private int chour;

    //当前年月日
    private String cymd;

    private TextView tempmax,tempmin;
    private TextView hummax,hummin;
    private LinearLayout llleft,llright;
    private CustomDialog dialog;

    private String currentsymbol;

    /**
     * 获取是否为c或者f的之间的转换
     */
    private static final String PREFS_SET_CORF="SetCORF";

    //存储度数和华氏度
    private SharedPreferences spsetcorf;

    private NormalShared normalShared;

    private HandlerUtils.HandlerHolder handlerHolder;

    private String ErrorMsg;

    private String netbgtime,netendtime;

    private CustomProgress customProgress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        saa = (SerachAllActivity) getActivity();
        View view = inflater.inflate(R.layout.search_chart_fg_layout,container,false);

        initView(view);
        initData();

        return view;
    }

    private void initView(View view) {
        //摄氏度和华氏度之间的转换
        spsetcorf =  getActivity().getSharedPreferences(PREFS_SET_CORF, 0);
        probename = view.findViewById(R.id.probe_name);
        deviceaddre = view.findViewById(R.id.device_addre);
        tempchart = view.findViewById(R.id.tempChart);
        humchart = view.findViewById(R.id.humChart);
        tempmax = view.findViewById(R.id.temp_max);
        tempmin = view.findViewById(R.id.temp_min);
        hummax = view.findViewById(R.id.hum_max);
        hummin = view.findViewById(R.id.hum_min);

        llleft = view.findViewById(R.id.llleft);
        llright = view.findViewById(R.id.llright);

        llleft.setOnClickListener(this);
        llright.setOnClickListener(this);
    }

    private void initData(){
        normalShared = new NormalShared();
        probePresenter = new ProbePresenter();
        handlerHolder = new HandlerUtils.HandlerHolder(this);
        temp = new Temperature();
        templist = new ArrayList<>();
        chartManager = new ChartManager(getActivity());

        //时间格式化
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

        if(!SerachAllActivity.date.isEmpty()){
            //获取时间
            cuerrnttime = SerachAllActivity.date;
        }

        if(!SerachAllActivity.probename.isEmpty()){
            pbname = SerachAllActivity.probename;
            probename.setText(pbname);
        }

        if(SerachAllActivity.deviceid!=-1){
            deviceid = SerachAllActivity.deviceid;
        }

        if(!SerachAllActivity.addre.isEmpty()){
            addre = SerachAllActivity.addre;
            deviceaddre.setText(addre);
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

    private void getData(int value) {
        //得到当前温度符号
        currentsymbol = normalShared.getCORF(spsetcorf,pbname,addre);
        //得到当前的系统时间
        currentdate = new Date(System.currentTimeMillis());
        String date = dateFormat.format(currentdate);
        //拆解当前的时间格式
        String[] cdate = date.split(" ");
        //拆解当前后面时
        String[] cdateh = cdate[1].split(":");
        //得到当前的小时
        chour = Integer.parseInt(cdateh[0]);
        //MM/DD/YYYY
        String[] localtime = cuerrnttime.split("/");
        //得到当前的年月日
        String localYmd = localtime[2] + "-" + localtime[0] + "-" + localtime[1];
        cymd = localYmd;
        //得到开始时间
        String bgtime = localYmd + " " + chour + ":" + "00" + ":" + "00";
        //得到结束时间
        String edtime = localYmd + " " + chour + ":" + "59" + ":" + "59";
        //把得到的时间传给net获取的时间提示错误信息
        netbgtime = bgtime;
        netendtime = edtime;

        long bg = DateUtils.getStringToDate(bgtime);
        long ed = DateUtils.getStringToDate(edtime);

        //获取本地数据库的数据
        if (value == 0) {
            //执行数据库
            if (bg != 0 && ed != 0 && pbname != null) {
                templist = DBManager.getInstance(getActivity()).queryTempTimeData(pbname, ed, bg);
                if (templist.size() > 0) {
                    setLoadTempData();
                    setLoadHumData();
                    chartManager.setListTempMaxOrMin(templist, currentsymbol, tempmax, tempmin, currentsymbol);
                    chartManager.setListHumMaxOrMin(templist, hummax, hummin);
                } else {
                    //Toast.makeText(getActivity(), bgtime+"~"+edtime+" "+"There's no data", Toast.LENGTH_SHORT).show();
                    createDialog(bgtime+"~"+edtime+" "+"There's no data");
                }
            }
        }else if(value==1){
            if(deviceid!=0&&pbname!=null && bg!=0 && ed!=0) {
                //执行网络查询
                probePresenter.getProbe(deviceid + "", pbname, bg, ed, iPorbeListener);
            }else{
                handlerHolder.sendEmptyMessage(2);
            }
        }
    }


    /**
     * 加载温度数据 显示的曲线图表
     */
    private void setLoadTempData() {
        if(lineChartManager==null){
            lineChartManager = new LineChartManager(getActivity(),tempchart,0,currentsymbol);
        }

        // 添加X轴坐标
        List<String> xVals = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            Temperature temp = templist.get(i);
            Long a = temp.getTptime();
            String ab = DateUtils.getDateToString(a);
            String ss = ab.substring(11, 19);
            xVals.add(ss);
        }

        // 添加Y轴坐标
        List<Float> yVals = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            Temperature temp = templist.get(i);
            String y = temp.getTptemp();
            if(currentsymbol.equals("°C")){
                if(!y.equals("")&&!y.equals(null)) {
                    yVals.add(Float.parseFloat(y));
                }
            }else if(currentsymbol.equals("°F")){
                float valf = Float.parseFloat(y);
                float val = NormalUtil.ConvertCelciusToFahren((float)valf);
                String tempFvalue =val+"";
                String value = NormalUtil.saveOneDecimalPoint(tempFvalue);
                if(!value.equals("")&&!value.equals(null)) {
                    yVals.add(Float.parseFloat(value));
                }
            }
        }

        if(templist.size()>0) {
            lineChartManager.showLineChart(xVals, yVals, getResources().getColor(R.color.colorBlue), "Diagram (degree/time)"+" "+cuerrnttime);
        }

        lineChartManager.setDescription("");
    }


    /**
     * 加载湿度数据的曲线图表
     */
    private void setLoadHumData() {
        if(humChartManager==null){
            humChartManager = new LineChartManager(getActivity(),humchart,1,currentsymbol);
        }

        // 添加X轴坐标
        List<String> xVals = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            Temperature temp = templist.get(i);
            Long a = temp.getTptime();
            String ab = DateUtils.getDateToString(a);
            String ss = ab.substring(11, 19);
            if(!ss.isEmpty()) {
                xVals.add(ss);
            }
        }

        // 添加Y轴坐标
        List<Float> yVals = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            Temperature temp = templist.get(i);
            String y = temp.getTphum();
            String yy = y.replace("%","");
            if(yy!=null&&!yy.isEmpty()) {
                yVals.add(Float.parseFloat(yy));
            }
        }

        if(templist.size()>0) {
            humChartManager.showLineChart(xVals, yVals, getResources().getColor(R.color.colorBlue), "Humidity (percent/time)"+" "+cuerrnttime);
        }

        humChartManager.setDescription("");
    }

    /**
     * 转换时间格式
     * @return timee
     */
    private String getCurrentDataTime(){
        //currentdate = new Date(System.currentTimeMillis());
        String times = dateFormat1.format(currentdate);
        String ss[] = times.split(" ");
        String sss[] = ss[0].split("-");
        String timee = sss[1]+"/"+sss[2]+"/"+sss[0];
        return timee;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.llleft:
                int qqtime = chour-1;

                if(qqtime==0){
                    qqtime = 0;
                }

                if(chour==0){
                    qqtime = 0;
                }else{
                    chour = qqtime;
                }

                String bgtime = cymd+" "+qqtime+":"+"00"+":"+"00";

                String edtime = cymd+" "+qqtime+":"+"59"+":"+"59";

                netbgtime = bgtime;
                netendtime = edtime;

                long bg = DateUtils.getStringToDate(bgtime);
                long ed = DateUtils.getStringToDate(edtime);

                if(SerachAllActivity.localornet==0) {
                    templist = DBManager.getInstance(getActivity()).queryTempTimeData(pbname, ed, bg);
                    if (templist.size() >= 2) {
                        setLoadTempData();
                        setLoadHumData();
                        chartManager.setListTempMaxOrMin(templist, currentsymbol, tempmax, tempmin, currentsymbol);
                        chartManager.setListHumMaxOrMin(templist, hummax, hummin);
                    } else {
                        createDialog(bgtime + "~" + edtime + " " + "There's no data");
                    }
                }else{
                    //执行网络的请求数据
                    if(deviceid!=0&&pbname!=null && bg!=0 && ed!=0) {
                        //执行网络查询
                        probePresenter.getProbe(deviceid + "", pbname, bg, ed, iPorbeListener);
                    }else{
                        handlerHolder.sendEmptyMessage(2);
                    }
                }

                break;

            case R.id.llright:
                int hhtime = chour+1;

                if(hhtime==24){
                    hhtime=0;
                }
                if(chour==24){
                    hhtime = 0;
                }else{
                    chour = hhtime;
                }

                String bgtimeh = cymd+" "+hhtime+":"+"00"+":"+"00";
                netbgtime = bgtimeh;

                String edtimeh = cymd+" "+hhtime+":"+"59"+":"+"59";
                netendtime = edtimeh;

                long bgh = DateUtils.getStringToDate(bgtimeh);
                long edh = DateUtils.getStringToDate(edtimeh);

                if(SerachAllActivity.localornet==0) {
                    templist = DBManager.getInstance(getActivity()).queryTempTimeData(pbname, edh, bgh);

                    if (templist.size() >= 2) {
                        setLoadTempData();
                        setLoadHumData();
                        chartManager.setListTempMaxOrMin(templist, currentsymbol, tempmax, tempmin, currentsymbol);
                        chartManager.setListHumMaxOrMin(templist, hummax, hummin);
                    } else {
                        createDialog(bgtimeh + "~" + edtimeh + " " + "There's no data");
                    }
                }else{
                    //执行网络的请求数据
                    if(deviceid!=0&&pbname!=null && bgh!=0 && edh!=0) {
                        if(customProgress==null){
                            customProgress = CustomProgress.show(getActivity(),"Loading...",true,null);
                        }

                        //执行网络查询
                        probePresenter.getProbe(deviceid + "", pbname, bgh, edh, iPorbeListener);
                    }else{
                        handlerHolder.sendEmptyMessage(2);
                    }
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

    @Override
    public void handlerMessage(Message msg) {
        switch (msg.what){
            case 0:
                if(customProgress!=null){
                    customProgress.dismiss();
                    customProgress = null;
                }
                Toast.makeText(getActivity(),"网络错误",Toast.LENGTH_SHORT).show();
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
                    if (templist.size() > 0) {
                        setLoadTempData();
                        setLoadHumData();
                        chartManager.setListTempMaxOrMin(templist, currentsymbol, tempmax, tempmin, currentsymbol);
                        chartManager.setListHumMaxOrMin(templist, hummax, hummin);
                    } else {
                        //Toast.makeText(getActivity(), bgtime+"~"+edtime+" "+"There's no data", Toast.LENGTH_SHORT).show();
                        createDialog(netbgtime+"~"+netendtime+" "+"There's no data");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
        }
    }
}
