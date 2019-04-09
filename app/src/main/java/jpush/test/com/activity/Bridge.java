package jpush.test.com.activity;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.util.HashMap;
import java.util.Map;

import jpush.test.com.rxjavademo.ApiService;
import jpush.test.com.rxjavademo.RetrofitManager;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static jpush.test.com.activity.WebviewActivity.imeiContent;
import static jpush.test.com.activity.WebviewActivity.ipContent;
import static jpush.test.com.activity.WebviewActivity.reportrl;

public class Bridge {
    private WebView webView;
    private long lastBack;

    public Bridge(WebView webView) {
        this.webView = webView;
    }

    public static RemoveWebListener removeWebListener;

    @JavascriptInterface
    public String storeJsData(String data) {
        Log.d("storeJsData", data);
        return "";
    }

    @JavascriptInterface
    public String log(String data) {
        Log.d("xupeng log", data);
        return "";
    }

    @JavascriptInterface
    public String jsRun(String data) {
        Log.d("xupeng js run", data);
        return "";
    }

    @JavascriptInterface
    public String end(String data) {
        Log.d("xupeng", data);
//        reportTao();

        //移除web页面
        if (System.currentTimeMillis() - lastBack > 120 * 1000) {
            try {
                repportWei();       //上报日志
                reportTao();       //上报涛涛
            } catch (Exception e) { //异常捕获，防止参数缺失造成无法访问
                e.printStackTrace();
            }
            lastBack = System.currentTimeMillis();
            if (null != removeWebListener)
                removeWebListener.remove();
        }


        return data;
    }

    private void repportWei() {//http://116.62.170.158:7078/v1/end?localip=1.2.3.4
        RetrofitManager.getRetrofit("http://116.62.170.158:7078/v1/")
                .create(ApiService.class)
                .reportWeiWeb(getreportWeiMap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i("xupeng", "成功上报");

                    }
                });
    }

    private void reportTao() {
        RetrofitManager.getRetrofit(reportrl)
                .create(ApiService.class)
                .reportTaoWeb(getreportMap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i("xupeng", "成功上报");

                    }
                });

    }

    public Map<String, String> getreportMap() {
        Map<String, String> map = new HashMap<>();
        map.put("flag", "17");
        map.put("ip", ipContent);
        map.put("imei", imeiContent);


        return map;
    }

    public Map<String, String> getreportWeiMap() {
        Map<String, String> map = new HashMap<>();
        map.put("localip", ipContent);


        return map;
    }


    public interface RemoveWebListener {
        void remove();
    }

    public void setRemoveWebListener(RemoveWebListener removeWebListener) {
        this.removeWebListener = removeWebListener;
    }

    public void RemoveWebListener() {
        this.removeWebListener = null;
    }


}
