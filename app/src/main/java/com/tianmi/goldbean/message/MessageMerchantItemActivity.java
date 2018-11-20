package com.tianmi.goldbean.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.bean.AccountWaterBean;
import com.tianmi.goldbean.bean.MerchantWaterBean;

public class MessageMerchantItemActivity extends BaseActivity {
    private TextView msgText;
    private MerchantWaterBean bean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(true);
        setContentView(R.layout.activity_message_item);
        bean = (MerchantWaterBean)getIntent().getExtras().getSerializable("bean");
        initTitle("消息内容");
        init();
    }
    private void init(){
        msgText = (TextView)findViewById(R.id.msg_content);
        switch (bean.getGoodsStatus()){
            case 1:
                msgText.setText("商品ID为"+bean.getId()+"的商品审核通过了");
                break;
            case 2:
                msgText.setText("商品ID为"+bean.getId()+"的商品被驳回了");
                break;
            case 3:
                msgText.setText("商品ID为"+bean.getId()+"的商品下架了");
                break;
            case 0:
                msgText.setText("商品ID为"+bean.getId()+"的商品等待审核中");
                break;
        }

    }
}
