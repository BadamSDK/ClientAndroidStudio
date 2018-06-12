package com.game.ziipin.badamsdkv2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.ziipin.pay.sdk.library.common.BadamContant;
import com.ziipin.pay.sdk.library.utils.Logger;

/**
 * 用户切换账户时的回调通知<br />
 * 作者:邱雷(Hanker) on 18/4/11 16:44.<br />
 * Email:qiulei@ziipin.com
 */
public class LogoutReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if(TextUtils.equals(BadamContant.ACTION_LOGOUT_BADAM, intent.getAction())){
            boolean logoutSucceed = intent.getBooleanExtra(BadamContant.LOGOUT_SDK_INTENT_RESULT_KEY,false);
            Logger.debug("logoutSucceed = "+logoutSucceed);
            if(logoutSucceed){
                //todo 客户端做登出操作
            }
        }
    }
}
