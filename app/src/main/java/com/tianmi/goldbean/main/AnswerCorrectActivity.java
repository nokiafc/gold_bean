package com.tianmi.goldbean.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.MyDialog;
import com.tianmi.goldbean.bean.QuestionAnswer;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.QuestionBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class AnswerCorrectActivity extends BaseActivity {
    private Button completeBtn;
    private Button goldBtn;
    private String gold;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_answer_correct);
        gold = getIntent().getStringExtra("key");
        initTitle("答题");
        init();
    }
    private void init(){
        goldBtn = (Button)findViewById(R.id.gold_btn) ;
        goldBtn.setText("获得"+gold+"元");
        completeBtn = (Button)findViewById(R.id.next_question);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }
}
