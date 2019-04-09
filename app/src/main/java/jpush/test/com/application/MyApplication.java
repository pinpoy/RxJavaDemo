package jpush.test.com.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import jpush.test.com.service.BootService;

/**
 * @author: 徐鹏android
 * @Description:
 * @time: create at 2017/11/8 18:13
 */

public class MyApplication extends Application {
    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        Log.i("xupeng","MyApplication的oncreate");


        //开机的时候：开启后台常驻服务
//        Intent bootIntent = new Intent(this, BootService.class);
//        startService(bootIntent);
    }

    public static Context getContext() {
        return mContext;
    }


}
