package com.example.demo11.myClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo11.CourseTeacherActivity;
import com.example.demo11.IndexTeacherActivity;
import com.example.demo11.MainActivity;
import com.example.demo11.R;
import com.example.demo11.SignInDetailsActivity;

import java.util.List;
import java.util.Map;

public class ListViewAdapterCourseTeacher extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;

    public ListViewAdapterCourseTeacher(Context context, List<Map<String, Object>> data) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int i) {
        return this.data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemViewCourseTeacher item = null;
        if(convertView == null){
            item = new ItemViewCourseTeacher();

            convertView = layoutInflater.inflate(R.layout.course_teacher_row, null);
            item.setmTxt_row_course_teacher_cid((TextView) convertView.findViewById(R.id.mTxt_row_course_teacher_cid));
            item.setmTxt_row_course_teacher_cname((TextView) convertView.findViewById(R.id.mTxt_row_course_teacher_cname));
            item.setmTxt_row_course_teacher_time((TextView) convertView.findViewById(R.id.mTxt_row_course_teacher_time));
            item.setmBtn_row_course_teacher((Button) convertView.findViewById(R.id.mBtn_row_course_teacher));

            convertView.setTag(item);
        }else{
            item = (ItemViewCourseTeacher) convertView.getTag();
        }

        item.getmTxt_row_course_teacher_cid().setText((String) data.get(position).get("cid"));
        item.getmTxt_row_course_teacher_cname().setText((String) data.get(position).get("cname"));
        item.getmTxt_row_course_teacher_time().setText((String) data.get(position).get("time"));
        item.getmBtn_row_course_teacher().setText((String) data.get(position).get("btn"));

        final View finalConvertView = convertView;
        item.getmBtn_row_course_teacher().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cid = (String) data.get(position).get("cid");
                String time = (String) data.get(position).get("time");
                CourseTeacherActivity courseTeacherActivity = new CourseTeacherActivity();
                courseTeacherActivity.toSignInDetailsActivity(cid, time);
            }
        });
        return convertView;
    }
}
