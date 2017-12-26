package com.allens.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 描述:
 * <p>
 * Created by allens on 2017/12/26.
 */

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(T_UserInfo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //使用者登录信息
    private static final String T_UserInfo =
            "create table T_UserInfo("
                    + "id varchar,"//id
                    + "account varchar,"//手动输入的账号
                    + "Name varchar,"//账号姓名
                    + "pwd varchar)";//pwd

}
