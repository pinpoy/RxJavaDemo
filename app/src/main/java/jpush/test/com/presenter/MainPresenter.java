package jpush.test.com.presenter;

import javax.inject.Inject;

import jpush.test.com.rxjavademo.MainActivity;

/**
 * @author: 徐鹏android
 * @Description:
 * @time: create at 2017/11/9 11:52
 */

public class MainPresenter {
    //MainContract是个接口，View是他的内部接口，这里看做View接口即可
//    private MainActivity activity;
    @Inject
    public MainPresenter() {
//        this.activity = activity;
    }

    public void loadData() {
        //调用model层方法，加载数据
        String data = "假数据";
        //回调方法成功时
//        activity.setMainActivityData(data);
    }


}
