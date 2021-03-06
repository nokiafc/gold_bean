package com.tianmi.goldbean.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.bean.FriendInfoBean;
import com.tianmi.goldbean.bean.MemberUpInfoBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;
import java.util.ArrayList;

import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Request;

public class ManagerMemberUpInfoActivity extends BaseActivity implements View.OnClickListener {

    private MultiPickResultView multiPickResultView;
    private TextView contentText;
    private MemberUpInfoBean bean;
    private Button passBtn, refuseBtn, checkBtn, upBtn;
    private String userId = DataUtil.getPreferences("userId", "");
    private int goodsId ;
    private EditText remarkEdit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_manager_verify_member_up);
         bean = (MemberUpInfoBean)getIntent().getExtras().get("bean");
        initTitle("审核上架");
        init();
    }
    private void init(){
        remarkEdit = findViewById(R.id.edit_remark);
        upBtn = (Button)findViewById(R.id.pass_up_btn);
        upBtn.setOnClickListener(this);
        passBtn = (Button)findViewById(R.id.pass_btn) ;
        passBtn.setOnClickListener(this);
        refuseBtn = (Button)findViewById(R.id.refuse_btn) ;
        refuseBtn.setOnClickListener(this);
        checkBtn = (Button)findViewById(R.id.check_btn) ;
        checkBtn.setOnClickListener(this);
        multiPickResultView = (MultiPickResultView)findViewById(R.id.multiPick);
        contentText = (TextView)findViewById(R.id.content_text);
        if (bean != null){
            String urls = bean.getRetweetImage();
            String url[] = urls.split(",");
            ArrayList<String> list = new ArrayList<String>();
            for(int i=0; i<url.length; i++){
                list.add(url[i]);

            }
            multiPickResultView.init(ManagerMemberUpInfoActivity.this, MultiPickResultView.ACTION_ONLY_SHOW, (ArrayList<String>) list, 5, 0);


        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pass_btn:
                shenhe(1, Integer.parseInt(userId), bean.getRetweetNo());
                break;
            case R.id.refuse_btn:
                shenhe(2, Integer.parseInt(userId), bean.getRetweetNo());
                break;
            case R.id.check_btn:
                ActivityUtil.startActivity(this, MerchantInfoActivity.class, bean.getUserId()+"");
                break;
        }
    }
    private void shenhe(int auditStatus, int userId, String remark){
        RequestInterface request = new RequestInterface(this);
        request.manageMemberUpInfo(userId, auditStatus, remarkEdit.getText().toString(), bean.getRetweetNo(), bean.getUserId());
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
               finish();
            }
        });
    }
}
