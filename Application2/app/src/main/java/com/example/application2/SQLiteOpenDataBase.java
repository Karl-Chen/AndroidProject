package com.example.application2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteOpenDataBase extends SQLiteOpenHelper {

    private static final String SQLName = "Class";
    private static final int SQLVersion = 1;
    public SQLiteOpenDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteOpenDataBase(Context context, String name, int version) {
        super(context, name, null, version);
    }

    public SQLiteOpenDataBase(Context c) {
        super(c, SQLName, null, SQLVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //創建資料庫時進來
        String strSQL = "CREATE TABLE IF NOT EXISTS students (student_id INTEGER PRIMARY KEY ," +
                "student_name TEXT NOT NULL," +
                "student_grade REAL NOT NULL)";
        db.execSQL(strSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //資料庫版本升級時會進到這裡
    }
}
