package com.example.demo11.myClass;

import android.widget.Button;
import android.widget.TextView;

public class ItemView {
    private TextView mTxt_row_course_name;
    private TextView mTxt_row_teacher_name;
    private TextView mTxt_row_time;

    public void setmTxt_row_course_name(TextView tv){
        this.mTxt_row_course_name = tv;
    }

    public void setmTxt_row_teacher_name(TextView tv){
        this.mTxt_row_teacher_name = tv;
    }

    public TextView getmTxt_row_course_name() {
        return mTxt_row_course_name;
    }

    public TextView getmTxt_row_teacher_name() {
        return mTxt_row_teacher_name;
    }

    public void setmTxt_row_time(TextView mTxt_row_time) {
        this.mTxt_row_time = mTxt_row_time;
    }

    public TextView getmTxt_row_time() {
        return mTxt_row_time;
    }
}
