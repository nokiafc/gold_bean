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

import com.tianmi.goldbean.Utils.ActivityUtil;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        GoldApplication.getAppInstance().addOutActivity(this);
        ActivityUtil.setStatusBarColor(this, "#ffffff");
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
//        RelativeLayout rightLayout = (RelativeLayout)findViewById(R.id.layout_right);
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
}
