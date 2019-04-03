package com.game.ziipin.badamsdkv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.abc.def.ghi.BadamUserListener;
import com.abc.def.ghi.InitListener;
import com.ziipin.pay.sdk.library.BadamSdk;
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
public class MainActivity extends Activity implements View.OnClickListener, BadamUserListener{

    BadamSdk sdk = (BadamSdk)BadamSdk.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.debug("onCreate");
        setContentView(R.layout.activity_main);

        // BadamSDK Activity初始化，只有初始化成功之后才能做其他的操作
        sdk.initActivity(this,  BaseApp.LANG, new InitListener() {
            @Override
            public void onInitResult(boolean success, int erro, String message) {
                Logger.debug( success ? "初始化成功" : "初始化失败" + message);
            }
        });

        findViewById(R.id.btn_sdk_enter).setOnClickListener(this);
        findViewById(R.id.btn_quick_login_sdk).setOnClickListener(this);
        findViewById(R.id.btn_pay).setOnClickListener(this);

    }

    @Override
    public void onUserResult(UserBean userBean, int errorCode, String message) {
        // 注意, 当前SDK版本号为311, 服务端登录校验地址请看: https://h5-doc.badambiz.com/chapter3-started/step1.html
        if(errorCode == 0){
            Logger.debug(userBean.toString());
            // 测试 add role
            sdk.addRole(this, BaseApp.mAppId, userBean.openid, "name", "area1", userBean.openid, 1);
        } else {
            Logger.debug(message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_switch_tv:
                sdk.toAccountSwitchActivity(this,this);
                break;
            case R.id.btn_sdk_enter:
                sdk.enterBadamSdk(this, this);//BadamSDK普通入口
                break;
            case R.id.btn_quick_login_sdk:
                sdk.quickLoginBadamSdk(this, this);//BadamSDK快速登录入口
                break;
            case R.id.btn_pay:
                //sdk.upateUserInfo(this,this);
                startActivity(new Intent(this, PayActivity.class));//BadamSDK支付Demo
//                loginByToken();
                break;
        }
    }

    @Override
    protected void onResume() {
        // 应用切换或者切换到后台之后，再切换游戏时显示悬浮窗
        sdk.showWindow(this, BaseApp.mAppId, this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 应用切换或者切换到后台之时，隐藏悬浮窗
        sdk.hideWindow(this);
    }

    @Override
    protected void onDestroy() {
        // 退出游戏或者杀死游戏进程时，隐藏悬浮窗
        sdk.hideWindow(this);
        super.onDestroy();
    }
}

