package jpush.test.com.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import jpush.test.com.BuildConfig;
import jpush.test.com.R;
import jpush.test.com.bean.BrowserInfo_2;
import jpush.test.com.rxjavademo.ApiService;
import jpush.test.com.rxjavademo.RetrofitManager;
import jpush.test.com.utils.VirtualTerminal;
import jpush.test.com.utils.WebViewProxySettings;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 帮助webview的页面
 * Created by xupeng on 2017/12/12.
 */

public class WebviewActivity extends AppCompatActivity implements Bridge.RemoveWebListener {
    private static final String TAG = "WebviewActivity";
//    @Bind(R.id.web_webview)
//    WebView webWebview;

    @Bind(R.id.fl_content)
    FrameLayout fl_content;

    @Bind(R.id.tv_error)
    TextView tv_Error;

    private String filePath;
    private WebView webWebview;
    private String httpsAdditionalJs;
    public static String serverContent;
    public static String ipContent;
    public static String imeiContent;
    String ipPath = "/storage/emulated/0/TouchSprite/lua/ip.txt";
    String imeiPath = "/storage/emulated/0/TouchSprite/lua/imei.txt";
    String serverPath = "/storage/emulated/0/TouchSprite/lua/server.txt";
    String jsPath = "/storage/emulated/0/TouchSprite/lua/i.js";
    public static String reportrl;
    private boolean startFlag;
    private int getNum;
    private static Handler handler = new Handler();
    private Runnable runnable;
    private static Bridge bridge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        Log.i("xupeng", "WebviewActivity----onCreate " + Thread.currentThread().getId());

        httpsAdditionalJs = readLocalFile(jsPath);
        ipContent = readLocalFile(ipPath).trim();

        serverContent = readLocalFile(serverPath);
        imeiContent = readLocalFile(imeiPath);
        //拼接url
        reportrl = serverContent + "/cap.manage/phone/cpa/";
        if (httpsAdditionalJs.isEmpty()) {
            Toast.makeText(this, "js文件为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (ipContent.isEmpty()) {
            Toast.makeText(this, "ip文件为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (serverContent.isEmpty() || imeiContent.isEmpty()) {
            Toast.makeText(this, "server或imei为空", Toast.LENGTH_LONG).show();
            return;
        }


        //要做的事情，这里再次调用此Runnable对象
        runnable = new Runnable() {
            @Override
            public void run() {
                //要做的事情，这里再次调用此Runnable对象
                Log.i("xupeng", "页面停留超时，重新请求网络，加载页面");
                getBrowserInfo_2();

            }
        };
        handler.postDelayed(runnable, 240 * 1000);


        //访问网络
        getBrowserInfo_2();


//        httpsAdditionalJs = getJsFromAssetFile("i.js");
//        initSetting("112.113.118.53", 4235);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                initSetting("117.57.36.60", 4212);
//            }
//        }, 20 * 1000);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                initSetting("218.95.51.75", 4243);
//            }
//        }, 40 * 1000);


    }

    private void getBrowserInfo_2() {
        RetrofitManager.getRetrofit("http://116.62.170.158:7078/v1/")
                .create(ApiService.class)
                .getBrowserInfo_2(getBrowserInfo_2Map())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BrowserInfo_2>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("xupeng", e.getMessage());
                        Toast.makeText(WebviewActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        if (getNum <= 2) {
                            getBrowserInfo_2();
                            getNum++;
                            tv_Error.setText("第" + getNum + "--次请求配置文件");
                        }

                    }

                    @Override
                    public void onNext(BrowserInfo_2 browserInfo_2) {
                        if (null != browserInfo_2 && browserInfo_2.getVpn().equals("on")) { //vpn开启成功
                            initWebview(browserInfo_2);
                            Toast.makeText(WebviewActivity.this, "activity---成功获取到配置参数", Toast.LENGTH_SHORT).show();
                        } else if (null != browserInfo_2 && browserInfo_2.getVpn().equals("off")) {//开启失败
                            SystemClock.sleep(10 * 1000);
                            getBrowserInfo_2();
                        }
                    }
                });
    }


    public Map<String, String> getBrowserInfo_2Map() {
        Map<String, String> map = new HashMap<>();
        map.put("localip", ipContent);
        map.put("token", "db0aa8c2a3fc0f7ada0c2668a2c8d87e");
        return map;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("xupeng", "WebviewActivity------onDestroy");

        handler.removeCallbacks(runnable);  //移除任务

    }

    private static boolean exeCommand(String command) {
        boolean ret = false;
        try {
            VirtualTerminal vt;
            vt = new VirtualTerminal("su");
            VirtualTerminal.VTCommandResult r = vt.runCommand(command);
            ret = r.success();
            vt.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
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

//    private void getrowserBInfo() {
//        RetrofitManager.getRetrofit("http://116.62.170.158:7078/v1/task/")
//                .create(ApiService.class)
//                .getBrowserInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<BrowserInfo>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("xupeng", e.getMessage());
//                        tv_Error.setText(e.getMessage());
//                        Toast.makeText(WebviewActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                        if (getNum <= 2) {
//                            getrowserBInfo();
//                            getNum++;
//                            tv_Error.setText("第" + getNum + "--次请求配置文件");
//                        }
//
//                    }
//
//                    @Override
//                    public void onNext(BrowserInfo browserInfo) {
//                        if (null != browserInfo) {
//                            initWebview(browserInfo);
//                            Toast.makeText(WebviewActivity.this, "activity---成功获取到配置参数", Toast.LENGTH_SHORT).show();
//                            tv_Error.setText("成功获取到配置参数，等待加载url");
//                        }
//                    }
//                });
//    }


    private void initWebview(BrowserInfo_2 browserInfo) {
        if (null == webWebview)
            webWebview = new WebView(this);

        //设置cookie
        updateCookies(this, new UpdateCookies() {
            @Override
            public void update(CookieManager cookieManager) {
                initPage(browserInfo);
//                setUpWebView(browserInfo);
            }
        });
    }


    private void initSetting(String host, int port) {
        if (null == webWebview)
            webWebview = new WebView(this);
        //设置代理
        if (WebViewProxySettings.setProxy(webWebview, host, port)) {
            //设置cookie
            updateCookies(this, new UpdateCookies() {
                @Override
                public void update(CookieManager cookieManager) {
//                    initPage(filePath);
                }
            });
        }
    }


    private void initPage(BrowserInfo_2 browserInfo) {
        WebSettings webSettings = webWebview.getSettings();
        webSettings.setUserAgentString(browserInfo.getUa());         //设置useragent，一定要放在loadUrl前面
        webSettings.setJavaScriptEnabled(true);
        bridge = new Bridge(webWebview);
        bridge.setRemoveWebListener(this);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        String appCacheDir = getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCacheDir);
        webSettings.setDatabasePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setAppCacheMaxSize(1024 * 1024 * 10);
        webSettings.setNeedInitialFocus(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setPluginState(WebSettings.PluginState.OFF);
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setLoadsImagesAutomatically(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
//                // Hide the zoom controls for HONEYCOMB+
            webSettings.setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        webWebview.setDrawingCacheEnabled(true);
        webWebview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        webWebview.addJavascriptInterface(bridge, "xjsObj");
        webWebview.setWebViewClient(new WebViewClient() {
            //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("xupeng", "onPageFinished");
//                if (!url.contains("video"))
                view.loadUrl("javascript:" + httpsAdditionalJs);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (browserInfo.getUrl().equals(url)) { //一级页面
                    Log.i("xupeng", "一级页面");
                }
                Log.i("xupeng", "onPageStarted " + url);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                // 这个方法在 android 6.0才出现
                int statusCode = errorResponse.getStatusCode();
                if (404 == statusCode || 500 == statusCode) {
//                    view.loadUrl(browserInfo.getUrl());// 加载自定义错误页面
                    Toast.makeText(WebviewActivity.this, "onReceivedHttpError" + statusCode, Toast.LENGTH_SHORT).show();
                }

//                tv_Error.setText("webview加载url出现错误");
//                Toast.makeText(WebviewActivity.this, "onReceivedHttpError--webview加载url出现错误", Toast.LENGTH_SHORT).show();
//                view.loadUrl(browserInfo.getUrl());// 加载自定义错误页面
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                tv_Error.setText("webview加载url出现错误");
                Toast.makeText(WebviewActivity.this, "onReceivedError--webview加载url出现错误", Toast.LENGTH_SHORT).show();
                Log.i("xupeng", description);
                view.loadUrl(failingUrl);// 重启加载
            }
        });


        webWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }
        });

        webWebview.loadUrl(browserInfo.getUrl());

        fl_content.removeAllViews();
        fl_content.addView(webWebview);
        Log.i("xupeng", "加载url线程的id---" + Process.myPid());

    }


    /**
     * 向网页设置Cookie，设置Cookie后不需要页面刷新即可生效；
     * <p>
     * 1、用一级域名设置Cookie：cookieManager.setCookie("360.cn", "key=value;path=/;domain=360.cn")（android所有版本都支持这种格式）;
     * http://www.360doc.com/content/14/0903/22/9200790_406874810.shtml
     * 2、为何不能是“.360.cn”，在android2.3及以下版本，setCookie方法中URL参数必须是地址，如“360.cn”，而不能是“.360.cn”，否则存入webview.db-cookies表中的domain字段会为空导致无法在网页中生效;
     * http://zlping.iteye.com/blog/1633213
     */
    public static void updateCookies(Context context, UpdateCookies updateCookies) {
        // 1、2.3及以下需要调用CookieSyncManager.createInstance；
        // 2、Samsung GTI9300 Android4.3，在调用cookieManager.setAcceptCookie之前不调用CookieSyncManager.createInstance会发生native崩溃：java.lang.UnsatisfiedLinkError: Native method not found: android.webkit.CookieManagerClassic.nativeSetAcceptCookie:(Z)V at android.webkit.CookieManagerClassic.nativeSetAcceptCookie(Native Method)
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = null;
        try {
            cookieManager = CookieManager.getInstance();
        } catch (AndroidRuntimeException e) { // 当webview内核apk正在升级时会发生崩溃（Meizu	m2 note Android5.1）
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
        if (cookieManager != null) {
            cookieManager.setAcceptCookie(true);
            /**
             * 写入cookie
             */
            cookieManager.removeSessionCookie();// 移除旧的[可以省略]
            cookieManager.removeAllCookie();//清除cooike
//            cookieManager.setCookie("key", "value");


            if (updateCookies != null) {
                updateCookies.update(cookieManager);
            }
            CookieSyncManager.getInstance().sync();
        }
    }

    @Override
    public void remove() {
//        fl_content.removeAllViews();
//        tv_Error.setText("成功清除页面----等待下次任务执行");
//        Log.i("xupeng", "webview的remove");
//        if (Looper.myLooper() != Looper.getMainLooper()) {
//            Log.i("xupeng", "不在主线程");
//
//        }
        bridge.RemoveWebListener();


        //切换到主线程来执行操作
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("xupeng", "runOnUiThread: " + Thread.currentThread().getId());
                webWebview.stopLoading();
                webWebview.clearHistory();
                webWebview.clearCache(true);
                webWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//                webWebview.pauseTimers();
                fl_content.removeView(webWebview);
                fl_content.removeAllViews();
                webWebview.destroy();
                webWebview = null;

            }
        });

        Log.i("xupeng", "remove操作线程的id---" + Process.myTid());
        Log.i("xupeng", "remove操作执行一次");

        Toast.makeText(WebviewActivity.this, "结束vpn进程", Toast.LENGTH_SHORT).show();
        if (exeCommand("am force-stop it.colucciweb.sstpvpnclient")) {
            SystemClock.sleep(5000);
        }

        startActivity(new Intent(WebviewActivity.this, CommandInitActivity.class));
        WebviewActivity.this.finish();

    }


    public interface UpdateCookies {
        void update(CookieManager cookieManager);
    }


}
