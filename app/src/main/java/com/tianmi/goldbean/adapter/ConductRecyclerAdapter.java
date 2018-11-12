package com.tianmi.goldbean.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.bean.GoodsQuestion;

import java.util.List;

public class ConductRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<GoodsQuestion> list;
    public ConductRecyclerAdapter(Context context, List<GoodsQuestion> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question , parent , false);
        ConductRecyclerAdapter.MyViewHolder viewHolder = new ConductRecyclerAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder h = (MyViewHolder)holder;
        h.textNum.setText("问题"+(position+1));
        h.editQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.get(position).setQuestionName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        h.editAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.get(position).setQuestionAnswer(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textNum;
        EditText editAnswer, editQuestion;

        public MyViewHolder(View itemView) {
            super(itemView);
            textNum = (TextView)itemView.findViewById(R.id.text_question);
            editQuestion = (EditText)itemView.findViewById(R.id.edit_question);
            editAnswer = (EditText)itemView.findViewById(R.id.edit_answer);
        }
    }
}
