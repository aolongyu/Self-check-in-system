package com.example.demo11.myClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo11.R;

import java.util.List;
import java.util.Map;

public class ListViewAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;

    public ListViewAdapter(Context context, List<Map<String, Object>> data) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView item = null;
        if(convertView == null){
            item = new ItemView();
            convertView = layoutInflater.inflate(R.layout.course_row, null);
            item.setmTxt_row_course_name((TextView) convertView.findViewById(R.id.mTxt_row_course_name));
            item.setmTxt_row_teacher_name((TextView) convertView.findViewById(R.id.mTxt_row_teacher_name));
            item.setmTxt_row_time((TextView) convertView.findViewById(R.id.mTxt_row_time));

            convertView.setTag(item);
        }else{
            item = (ItemView) convertView.getTag();
        }

        item.getmTxt_row_course_name().setText((String) data.get(position).get("course_name"));
        item.getmTxt_row_teacher_name().setText((String) data.get(position).get("teacher_name"));
        item.getmTxt_row_time().setText((String) data.get(position).get("time"));
        return convertView;
    }
}
