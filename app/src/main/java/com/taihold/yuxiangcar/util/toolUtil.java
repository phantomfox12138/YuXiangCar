package com.taihold.yuxiangcar.util;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * Created by jxy on 2018/4/3.
 */

public class toolUtil {

    private static  String TAG="toolUtil";
    public static String APP_CACAHE_DIRNAME = "yuxiangview";
    public static void clearWebCache(Context context)
    {
        try
        {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        File appCacheDir = new File(context.getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
        File webviewCacheDir = new File(context.getCacheDir().getAbsolutePath()
                + "/webviewCache");
              Log.e(TAG, ""+webviewCacheDir.getAbsolutePath());
        //删除webview 缓存目录
        if (webviewCacheDir.exists())
        {
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists())
        {
            deleteFile(appCacheDir);
        }
    }
    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    private static void deleteFile(File file)
    {
        if (file.exists())
        {
            if (file.isFile())
            {
                file.delete();
            }
            else if (file.isDirectory())
            {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++)
                {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
        else
        {
        }
    }

}
