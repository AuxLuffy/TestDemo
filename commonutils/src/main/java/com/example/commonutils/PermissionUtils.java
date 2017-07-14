package com.example.commonutils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.lang.reflect.Method;

/**
 * Created by 彤 on 2016/5/20.
 */
public class PermissionUtils {
    /**
     * 申请拨打电话权限
     */
    public static boolean requestCallPermission(Activity context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, UIinterfaceCode.PERMISSION_CALL_REQUEST);
            return false;
        }
        return true;
    }

    /**
     * 申请定位权限
     */
    public static boolean requestLocationPermission(Activity context) {
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        try{
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, UIinterfaceCode.PERMISSION_LOCATION_REQUEST);
                return false;
            }
        }catch (Exception e){

        }

        return true;
    }

    //申请查看联系人权限
    public static boolean requestGetAccountsPermission(Activity contenxt) {
        if (ContextCompat.checkSelfPermission(contenxt, Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(contenxt, new String[]{Manifest.permission.GET_ACCOUNTS},
                    UIinterfaceCode.PERMISSION_GET_CONTACTS_REQUEST);
            return false;
        }
        return true;
    }

    /**
     * 申请照相机权限
     */
    public static boolean requestCameraPermission(Activity context) {
        boolean hasPermission = false;
//        if(Build.VERSION.SDK_INT >= 23){//Mashmallow以上的版本
//            if (isMeizu()) {
                hasPermission = cameraIsCanUse();
                if (hasPermission == false){
//                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, UIinterfaceCode.PERMISSION_CAMARA_REQUEST);
                    Toast.makeText(context,"请在应用管理中打开“相机”访问权限!", Toast.LENGTH_LONG).show();
                }
//            }else {
//                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, UIinterfaceCode.PERMISSION_CAMARA_REQUEST);
//                } else {
//                    hasPermission = true;
//                }
//            }
//        }else{//Mashmallow之前的版本
//            PackageManager packageManager = context.getPackageManager();
////            hasPermission = PackageManager.PERMISSION_GRANTED  == packageManager.checkPermission(Manifest.permission.CAMERA,context.getPackageName());
//            hasPermission = cameraIsCanUse();
//            Log.e("Mashmallow permission",hasPermission+"");
//
////            packageManager.per
//       if(!hasPermission)
//           DebugUtils.getInstance().dToast(context, R.string.com_lenovo_lsf_no_such_permission_camera);
//        }
//        Log.e("permission",hasPermission+"");
        return hasPermission;
    }


    public static boolean isMeizu(){
        return "Meizu".equals(android.os.Build.BRAND);
    }
    public  static boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
                mCamera = Camera.open();
                Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
                mCamera.setParameters(mParameters);
            } catch (Exception e) {
                isCanUse = false;
            }

            if (mCamera != null) {
                try {
                    mCamera.release();
                } catch (Exception e) {
                    e.printStackTrace();
                    return isCanUse;
                }
            }
             return isCanUse;
          }

    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }


    public static boolean requestPhoneStatePermission(Activity context) {
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        try{
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_PHONE_STATE}, UIinterfaceCode.PERMISSION_PHONE_STATE_REQUEST);
                return false;
            }
        }catch (Exception e){
                return false;
        }

        return true;
    }

    public static void requestMultiplePermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

    }

}
