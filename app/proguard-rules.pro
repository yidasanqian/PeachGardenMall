# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Library/Android/sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# banner
-keep class com.youth.banner.** {
    *;
}

# Retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# For GSON https://github.com/google/gson/blob/master/examples/android-proguard-example/proguard.cfg
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class me.dengfengdecao.happyhelp.datasource.domain.** {*;}
# -keep class com.google.gson.examples.android.model.** { *; } # 会被Gson序列化/反序列化的model

# okhttp3
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# okio
-dontwarn okio.**

# picasso
-dontwarn com.squareup.okhttp.**


# 支付宝
#-libraryjars libs/alipaySDK-20161129.jar
-dontwarn rx.internal.util.unsafe.**
-dontwarn android.net.SSLCertificateSocketFactory
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

# bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}