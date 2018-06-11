package com.game.ziipin.badamsdkv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.abc.def.ghi.InitListener;
import com.abc.def.ghi.PayResultListener;
import com.abc.def.ghi.Utils;
import com.ziipin.pay.sdk.library.BadamSdk;
import com.ziipin.pay.sdk.library.modle.User;
import com.ziipin.pay.sdk.publish.common.AccountManager;

import java.util.Locale;

/**
 * 测试支付的 Demo， 需要注意的是：<ul>
 *     <li><b>如果后台申请应用后没有切换应用状态的话，支付时你会进入到测试通道，界面上只有三个按钮，每个按钮对应你后面得到的支付结果。此时的支付是模拟的，
 * 不需要实际支付 Money。在点击支付成功时后台也会给你们但服务器发送回调</b></li>
 * <li>只有在支付成功时支付后台才会给你们的服务器发送支付回调，所以在本 activity 中接收到支付结果为成功时请到你们的服务器核查支付结果</li>
 * </ul>
 * 作者:邱雷(Hanker) on 17/11/22 14:47.<br />
 * Email:qiulei@ziipin.com
 */

public class PayActivity extends Activity implements PayResultListener, InitListener{

    private int mAmount;
    private String mUserData;
    private String mAppOrder;
    private String mGoodsName;
    private EditText mAmountEdit;
    private String mOpenId;

    private BadamSdk sdk = (BadamSdk)BadamSdk.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        //mPayInterface = BadamSdk.getInstance();

        mAmountEdit = (EditText) findViewById(R.id.amount);
        User user = AccountManager.getInstance().getUserFromPrefList();
        if(user != null){
            mAmount = 300;
            mUserData = "test";
            mOpenId = user.openid;
            mAppOrder = "00000";
            mGoodsName = "斗地主";
        }else {
            Toast.makeText(this,"未登录，请先登录再支付！",Toast.LENGTH_LONG).show();
        }
        // 支付初始化
        sdk.initActivity(this, BaseApp.LANG, this);

    }

    @Override
    public void onInitResult(boolean succeed, int errorCode, String message) {
        Utils.showToast(PayActivity.this, (succeed ? "初始化成功" :
                "初始化失败：") + message);
    }

    private boolean updateMoney() {
        String text = mAmountEdit.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Utils.showToast(this, "请先输入支付金额");
            return false;
        }
        try {
            mAmount = Integer.parseInt(text);
            if (mAmount <= 0) throw new RuntimeException();
        } catch (Exception e) {
            Utils.showToast(this, "请输入大于0的金额");
            return false;
        }
        mAppOrder = "Test_" + System.currentTimeMillis();
        return true;
    }


    public void onPay(View v) {
        // 支付
        if (updateMoney()){
            sdk.pay(this,mAppOrder,mAmount,mGoodsName,mUserData,mOpenId,this);
        }
    }


    public void onPayCash(View v) {
        // 支付现金
        if (updateMoney()){
            sdk.payCash(this, mAppOrder, mAmount, mGoodsName, mUserData, mOpenId,this);
        }
    }

    @Override
    public void onPayResult(String orderId, int resultCode, int errorCode, String message) {
        String text = String.format(Locale.CHINESE, "%d - %s - %s", errorCode, message, orderId);
        Utils.showToast(this, text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        sdk.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sdk.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sdk.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sdk.onDestroy(this);
    }
}
