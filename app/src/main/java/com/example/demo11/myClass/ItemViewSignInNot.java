package com.example.demo11.myClass;

import android.widget.TextView;

public class ItemViewSignInNot {

    private TextView mTxt_row_signin_not_uname;
    private TextView mTxt_row_signin_not_stuid;
    private TextView mTxt_row_signin_not_status;

    public void setmTxt_row_signin_not_uname(TextView tv){
        this.mTxt_row_signin_not_uname = tv;
    }

    public void setmTxt_row_signin_not_stuid(TextView tv){
        this.mTxt_row_signin_not_stuid = tv;
    }

    public TextView getmTxt_row_signin_not_uname() {
        return mTxt_row_signin_not_uname;
    }

    public TextView getmTxt_row_signin_not_stuid() {
        return mTxt_row_signin_not_stuid;
    }

    public void setmTxt_row_signin_not_status(TextView mTxt_row_signin_not_status) {
        this.mTxt_row_signin_not_status = mTxt_row_signin_not_status;
    }

    public TextView getmTxt_row_signin_not_status() {
        return mTxt_row_signin_not_status;
    }
}
