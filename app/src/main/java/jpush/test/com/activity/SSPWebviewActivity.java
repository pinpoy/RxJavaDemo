package jpush.test.com.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AndroidRuntimeException;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Random;

import jpush.test.com.BuildConfig;
import jpush.test.com.R;

/**
 * 换ua清除cookie的webview展示
 */
public class SSPWebviewActivity extends AppCompatActivity {

    private WebView webView;
    private String danUrl = "http://www.shdskj.cn";
    private String qiumiUrl = "https://www.qiumiwu.com/";
    private String[] uaStr = {"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11",
            "Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sspwebview);
        webView = (WebView) findViewById(R.id.web_webview);


        //设置cookie,并清除cookie
        updateCookies(this, new WebviewActivity.UpdateCookies() {
            @Override
            public void update(CookieManager cookieManager) {
                initPage();
            }
        });


    }

    private void initPage() {
        Random random = new Random();
        //不使用缓存:
        WebSettings webSettings = webView.getSettings();
//        webSettings.setUserAgentString(uaStr[random.nextInt(uaStr.length)]);         //设置useragent，一定要放在loadUrl前面
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        //设置缩放
//        webSettings.setSupportZoom(true);


        webView.clearCache(true);
        webView.clearHistory();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.loadUrl(qiumiUrl);
    }

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

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
