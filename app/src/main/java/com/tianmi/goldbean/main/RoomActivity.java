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
import com.tianmi.goldbean.adapter.CommentAdapter;
import com.tianmi.goldbean.adapter.RoomPagerAdapter;
import com.tianmi.goldbean.my.MerchantInfoActivity;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.tianmi.goldbean.net.bean.RecyclerBean;
import com.tianmi.goldbean.net.bean.RoomBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import cn.sharesdk.framework.Platform;
//import cn.sharesdk.onekeyshare.OnekeyShare;
//import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
//import cn.sharesdk.wechat.friends.Wechat;
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
//        viewPager = (ViewPager)findViewById(R.id.viewPager) ;
//        adapter = new RoomPagerAdapter(list, this);
//        viewPager.setAdapter(adapter);

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
//                showShare();
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

//        private void showShare() {
//            OnekeyShare oks = new OnekeyShare();
//            //关闭sso授权
//            oks.disableSSOWhenAuthorize();
//
//            // title标题，微信、QQ和QQ空间等平台使用
//            oks.setTitle("捞金豆");
//            // titleUrl QQ和QQ空间跳转链接
//            oks.setTitleUrl("http://sharesdk.cn");
//            // text是分享文本，所有平台都需要这个字段
//            oks.setText("我是分享文本");
//            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//            oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//            // url在微信、微博，Facebook等平台中使用
//            oks.setUrl("http://sharesdk.cn");
//            // comment是我对这条分享的评论，仅在人人网使用
//            oks.setComment("我是测试评论文本");
//           oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
//               @Override
//               public void onShare(Platform platform, Platform.ShareParams shareParams) {
//                   if (Wechat.NAME.equals(platform.getName())) {
//                       Log.d("FC", platform.getName());
//                       shareParams.setShareType(Platform.SHARE_WEBPAGE);
//                       shareParams.setUrl("www.baidu.com");
//                       shareParams.setText("捞金豆");
//                       shareParams.setImageUrl("/sdcard/test.jpg");
//                       shareParams.setTitle("捞金豆哈哈哈");
//                   }
//
//               }
//           });
//            // 启动分享GUI
//            oks.show(this);
//
//    }
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
