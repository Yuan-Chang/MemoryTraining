package com.teeoda.memorytraining.global.GreenDAO;

import android.content.Context;

import com.teeoda.memorytraining.global.BaseActivity;

import de.greenrobot.dao.async.AsyncSession;

/**
 * Created by home on 3/26/16.
 */
public class DBHelper {
    private static DBHelper instance = null;

    /**
     * not thread-safe
     */
    public static DBHelper getInstance() {
        Context context = BaseActivity.getCurrent();
        if(instance == null)
            instance = new DBHelper(context);
        return instance;
    }

    private static final String DB_NAME = "MemoryTraining.db";
    private DaoSession daoSession;
    private AsyncSession asyncSession;

    private DBHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);

        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());

        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
    }

    public TrainingHistoryDao getTrainingHitoryDAO(){
        return daoSession.getTrainingHistoryDao();
    }

    public AsyncSession getAsyncSession(){
        return asyncSession;
    }


}
