package com.game.ziipin.badamsdkv2;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.abc.def.ghi.InitListener;
import com.ziipin.pay.sdk.library.BadamSdk;
import com.ziipin.pay.sdk.library.common.BadamContant;
import com.ziipin.pay.sdk.library.modle.UserBean;
import com.ziipin.pay.sdk.library.utils.Logger;

/**
 * BadamSDK Demo展示页面， 包含了SDK普通入口、快速登录入口和支付测试入口，分别对应三个按钮；
 * 当前中还展示了SDK初始化、调用SDK、Activity生命周期等整个流程，CP厂商在接入的时候直接参考就行了；
 * 需要注意的是：<ul>
 *     <li><b>在做SDK Activity初始化时，一定要先做SDK Application初始化</b></li>
 *     <li><b>这里将支付和更新角色全部单独写到了另外的Activity中，只是为了让代码逻辑更简单明了，CP在接入的时候通常都是放到一个Activity中的</b></li>
 * * </ul>
 * 作者:邱雷(Hanker) on 17/11/22 14:47.<br />
 * Email:qiulei@ziipin.com
 */
public class MainActivity extends Activity implements View.OnClickListener{

    BadamSdk sdk = (BadamSdk)BadamSdk.getInstance();

    LogoutReceiver logoutReceiver = new LogoutReceiver();
    IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.debug("onCreate");
        setContentView(R.layout.activity_main);

        /**
         * BadamSDK Activity初始化，只有初始化成功之后才能做其他的操作
         */
        sdk.initActivity(this, BaseApp.LANG, new InitListener() {
            @Override
            public void onInitResult(boolean success, int erro, String message) {
                Logger.debug( success ? "初始化成功" : "初始化失败" + message);
            }
        });


        findViewById(R.id.btn_sdk_enter).setOnClickListener(this);
        findViewById(R.id.btn_quick_login_sdk).setOnClickListener(this);
        findViewById(R.id.btn_pay).setOnClickListener(this);

        /**
         * 广播接收器，切换账号的时候用到
         */
        intentFilter.addAction(BadamContant.ACTION_LOGOUT_BADAM);
        registerReceiver(logoutReceiver,intentFilter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sdk_enter:
                sdk.enterBadamSdk(this);//BadamSDK普通入口
                break;
            case R.id.btn_quick_login_sdk:
                sdk.quickLoginBadamSdk(this);//BadamSDK快速登录入口
                break;
            case R.id.btn_pay:
                startActivity(new Intent(this, PayActivity.class));//BadamSDK支付Demo
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 接收BadamSDK回调，在这里接收UserBean，不为null则表示有回调
         */
        Logger.debug( "MainActivity  requestCode="+requestCode+"   resultCode="+resultCode+"  data = "+data);
        if(data == null){
            return;
        }

        UserBean userBean = null;
        /**
         * 下面是SDK各种情况下的页面跳转判断，CP在接入的时候不用管，只需要将下面代码全部粘贴过去就好了
         */
        switch (resultCode){
            case BadamContant.SDK_ENTER_RESULT_CODE:
                userBean = (UserBean)data.getSerializableExtra(BadamContant.SDK_ENTER_INTENT_RESULT_KEY);
                break;

            case BadamContant.ACCOUNT_LIST_LOGIN_RESULT_CODE:
                userBean = (UserBean)data.getSerializableExtra(BadamContant.ACCOUNT_LIST_LOGIN_INTENT_RESULT_KEY);
                break;

            case BadamContant.QUICK_LOGIN_SDK_RESULT_CODE:
                userBean = (UserBean)data.getSerializableExtra(BadamContant.QUICK_LOGIN_SDK_INTENT_RESULT_KEY);

                /**
                 * 跳转到角色更新Demo,CP在接入的时候这一部分代码可以删除调，再续约更新角色的地方更新角色接口 BadamSdk.getInstance()updateRoleInfo(UserReq userReq);
                 */
                Intent intent = new Intent(MainActivity.this,UpdateRoleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", userBean);
                intent.putExtras(bundle);
                startActivity(intent);

                break;

            case BadamContant.ACCOUNT_REGISTER_RESULT_CODE:
                userBean = (UserBean)data.getSerializableExtra(BadamContant.ACCOUNT_REGISTER_INTENT_RESULT_KEY);
                break;

            case BadamContant.ACCOUNT_LOGIN_RESULT_CODE:
                userBean = (UserBean)data.getSerializableExtra(BadamContant.ACCOUNT_LOGIN_INTENT_RESULT_KEY);
                break;

            case BadamContant.PHONE_REGISTER_RESULT_CODE:
                userBean = (UserBean)data.getSerializableExtra(BadamContant.PHONE_REGISTER_INTENT_RESULT_KEY);
                break;

            case BadamContant.PHONE_LOGIN_RESULT_CODE:
                userBean = (UserBean)data.getSerializableExtra(BadamContant.PHONE_LOGIN_INTENT_RESULT_KEY);
                break;

        }

        /**
         * 最后再这里判断UserBean，如果不为null则显示悬浮窗和进入游戏主页
         */
        Logger.debug( "userBean= "+userBean);
        if(userBean != null){
            sdk.showWindow(this);
        }


    }

    @Override
    protected void onResume() {
        /**
         * 应用切换或者切换到后台之后，再切换游戏时显示悬浮窗
         */
        sdk.showWindow(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /**
         * 应用切换或者切换到后台之时，隐藏悬浮窗
         */
        sdk.hideWindow(this);
    }

    @Override
    protected void onDestroy() {
        /**
         * 退出游戏或者杀死游戏进程时，隐藏悬浮窗
         */
        sdk.hideWindow(this);

        unregisterReceiver(logoutReceiver);//释放切换账号广播
        super.onDestroy();
    }
}

