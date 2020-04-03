package com.example.demo11.myClass;

import android.widget.Button;
import android.widget.TextView;

public class ItemViewCourseTeacher {
    private TextView mTxt_row_course_teacher_cid;
    private TextView mTxt_row_course_teacher_cname;
    private TextView mTxt_row_course_teacher_time;
    private Button mBtn_row_course_teacher;

    public void setmBtn_row_course_teacher(Button mBtn_row_course_teacher) {
        this.mBtn_row_course_teacher = mBtn_row_course_teacher;
    }

    public void setmTxt_row_course_teacher_cid(TextView mTxt_row_course_teacher_cid) {
        this.mTxt_row_course_teacher_cid = mTxt_row_course_teacher_cid;
    }

    public void setmTxt_row_course_teacher_cname(TextView mTxt_row_course_teacher_cname) {
        this.mTxt_row_course_teacher_cname = mTxt_row_course_teacher_cname;
    }

    public void setmTxt_row_course_teacher_time(TextView mTxt_row_course_teacher_time) {
        this.mTxt_row_course_teacher_time = mTxt_row_course_teacher_time;
    }

    public Button getmBtn_row_course_teacher() {
        return mBtn_row_course_teacher;
    }

    public TextView getmTxt_row_course_teacher_cid() {
        return mTxt_row_course_teacher_cid;
    }

    public TextView getmTxt_row_course_teacher_cname() {
        return mTxt_row_course_teacher_cname;
    }

    public TextView getmTxt_row_course_teacher_time() {
        return mTxt_row_course_teacher_time;
    }
}
