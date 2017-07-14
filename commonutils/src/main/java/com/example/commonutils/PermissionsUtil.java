package com.example.commonutils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 权限工具类
 * @author: 袁东华
 * created at 2016/11/24 14:31
 */
public class PermissionsUtil {
    private final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    /**
     * @description: 请求操作sd卡权限
     * @author:袁东华 created at 2016/10/4 19:32
     */
    public boolean requestStorage(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    /**
     * @description: 获取语音权限
     * @author: 袁东华
     * created at 2016/11/24 14:36
     */
    public boolean requestAudio(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    /**
     * @description: 获取视频权限
     * @author: 袁东华
     * created at 2016/11/24 14:36
     */
    public boolean requestVideo(Activity activity) {
        if (afterM()) {
            final List<String> permissionsList = new ArrayList<>();
            if ((activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.CAMERA);
            if ((activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.RECORD_AUDIO);
            if (permissionsList.size() != 0) {
                activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
            int hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    /**
     * @description: 请求相机权限
     * @author:袁东华 created at 2016/10/4 19:30
     */
    public boolean requestCamera(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    /**
     * @description: 比较版本
     * @author:袁东华 created at 2016/10/4 19:30
     */
    private boolean afterM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    private static PermissionsUtil instance;

    private PermissionsUtil() {
    }

    public static PermissionsUtil getInstance() {
        if (instance == null) {
            synchronized (PermissionsUtil.class) {
                if (instance == null) {
                    instance = new PermissionsUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 录音功能是否可用
     *
     * @return
     */
    public boolean voiceIsCanUse() {
        try {
            AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC, 22050, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, AudioRecord.getMinBufferSize(22050, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT));
            record.startRecording();
            int recordingState = record.getRecordingState();
            if (recordingState == AudioRecord.RECORDSTATE_STOPPED) {
                return false;
            }
            record.release();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMeizu() {
        return "Meizu".equals(android.os.Build.BRAND);
    }

    public boolean cameraIsCanUse() {
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
}
