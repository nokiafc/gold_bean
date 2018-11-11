package com.tianmi.goldbean.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.adapter.ConductRecyclerAdapter;
import com.tianmi.goldbean.bean.GoodsQuestion;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.widget.MultiPickResultView;

public class StartConductActivity extends BaseActivity {
    private Button cashBtn;
    private MultiPickResultView upAlbum;
    private ArrayList<String> photos;
    private ListView listView ;
    private List<GoodsQuestion> list = new ArrayList<GoodsQuestion>();
    private RecyclerView  recycleview;
    private ConductRecyclerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_conduct);
        initTitle("商家设置");
        init();
    }
    private void init(){
        list.add(new GoodsQuestion());
        adapter = new ConductRecyclerAdapter(this, list);
        recycleview = (RecyclerView)findViewById(R.id.recycleview);
        recycleview.setAdapter(adapter);
        recycleview.setLayoutManager(new LinearLayoutManager(this));


        upAlbum = (MultiPickResultView) findViewById(R.id.up_photo_album);
        upAlbum.init(this, MultiPickResultView.ACTION_SELECT, null, 5, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1) {
                upAlbum.onActivityResult(requestCode, resultCode, data);
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }


        }
    }
}
