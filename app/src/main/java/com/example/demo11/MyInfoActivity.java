package com.example.demo11;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.demo11.myClass.SingletonUserInfo;
import com.example.demo11.myClass.TurnPwd;

public class MyInfoActivity extends AppCompatActivity {

    private EditText mEdt_myinfo_email;
    private EditText mEdt_myinfo_uid;
    private EditText mEdt_myinfo_name;
    private EditText mEdt_myinfo_pwd1;
    private EditText mEdt_myinfo_pwd2;
    private Button mBtn_myinfo_modify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        mEdt_myinfo_email = findViewById(R.id.mEdt_myinfo_email);
        mEdt_myinfo_uid = findViewById(R.id.mEdt_myinfo_uid);
        mEdt_myinfo_name = findViewById(R.id.mEdt_myinfo_name);
        mEdt_myinfo_pwd1 = findViewById(R.id.mEdt_myinfo_pwd1);
        mEdt_myinfo_pwd2 = findViewById(R.id.mEdt_myinfo_pwd2);
        mBtn_myinfo_modify = findViewById(R.id.mBtn_modify_do);

        mEdt_myinfo_email.setText(SingletonUserInfo.getEmail());
        mEdt_myinfo_uid.setText(SingletonUserInfo.getUid());
        mEdt_myinfo_name.setText(SingletonUserInfo.getName());

        mBtn_myinfo_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String info_email = mEdt_myinfo_email.getText().toString();
                String info_uid = mEdt_myinfo_uid.getText().toString();
                String info_name = mEdt_myinfo_name.getText().toString();
                String info_pwd1 = mEdt_myinfo_pwd1.getText().toString();
                String info_pwd2 = mEdt_myinfo_pwd2.getText().toString();

                if(info_pwd1.equals(info_pwd2)) {
                    String sqlStr;
                    if(info_pwd1.equals("")){   //当没有修改密码时
                        sqlStr = "3!update!userinfo!set!email!=!'" + info_email + "'!,uname!=!'" + info_name + "'!where!uid!=!'" + info_uid + "'";
                    } else {
                        String pwd = TurnPwd.turnPwd(info_pwd1);
                        sqlStr = "3!update!userinfo!set!email!=!'" + info_email + "'!,uname!=!'" + info_name + "',pwd!=!'" + pwd + "'!where!uid!=!'" + info_uid + "'";
                    }
                    MainActivity.CC.setSendMsg(sqlStr);
                    Intent intent = new Intent(MyInfoActivity.this, MyInfoActivity.class);
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(
                            MyInfoActivity.this)
                            .setTitle("错误")
                            .setMessage("请再次确认您的密码")
                            .setPositiveButton("确定", null)
                            .show();
                }
            }
        });
    }
}
