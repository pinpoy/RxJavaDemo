package jpush.test.com.rxjavademo;

/**
 * Created by jesgoo on 2019/3/9.
 */

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 自定义的，重试N次的拦截器
 * 通过：addInterceptor 设置
 */
public class Retry implements Interceptor {
    public int maxRetry;//最大重试次数
    private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    public Retry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Log.i("Retry", "num:" + retryNum);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            Log.i("Retry", "num:" + retryNum);
            response = chain.proceed(request);
        }
        return response;
    }
}
