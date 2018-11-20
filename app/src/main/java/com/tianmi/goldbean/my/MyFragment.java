package com.tianmi.goldbean.my;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.bean.MyInfoBean;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;

import java.io.IOException;

import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Request;

public class MyFragment extends Fragment implements View.OnClickListener {
    private Button rechargeBtn, cashBtn;
    private ImageView conductImg;
    private TextView userPhone, userIdText ;
    //是否开通商户
    private String merchantsFlag = DataUtil.getPreferences("merchantsFlag", "");
    private String phone = DataUtil.getPreferences("userPhone", "");
    private String userId = DataUtil.getPreferences("userId", "");
    private ImageView managerImg;
    private TextView accountText, allPersonText, monPersonText;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        init(view);
        getMyInfo();
        return view;
    }
    private void init(View view ){
        accountText = (TextView)view.findViewById(R.id.text_gold_num) ;
        allPersonText = (TextView)view.findViewById(R.id.text_recommend_num);
        monPersonText = (TextView)view.findViewById(R.id.text_recommend_month) ;

        managerImg = (ImageView)view.findViewById(R.id.img_head) ;
        managerImg.setOnClickListener(this);
        userPhone = (TextView)view.findViewById(R.id.text_phone_num) ;
        userPhone.setText(phone.substring(0,3)+"****"+phone.substring(7, 11));
        userIdText = (TextView)view.findViewById(R.id.text_id_num) ;
        userIdText.setText("ID:  "+userId);
        rechargeBtn = (Button)view.findViewById(R.id.btn_recharge);
        rechargeBtn.setOnClickListener(this);
        cashBtn = (Button)view.findViewById(R.id.btn_cash);
        cashBtn.setOnClickListener(this);
        conductImg = (ImageView)view.findViewById(R.id.img_conduct);
        conductImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_recharge://充值
                ActivityUtil.startActivity(getActivity(), RechargeActivity.class);
                break;
            case R.id.btn_cash://提现
                ActivityUtil.startActivity(getActivity(), CashActivity.class, accountText.getText().toString());
                break;
            case R.id.img_conduct:
                startConduct();
                break;
            case R.id.img_head://跳转设置页面
                ActivityUtil.startActivity(getActivity(), SetActivity.class);
                break;
        }
    }
    //开始做宣传
    private void startConduct(){
        //1.判断个人状态是个人还是已经是商户了
        if(merchantsFlag.equals("0") || merchantsFlag == null){
            ActivityUtil.startActivity(getActivity(), OpenActivity.class);
        }else {//2.宣传产品
            ActivityUtil.startActivity(getActivity(), StartConductActivity.class);

        }

    }

    private void getMyInfo(){
        RequestInterface requestInterface = new RequestInterface(getActivity());
        requestInterface.getMyInfo(Integer.parseInt(userId));
        requestInterface.setCallback(new JsonCallback<MyInfoBean>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(MyInfoBean bean, String message) throws IOException {
                if(bean != null){
                    accountText.setText(bean.getAccountBalance());
                    allPersonText.setText("总计"+bean.getTotalCount()+"人");
                    monPersonText.setText("总计"+bean.getMonthCount()+"人");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        merchantsFlag = DataUtil.getPreferences("merchantsFlag", "");
    }
}
