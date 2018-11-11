package com.tianmi.goldbean.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.bean.GoodsQuestion;

import java.util.List;

public class QuestionAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsQuestion> list;
    public QuestionAdapter(Context context, List<GoodsQuestion> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, null);

        return null;
    }
}
