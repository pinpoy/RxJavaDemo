package jpush.test.com.rxjavademo;

import com.google.gson.JsonObject;

import java.util.Map;

import jpush.test.com.bean.BrowserInfo;
import jpush.test.com.bean.BrowserInfo_2;
import jpush.test.com.bean.ResultBean;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author: 徐鹏android
 * @Description:
 * @time: create at 2017/11/8 14:20
 */

public interface ApiService {

    @GET("getPaperList.action?")
    Observable<JavaBean> getData();


    @FormUrlEncoded
    @POST("onlinepay.do? ")
    Observable<Orderxml> getDataToXml(@FieldMap Map<String, String> param);


    @Headers({
            "Content-type:application/json;charset=UTF-8;",
            "X-LC-Id:ORHVF0BG0mjUcSdINFSdbpIx-gzGzoHsz",
            "X-LC-Key:TsKBD4GiBkcePiixFwCcp8V3"})
    @POST("orderData")
    Observable<Object> saveOrder(@Body Orderxml beanToJson);


    @Headers({
            "Content-type:application/json;charset=UTF-8;",
            "X-LC-Id:ORHVF0BG0mjUcSdINFSdbpIx-gzGzoHsz",
            "X-LC-Key:TsKBD4GiBkcePiixFwCcp8V3"})
    @POST("phoneInfo")
    Observable<Object> savephoneInfo(@Body JsonObject jsonObject);


    @GET("restartDevice.do?")
    Observable<ResultBean> getChudong(@QueryMap Map<String, String> map);


    @GET("db0aa8c2a3fc0f7ada0c2668a2c8d87e")
    Observable<BrowserInfo> getBrowserInfo();

    @GET("luacb.do")
    Observable<Object> reportTaoWeb(@QueryMap Map<String, String> map);


    @GET("end")
    Observable<Object> reportWeiWeb(@QueryMap Map<String, String> map);


    @GET("conf")
    Observable<BrowserInfo_2> getBrowserInfo_2(@QueryMap Map<String, String> map);
}
