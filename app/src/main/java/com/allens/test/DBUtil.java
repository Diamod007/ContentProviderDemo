package com.allens.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 描述:
 * <p>
 * Created by allens on 2017/12/26.
 */

public class DBUtil {
    public static SQLiteDatabase db(Context context) {
        return new DBHelper(context, "text.db", null, 1).getWritableDatabase();
    }
}
