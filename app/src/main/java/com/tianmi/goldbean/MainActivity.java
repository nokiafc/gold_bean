package com.tianmi.goldbean;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.main.MainFragment;
import com.tianmi.goldbean.message.MessageFragment;
import com.tianmi.goldbean.my.MyFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout mainLayout, messageLayout, myLayout;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment mainFragment, messageFragment, myFragment;
    private ImageView mainImg, messageImg, myImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransitionFade(true);
        setContentView(R.layout.activity_main);
        ActivityUtil.setStatusBarColor(this, "#ffffff");
        init();
    }
    private void init(){

        mainImg = (ImageView)findViewById(R.id.img_main);
        messageImg = (ImageView)findViewById(R.id.img_message);
        myImg = (ImageView)findViewById(R.id.img_my);

        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        mainFragment = new MainFragment();

        mainLayout = (RelativeLayout)findViewById(R.id.layout_main);
        mainLayout.setOnClickListener(this);

        messageLayout = (RelativeLayout)findViewById(R.id.layout_message);
        messageLayout.setOnClickListener(this);

        myLayout = (RelativeLayout)findViewById(R.id.layout_my);
        myLayout.setOnClickListener(this);

        transaction.add(R.id.frame_container, mainFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.layout_main){
            showMainFragment();
            ActivityUtil.setStatusBarColor(this, "#ffffff");

        }else if(id == R.id.layout_message){
            showMessageFragment();
            ActivityUtil.setStatusBarColor(this, "#ffffff");

        }else if(id == R.id.layout_my){
            showMyFragment();
            ActivityUtil.setStatusBarColor(this, "#FB5352");
        }
    }

    private void hideFragment(FragmentTransaction transaction){
        if(mainFragment != null){
            transaction.hide(mainFragment);
        }
        if(messageFragment != null){
            transaction.hide(messageFragment);
        }
        if(myFragment != null){
            transaction.hide(myFragment);
        }
    }
    private void showMainFragment(){
        mainImg.setImageResource(R.drawable.icon_main_choose);
        messageImg.setImageResource(R.drawable.icon_message_unchoose);
        myImg.setImageResource(R.drawable.icon_my_unchoose);

        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        if(mainFragment == null){
            mainFragment = new MainFragment();
            transaction.add(R.id.frame_container, mainFragment);
        }

        hideFragment(transaction);
        transaction.show(mainFragment);
        transaction.commit();
    }
    private void showMessageFragment(){
        mainImg.setImageResource(R.drawable.icon_main_unchoose);
        messageImg.setImageResource(R.drawable.icon_message_choose);
        myImg.setImageResource(R.drawable.icon_my_unchoose);

        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        if(messageFragment == null){
            messageFragment = new MessageFragment();
            transaction.add(R.id.frame_container, messageFragment);
        }

        hideFragment(transaction);
        transaction.show(messageFragment);
        transaction.commit();
    }
    private void showMyFragment(){
        mainImg.setImageResource(R.drawable.icon_main_unchoose);
        messageImg.setImageResource(R.drawable.icon_message_unchoose);
        myImg.setImageResource(R.drawable.icon_my_choose);

        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        if(myFragment == null){
            myFragment = new MyFragment();
            transaction.add(R.id.frame_container, myFragment);
        }

        hideFragment(transaction);
        transaction.show(myFragment);
        transaction.commit();
    }

    private long exitTime = 0;

    @Override
    protected void onResume() {
        super.onResume();
        GoldApplication.getAppInstance().finishActivity();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finishAfterTransition();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
