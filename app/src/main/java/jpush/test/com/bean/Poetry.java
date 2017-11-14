package jpush.test.com.bean;

import javax.inject.Inject;

/**
 * @author: 徐鹏android
 * @Description:
 * @time: create at 2017/11/9 14:02
 */

public class Poetry {
    private String mPemo;

    // 用Inject标记构造函数,表示用它来注入到目标对象中去
    @Inject
    public Poetry() {
        mPemo = "生活就像海洋";
    }

    public Poetry(String poems) {
        this.mPemo = poems;
    }


    public String getPemo() {
        return mPemo;
    }
}
