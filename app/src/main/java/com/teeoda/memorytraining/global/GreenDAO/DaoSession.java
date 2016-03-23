package com.teeoda.memorytraining.global.GreenDAO;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.teeoda.memorytraining.global.GreenDAO.TrainingHistory;

import com.teeoda.memorytraining.global.GreenDAO.TrainingHistoryDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig trainingHistoryDaoConfig;

    private final TrainingHistoryDao trainingHistoryDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        trainingHistoryDaoConfig = daoConfigMap.get(TrainingHistoryDao.class).clone();
        trainingHistoryDaoConfig.initIdentityScope(type);

        trainingHistoryDao = new TrainingHistoryDao(trainingHistoryDaoConfig, this);

        registerDao(TrainingHistory.class, trainingHistoryDao);
    }
    
    public void clear() {
        trainingHistoryDaoConfig.getIdentityScope().clear();
    }

    public TrainingHistoryDao getTrainingHistoryDao() {
        return trainingHistoryDao;
    }

}
