package com.example.demo11;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.demo11.myClass.ChatClient;
import com.example.demo11.myClass.ParseStr;
import com.example.demo11.myClass.SingletonUserInfo;
import com.example.demo11.myClass.TurnPwd;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Runnable{

    private EditText mEdt_login_email;
    private EditText mEdt_login_pwd;
    private Button mBtn_login;
    private CheckBox mCb_remenber;
    private RadioButton mRdb_login_teacher;
    private TextView mTxt_toregist;

    SharedPreferences setting;

    public static ChatClient CC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        channelShow();

        new Thread(this).start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(60000);        //一分钟监测一次是否有签到项目
                    String sqlStr = "0!select!*!from!elective!where!stuid!=!'" + SingletonUserInfo.getUid() + "'!and!now()!between!date_add(ctime,!interval-10!minute)!and!ctime";
                    MainActivity.CC.setSendMsg(sqlStr);
                    Thread.sleep(5000);
                    String recvMsg = MainActivity.CC.getRecvMsg();
                    if (recvMsg.equals("exist")) {
                        channelShow();
                    }
//                    Thread.sleep(540000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        mEdt_login_email = findViewById(R.id.mEdt_login_email);
        mEdt_login_pwd = findViewById(R.id.mEdt_login_pwd);
        mBtn_login = findViewById(R.id.mBtn_login_do);
        mCb_remenber = findViewById(R.id.mCb_remenber);
        mRdb_login_teacher = findViewById(R.id.mRdb_login_teacher);
        mTxt_toregist = findViewById(R.id.mTxt_toregist);

        setting = getSharedPreferences("user", MODE_PRIVATE);
        mCb_remenber.setChecked(setting.getBoolean("isRemenber", false));

        if (mCb_remenber.isChecked()) {
            mEdt_login_email.setText(setting.getString("login_email", ""));
            mEdt_login_pwd.setText(setting.getString("login_pwd", ""));
        }

        mCb_remenber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setting.edit().putBoolean("isRemenber", b).commit();
            }
        });


        mBtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login_email = mEdt_login_email.getText().toString(); //获取数据
                String login_pwd1 = mEdt_login_pwd.getText().toString();
                String login_pwd = TurnPwd.turnPwd(login_pwd1); //加密

                int role = 0;
                if (mRdb_login_teacher.isChecked()) {
                    role = 1;
                }

                MainActivity.CC.setSendMsg("0!select!*!from!userinfo!where!email!=!'" + login_email + "'!and!pwd!=!'" + login_pwd + "'!and!role!=!" + role);  //提供email和密码登录
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String recvMsg = MainActivity.CC.getRecvMsg();

                if (recvMsg.equals("exist")) {
                    String sqlStr = "4!select!*!from!userinfo!where!email!='" + login_email + "'";
                    MainActivity.CC.setSendMsg(sqlStr);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    recvMsg = MainActivity.CC.getRecvMsg();

                    ParseStr ps = new ParseStr();
                    String[][] str = ps.parseStr(recvMsg);

                    SingletonUserInfo.setUid(str[0][4]);    //将用户信息存入到单例模式
                    SingletonUserInfo.setName(str[0][3]);
                    SingletonUserInfo.setRole(str[0][1]);
                    SingletonUserInfo.setEmail(login_email);

                    setting.edit().putString("login_email", login_email).commit();
                    setting.edit().putString("login_pwd", login_pwd1).commit();
                    Intent intent;
                    if (str[0][1].equals("0")) {
                        intent = new Intent(MainActivity.this, IndexActivity.class);   //登录成功直接跳转到签到界面
                    } else {
                        intent = new Intent(MainActivity.this, IndexTeacherActivity.class);
                    }
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(
                            MainActivity.this)
                            .setTitle("错误")
                            .setMessage("用户名、密码或角色错误")
                            .setPositiveButton("确定", null)
                            .show();
                }
            }
        });

        mTxt_toregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void run() {
        try {
            CC = new ChatClient();  //CC为静态变量，只会实例化一次
            CC.chat();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void channelShow(){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        String CHANNEL_ID = "1";
        String CHANNEL_Name = "channel_name";
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_Name, NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID);
        builder.setContentTitle("自助服务签到系统");
        builder.setContentText("快来签到啊！！！！！！！！！！！！！！！！！！！！！！！！");
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setChannelId(CHANNEL_ID);
        builder.setContentIntent(pi);
        Notification notification = builder.build();
        manager.notify(1, notification);
    }
}