package com.example.demo11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.demo11.myClass.ListViewAdapterSignIn;
import com.example.demo11.myClass.ParseStr;
import com.example.demo11.myClass.SingletonUserInfo;

import org.apache.log4j.chainsaw.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignInDetailsActivity extends AppCompatActivity {

    private ListView mLv_signin_info;
    private Button mBtn_signin_not;
    private Button mBtn_signin_export;
    private String[][] str;
    private int row;
    private String ctime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_details);

        mLv_signin_info = findViewById(R.id.mLv_signin_info);
        mBtn_signin_not = findViewById(R.id.mBtn_signin_not);
        mBtn_signin_export = findViewById(R.id.mBtn_signin_export);

        Bundle bundle = this.getIntent().getExtras();
        final String cid = bundle.getString("cid");
        final String time = bundle.getString("time");
        ctime = time;
        String sqlStr = "4 select distinct signin.cid,courses.cname,signin.stuid,userinfo.uname,signin.`At`,signin.ctime from signin, courses, userinfo, elective where signin.cid = courses.cid and userinfo.uid = signin.stuid and courses.cteaid = '"+SingletonUserInfo.getUid()+"' and courses.cid = '"+cid+"' and signin.ctime between date_sub('"+time+"', interval 10 minute) and '"+time+"'";
        sqlStr = sqlStr.replace(" ", "!");
        MainActivity.CC.setSendMsg(sqlStr);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String recvMsg = MainActivity.CC.getRecvMsg();
        ParseStr ps = new ParseStr();
        str = ps.parseStr(recvMsg);
        row = ps.getRow();

        List<Map<String, Object>> result = getData();
        mLv_signin_info.setAdapter(new ListViewAdapterSignIn(this, result));

        mBtn_signin_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInDetailsActivity.this, SignInDetailsNotActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("cid", cid);
                bundle1.putString("time", time);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        mBtn_signin_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sqlStr = "6!" + SingletonUserInfo.getEmail();
                MainActivity.CC.setSendMsg(sqlStr);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String recvMsg = MainActivity.CC.getRecvMsg();

                sqlStr = "7 select distinct signin.cid as '课程号',courses.cname as '课程名',signin.stuid as '学号',userinfo.uname '姓名',signin.`At` as '签到地址',signin.ctime as '签到时间' from signin, courses, userinfo, elective where signin.cid = courses.cid and userinfo.uid = signin.stuid and courses.cteaid = '"+SingletonUserInfo.getUid()+"' and courses.cid = '"+cid+"' and signin.ctime between date_sub('"+time+"', interval 10 minute) and '"+time+"'";
                sqlStr = sqlStr.replace(" ", "!");
                MainActivity.CC.setSendMsg(sqlStr);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                recvMsg = MainActivity.CC.getRecvMsg();
            }
        });
    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < row; i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("cid", str[i][0]);
            map.put("cname", str[i][1]);
            map.put("uid", str[i][2]);
            map.put("uname", str[i][3]);
            map.put("at", str[i][4]);
            map.put("time", str[i][5]);
            list.add(map);
        }
        return list;
    }


}
