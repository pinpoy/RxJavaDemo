package jpush.test.com.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import jpush.test.com.BuildConfig;
import jpush.test.com.R;
import jpush.test.com.bean.BrowserInfo_2;
import jpush.test.com.utils.WebViewProxySettings;

public class WebviewActivity_3 extends AppCompatActivity implements Bridge.RemoveWebListener{

    private static Bridge bridge;
    private BrowserInfo_2 browserInfo;
    private WebView webWebview1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_2);
        ButterKnife.bind(this);

        webWebview1 = (WebView) findViewById(R.id.web_webview_1);
        WebView webWebview2 = (WebView) findViewById(R.id.web_webview_2);


        //模拟假数据
        browserInfo = new BrowserInfo_2();
        browserInfo.setUa("User-Agent: Mozilla/5.0 (Linux; U; android 2.3.7; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
        browserInfo.setUrl("http://www.baidu.com");

//        initSetting("183.129.207.89", 27985);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initSetting("180.125.160.37", 4232, webWebview1);
            }
        }, 5 * 1000);


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                initSetting("27.221.32.140", 80, webWebview2);
//
//                startActivity(new Intent(WebviewActivity_2.this,we));
//            }
//        }, 10* 1000);




    }


    private void initSetting(String host, int port, WebView webWebview) {
        //设置代理
        if (WebViewProxySettings.setProxy(webWebview, host, port)) {
            //设置cookie
            updateCookies(this, new WebviewActivity.UpdateCookies() {
                @Override
                public void update(CookieManager cookieManager) {
                    initPage(browserInfo, webWebview);
                }
            });
        }


//        updateCookies(this, new WebviewActivity.UpdateCookies() {
//            @Override
//            public void update(CookieManager cookieManager) {
//                initPage(browserInfo);
//            }
//        });
    }


    private void initPage(BrowserInfo_2 browserInfo, WebView webWebview) {
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
//                view.loadUrl("javascript:" + httpsAdditionalJs);
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
                    Toast.makeText(WebviewActivity_3.this, "onReceivedHttpError" + statusCode, Toast.LENGTH_SHORT).show();
                }

//                tv_Error.setText("webview加载url出现错误");
//                Toast.makeText(WebviewActivity.this, "onReceivedHttpError--webview加载url出现错误", Toast.LENGTH_SHORT).show();
//                view.loadUrl(browserInfo.getUrl());// 加载自定义错误页面
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Toast.makeText(WebviewActivity_3.this, "onReceivedError--webview加载url出现错误", Toast.LENGTH_SHORT).show();
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

        Log.i("xupeng", "webWebview.loadUrl---" + Process.myTid());


    }


    /**
     * 向网页设置Cookie，设置Cookie后不需要页面刷新即可生效；
     * <p>
     * 1、用一级域名设置Cookie：cookieManager.setCookie("360.cn", "key=value;path=/;domain=360.cn")（android所有版本都支持这种格式）;
     * http://www.360doc.com/content/14/0903/22/9200790_406874810.shtml
     * 2、为何不能是“.360.cn”，在android2.3及以下版本，setCookie方法中URL参数必须是地址，如“360.cn”，而不能是“.360.cn”，否则存入webview.db-cookies表中的domain字段会为空导致无法在网页中生效;
     * http://zlping.iteye.com/blog/1633213
     */
    public static void updateCookies(Context context, WebviewActivity.UpdateCookies updateCookies) {
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

    }

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && webWebview1.canGoBack()) {
            webWebview1.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
