package com.example.demo11.myClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demo11.R;

import java.util.List;
import java.util.Map;

public class ListViewAdapterSignIn extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;

    public ListViewAdapterSignIn(Context context, List<Map<String, Object>> data) {
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
        ItemViewSignIn item = null;
        if(convertView == null){
            item = new ItemViewSignIn();
            convertView = layoutInflater.inflate(R.layout.signin_row, null);
            item.setmTxt_row_signin_cid((TextView) convertView.findViewById(R.id.mTxt_row_signin_cid));
            item.setmTxt_row_signin_cname((TextView) convertView.findViewById(R.id.mTxt_row_signin_cname));
            item.setmTxt_row_signin_uid((TextView) convertView.findViewById(R.id.mTxt_row_signin_stuid));
            item.setmTxt_row_signin_uname((TextView) convertView.findViewById(R.id.mTxt_row_signin_uname));
            item.setmTxt_row_signin_at((TextView) convertView.findViewById(R.id.mTxt_row_signin_at));
            item.setmTxt_row_signin_time((TextView) convertView.findViewById(R.id.mTxt_row_signin_time));

            convertView.setTag(item);

        }else{
            item = (ItemViewSignIn) convertView.getTag();
        }

        item.getmTxt_row_signin_cid().setText((String) data.get(position).get("cid"));
        item.getmTxt_row_signin_cname().setText((String) data.get(position).get("cname"));
        item.getmTxt_row_signin_uid().setText((String) data.get(position).get("uid"));
        item.getmTxt_row_signin_uname().setText((String) data.get(position).get("uname"));
        item.getmTxt_row_signin_at().setText((String) data.get(position).get("at"));
        item.getmTxt_row_signin_time().setText((String) data.get(position).get("time"));
        return convertView;
    }
}
