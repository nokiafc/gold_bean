package com.tianmi.goldbean.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianmi.goldbean.BaseActivity;
import com.tianmi.goldbean.R;
import com.tianmi.goldbean.Utils.ActivityUtil;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.Utils.Utils;
import com.tianmi.goldbean.adapter.CommentAdapter;
import com.tianmi.goldbean.adapter.RoomPagerAdapter;
import com.tianmi.goldbean.my.MerchantInfoActivity;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.RecyclerBean;
import com.tianmi.goldbean.net.bean.RoomBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Request;

public class RoomActivity extends BaseActivity implements View.OnClickListener {
    private String goodsId = "";
    private EditText wordEdit;
    private Button sayBtn ,getRedBtn;
    private String userId = DataUtil.getPreferences("userId", "");
    private RelativeLayout circleFriendLayout, merchantLayout;
    private String merchantUserId = "";
//    private ViewPager viewPager;
//    private RoomPagerAdapter adapter;
    private List<String> list = new ArrayList<String>();
    private TextView redNumText, merchantContent;
    private ListView sayListView;
    private CommentAdapter commentAdapter;
    private List<RoomBean.CommentInfo> commentInfos = new ArrayList<RoomBean.CommentInfo>();
    private MultiPickResultView multiPick;
    private String goodsName = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setEnterTransition(true);
        setContentView(R.layout.activity_room);
        initTitle("商品详情");
        RecyclerBean bean = (RecyclerBean)getIntent().getExtras().getSerializable("bean");
        goodsName = bean.getGoodsName()+"";
        goodsId = bean.getId()+"";
        init();
        getGoodsDetail();
    }
    private void init(){
        merchantContent = (TextView)findViewById(R.id.merchant_content) ;
        merchantContent.setText(goodsName);
        multiPick = (MultiPickResultView)findViewById(R.id.multiPick);

        sayListView = (ListView)findViewById(R.id.say_listview);
        commentAdapter = new CommentAdapter(this, commentInfos);
        sayListView.setAdapter(commentAdapter);

        redNumText = (TextView)findViewById(R.id.text_red) ;
        getRedBtn = (Button)findViewById(R.id.get_red);
        getRedBtn.setOnClickListener(this);

        wordEdit = (EditText)findViewById(R.id.edit_words);
        sayBtn = (Button)findViewById(R.id.btn_say);
        sayBtn.setOnClickListener(this);
        circleFriendLayout = (RelativeLayout)findViewById(R.id.friend_layout);
        circleFriendLayout.setOnClickListener(this);
        merchantLayout = (RelativeLayout)findViewById(R.id.merchant_layout);
        merchantLayout.setOnClickListener(this);
    }
    private void getGoodsDetail(){
        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.getGoodsDetail(Integer.parseInt(goodsId));
        requestInterface.setCallback(new JsonCallback<RoomBean>() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(RoomBean bean, String message) throws IOException {
                redNumText.setText("红包剩余"+bean.getRemainAmount());
                merchantUserId = bean.getUserId()+"";
                String urls = bean.getGoodsUrl();
                String [] urlStr = urls.split(",");
                for(int i=0; i<urlStr.length; i++){
                    if(urlStr[i] != null && !urlStr[i].equals("")){
                        list.add(urlStr[i]);
                    }
                }
                multiPick.init(RoomActivity.this, MultiPickResultView.ACTION_ONLY_SHOW, (ArrayList<String>) list, 5, 0);
                if(bean.getCommentInfo() != null){
                    commentInfos.addAll(bean.getCommentInfo());
                    commentAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_say:
                String remark = wordEdit.getText().toString().trim();
                if(remark.equals("") || remark == null){
                    Toast.makeText(this, "留言不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                goodsComment();
                break;
            case R.id.friend_layout:
                //分享到朋友圈
                Intent intent = new Intent(this, StartFriendActivity.class);
                intent.putExtra("goodsId", goodsId);
                startActivity(intent);
                break;
            case R.id.merchant_layout:
                //跳转商家信息页
                ActivityUtil.startActivity(this, MerchantInfoActivity.class, merchantUserId);
                break;
            case R.id.get_red://跳转回答问题页面
                Intent i  = new Intent(this, AnswerActivity.class);
                i.putExtra("goodsId", goodsId);
                startActivity(i);
                break;
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(RoomActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(RoomActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(RoomActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };
    private void goodsComment(){
        final String remark = wordEdit.getText().toString().trim();
        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.goodsComment(Integer.parseInt(goodsId), remark, userId);
        requestInterface.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                //成功把留言加载comments里，并更新
                RoomBean.CommentInfo commentInfo = new RoomBean().new CommentInfo();
                commentInfo.setUserComments(remark);
                commentInfo.setUserPhone("");
                commentInfos.add(commentInfo);
                commentAdapter.notifyDataSetChanged();



            }
        });
    }
}
