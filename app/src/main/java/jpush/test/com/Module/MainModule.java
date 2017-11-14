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
    private MainActivity activity;

    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    public MainModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    public MainPresenter provie(MainActivity activity) {
        return new MainPresenter(activity);
    }

    @Provides
    public MainActivity provide() {
        return activity;
    }
}
