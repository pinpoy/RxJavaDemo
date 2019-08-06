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

    @GET("v1/message/tab?ss=SkVTR09PX1dJRklfNUc%3D%0A&sr=1080*1920&isp=460110687763015&lan=zh-cn&lon=MTIxLjU5ODcx%0A&mi=861240031165988&fr=ANDROID&ch=360APP&cc=cn&kpf=ANDROID&dpbs=3sCt3iAAMjE1NjY1NzYwAAIQAIinM9cDaj9E7RAAAAB7RJkEjcBGiK0QtZpbTltP%0A&did=ANDROID_93143c92213a1c0e&kpn=pearl&app=pearl&os=ZFXCNCT5801803011S%20release-keys&oc=Coolpad&md=C107-9&nt=WIFI&ve=1.5.3.63&__clientSign2=ae7wgAGN_9YyMTU2NjU3NjA5Y2U3ZGU5ZGU0NmYyMGJjNDk4YWNjNTUyNGFhNjYwZTA&lat=MzEuMjA5MzQ1%0A&adve=1.0")
    Observable<Object> getCharles();


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
