package com.example.application2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Class";
    private static final int DATABASE_VERSION = 1;

    public SqliteDbHelper(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SqliteDbHelper(Context c, String strDbName, int intDbVersion){
        super(c, strDbName, null, intDbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String stringSql = "CREATE TABLE IF NOT EXISTS students (student_id INTEGER PRIMARY KEY ," +
                           "student_name TEXT NOT NULL ," +
                           "student_grade REAL NOT NULL)";

        db.execSQL(stringSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
