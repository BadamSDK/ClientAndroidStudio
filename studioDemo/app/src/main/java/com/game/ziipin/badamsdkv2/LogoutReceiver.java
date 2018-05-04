package com.game.ziipin.badamsdkv2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.ziipin.pay.sdk.library.common.BadamContant;
import com.ziipin.pay.sdk.library.utils.Logger;

/**
 * 作者:邱雷(Hanker) on 18/4/11 16:44.
 * Email:qiulei@ziipin.com
 */
public class LogoutReceiver extends BroadcastReceiver{

    public static final String ACTION_REQUEST = "com.ziipin.badam.sdk.logout";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (!TextUtils.isEmpty(action) && (action.equals(ACTION_REQUEST))) {
            boolean logoutSucceed = intent.getBooleanExtra(BadamContant.LOGOUT_SDK_INTENT_RESULT_KEY,false);
            Logger.debug("logoutSucceed = "+logoutSucceed);
            if(logoutSucceed){
                //todo 客户端做登出操作
            }
        }

    }
}
