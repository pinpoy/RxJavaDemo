package jpush.test.com.rxjavademo;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
}
