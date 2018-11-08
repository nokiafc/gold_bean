package com.tianmi.goldbean.my;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tianmi.goldbean.R;

public class MyFragment extends Fragment implements View.OnClickListener {
    private Button rechargeBtn, cashBtn;
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
        }
    }
}
