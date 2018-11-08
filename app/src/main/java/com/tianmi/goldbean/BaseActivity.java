package com.tianmi.goldbean;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BaseActivity extends Activity {
    public void initTitle(String name){
        TextView title = (TextView)findViewById(R.id.text_title);
        title.setText(name);
        RelativeLayout backLayout = (RelativeLayout)findViewById(R.id.layout_back);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
