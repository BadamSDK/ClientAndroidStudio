# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/artzok/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-verbose
-dontwarn
-dontshrink
-dontoptimize
-dontpreverify
-ignorewarning
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers

-keepattributes Signature
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keepattributes Exceptions,InnerClasses
-keepattributes SourceFile,LineNumberTable
-keepattributes Exceptions,InnerClasses,Signature
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#公共
-keep class **.R$*{*; }
-keep class **.R {*;}
-keep class * extends android.os.Parcelable {*;}
-keep public class * extends android.app.Activity
-keep public class * extends android.widget.TextView
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keepclassmembers enum  * {*;}
-keepclasseswithmembers,allowshrinking class * { native <methods>; }
-keepclasseswithmembers,allowshrinking class * {
    public <init>(android.content.Context,android.util.AttributeSet);
}
-keepclasseswithmembers,allowshrinking class * {
    public <init>(android.content.Context,android.util.AttributeSet,int);
}

# 接口和java bean
-keep class com.abc.def.ghi.**{*;}
-keep class com.ziipin.pay.sdk.library.BadamSdk {*;}
-keep class com.ziipin.pay.sdk.library.common.**{*;}
-keep class com.ziipin.pay.sdk.library.modle.**{*;}
-keep class com.ziipin.pay.sdk.library.nodoubleclick.**{*;}
-keep class com.ziipin.pay.sdk.library.ui.**{*;}
-keep class com.ziipin.pay.sdk.library.utils.**{*;}
-keep class com.ziipin.pay.sdk.library.widget.**{*;}
-keep interface com.ziipin.pay.sdk.library.PayListener {*;}
-keep class com.ziipin.pay.sdk.publish.PayListenerImpl{*;}
-keep class com.ziipin.pay.sdk.publish.api.model.** {*;}
-keep class com.ziipin.pay.sdk.publish.inner.bean.**{*;}
-keep class com.ziipin.pay.sdk.publish.inner.sdkprocessor.**{*;}
-keep class com.ziipin.pay.sdk.publish.common.**{*;}

# 广告相关
-keep class com.ziipin.pay.sdk.publish.AdFragment
-keep class com.ziipin.pay.sdk.publish.api.event.**{*;}
-keep class com.ziipin.pay.sdk.publish.ApkCreator {*;}
-keep class com.ziipin.pay.sdk.publish.ApkModel {*;}


#话费
-keep class com.arcsoft.hpay100.**{*;}

#爱贝
-keep class cn.gov.pbc.tsm.client.mobile.android.bank.service.** {*;}
-keep class com.iapppay.**{*;}
-keep class com.ta.utdid2.** {*;}
-keep class com.UCMobile.PayPlugin.** {*;}
-keep class com.ut.device.** {*;}

#掌支付
-keep class com.heepay.plugin.**{*;}
-keep class com.tbat.sdk.**{*;}
-keep class com.zwxpay.android.h5_library.**{*;}
-keep class com.sun.crypto.provider.** {*;}
-keep class com.third.wa5.sdk.** {*;}
#彩趣
-keep class com.payh5.bbnpay.mobile.activity.** {*;}

#支付宝
-keep class com.alipay.** {*;}
-keep class org.json.alipay.** {*;}

#银联
-keep class com.unionpay.** {*;}

#retrofit
-keep class retrofit2.**{*;}

#gson
-keep class com.google.**{*;}

#okhttp
-keep class okhttp3.**{*;}

#okio
-keep class okio.**{*;}

#tbs
-keep class com.tencent.**{*;}

#apache
-keep class org.apache.**{*;}

#大麦
-keep class com.android.zaLY.**{*;}
#禁止混淆js_bind
-keepnames class com.ziipin.pay.sdk.library.WebPayActivity$* {
    public <fields>;
    public <methods>;
}