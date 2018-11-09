package jpush.test.com.activity;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jpush.test.com.R;
import jpush.test.com.utils.SystemUtil;

/**
 * 帮助webview的页面
 * Created by xupeng on 2017/12/12.
 */

public class WebviewActivity extends AppCompatActivity {
    @Bind(R.id.web_webview)
    WebView webWebview;

    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        filePath = "https://cpu.baidu.com/block/app/cd8c941f/17231";


        initPage(filePath);

//        Settings.System.putString(getContentResolver(), "com.baidu.deviceid", null);
//        String var2 = Settings.System.getString(getContentResolver(), "com.baidu.deviceid");
//        String var3 = Settings.System.getString(getContentResolver(), "bd_setting_i");



        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        String ssid = connectionInfo.getSSID();

        List<ScanResult> scanResults = wifiManager.getScanResults();

        String mobileMAC = SystemUtil.getMobileMAC(this);


    }


    private void initPage(String filePath) {
        webWebview.loadUrl(filePath);
        WebSettings webSettings = webWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webWebview.setWebViewClient(new WebViewClient() {
            //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }


}
