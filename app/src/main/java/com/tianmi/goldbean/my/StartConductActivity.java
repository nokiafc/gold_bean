package com.tianmi.goldbean.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.bean.GoodsQuestion;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.widget.MultiPickResultView;

public class StartConductActivity extends BaseActivity {
    private Button cashBtn;
    private MultiPickResultView upAlbum;
    private ArrayList<String> photos;
    private ListView listView ;
    private List<GoodsQuestion> list = new ArrayList<GoodsQuestion>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_conduct);
        initTitle("商家设置");
        init();
    }
    private void init(){
        listView = (ListView)findViewById(R.id.listview) ;
        upAlbum = (MultiPickResultView) findViewById(R.id.up_photo_album);
        upAlbum.init(this, MultiPickResultView.ACTION_SELECT, null, 5, 1);

        cashBtn = (Button)findViewById(R.id.btn_confirm_commit);
        cashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInfo();
            }
        });
    }
    private void checkInfo(){

    }
}
