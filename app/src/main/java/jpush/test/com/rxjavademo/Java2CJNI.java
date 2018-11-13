package jpush.test.com.rxjavademo;

/**
 * Created by jesgoo on 2018/11/12.
 */

public class Java2CJNI {

    static {
        System.loadLibrary("NameProvider");
    }

    public static native String java2c();
}
