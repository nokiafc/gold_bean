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
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.MyDialog;
import com.tianmi.goldbean.bean.AnswerBean;
import com.tianmi.goldbean.bean.QuestionAnswer;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.QuestionBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class AnswerActivity extends BaseActivity implements View.OnClickListener {
    private String userId = DataUtil.getPreferences("userId", "");
    private String goodsId = "";
    private List<QuestionBean> questionList = new ArrayList<QuestionBean>();
    private TextView questionNumText, questionIndexText, questionContextText;
    private Button nextBtn;
    private int index = 1;
    private EditText editAnswer;
    private List<QuestionAnswer> answerBeans = new ArrayList<QuestionAnswer>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_answer);
        goodsId = getIntent().getStringExtra("goodsId");
        initTitle("答题");
        init();
        getQuestions();
    }
    private void init(){
        editAnswer = (EditText)findViewById(R.id.edit_answer);
        nextBtn = (Button)findViewById(R.id.next_question) ;
        nextBtn.setOnClickListener(this);
        questionIndexText = (TextView)findViewById(R.id.text_question_index);
        questionNumText = (TextView)findViewById(R.id.text_question_num);
        questionContextText = (TextView)findViewById(R.id.text_question_content);
    }
    private void getQuestions(){
        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.getQuestions(Integer.parseInt(userId), Integer.parseInt(goodsId));
        requestInterface.setCallback(new JsonCallback<List<QuestionBean>>() {
            @Override
            public void onError(Request request, String e) {
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(List<QuestionBean> list, String message) throws IOException {
                questionList.addAll(list);
                questionNumText.setText("本次抢答共计"+list.size()+"题");
                questionIndexText.setText(index+"");
                questionContextText.setText("问题:"+list.get(0).getQuestionName());

                if(list.size() == 1){//只有一个问题，修改button内容
                    nextBtn.setText("提交");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_question:
                if(editAnswer.getText().toString() .equals("")){
                    Toast.makeText(this, "问题答案不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                String answer = editAnswer.getText().toString();


                QuestionAnswer bean = new QuestionAnswer();
                bean.setQuestionAnswer(answer);
                bean.setQuestionId(questionList.get(index-1).getId());
                bean.setUserId(Integer.parseInt(userId));
                answerBeans.add(bean);
                editAnswer.setText("");

                if(nextBtn.getText().toString().equals("提交")){
                    answer();
                }
                if(index < questionList.size()){//
                    questionIndexText.setText(index+1+"");
                    questionContextText.setText("问题:"+questionList.get(index).getQuestionName());
                    index = index+1;
                    if(index == questionList.size()){
                        nextBtn.setText("提交");
                    }
                }
                break;
        }
    }
    private Dialog myDialog ;
    private void answer(){
        myDialog = MyDialog.createLoadingDialog(this, "提交中...");
        myDialog.show();
        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.answer(answerBeans, Integer.parseInt(userId), Integer.parseInt(goodsId));
        requestInterface.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                myDialog.dismiss();
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                myDialog.dismiss();
                ActivityUtil.startActivity(AnswerActivity.this, AnswerCorrectActivity.class, o+"");
                finishAfterTransition();

            }
        });

    }
}
