package jpush.test.com.Module;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import jpush.test.com.presenter.MainPresenter;
import jpush.test.com.rxjavademo.MainActivity;

/**
 * @author: 徐鹏android
 * @Description:
 * @time: create at 2017/11/9 14:32
 */
@Module
public class MainModule {
    /**
     * 三方框架不能对其构造函数践行@inject，在该处new Gson(),如果内部需要参数,假如：new Gson(context),
     * 利用@provide进行提供
     * @Provides
     * public Context provide（）{
     *      return context；
     * }
     */
    @Provides
    public Gson provideGson() {
        return new Gson();
    }
//
//    public MainModule(MainActivity activity) {
//        this.activity = activity;
//    }
//
//    @Provides
//    public MainPresenter provie(MainActivity activity) {
//        return new MainPresenter(activity);
//    }
//
//    @Provides
//    public MainActivity provide() {
//        return activity;
//    }
}
