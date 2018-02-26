package com.wbt.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.wbt.bean.Temperature;
import com.wbt.util.db.DaoMaster;
import com.wbt.util.db.DaoSession;
import com.wbt.util.db.TemperatureDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rnd on 2018/1/18.
 * 数据库管理类
 *
 */

public class DBManager {
    private final static String dbName = "temperature_green_dao_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    private List<Temperature> temps = new ArrayList<>();

    public DBManager(Context context){
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context){
        if (mInstance == null){
            synchronized (DBManager.class){
                if(mInstance == null){
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     * @return
     */
    private SQLiteDatabase getReadableDatabase(){
        if(openHelper == null){
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     * @return
     */
    private SQLiteDatabase getWriteableDatabase(){
        if(openHelper == null){
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 插入一条记录到数据库中
     * @param temperature
     */
    public void insertTemp(Temperature temperature){
        DaoMaster daoMaster = new DaoMaster(getWriteableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TemperatureDao temperatureDao = daoSession.getTemperatureDao();
        temperatureDao.insert(temperature);
    }

    /**
     * 查询前一百个数
     * @param addre
     * @return
     */
    public List<Temperature> query100TempChartData(String addre,String probename){
        if(temps!=null){
            temps.clear();
        }
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TemperatureDao tempDao = daoSession.getTemperatureDao();
        QueryBuilder<Temperature> qb = tempDao.queryBuilder();

        //String sql = "select * from LHTemperature where temp_addre = '%@' and temp_time in " +
        // "(select temp_time from LHTemperature ORDER BY temp_time DESC limit 100)";
        qb.where(TemperatureDao.Properties.Tpdvid.eq(addre),TemperatureDao.Properties.Tpname.eq(probename))
                .limit(100).orderDesc(TemperatureDao.Properties.Tptime);

        List<Temperature> list = qb.list();

        //排序 从小到大排序
        Collections.sort(list);

        //必须要增加判断才能添加
        for(int i=0;i<list.size();i++){
            Temperature temp = list.get(i);
            float a = Float.parseFloat(temp.getTptemp());
            if(a<900&&a>-900){
                temps.add(temp);
            }
        }
        return temps;
    }

    /**
     * 查询前一百个数
     * @param addre
     * @return
     */
    public List<Temperature> query5TempChartData(String addre,String probename){
        if(temps!=null){
            temps.clear();
        }
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TemperatureDao tempDao = daoSession.getTemperatureDao();
        QueryBuilder<Temperature> qb = tempDao.queryBuilder();

        //String sql = "select * from LHTemperature where temp_addre = '%@' and temp_time in " +
        // "(select temp_time from LHTemperature ORDER BY temp_time DESC limit 100)";
        qb.where(TemperatureDao.Properties.Tpdvid.eq(addre),TemperatureDao.Properties.Tpname.eq(probename))
                .limit(5).orderDesc(TemperatureDao.Properties.Tptime);

        List<Temperature> list = qb.list();

        //排序 从小到大排序
        Collections.sort(list);

        //必须要增加判断才能添加
        for(int i=0;i<list.size();i++){
            Temperature temp = list.get(i);
            float a = Float.parseFloat(temp.getTptemp());
            if(a<900&&a>-900){
                temps.add(temp);
            }
        }
        return temps;
    }




    /**
     * 按时间查询数据
     */
    /**
     * 查询当天的数据
     * @param ed
     * @param bg
     * @return
     */
    public List<Temperature> queryTempTimeData(String probename,long ed,long bg){
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TemperatureDao tempDao = daoSession.getTemperatureDao();
        QueryBuilder<Temperature> qb = tempDao.queryBuilder();
        //加条件
        qb.where(TemperatureDao.Properties.Tpname.eq(probename),
                qb.and(TemperatureDao.Properties.Tptime.ge(bg),TemperatureDao.Properties.Tptime.lt(ed)));
        List<Temperature> list = qb.list();
        return list;
    }




}
