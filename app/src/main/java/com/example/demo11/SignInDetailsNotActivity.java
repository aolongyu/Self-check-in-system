package com.example.demo11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.demo11.myClass.ListViewAdapterSignInNot;
import com.example.demo11.myClass.ParseStr;
import com.example.demo11.myClass.SingletonUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignInDetailsNotActivity extends AppCompatActivity {

    private ListView mLv_signin_not_info;
    private Button mBtn_signin_export_not;
    private String[][] str;
    private int row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_details_not);

        mLv_signin_not_info = findViewById(R.id.mLv_signin_not_info);
        mBtn_signin_export_not = findViewById(R.id.mBtn_signin_export_not);

        Bundle bundle = this.getIntent().getExtras();
        String cid = bundle.getString("cid");
        String time = bundle.getString("time");

        String sqlStr =
                "4 select userinfo.uid, userinfo.uname from userinfo where userinfo.role = '0' and userinfo.uid not in" +
                    "(" +
                        "select stuid from" +
                        "(" +
                            "select distinct signin.cid,courses.cname,signin.stuid,userinfo.uname,signin.`At`,signin.ctime from signin, courses, userinfo, elective where signin.cid = courses.cid and userinfo.uid = signin.stuid and courses.cteaid = '17301' and courses.cid = '1' and signin.ctime between date_sub('2020-03-25 08:20:00', interval 10 minute) and '2020-03-25 08:20:00'" +
                        ") as temp" +
                    ")";
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
        mLv_signin_not_info.setAdapter(new ListViewAdapterSignInNot(this, result));

        mBtn_signin_export_not.setOnClickListener(new View.OnClickListener() {
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

                sqlStr =
                        "7 select userinfo.uid as '学号', userinfo.uname as '姓名' from userinfo where userinfo.role = '0' and userinfo.uid not in" +
                                "(" +
                                "select stuid from" +
                                "(" +
                                "select distinct signin.cid,courses.cname,signin.stuid,userinfo.uname,signin.`At`,signin.ctime from signin, courses, userinfo, elective where signin.cid = courses.cid and userinfo.uid = signin.stuid and courses.cteaid = '17301' and courses.cid = '1' and signin.ctime between date_sub('2020-03-25 08:20:00', interval 10 minute) and '2020-03-25 08:20:00'" +
                                ") as temp" +
                                ")";
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
            map.put("stuid", str[i][0]);
            map.put("uname", str[i][1]);
            map.put("status", "未签");
            list.add(map);
        }
        return list;
    }
}
