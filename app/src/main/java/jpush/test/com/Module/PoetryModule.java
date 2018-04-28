package jpush.test.com.Module;

import dagger.Module;
import dagger.Provides;
import jpush.test.com.bean.Poetry;

/**
 * @author: 徐鹏android
 * @Description:
 * @time: create at 2017/11/9 15:06
 */
@Module
public class PoetryModule {
    // 这个方法需要一个String参数，在Dagger2注入中，这些参数也是注入形式的，也就是
    // 要有其他对方提供参数poems的生成，不然会造成编译出错
    @Provides
    public Poetry Poetry(String poems) {
        return new Poetry(poems);
    }

    // 这里提供了一个生成String的方法，在这个Module里生成Poetry实例时，会查找到这里
    // 可以为上面提供String类型的参数
    @Provides
    public String xx() {
        return "只有意志坚强的人，才能到达彼岸";
    }


}
