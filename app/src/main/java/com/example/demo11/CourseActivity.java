package com.example.demo11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.demo11.myClass.ListViewAdapter;
import com.example.demo11.myClass.ParseStr;
import com.example.demo11.myClass.SingletonUserInfo;

import org.apache.log4j.chainsaw.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseActivity extends AppCompatActivity {

    private ListView mLv_course_info;

    private String[][] str;

    private int row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        mLv_course_info = findViewById(R.id.mLv_course_info);

        String sqlStr = "4 select courses.cname,userinfo.uname,elective.ctime from courses,userinfo,elective where elective.stuid = (select uid from userinfo where email = '"+SingletonUserInfo.getEmail()+"') and elective.cid = courses.cid and courses.cteaid = userinfo.uid order by elective.ctime asc";
        sqlStr = sqlStr.replace(" ", "!");
        MainActivity.CC.setSendMsg(sqlStr);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String recvMsg = MainActivity.CC.getRecvMsg();

        ParseStr ps = new ParseStr();
        str = ps.parseStr(recvMsg);    //拿去解析
        row = ps.getRow();

        List<Map<String, Object>> result = getData();
        mLv_course_info.setAdapter(new ListViewAdapter(this, result));
    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < row; i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("course_name", str[i][0]);
            map.put("teacher_name", str[i][1]);
            map.put("time", str[i][2]);
            list.add(map);
        }
        return list;
    }


}
