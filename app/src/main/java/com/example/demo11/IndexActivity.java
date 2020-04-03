package com.example.demo11;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.example.demo11.myClass.ParseStr;
import com.example.demo11.myClass.SingletonUserInfo;


public class IndexActivity extends AppCompatActivity {

    private Button mBtn_tosignin;
    private Button mBtn_course;
    private Button mBtn_myinfo;
    private Button mBtn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        mBtn_tosignin = findViewById(R.id.mBtn_tosignin);
        mBtn_course = findViewById(R.id.mBtn_course);
        mBtn_myinfo = findViewById(R.id.mBtn_myinfo);
        mBtn_exit = findViewById(R.id.mBtn_exit);

        mBtn_tosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sqlStr = "4!select!*!from!elective!where!stuid!=!'"+ SingletonUserInfo.getUid() +"'!and!now()!between!date_add(ctime,!interval-10!minute)!and!ctime";
                MainActivity.CC.setSendMsg(sqlStr);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String recvMsg = MainActivity.CC.getRecvMsg();
                ParseStr ps = new ParseStr();
                String[][] str = ps.parseStr(recvMsg);
                String cid = str[0][0];
                if(!recvMsg.equals("")) {
                    Intent intent = new Intent(IndexActivity.this, SignInActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("cid", cid);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(
                            IndexActivity.this)
                            .setTitle("提示")
                            .setMessage("目前没有签到")
                            .setPositiveButton("确定", null)
                            .show();
                }
            }
        });

        mBtn_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexActivity.this, CourseActivity.class);
                startActivity(intent);
            }
        });

        mBtn_myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexActivity.this, MyInfoActivity.class);
                startActivity(intent);
            }
        });

        mBtn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
