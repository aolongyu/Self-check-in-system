package com.example.demo11;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.demo11.myClass.TurnPwd;

public class RegistActivity extends AppCompatActivity {

    private EditText mEdt_reg_email;
    private EditText mEdt_reg_uid;
    private EditText mEdt_reg_name;
    private EditText mEdt_reg_pwd1;
    private EditText mEdt_reg_pwd2;
    private RadioButton mRdb_reg_teacher;
    private RadioButton mRdb_reg_student;
    private Button mBtn_reg_do;
    private Button mBtn_reg_verification;
    private EditText mEdt_reg_verification;

    private String verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        mEdt_reg_email = findViewById(R.id.mEdt_reg_email);
        mEdt_reg_uid = findViewById(R.id.mEdt_reg_uid);
        mEdt_reg_name = findViewById(R.id.mEdt_reg_name);
        mEdt_reg_pwd1 = findViewById(R.id.mEdt_reg_pwd1);
        mEdt_reg_pwd2 = findViewById(R.id.mEdt_reg_pwd2);
        mRdb_reg_teacher = findViewById(R.id.mRdb_reg_teacher);
        mRdb_reg_student = findViewById(R.id.mRdb_reg_student);
        mBtn_reg_do = findViewById(R.id.mBtn_reg_do);
        mBtn_reg_verification = findViewById(R.id.mBtn_reg_verification);
        mEdt_reg_verification = findViewById(R.id.mEdt_reg_verification);

        mBtn_reg_do.setEnabled(false);  //一开始注册提交按钮不可点击

        mBtn_reg_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reg_email = mEdt_reg_email.getText().toString();

                String sqlStr = "0!select!*!from!userinfo!where!email!=!'" + reg_email + "'";

                MainActivity.CC.setSendMsg(sqlStr);

                try {
                    Thread.sleep(5000);     //为解决网络延时，提供5s等待服务器反馈时间，此时手机不可操作，若要解决此问题，需再提供一个线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String recvMsg = MainActivity.CC.getRecvMsg();

                if(recvMsg.equals("not_exist")) {   //当当前邮箱未注册过，则注册提交按钮可使用
                    mBtn_reg_verification.setEnabled(false);
                    mBtn_reg_do.setEnabled(true);
                    MainActivity.CC.setSendMsg("5!" + reg_email);

                    try {
                        Thread.sleep(5000);     //为解决网络延时，提供5s等待服务器反馈时间，此时手机不可操作，若要解决此问题，需再提供一个线程
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    verification = MainActivity.CC.getRecvMsg();    //从服务端获取验证码
                } else {
                    Toast.makeText(RegistActivity.this, "此邮箱已被注册", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtn_reg_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reg_email = mEdt_reg_email.getText().toString();     //获取注册信息
                String reg_uid = mEdt_reg_uid.getText().toString();
                String reg_name = mEdt_reg_name.getText().toString();
                String reg_pwd1 = mEdt_reg_pwd1.getText().toString();
                String reg_pwd2 = mEdt_reg_pwd2.getText().toString();
                String reg_verification = mEdt_reg_verification.getText().toString();
                int reg_role = 0;   //默认学生；  0学生，1老师
                if (mRdb_reg_teacher.isChecked()){  //老师
                    reg_role = 1;
                }
                if(reg_verification.equals(verification)) {
                    if (reg_pwd1.equals(reg_pwd2)) {
                        String pwd = TurnPwd.turnPwd(reg_pwd1);  //密码加密
                        String sqlStr = "1!insert!into!userinfo(email,uid,uname,role,pwd)!values('" + reg_email + "','" + reg_uid + "','" + reg_name + "'," + reg_role + ",'" + pwd + "')";
                        sqlStr = sqlStr.replace(" ", "!");
                        MainActivity.CC.setSendMsg(sqlStr); //用于修改数据库(插入到userinfo)
                        new AlertDialog.Builder(
                                RegistActivity.this)
                                .setTitle("提示")
                                .setMessage("注册成功，请登录")
                                .setPositiveButton("确定", null)
                                .show();
                        Intent intent = new Intent(RegistActivity.this, MainActivity.class);   //注册成功跳转到登录界面
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegistActivity.this, "两次密码不正确！！！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistActivity.this, "验证码不正确！！！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
