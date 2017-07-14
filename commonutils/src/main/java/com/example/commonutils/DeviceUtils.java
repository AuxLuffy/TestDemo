package com.example.commonutils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebView;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by 彤 on 2016/4/28.
 */
public class DeviceUtils {
    /**
     * 当前为wifi状态
     */
    public static final int WIFI = 1;
    /**
     * 当前为手机网络状态
     */
    private static final int MOBILE = 2;
    /**
     * 当前为无网状态（其他状态）
     */
    private static final int UNCONNECTED = 0;
    private static long lastClickTime = 300;


    /**
     * 获取设备ID
     */
    public static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getDeclaredMethod("get", String.class);
            method.setAccessible(true);
            serial = (String) method.invoke(clazz, "ro.build.sys.sn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    /**
     * 获取设备SN号码
     */
    public static String getAndroidOsSystemProperties() {
        String ret = "";
        try {
            Method systemProperties_get = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
            /*ret = (String) systemProperties_get.invoke(null, "ro.serialno");
            if(!TextUtils.isEmpty(ret)){
                return ret;
            }*/
            ret = (String) systemProperties_get.invoke(null, "ro.boot.serialno");
            if (!TextUtils.isEmpty(ret)) {
                return ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 300) {
            return true;
        }
        lastClickTime = time;
        return false;

    }


    public static boolean isTablet(Context context) {
        WebView webView = new WebView(context);
        String userAgentString = webView.getSettings().getUserAgentString();
        webView = null;
        if (userAgentString.contains("Android") && userAgentString.contains("Mobile"))
            return false;
        return true;
    }

    /**
     * 获取设备屏幕像素密度
     */
    public static float getDensity(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }

    /**
     * 获取设备屏幕像素密度
     */
    public static int getMesure(int which, Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        if (which == 0) {
            return metrics.widthPixels;
        } else if (which == 1) {

            return metrics.heightPixels;
        }
        return 0;
    }

    /**
     * 获取网络状态
     */
    public static boolean isNetAvalible(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 网络加载前调用
     */
    public static void checkNetState(Context context) {
        if (!isNetAvalible(context)) {
            DebugUtils.getInstance().dToast(context, "网络错误");

            return;
        }
    }

    /**
     * 拨打电话
     */
    public static void dialPhone(Context context, String number) {
        PackageManager pm = context.getPackageManager();
        // 获取是否支持电话
        boolean telephony = pm
                .hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
        if (telephony) {
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                Uri uri = Uri.parse("tel:" + number);
                intent.setData(uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                ToastUtil.getInstance().setText("此设备不支持拨打电话");
//                DebugUtils.getInstance().dToast(context, "此设备不支持拨打电话!");
            }
        } else {
            ToastUtil.getInstance().setText("此设备不支持拨打电话");
//            DebugUtils.getInstance().dToast(context, "此设备不支持拨打电话!");
        }


    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static String getIMEI(Context context) {
        String imei = "";
        if (PermissionUtils.requestPhoneStatePermission((Activity) context)) {
            LogUtil.e("获取手机状态权限");
//            return "";
            imei = ((TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE)).getDeviceId();
        } else {
            LogUtil.e("没有获取手机状态权限");
            return "";
        }

        if (TextUtils.isEmpty(imei)) {
            String mac = MacUtil.getInstance().getMacAddressByNetworkInterface();
            LogUtil.e("mac:" + mac);
            imei = mac;
        }
        LogUtil.e("imei:" + imei);
        return imei;
    }

    /**
     * 屏幕是否被锁.如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
     *
     * @param context
     * @return
     */
    public static boolean isLocked(Context context) {
        PowerManager
                pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        boolean isScreenOn = pm.isScreenOn();
        return isScreenOn;
    }

    /**
     * 判断当前应用程序前后台
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {


        android.app.ActivityManager activityManager = (android.app.ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (android.app.ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 获取网络状态
     *
     * @return one of {@link DeviceUtils#WIFI}, {@link
     * DeviceUtils#MOBILE}, {@link DeviceUtils#UNCONNECTED}
     */
    public static int getAvalibleNetWorkState(Context context) {
        if (context != null) {
            ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = conn.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                String typeName = activeNetworkInfo.getTypeName();//"WIFI" or "MOBILE"
                if ("WIFI".equals(typeName)) {
                    return WIFI;
                } else if ("MOBILE".equals(typeName)) {
                    return MOBILE;
                }
            }
        }
        return UNCONNECTED;
    }

    /**
     * 判断位置信息是否可用，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
        /*
        //以下方法只用来确定GPS是否可用，不是AGPS
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        Class secureClass = null;
        try {
            secureClass = cl.loadClass("android.provider.Settings$Secure");
            Method isMethod = secureClass.getMethod("isLocationProviderEnabled",
                    ContentResolver.class, String.class);
            Boolean ret = (Boolean) isMethod.invoke(secureClass, context
                    .getContentResolver(), "gps");
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }*/

    }

    /**
     * GPS是否可用, 即位置信息模式为高精度模式
     *
     * @param context
     * @return
     */
    public static boolean isGPSEnable(Context context) {
        /* 用Setting.System来读取也可以，只是这是更旧的用法
        String str = Settings.System.getString(getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		*/
        String str = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        LogUtil.v("GPS:" + str);
        if (str != null) {
            return str.contains("gps");
        } else {
            return false;
        }
    }

    /**
     * 强制帮用户打开GPS
     * 只有低版本可用：API高版本的自动打开GPS为系统应用权限，非系统应用不能强制打开GPS
     *
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}
