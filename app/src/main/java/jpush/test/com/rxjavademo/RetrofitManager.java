package jpush.test.com.rxjavademo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jesgoo on 2019/3/8.
 */

public class RetrofitManager {
    private static RetrofitManager manager;
    private static OkHttpClient client;
    private static long DEFAULT_TIMEOUT = 5 * 1000;


    public static RetrofitManager getManager() {
        if (manager == null) {
            synchronized (RetrofitManager.class) {
                if (manager == null) {
                    manager = new RetrofitManager();
                }
            }
        }
        return manager;
    }


    public static Retrofit getRetrofit(String baseUrl) {
        if (null == client)
            client = new OkHttpClient.Builder()
                    .addInterceptor(new Retry(3))
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();


        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)      //"https://orhvf0bg.api.lncld.net/1.1/classes/"
                .build();

    }
}
