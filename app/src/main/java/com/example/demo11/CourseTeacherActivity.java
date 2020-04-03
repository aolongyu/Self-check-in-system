package com.example.demo11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.demo11.myClass.ListViewAdapterCourseTeacher;
import com.example.demo11.myClass.ListViewAdapterSignIn;
import com.example.demo11.myClass.ParseStr;
import com.example.demo11.myClass.SingletonUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseTeacherActivity extends AppCompatActivity {

    private ListView mLv_course_teacher_info;
    private String[][] str;
    private int row;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_teacher);

        context = CourseTeacherActivity.this;

        mLv_course_teacher_info = findViewById(R.id.mLv_course_teacher_info);

        String sqlStr = "4 select DISTINCT courses.cid, courses.cname, elective.ctime from courses, elective where courses.cid = elective.cid and courses.cteaid = '"+SingletonUserInfo.getUid()+"' and elective.ctime <= now()"; //sql语句
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
        mLv_course_teacher_info.setAdapter(new ListViewAdapterCourseTeacher(this, result));
    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < row; i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("cid", str[i][0]);
            map.put("cname", str[i][1]);
            map.put("time", str[i][2]);
            map.put("btn", "查看");
            list.add(map);
        }
        return list;
    }

    public void toSignInDetailsActivity(String cid, String time) {
        Intent intent = new Intent(context, SignInDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cid", cid);
        bundle.putString("time", time);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
