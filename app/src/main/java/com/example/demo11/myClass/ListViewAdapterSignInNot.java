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

public class ListViewAdapterSignInNot extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;

    public ListViewAdapterSignInNot(Context context, List<Map<String, Object>> data) {
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
        ItemViewSignInNot item = null;
        if(convertView == null){
            item = new ItemViewSignInNot();
            convertView = layoutInflater.inflate(R.layout.signin_not_row, null);
            item.setmTxt_row_signin_not_stuid((TextView) convertView.findViewById(R.id.mTxt_row_signin_not_stuid));
            item.setmTxt_row_signin_not_uname((TextView) convertView.findViewById(R.id.mTxt_row_signin_not_uname));
            item.setmTxt_row_signin_not_status((TextView) convertView.findViewById(R.id.mTxt_row_signin_not_status));

            convertView.setTag(item);
        }else{
            item = (ItemViewSignInNot) convertView.getTag();
        }

        item.getmTxt_row_signin_not_stuid().setText((String) data.get(position).get("stuid"));
        item.getmTxt_row_signin_not_uname().setText((String) data.get(position).get("uname"));
        item.getmTxt_row_signin_not_status().setText((String) data.get(position).get("status"));
        return convertView;
    }
}
