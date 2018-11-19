package com.tianmi.goldbean.message;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tianmi.goldbean.R;

public class MessageFragment extends Fragment implements View.OnClickListener {
    private TextView title;
    private Button accountBtn, merchantBtn;
    private boolean buttonFlag = true;
    private FragmentManager manager;
    private Fragment accountFragment, merchantFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        init(view);
        return view;
    }
    private void init(View view){
        manager = getChildFragmentManager();
        loadFragment(0);

        title = (TextView)view.findViewById(R.id.text_title);
        title.setText("消息");

        accountBtn = (Button)view.findViewById(R.id.account_btn);
        accountBtn.setOnClickListener(this);
        merchantBtn = (Button)view.findViewById(R.id.merchant_btn);
        merchantBtn.setOnClickListener(this);
    }

    private void loadFragment(int index) {

        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);
        if(index == 0){
            if(accountFragment == null ){
                accountFragment = new AccountFragment();
                transaction.add(R.id.message_container, accountFragment);
            }else {
                transaction.show(accountFragment);
            }
        }else if(index == 1){
            if(merchantFragment == null ){
                merchantFragment = new MerchantFragment();
                transaction.add(R.id.message_container, merchantFragment);
            }else {
                transaction.show(merchantFragment);
            }
        }
        transaction.commit();
    }

    public void hideFragment(FragmentTransaction transaction){
        if(accountFragment != null){
            transaction.hide(accountFragment);
        }
        if(merchantFragment != null){
            transaction.hide(merchantFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_btn:
                loadFragment(0);
                accountBtn.setBackgroundColor(Color.parseColor("#fe8d2f"));
                accountBtn.setTextColor(Color.parseColor("#ffffff"));

                merchantBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                merchantBtn.setTextColor(Color.parseColor("#fe8d2f"));
                break;
            case R.id.merchant_btn:
                loadFragment(1);
                accountBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                accountBtn.setTextColor(Color.parseColor("#fe8d2f"));

                merchantBtn.setBackgroundColor(Color.parseColor("#fe8d2f"));
                merchantBtn.setTextColor(Color.parseColor("#ffffff"));
                break;
        }
    }
}
