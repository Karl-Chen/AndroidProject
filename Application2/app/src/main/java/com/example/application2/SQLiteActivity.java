package com.example.application2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SQLiteActivity extends AppCompatActivity {

    private Button btnInsert,btnUpdate, btnDelete, btnShowAll, btnExec;

    private EditText edStudentSQLCommand, edStudentNewGrade,edStudentGrade, edStudentName, edStudentID;

    private TextView tvDataOutput;

    private  SQLiteOpenDataBase mySQLiteOpenDataBase;
    private SQLiteDatabase db;

    private static final String DATATABLE_NAME = "students";
    private  String strOutputTitle = "結果輸出:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sqlite);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        btnInsert = findViewById(R.id.btn_insert);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        btnExec = findViewById(R.id.btn_exec);
        btnShowAll = findViewById(R.id.btn_show_all);
        edStudentGrade = findViewById(R.id.ed_student_grade);
        edStudentNewGrade = findViewById(R.id.ed_student_new_grade);
        edStudentSQLCommand = findViewById(R.id.ed_student_sql_command);
        edStudentID = findViewById(R.id.ed_student_id);
        edStudentName = findViewById(R.id.ed_student_name);
        tvDataOutput = findViewById(R.id.tv_data_output);
        mySQLiteOpenDataBase = new SQLiteOpenDataBase(this);
        db = mySQLiteOpenDataBase.getWritableDatabase();
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id;
                String name;
                double grade;
                ContentValues cv;
                long longInsertResult;
                id = Integer.parseInt(edStudentID.getText().toString());
                name = edStudentName.getText().toString();
                grade = Double.parseDouble(edStudentGrade.getText().toString());
                cv = new ContentValues();
                cv.put("student_id", id);
                cv.put("student_name", name);
                cv.put("student_grade", grade);
                longInsertResult = db.insert(DATATABLE_NAME, null, cv);
                if (longInsertResult != -1) {
                    tvDataOutput.setText(strOutputTitle + "新增學生資料成功，學號id:" + longInsertResult);
                } else {
                    tvDataOutput.setText(strOutputTitle + "新增學生資料失敗!!");
                }
            }
        });

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] columnsName;
                String outputString = "";
                String sqlCommandString = "SELECT * FROM students";
                Cursor c = db.rawQuery(sqlCommandString, null);
                columnsName = c.getColumnNames();
                for (int i = 0; i < columnsName.length; i++) {
                    outputString += columnsName[i] + "\t\t";
                }
                outputString += "\n";
                c.moveToFirst();

                for (int i = 0; i < c.getCount(); i++) {
                    for (int j = 0; j < columnsName.length; j++) {
                        outputString += c.getString(j) + "\t\t\t\t\t     ";
                    }
                    outputString += c.getString(columnsName.length - 1) + "\n";
                    c.moveToNext();
                }
                tvDataOutput.setText(strOutputTitle + "\n" + outputString);

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i, id, intResultCount;
                double newgrade;
                ContentValues cv;
                String[] newDataColume, columeName;
                Cursor c;
                String outputStr = "";
                id = Integer.parseInt(edStudentID.getText().toString());
                newgrade = Double.parseDouble(edStudentNewGrade.getText().toString());
                cv = new ContentValues();
                cv.put("student_grade", newgrade);
                intResultCount = db.update(DATATABLE_NAME, cv, "student_id = "+id, null);
                if (intResultCount > 0) {
                    tvDataOutput.setText(strOutputTitle + "更新學生資料成功，更新資料筆數 :" + intResultCount);
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id, intResultCount;
                ContentValues cv;
                id = Integer.parseInt(edStudentID.getText().toString());
                cv = new ContentValues();
                cv.put("student_id", id);
                intResultCount = db.delete(DATATABLE_NAME, "student_id = " + id, null);

                if (intResultCount > 0){
                    tvDataOutput.setText(strOutputTitle + "刪除學生資料成功，更新資料筆數 : " + intResultCount);
                } else {
                    tvDataOutput.setText(strOutputTitle + "刪除學生資料失敗!!" );
                }
            }
        });
        btnExec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSQLComand;
                strSQLComand = edStudentSQLCommand.getText().toString();
                try {
                    db.execSQL(strSQLComand);
                    tvDataOutput.setText(strOutputTitle + "執行SQL指令成功，請按「顯示所有資料」鈕");
                } catch (SQLException e) {
                    e.getMessage();
                }
            }
        });
    }
}