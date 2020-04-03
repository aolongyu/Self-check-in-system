package com.example.demo11;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo11.myClass.ParseStr;
import com.example.demo11.myClass.SingletonUserInfo;

public class IndexTeacherActivity extends AppCompatActivity {

    private Button mBtn_teacher_signin;
    private Button mBtn_teacher_myinfo;
    private Button mBtn_teacher_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_teacher);

        mBtn_teacher_signin = findViewById(R.id.mBtn_teacher_signin);
        mBtn_teacher_myinfo = findViewById(R.id.mBtn_teacher_myinfo);
        mBtn_teacher_exit = findViewById(R.id.mBtn_teacher_exit);


        mBtn_teacher_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexTeacherActivity.this, CourseTeacherActivity.class);
                startActivity(intent);
            }
        });

        mBtn_teacher_myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexTeacherActivity.this, MyInfoActivity.class);
                startActivity(intent);
            }
        });

        mBtn_teacher_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexTeacherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
