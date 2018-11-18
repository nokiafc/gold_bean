package com.tianmi.goldbean;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        GoldApplication.getAppInstance().addOutActivity(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void initTitle(String name){

        TextView title = (TextView)findViewById(R.id.text_title);
        title.setText(name);
        RelativeLayout backLayout = (RelativeLayout)findViewById(R.id.layout_back);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }

    public void showRight(){
        RelativeLayout rightLayout = (RelativeLayout)findViewById(R.id.layout_right);
    }

    public void setEnterTransition(boolean enterT){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if(enterT){
                Transition slide = TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right);
                getWindow().setEnterTransition(slide);
            }
        }
    }

    public void setEnterTransitionExplode(boolean enterT){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if(enterT){
                Transition slide = TransitionInflater.from(this).inflateTransition(android.R.transition.explode);
                getWindow().setEnterTransition(slide);
            }
        }
    }

    public void setEnterTransitionFade(boolean enterT){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if(enterT){
                Transition slide = TransitionInflater.from(this).inflateTransition(android.R.transition.fade);
                getWindow().setEnterTransition(slide);
            }
        }
    }
    public void setExitTransition(boolean exit){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if(exit){
                Transition slide = TransitionInflater.from(this).inflateTransition(android.R.transition.slide_left);
                getWindow().setExitTransition(slide);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        GoldApplication.getAppInstance().finishActivity();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                finishAfterTransition();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
