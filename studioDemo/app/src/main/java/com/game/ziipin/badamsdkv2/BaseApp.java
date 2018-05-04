package com.game.ziipin.badamsdkv2;

import android.app.Application;

import com.ziipin.pay.sdk.library.BadamSdk;
import com.ziipin.pay.sdk.library.utils.Logger;


public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BadamSdk.getInstance().initApplication(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.debug("on Terminate");
    }
}
