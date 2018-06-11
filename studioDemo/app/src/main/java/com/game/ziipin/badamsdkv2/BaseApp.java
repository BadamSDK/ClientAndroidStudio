package com.game.ziipin.badamsdkv2;

import android.app.Application;

import com.ziipin.pay.sdk.library.BadamSdk;
import com.ziipin.pay.sdk.library.common.BadamContant;
import com.ziipin.pay.sdk.library.utils.Logger;


public class BaseApp extends Application {
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

    @Override
    public void onCreate() {
        super.onCreate();
        // Logger.setEnable(true/false) 会启用/禁用 sdk 的日志信息, 调试阶段 设置为 true 可以看一些调试信息
        // 默认值为 false，即默认不打印 sdk 日志信息
        Logger.setEnable(true);
        BadamSdk.getInstance().init(this, mAppId, mApiSecret, LANG);
        // 仅单机需要接入闪屏广告，非单机请不要调用下面这一行，单机游戏调用下面这一行时, 同时需要参考 AndroidManitest.xml 中
        // 关于闪屏 activity 的配置信息
        // BadamSdk.getInstance().splashTo(MainActivity.class);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.debug("on Terminate");
    }
}
