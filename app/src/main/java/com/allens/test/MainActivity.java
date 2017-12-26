package com.allens.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("id", "1");
                values.put("account", "account1");
                values.put("name", "allens");
                values.put("pwd", "123456");
                getContentResolver().insert(MyProvider.CONTENT_URI, values);
            }
        });


        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().delete(MyProvider.CONTENT_URI, "name = ?", new String[]{"allens"});
            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("pwd", "654321");
                getContentResolver().update(MyProvider.CONTENT_URI, values, "name = ?", new String[]{"allens"});
            }
        });


        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /***
                 * 使用ContentProvider
                 */
                Cursor cursor = getContentResolver().query(MyProvider.CONTENT_URI, new String[]{"id", "account", "name", "pwd"}, null, null, null);
                while (cursor.moveToNext()) {
                    Log.e("TAG", "id--->" + cursor.getString(0));
                    Log.e("TAG", "account--->" + cursor.getString(1));
                    Log.e("TAG", "name--->" + cursor.getString(2));
                    Log.e("TAG", "pwd--->" + cursor.getString(3));
                }

            }
        });
    }
}
