package com.anye.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import jpush.test.com.bean.AlarmClock;
import jpush.test.com.bean.User;

import com.anye.greendao.gen.AlarmClockDao;
import com.anye.greendao.gen.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig alarmClockDaoConfig;
    private final DaoConfig userDaoConfig;

    private final AlarmClockDao alarmClockDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        alarmClockDaoConfig = daoConfigMap.get(AlarmClockDao.class).clone();
        alarmClockDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        alarmClockDao = new AlarmClockDao(alarmClockDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(AlarmClock.class, alarmClockDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        alarmClockDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public AlarmClockDao getAlarmClockDao() {
        return alarmClockDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
