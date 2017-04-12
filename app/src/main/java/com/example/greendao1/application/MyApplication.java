package com.example.greendao1.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.greendao1.gen.DaoMaster;
import com.example.greendao1.gen.DaoSession;

/**
 * Created by lijianfu on 2017/4/12.
 */
public class MyApplication extends Application {
    private DaoMaster.DevOpenHelper dbHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initDatabass();
    }
    public static MyApplication getInstance(){
        return instance;
    }
    private void initDatabass() {
        //这里之后会修改，关于升级数据库
        dbHelper = new DaoMaster.DevOpenHelper(this, "card-db", null);
        db = dbHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getSession(){
        return mDaoSession;
    }
    public SQLiteDatabase getDb(){
        return db;
    }
    private void init() {
        instance = this;
    }
}
