package com.wbt.util.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.wbt.bean.Temperature;

import com.wbt.util.db.TemperatureDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig temperatureDaoConfig;

    private final TemperatureDao temperatureDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        temperatureDaoConfig = daoConfigMap.get(TemperatureDao.class).clone();
        temperatureDaoConfig.initIdentityScope(type);

        temperatureDao = new TemperatureDao(temperatureDaoConfig, this);

        registerDao(Temperature.class, temperatureDao);
    }
    
    public void clear() {
        temperatureDaoConfig.clearIdentityScope();
    }

    public TemperatureDao getTemperatureDao() {
        return temperatureDao;
    }

}
