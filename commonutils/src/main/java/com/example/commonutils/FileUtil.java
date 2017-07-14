package com.example.commonutils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

/**
 * @description: 文件工具类
 * @author:袁东华 created at 2016/9/28 10:50
 */
public class FileUtil {

    private static final String TAG = "FileUtil";
    private static String pathDiv = "/";


    private FileUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     *@description:删除文件的方法
     *@author:袁东华
     *@time:2016/12/26 下午6:49
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        try {
            File file = new File(sPath);
            // 路径为文件且不为空则进行删除
            if (file.isFile() && file.exists()) {
                LogUtil.e("缓存文件存在,删除数据库");
                file.delete();
                flag = true;
            }else{
                LogUtil.e("缓存文件不存在,不能删除数据库");
            }
        }catch (Exception e){
            LogUtil.e("删除缓存文件失败");
        }
        return flag;
    }

    /**
     * @description: 插入图片到相册
     * @author:袁东华
     * @time:2016/12/24 下午3:30
     */
    public static void insertImage2DCIM(Context context, String fileName) {
        try {
            File file1 = new File(FileUtil.getCacheFilePath(context, fileName));
            File folder = new File(Environment.getExternalStorageDirectory(), "DCIM/lenovo");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file2 = new File(folder, fileName + ".jpg");
            if (file2.exists()) {
                ToastUtil.getInstance().setText("保存到相册成功");
            } else {
                //复制本地图片到相册
                FileUtil.copyFileUsingFileChannels(file1,
                        file2);
//                MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                        file2.getAbsolutePath(), fileName + ".jpg", null);
                // 最后通知图库更新
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file2.getPath()))));
                ToastUtil.getInstance().setText("保存到相册成功");
            }
        } catch (IOException e) {
            LogUtil.e("异常:" + e.getMessage());
        }
    }

    /**
     * @description: 复制文件
     * @author:袁东华
     * @time:2016/12/16 下午3:34
     */
    public static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            if (inputChannel != null) {

                inputChannel.close();
            }
            if (outputChannel != null) {

                outputChannel.close();
            }
        }
    }

    /**
     * @description: 获取图片大小
     * @author:袁东华 created at 2016/10/5 14:06
     */
    public static String getFileSize(long size) {
        StringBuilder strSize = new StringBuilder();
        if (size < 1024) {
            strSize.append(size).append("B");
        } else if (size < 1024 * 1024) {
            strSize.append(size / 1024).append("K");
        } else {
            long kb = size - 1024 * 1024;
            LogUtil.e("kb:" + kb);
            double scale = size / 1024.0 / 1024.0;
            DecimalFormat d = new DecimalFormat("0.0");
            String length = d.format(scale);
            strSize.append(length).append("M");
        }
        return strSize.toString();
    }

    /**
     * 创建临时文件
     *
     * @param type 文件类型
     */
    public static File getTempFile(Context context, FileType type) {
        try {
            File cacheDir = !isExternalStorageWritable() ? context.getFilesDir() : context.getExternalCacheDir();
            File file = File.createTempFile(type.toString(), null, cacheDir);
            file.deleteOnExit();
            return file;
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * @description: 获取缓存文件地址
     * @author: 袁东华
     * created at 2016/10/6 11:35
     */
    public static String getCacheFilePath(Context context, String fileName) {
        File cacheDir = !isExternalStorageWritable() ? context.getFilesDir() : context.getExternalCacheDir();
        return cacheDir.getAbsolutePath() + pathDiv + fileName;
    }

    /**
     * @description: 判断缓存文件是否存在
     * @author:袁东华
     * @time:2016/12/24 下午2:19
     */
    public static boolean isCacheFileExist(Context context, String fileName) {
        File file = new File(getCacheFilePath(context, fileName));
        return file.exists() && file.length() > 0;
    }


    /**
     * 将图片存储为文件
     *
     * @param bitmap 图片
     */
    public static String createFile(Context context, Bitmap bitmap, String filename) {
        File cacheDir = !isExternalStorageWritable() ? context.getFilesDir() : context.getExternalCacheDir();
        File f = new File(cacheDir, filename);
        try {
            if (f.createNewFile()) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] bitmapdata = bos.toByteArray();
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "create bitmap file error" + e);
        }
        if (f.exists()) {
            return f.getAbsolutePath();
        }
        return null;
    }

    /**
     * 将数据存储为文件
     *
     * @param data 数据
     */
    public static void createFile(Context context, byte[] data, String filename) {
        File cacheDir = !isExternalStorageWritable() ? context.getFilesDir() : context.getExternalCacheDir();
        File f = new File(cacheDir, filename);
        try {
            if (f.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(data);
                fos.flush();
                fos.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "create file error" + e);
        }
    }


    /**
     * @description: 将数据存储为文件
     * @author: 袁东华
     * created at 2016/10/5 16:40
     */
    public static boolean createFile(Context context, byte[] data, String fileName, String type) {
        if (isExternalStorageWritable()) {
            //获取存储目录data/data/包名/files/Download
            File dir = context.getExternalFilesDir(type);
            if (dir != null) {
                File f = new File(dir, fileName);
                try {
                    if (f.createNewFile()) {
                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(data);
                        fos.flush();
                        fos.close();
                        return true;
                    }
                } catch (IOException e) {
                    LogUtil.e("保持文件失败:" + e);
                    return false;
                }
            }
        }
        return false;
    }


    /**
     * 从URI获取图片文件地址
     *
     * @param context 上下文
     * @param uri     文件uri
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getImageFilePath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String path = null;
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            if (!isMediaDocument(uri)) {
                try {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                } catch (IllegalArgumentException e) {
                    path = null;
                }
            }
        }
        if (path == null) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = ((Activity) context).managedQuery(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }

            path = null;
        }
        return path;
    }


    /**
     * @description: 从URI获取文件地址
     * @author:袁东华 created at 2016/10/5 15:31
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getFilePath(Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @description: 判断外部存储是否可用
     * @author: 袁东华
     * created at 2016/10/5 16:40
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        LogUtil.e("外部存储不可用");
        return false;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    public enum FileType {
        IMG,
        AUDIO,
        VIDEO,
        FILE,
    }


}
