package jpush.test.com.component;

import dagger.Component;
import jpush.test.com.Module.MainModule;
import jpush.test.com.Module.PoetryModule;
import jpush.test.com.rxjavademo.MainActivity;
import jpush.test.com.rxjavademo.OtherActivity;

/**
 * @author: 徐鹏android
 * @Description:
 * @time: create at 2017/11/9 13:47
 */
@Component(modules = {MainModule.class, PoetryModule.class})
public interface MainComponent {

    /**
     * 需要用到这个连接器的对象，就是这个对象里面有需要注入的属性
     * （被标记为@Inject的属性）
     * 这里inject表示注入的意思，这个方法名可以随意更改，但建议就
     * 用inject即可。
     */
    void inject(MainActivity activity);

    void inject(OtherActivity activity);


}

