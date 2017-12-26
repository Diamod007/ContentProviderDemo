package com.allens.test;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 描述:
 * <p>
 * Created by allens on 2017/12/26.
 */

public class MyProvider extends ContentProvider {


    private SQLiteDatabase db;
    private UriMatcher uriMatcher;

    private static final String AUTHORITY = "com.allens.test.MyProvider"; // 包名 + 类名   在清单文件中也是一样的
    public static final String TABLE = "T_UserInfo"; // 数据库 表名字

    //两个类型 自己随意写的
    private static final int PERSON_ALL = 0;
    private static final int PERSON_ONE = 1;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE); // 对外的URI  之前说的 URI 主成的部分


    @Override
    public boolean onCreate() {
        db = DBUtil.db(getContext());
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //如果match()方法匹配content://com.com.allens.test.MyProvider/T_UserInfo，返回匹配码为1
        //添加需要匹配uri，如果匹配就会返回匹配码
        uriMatcher.addURI(AUTHORITY, TABLE, PERSON_ALL);

        //如果match()方法匹配content://com.allens.test.MyProvider/T_UserInfo/230路径，返回匹配码为2
        //#号为通配符
        uriMatcher.addURI(AUTHORITY, TABLE + "/#", PERSON_ONE);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        int code = uriMatcher.match(uri);
        if (code == PERSON_ALL) {
            cursor = db.query(
                    TABLE,
                    projection, selection,
                    selectionArgs, null, null, sortOrder);
        } else if (code == PERSON_ONE) {
            // 从uri中取出id
            long id = ContentUris.parseId(uri);
            cursor = db.query(
                    TABLE,
                    new String[]{"id", "account", "name", "pwd"}, "id = ?",
                    new String[]{String.valueOf(id)}, null, null, sortOrder);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = uriMatcher.match(uri);
        Log.e("TAG", "getType---->" + match);
        switch (match) {
            case PERSON_ALL:
                break;
            case PERSON_ONE:
                break;
            default://不匹配
                //throw是处理异常的，java中的语法
                throw new IllegalArgumentException("查询不到 URI" + uri);
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowId = db.insert(TABLE, null, values);
        if (rowId > 0) {
            //发出通知给监听器，说明数据已经改变
            Uri insertedUserUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(insertedUserUri, null);
            return insertedUserUri;
        }
        throw new SQLException("Failed to insert row into" + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return db.delete(TABLE, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return db.update(TABLE, values, selection, selectionArgs);
    }
}
