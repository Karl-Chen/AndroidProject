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

    private SQLiteDatabase db;
    private SqliteDbHelper mySqliteDbHelper;
    private Button button_insert, button_update, button_delete, button_show_all, button_execute_sql_command;
    private EditText edittext_student_id, edittext_student_name, edittext_student_grade, edittext_student_new_grade, edittext_sql_command_string;
    private TextView textview_data_output;

    private static final String DATATABLE_NAME = "students";
    private String strOutputTitle = "結果輸出 : ";

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

        edittext_student_id = findViewById(R.id.edittext_student_id);
        edittext_student_name = findViewById(R.id.edittext_student_name);
        edittext_student_grade = findViewById(R.id.edittext_student_grade);
        edittext_student_new_grade = findViewById(R.id.edittext_student_new_grade);
        edittext_sql_command_string = findViewById(R.id.edittext_sql_command_string);

        button_insert = findViewById(R.id.button_insert);
        button_update = findViewById(R.id.button_update);
        button_delete = findViewById(R.id.button_delete);
        button_show_all = findViewById(R.id.button_show_all);
        button_execute_sql_command = findViewById(R.id.button_execute_sql_command);

        textview_data_output = findViewById(R.id.textview_data_output);

        mySqliteDbHelper = new SqliteDbHelper(SQLiteActivity.this);
        db = mySqliteDbHelper.getWritableDatabase();

        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id;
                String name;
                double grade;
                ContentValues cv;
                long longInsertResult;

                id = Integer.parseInt(edittext_student_id.getText().toString());
                name = edittext_student_name.getText().toString();
                grade = Double.parseDouble(edittext_student_grade.getText().toString());

                cv= new ContentValues();
                cv.put("student_id", id);
                cv.put("student_name", name);
                cv.put("student_grade", grade);

                longInsertResult = db.insert(DATATABLE_NAME, null, cv);
                if (longInsertResult != -1) {
                    textview_data_output.setText(strOutputTitle + "新增學生資料成功，學號 id : " + longInsertResult);
                } else {
                    textview_data_output.setText(strOutputTitle + "新增學生資料失敗!!" );
                }

            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i,id,intResultCount;
                double new_grade;
                ContentValues cv;
                String[] newDataColumns, columnsName;
                Cursor c;
                String outputString = "";

                id = Integer.parseInt(edittext_student_id.getText().toString());
                new_grade = Double.parseDouble(edittext_student_new_grade.getText().toString());

                cv= new ContentValues();
                cv.put("student_grade", new_grade);
                intResultCount = db.update(DATATABLE_NAME, cv, "student_id = " + id, null );

                if (intResultCount > 0){
                    textview_data_output.setText(strOutputTitle + "更新學生資料成功，更新資料筆數 : " + intResultCount);
                } else {
                    textview_data_output.setText(strOutputTitle + "更新學生資料失敗!!" );
                }

            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id,intResultCount;

                id = Integer.parseInt(edittext_student_id.getText().toString());
                intResultCount = db.delete(DATATABLE_NAME,"student_id = " + id, null );

                if (intResultCount > 0){
                    textview_data_output.setText(strOutputTitle + "刪除學生資料成功，更新資料筆數 : " + intResultCount);
                } else {
                    textview_data_output.setText(strOutputTitle + "刪除學生資料失敗!!" );
                }
            }
        });


        button_show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i, j;
                String[] columnsName;
                String outputString = "";
                String sqlCommandString = "SELECT * FROM students";

                Cursor c = db.rawQuery(sqlCommandString, null);
                columnsName = c.getColumnNames();

                for(i=0 ; i<columnsName.length ; i++){
                    outputString += columnsName[i] + "\t\t";
                }
                outputString += "\n";
                c.moveToFirst();

                for(i=0 ; i<c.getCount() ; i++){
                    for(j=0 ; j<columnsName.length  - 1 ; j++){
                        outputString += c.getString(j) + "\t\t\t\t\t    ";
                    }
                    outputString += c.getString(j) + "\n";
                    c.moveToNext();
                }

                 textview_data_output.setText(strOutputTitle + "\n" + outputString);
            }
        });

        button_execute_sql_command.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSqlCommand;

                strSqlCommand = edittext_sql_command_string.getText().toString();

                try {
                    db.execSQL(strSqlCommand);
                    textview_data_output.setText(strOutputTitle + "執行SQL指令成功，請按 [顯示所有資料] 按鈕以檢視結果");
                } catch (SQLException ex) {
                    textview_data_output.setText(strOutputTitle + "執行SQL指令失敗，錯誤訊息 : \n" + ex.getMessage());
                }
            }
        });


    }
}