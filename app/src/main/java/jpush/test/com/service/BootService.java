package jpush.test.com.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import jpush.test.com.bean.ResultBean;
import jpush.test.com.rxjavademo.ApiService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 开机服务
 * Created by jesgoo on 2019/1/11.
 */

public class BootService extends Service {

    private TimerTask mTimerTask;
    private OkHttpClient client;
    private Retrofit retrofit;
    private String status;
    private String serverContent;
    private String ipContent;

    String ipPath = "/storage/emulated/0/TouchSprite/lua/ip.txt";
    String serverPath = "/storage/emulated/0/TouchSprite/lua/server.txt";
    private Retrofit leancloudRetrofit;
    private static int errorNum;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serverContent = readLocalFile(serverPath);
        ipContent = readLocalFile(ipPath);
        if (serverContent.isEmpty() || ipContent.isEmpty()) {
            return;
        }
        String url = serverContent + "/cap.manage/phone/cpa/";  //拼接url
        Log.i("xupeng", "BootService的onCreate()");
        status = "1";   //每次服务开启（重启开机为1）

        client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();

        leancloudRetrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://orhvf0bg.api.lncld.net/1.1/classes/")
                .build();


        if (null != retrofit) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    Log.i("xupeng", "TimerTask");
                    getSwitch();

                }
            };
        }


        new Timer(true).schedule(mTimerTask, 5 * 60 * 1000, 5 * 60 * 1000);//延时1s，每隔5分钟执行一次run方法

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 外部命令
     *
     * @param cmd
     * @return
     */
    private String doCommand(String cmd) {
        try {
            // Executes the command.
            Process process = Runtime.getRuntime().exec(cmd);

            // NOTE: You can write to stdin of the command using
            // process.getOutputStream().
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();

            // Waits for the command to finish.
            process.waitFor();

            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从涛涛服务器拿到result返回，是否重启
     */
    public void getSwitch() {
        retrofit.create(ApiService.class)
                .getChudong(getChudongMap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("xupeng", "onError");
                        reportPhoneInfo(-1);//-1代表返回error
                        errorNum++;
                        if (errorNum >= 2) {    //接口不通超过2次，重启机器
                            doCommand("reboot");
                        }

                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        if (null != resultBean) {
                            //上报LeanCloud
                            int result = resultBean.getResult();
                            reportPhoneInfo(result);

                        }
                    }
                });
    }

    /**
     * 利用LeanCloud云存储的api进行联网上报
     *
     * @param result
     */
    private void reportPhoneInfo(int result) {
        leancloudRetrofit.create(ApiService.class)
                .savephoneInfo(getJsonObject(result))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        status = "0";
                        if (result == 1)
                            //进行重启，重启后的以第一次status=1，之后默认为0
                            doCommand("reboot");
                    }

                    @Override
                    public void onNext(Object o) {
                        status = "0";
                        if (result == 1)
                            //进行重启，重启后的以第一次status=1，之后默认为0
                            doCommand("reboot");

                    }
                });
    }


    private String readLocalFile(String path) {
        String fileContent = "";
        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            int length = fileInputStream.available();
            byte[] buffer = new byte[length];
            fileInputStream.read(buffer);
            fileContent = new String(buffer, "UTF-8");
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;

    }


    public Map<String, String> getChudongMap() {
        Map<String, String> map = new HashMap<>();
        map.put("host", ipContent);
        map.put("status", status);


        return map;
    }

    public JsonObject getJsonObject(int result) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("host", ipContent);
        jsonObject.addProperty("status", status);
        jsonObject.addProperty("result", result);


        return jsonObject;
    }
}
