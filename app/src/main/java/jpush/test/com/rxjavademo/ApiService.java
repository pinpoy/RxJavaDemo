package jpush.test.com.rxjavademo;

import retrofit.http.GET;
import rx.Observable;

/**
 * @author: 徐鹏android
 * @Description:
 * @time: create at 2017/11/8 14:20
 */

public interface ApiService {

    @GET("getPaperList.action?")
    Observable<JavaBean> getData();
}
