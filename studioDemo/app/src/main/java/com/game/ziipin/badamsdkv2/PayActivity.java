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
    /**
     * 支付金额, 新疆地区也使用人民币支付, 单位为分, 传入时记得进行单位转换。
     * 关于商品价格的参考建议，请参考 <a href="https://sdk-doc.badambiz.com/chapter1-started/step3.3.html">商品价格设置</a><br />
     * 测试时有专用的测试通道(不花钱的）,最好传入超过2元（200分）的金额，否则一些支付选项由于支付限额不会显示。
     * 如果测试时没有看到微信和支付宝选项，此时最可能的问题是您运行的设备上没有安装 微信和支付宝。
     */
    private int mAmount;
    /**
     * 用户透传数据,可为空，后台校验充值回调时 user data 会参与加密校验，建议不要传为空
     */
    private String mUserData;
    /**
     * 订单ID，这里的订单ID为接入方游戏服务器中的商品ID，为了后续订单查询方便，请保证订单号在
     * CP 系统内唯一
     */
    private String mAppOrder;
    /**
     * 商品名，支付时显示给用户的名字
     */
    private String mGoodsName;
    private EditText mAmountEdit;
    /**
     * 用户登录SDK后获取到的用户 openid。
     * 需要注意的是，这里请一定要传入登录用户的 openid，否则后续用户投诉或咨询客服时
     * 客服无法快速定位用户订单，增加一些不必要的人为BUG
     */
    private String mOpenId;

    private BadamSdk sdk = (BadamSdk)BadamSdk.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // CP 兄弟, 开干前请花几秒钟看看头部的文本说明，会解决接入、支付测试过程中即将遇到的一些困扰
        setContentView(R.layout.activity_pay);
        mAmountEdit = (EditText) findViewById(R.id.amount);
        User user = AccountManager.getInstance().getUser();
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

    /**
     * 发起支付功能，这是用户可选择短信+现金支付方式(微信，支付宝，银联，QQ，京东支付等）。<br />
     * 强烈建议使用 pay 方法发起支付（即 pay 和 payCash 中只选用 pay, payCash 是满足部分 CP 的需求而添加的缩减版的支付入口）
     */
    public void onPay(View v) {
        // 支付
        if (updateMoney()){
            sdk.pay(this,mAppOrder,mAmount,mGoodsName,mUserData,mOpenId,this);
        }
    }

    /**
     * 强制用户使用现金支付（微信，支付宝，银联，QQ，京东支付等）。<br />
     * 使用 payCash 发起的支付，短信支付选项不会出现在支付选项中。非特殊情况建议使用 pay 而不是 payCash
     */
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
        // 支付时需要接收这一行为，必须传入
        sdk.onActivityResult(requestCode, resultCode, data);
        // 下面处理自己业务逻辑内的行为
    }
    // 下面三个回调为 sdk 处理 activity 生命周期内的资源初始化以及销毁，也请复写
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
