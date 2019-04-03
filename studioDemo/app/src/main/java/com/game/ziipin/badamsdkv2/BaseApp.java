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
    private static final String mApiSecret = BadamContant.APISECRET;
    /**
     * 语言使用, 这里使用的是维语，其他语言如下：
     * {@link BadamContant#LANG_TYPE_ZH} 中文(默认）
     * {@link BadamContant#LANG_TYPE_IR} 伊朗波斯语
     * {@link BadamContant#LANG_TYPE_HE} 中国新疆 哈萨克语
     * {@link BadamContant#LANG_TYPE_EN} 英文(当前实际上是中文）
     *
     * 建议：测试阶段使用中文(毕竟维语🐜文没几个人看得懂), 测试OK后使用对应都目标语言, 一般为维语
     */
    public static final int LANG = BadamContant.LANG_TYPE_ZH;

    public static final String APP_ID = "ee1884a9ec0f7f50a7e39636c0d7106f"; // 添加 APp, 使用时请替换为自己的 appId

    public static final String APP_SECRET = "cf9cab9b922b43dc26252ac25b42829b";

    @Override
    public void onCreate() {
        super.onCreate();
        // Logger.setEnable(true/false) 会启用/禁用 sdk 的日志信息, 调试阶段 设置为 true 可以看一些调试信息
        // 默认值为 false，即默认不打印 sdk 日志信息
        Logger.setEnable(true);
        BadamSdk.getInstance().initApplication(this, mAppId, mApiSecret);
    }
}
