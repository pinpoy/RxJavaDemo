package jpush.test.com.application;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;

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

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"7J6exxS1tvNrkMW8jkMfCGRt-gzGzoHsz","4hKKFfaTvPASWEak9lDhl79d");
        AVOSCloud.setDebugLogEnabled(true);
    }

    public static Context getContext() {
        return mContext;
    }


}
