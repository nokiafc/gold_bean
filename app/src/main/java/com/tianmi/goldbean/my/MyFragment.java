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

import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.DataUtil;

import me.iwf.photopicker.widget.MultiPickResultView;

public class MyFragment extends Fragment implements View.OnClickListener {
    private Button rechargeBtn, cashBtn;
    private ImageView conductImg;
    //是否开通商户
    private String merchantsFlag = DataUtil.getPreferences("merchantsFlag", "");

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        init(view);
        return view;
    }
    private void init(View view ){
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
                Intent i = new Intent(getActivity(), RechargeActivity.class);
                startActivity(i);
                break;
            case R.id.btn_cash://提现
                Intent ii = new Intent(getActivity(), CashActivity.class);
                startActivity(ii);
                break;
            case R.id.img_conduct:
                startConduct();
                break;
        }
    }
    //开始做宣传
    private void startConduct(){
        //1.判断个人状态是个人还是已经是商户了
        if(merchantsFlag.equals("0") || merchantsFlag == null){
            Intent intent = new Intent(getActivity(), OpenActivity.class);
            startActivity(intent);
        }else {//2.宣传产品
            Intent i = new Intent(getActivity(), StartConductActivity.class);
            startActivity(i);
        }




    }
}
