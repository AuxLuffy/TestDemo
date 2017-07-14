package com.example.databasedemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.widget.FrameLayout;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {
    public static final int REQUEST_CODE = 100;
    private TextureView mTextureView1;
    private Camera mCamera;
    private TextureView myTexture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //如果没有对应的数据库对象，则创建；如果已经有了，则不重新创建，只返回此数据库对象的引用
//        DBHelper helper = DBHelper.getInstance();//注意：此句话没有创建数据库，只是有一个对象而已
//        SQLiteDatabase db = helper.getWritableDatabase();//此时才真正创建数据库
//        db.insert()
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//        getRequestedOrientation();
        checkPerm();

        mTextureView1 = (TextureView) findViewById(R.id.textureView1);

        myTexture = new TextureView(this);
        if (canCameraUse) {
            myTexture.setSurfaceTextureListener(this);
            setContentView(myTexture);
        }
    }

    private boolean canCameraUse;

    private void checkPerm() {
        canCameraUse = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                canCameraUse = false;
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size previewSize = parameters.getPreviewSize();
        Camera.Size pictureSize = parameters.getPictureSize();
        List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
        Log.e("TAG", "supportedPictureSixes.size:" + supportedPictureSizes.size());
        for (int i = 0; i < supportedPictureSizes.size(); i++) {
            Log.e("TAG", "picturesize(" + i + "):" + supportedPictureSizes.get(i).width + ", " + supportedPictureSizes.get(i).height + " (w,h)");
        }
        Log.e("TAG", "camera preview height:" + previewSize.height + ", width:" + previewSize.width);
        Log.e("TAG", "camera picture height:" + pictureSize.height + ", width:" + pictureSize.width);
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        Log.e("TAG", "w:" + widthPixels + ", h:" + heightPixels);
        myTexture.setLayoutParams(new FrameLayout.LayoutParams(previewSize.height, previewSize.width, Gravity.CENTER));
        try {
            mCamera.setPreviewTexture(surface);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
        myTexture.setAlpha(1.0f);
//        myTexture.setRotation(90.0f);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (Manifest.permission.CAMERA.equals(permission) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                canCameraUse = true;
                myTexture.setSurfaceTextureListener(this);
                setContentView(myTexture);
                break;
            }
        }
    }
}
