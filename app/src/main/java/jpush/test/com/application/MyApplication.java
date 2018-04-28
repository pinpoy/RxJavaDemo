package jpush.test.com.application;

import android.app.Application;
import android.content.Context;

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
    }

    public static Context getContext() {
        return mContext;
    }


}
