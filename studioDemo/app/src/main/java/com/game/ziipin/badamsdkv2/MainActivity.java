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


public class MainActivity extends Activity implements View.OnClickListener{
    private UserBean mUserBean;
    /**
     * 应用 APP ID，请到 巴达木支付后台管理界面去申请（需要登录） or 联系商务，一般你看到这里到时候商务应该已经有账号了的<br />
     * 这里的{@link BadamContant#EXTRA_APP_ID} 为测试账户，支持全部功能，BUT，如果你现金支付的话是不计入你们的账户的，所以在测试完成后记得切换到你们申请的账户上。
     */
    public static final String mAppId = BadamContant.EXTRA_APP_ID;
    /**
     * 通信的 加密字符串，同 {@link #mAppId}
     */
    public static final String mApiSecret = BadamContant.APISECRET;

    public static final int LANG = BadamContant.LANG_TYPE_ZH;

    BadamSdk sdk = (BadamSdk)BadamSdk.getInstance();

    LogoutReceiver logoutReceiver = new LogoutReceiver();
    IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.debug("onCreate");
        setContentView(R.layout.activity_main);

        sdk.initActivity(this, mAppId, mApiSecret, LANG, new InitListener() {
            @Override
            public void onInitResult(boolean success, int erro, String message) {
                Logger.debug( success ? "初始化成功" : "初始化失败" + message);
            }
        });
        findViewById(R.id.btn_sdk_enter).setOnClickListener(this);
        findViewById(R.id.btn_quick_login_sdk).setOnClickListener(this);
        findViewById(R.id.btn_pay).setOnClickListener(this);

        intentFilter.addAction(LogoutReceiver.ACTION_REQUEST);
        registerReceiver(logoutReceiver,intentFilter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sdk_enter:
                sdk.enterBadamSdk(this);
                break;
            case R.id.btn_quick_login_sdk:
                sdk.quickLoginBadamSdk(this);
                break;
            case R.id.btn_pay:
                startActivity(new Intent(this, PayActivity.class));
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.debug( "MainActivity  requestCode="+requestCode+"   resultCode="+resultCode+"  data = "+data);
        if(data == null){
            return;
        }

        UserBean userBean = null;
        switch (resultCode){
            case BadamContant.SDK_ENTER_RESULT_CODE:
                userBean = (UserBean)data.getSerializableExtra(BadamContant.SDK_ENTER_INTENT_RESULT_KEY);
                break;

            case BadamContant.ACCOUNT_LIST_LOGIN_RESULT_CODE:
                userBean = (UserBean)data.getSerializableExtra(BadamContant.ACCOUNT_LIST_LOGIN_INTENT_RESULT_KEY);
                break;

            case BadamContant.QUICK_LOGIN_SDK_RESULT_CODE:
                userBean = (UserBean)data.getSerializableExtra(BadamContant.QUICK_LOGIN_SDK_INTENT_RESULT_KEY);

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

        Logger.debug( "userBean= "+userBean);
        if(userBean != null){
            mUserBean = userBean;
            Logger.debug("onActivityResult");
            sdk.showWindow(this);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        //Logger.error("token:" + findViewById(R.id.btn_sdk_enter).getWindowToken());
        sdk.showWindow(this);
        //Logger.debug("onResume, hasUser=" + (mUserBean != null));

    }

    @Override
    protected void onPause() {
        super.onPause();
        sdk.hideWindow(this);
    }

    @Override
    protected void onDestroy() {
        sdk.hideWindow(this);
        Logger.debug("onDestroy");
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }
}

