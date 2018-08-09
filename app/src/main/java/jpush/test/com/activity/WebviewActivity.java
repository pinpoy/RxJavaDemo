package jpush.test.com.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import jpush.test.com.R;

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

        filePath = "http://www.baidu.com";


        initPage(filePath);
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
