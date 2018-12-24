package com.tianmi.goldbean.wxapi;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tianmi.goldbean.GoldApplication;
import com.tianmi.goldbean.Utils.DataUtil;
import com.tianmi.goldbean.config.Config;
import com.tianmi.goldbean.net.BaseRequest;
import com.tianmi.goldbean.net.JsonCallback;
import com.tianmi.goldbean.net.RequestInterface;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;


public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoldApplication.api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        SendAuth.Resp re = ((SendAuth.Resp) baseResp);
        String code = re.code;
        if(code != null){
            getOpenId(code);
        }
    }
    private void getOpenId(String code){
        String urlStr = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+Config.APP_ID+"&secret="+Config.APP_SECRECT+
                "&code="+code+"&grant_type=authorization_code";
        BaseRequest request = new BaseRequest();
        request.get(urlStr, this);
        request.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(o+"");
                    String openid = jsonObject.get("openid")+"";
                    DataUtil.putPreferences("openId", openid);
                    addOpenId(openid);
                    Toast.makeText(getApplicationContext(), openid, Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }


            }
        });
    }

    public void addOpenId(String opendId){
        RequestInterface requestInterface = new RequestInterface(this);
        requestInterface.bindWechat(opendId, Integer.parseInt(DataUtil.getPreferences("userId", "")));
        requestInterface.setCallback(new JsonCallback() {
            @Override
            public void onError(Request request, String e) {

            }

            @Override
            public void onResponse(Object o, String message) throws IOException {
                Toast.makeText(getApplicationContext(), "绑定成功，请点击提现", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
