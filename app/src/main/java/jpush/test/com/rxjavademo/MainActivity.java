package jpush.test.com.rxjavademo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jpush.test.com.R;
import jpush.test.com.activity.GreenDaoActivity;
import jpush.test.com.activity.NotificaActivity;
import jpush.test.com.activity.WebviewActivity;
import jpush.test.com.bean.Poetry;
import jpush.test.com.presenter.MainPresenter;
import jpush.test.com.utils.Md5Utils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv_permission)
    TextView tvPermission;
    private StringBuffer sb = new StringBuffer();
    // Camera Permissions
    public static final int REQUEST_TAKE_PHOTO = 0x002;
    private static String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // Phone Permissions
    public static final int REQUEST_PHONE_STATE = 0x003;
    private static String[] PERMISSIONS_PHONE = {
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE};
    @Inject
    Poetry poetry;
    @Inject
    Gson gson;
    @Inject
    MainPresenter mainPresenter;
    private static String TAG = "RetrofitManager";
    private OkHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        openPermission();   //开启相机权限
//
//        DaggerMainComponent.builder()
//                .mainModule(new MainModule())
//                .poetryModule(new PoetryModule())
//                .build()
//                .inject(this);
//
//
//        addInterceptors();//添加拦截器---打印日志
//
//
//        //上传设备信息，和外网ip
//        SystemUtil.getNetIP();
//        //收集信息
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                BaiduApiForLeancloud();
//            }
//        }, 5000);


    }







    /**
     * OkHttp3添加拦截器的方法
     */
    private void addInterceptors() {
        client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    private void requesFromPresenter() {
        tv2.setText(poetry.getPemo());
        //mainPresenter.loadData();
        //tv2.setText(gson.toJson(poetry.getPemo()));
    }

    private void openPermission() {
        //RxBinding和RxPermissions联合使用,开启相机的权限
        RxView.clicks(tvPermission)
                .compose(new RxPermissions(this).ensure(Manifest.permission.CAMERA))
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
                            startActivityForResult(intent, 1);
                        } else {
                            // 未获取权限
                            Toast.makeText(MainActivity.this, "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
//                            startActivityForResult(intent, 1);
                            //ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_CAMERA, REQUEST_TAKE_PHOTO);
                        }
                    }
                });
    }

    private static final Intent sSettingsIntent =
            new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);

    @OnClick({R.id.tv1, R.id.clear, R.id.tv_download, R.id.tv_greendao, R.id.tv_sha_256,
            R.id.tv_request_xml, R.id.tv_open_webview, R.id.tv_notification})
    void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.tv1:

                //rxJavaMethod();
                //rxJavaMethod2();
                //rxJavaMethod3();
                //rxJavaMethod4();
//                rxJavaMethod5();
//                rxJavaMethod7();

//                String s = XAdSDKFoundationFacade.getInstance().getCommonUtils().md5("d4:b5:d8:3a:0d:a4" + "&" + "861883248170984" + "&" + "&");

                //639cda89a81e16091bd306efd114c251  真实
                //639cda89a81e16091bd306efd114c251


//                Intent globalService = new Intent(this, GlobalTouchService.class);
//                startService(globalService);


//                startActivityForResult(sSettingsIntent, 0x123);

//                String s = doCommand("reboot");
//                Log.i("xupeng",s);
//
//                tv2.setText(s);



                break;
            case R.id.clear:
//                sb.setLength(0);
//                tv2.setText(" ");
                //requestData();
//                requesFromPresenter();  //从presenter请求网络获取数据

                String str = new String(Base64.decode("aHR0cDovL2FwaS5reGNvbnRyb2wuY29tOjY2Ni92MS9jb25maWc=", 0));
                Log.d(TAG, "main: " + str);
                break;

            case R.id.tv_download:      //开启多线程下载
//                startActivity(new Intent(this, ThreadDownActivity.class));

//                String s1 = Hello.helloJni();
//                Toast.makeText(this, s1, Toast.LENGTH_SHORT).show();

                String s1 = Java2CJNI.java2c();
                Toast.makeText(this, s1, Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_greendao:      //GreenDao
//               startActivity(new Intent(this));
                startActivity(new Intent(this, GreenDaoActivity.class));
                break;

            case R.id.tv_sha_256:      //加密SHA-256
                try {
                    String message = "我爱世界杯";
                    byte[] input = message.getBytes();
                    MessageDigest sha = MessageDigest.getInstance("SHA-256");
                    sha.update(input);

                    byte[] output = sha.digest();
                    String result = Base64.encodeToString(output, Base64.DEFAULT);
                    Log.i("Mainactivity", result);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_request_xml:       //网络请求返回xml格式
                requestToXml();
                break;
            case R.id.tv_open_webview:       //网络请求返回xml格式
                startActivity(new Intent(this, WebviewActivity.class));
                break;
            case R.id.tv_notification:       //通知栏的开发
                startActivity(new Intent(this, NotificaActivity.class));
                break;


        }
    }


    private void rxJavaMethod() {
        observable.subscribe(observe);
    }


    //创建观察者
    Observer<String> observe = new Observer<String>() {
        @Override
        public void onCompleted() {
            //sb.append("onCompleted");
            //tv2.setText(sb.toString());
            Log.i("onCompleted", "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            //tv2.setText("onError");
            Log.i("onError", "onError");
        }

        @Override
        public void onNext(String s) {
            //sb.append(s);
            //tv2.setText("s/n");
            Log.i("onNext", s);
        }
    };

    //創建被观察者
//        Observable observable = Observable.create(new Observable.OnSubscribe() {
//            @Override
//            public void call(Object o) {
//                observe.onNext("王");
//                observe.onNext("小");
//                observe.onNext("东");
//                observe.onCompleted();
//            }
//        });


    /**
     * =======================================================================================
     */

    String[] content = {"我是", "字符串", "数组"};
    //    Observable<String> observable = Observable.just("hello", "你", "好");
    Observable<String> observable = Observable.from(content);

    Action1<String> action1 = new Action1<String>() {
        @Override
        public void call(String s) {
            Log.i("Action1", s);
        }
    };
    Action1<Throwable> onError = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            Log.i("onError", "onError");
        }
    };
    Action0 action0 = new Action0() {
        @Override
        public void call() {
            Log.i("action0", "action0");
        }
    };


    private void rxJavaMethod2() {
        observable.subscribe(action1, onError, action0);
    }

    /**
     * =======================================================================================
     */
    private void rxJavaMethod3() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())   //在子线程订阅,将
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        tv2.setText(sb.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        sb.append(integer);

                    }
                });
    }

    /**
     * rxJavaMethod4
     * =======================================================================================
     */
    private void rxJavaMethod4() {
        Observable.just(126)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return String.valueOf(integer + 100);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        tv2.setText(s);
                    }
                });
    }

    /**
     * rxJavaMethod5
     * =======================================================================================
     */
    private void rxJavaMethod5() {
        Observable.just("hello")
                .flatMap(new Func1<String, Observable<?>>() {
                    @Override
                    public Observable<?> call(String s) {
                        return Observable.just(1, 3, 5, 7);
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.i("Action1", o.toString());
                    }
                });
    }

    /**
     * rxJavaMethod6
     * =======================================================================================
     */
    private void rxJavaMethod6() {
        Observable.just(1, 3, 6, 7)
                .switchMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer integer) {
                        return Observable.just(String.valueOf(integer)).subscribeOn(Schedulers.io());
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("Action1", s + "hello");
                    }
                });
    }

    public void setMainActivityData(String data) {
        tv2.setText(data);
    }

    /**
     * rxJavaMethod7
     * =======================================================================
     */
    private void rxJavaMethod7() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("onNext()执行方法");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i("Action1", s);
            }
        });
    }

    /**
     * 网络请求
     */
    //http://mtest.spider.com.cn/appmerch20/getPaperList.action?
    private void requestData() {
        new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://mtest.spider.com.cn/appmerch20/")
                .build()
                .create(ApiService.class)
                .getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JavaBean>() {
                    @Override
                    public void call(JavaBean javaBean) {
                        Toast.makeText(MainActivity.this, javaBean.getMessage(), Toast.LENGTH_LONG);
                        Log.i("JavaBean", javaBean.getMessage());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG);
                        Log.i("Throwable", throwable.toString());
                    }
                });
    }


    /**
     * 网络请求，返回xml
     */
    private void requestToXml() {
        new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl("http://180.96.21.204:29086/")
                .build()
                .create(ApiService.class)
                .getDataToXml(getOnlinePayMap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Orderxml>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Orderxml orderxml) {
                        //存储订单数据
                        saveOrderData(orderxml);

                        //表示充值成功，保存订单信息
                        if ("0".equals(orderxml.getResultno())) {

                        }
                    }
                });
    }

    /**
     * 利用LeanCloud云存储的api进行联网上报
     *
     * @param orderxml
     */
    private void saveOrderData(Orderxml orderxml) {
        new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://orhvf0bg.api.lncld.net/1.1/classes/")
                .build()
                .create(ApiService.class)
                .saveOrder(orderxml)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });
    }





    public static Map<String, String> getOnlinePayMap() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);

        String userid = "10001795";//商户数字id
        String productid = "";//商品编号
        String price = "10";//商品面值
        String num = "1";//固定值
        String mobile = "15205152300";//充值的号码（手机号,加油卡等）
        String spordertime = time;
        String sporderid = "1008990";//商户自定义订单号(32位以内)---------要变更的
        String key = "26b3e879b2d8bbae0eeca2907f3fc988";//商户Key
        String back_url = "http://47.105.57.119:8080/index.html";//商户回调接口

        String sign = Md5Utils.MD5HEX("userid=" + userid + "&productid=" +
                productid + "&price=" + price + "&num=" + num + "&mobile=" +
                mobile + "&spordertime=" + spordertime + "&sporderid=" +
                sporderid + "&key=" + key);

        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("productid", productid);
        map.put("price", price);
        map.put("num", num);
        map.put("mobile", mobile);
        map.put("spordertime", spordertime);
        map.put("sporderid", sporderid);
        map.put("sign", sign);
        map.put("back_url", back_url);

        return map;
    }


}


















