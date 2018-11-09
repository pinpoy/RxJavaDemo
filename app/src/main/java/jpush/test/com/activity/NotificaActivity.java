package jpush.test.com.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jpush.test.com.R;
import jpush.test.com.utils.StringUtils;

/**
 * Created by jesgoo on 2018/8/9.
 */

public class NotificaActivity extends AppCompatActivity {
    private static final String MY_ACTION = "polly.liu.Imageoo";
    private MyReceiver notificationReceiver;
    private String packageName = "com.mogo.space";
    private NotificationManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifica);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.tv_notification)
    void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_notification:
//                if (isAppInstalled(this, packageName))
//                    showNotification(this);
//                //控制20%的几率去执行该方法
//                if (controlPercent(0.2)) {
//                    Log.i("xupeng", "我是被筛选过来的————————————");
//
//                }


                String num = "0.5";   //模拟控比的比例
                String packageName = "com.mogo.space";  //模拟红包的包名

                if (!StringUtils.isEmpty(num)) {
                    if (isAppInstalled(this, packageName)) {
                        if (!num.equals(getNum(this))) {        //控比进行修改
                            setNum(this, num);
                            if (controlPercent(Double.valueOf(num))) {
                                showNotification(this);
                                setPass(this, true);
                            } else {
                                setPass(this, false);
                            }
                        } else { //控比不变
                            if (getPass(this)) {
                                showNotification(this);
                            }
                        }

                    }
                    return;
                }
                break;
        }
    }


    private boolean controlPercent(double percent) {
        int num = new Random().nextInt(100);
        if (num < 100 * percent) {
            return true;
        }
        return false;
    }

    private static String getNum(Context context) {
        SharedPreferences user = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        String value = user.getString("Value", "null");
        return value;
    }

    private static boolean getPass(Context context) {
        SharedPreferences user = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        boolean value = user.getBoolean("pass", false);
        return value;
    }

    /**
     * 保存控量百分比
     *
     * @param context
     * @param num
     */
    private static void setNum(Context context, String num) {
        SharedPreferences user = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("Value", num);
        //提交数据存入到xml文件中
        edit.commit();
    }

    private static void setPass(Context context, boolean pass) {
        SharedPreferences user = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        edit.putBoolean("pass", pass);
        //提交数据存入到xml文件中
        edit.commit();
    }


    /**
     * 根据包名判断本机是否已经安装apk
     *
     * @param context
     * @param packageName
     * @return
     */
    public boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }


    /**
     * 显示一个普通的通知
     *
     * @param context 上下文
     */
    public void showNotification(Context context) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(getPendingIntent(context))
                .setSmallIcon(getResId(context))         //设置状态栏里面的图标（小图标）
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher)) //下拉下拉列表里面的图标（大图标）
                .setWhen(System.currentTimeMillis())        //设置时间发生时间
                .setAutoCancel(true)                        //设置可以清除
                .setContentTitle("This is ContentTitle")    //设置下拉列表里的标题
                .setContentText("This is ContentText");    //设置上下文内容
//                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);    //设置声音和震动
        Notification notification = builder.getNotification();
        //通知被点击后，自动消失
        /**
         notification.flags = Notification.FLAG_NO_CLEAR; // 点击清除按钮时就会清除消息通知,但是点击通知栏的通知时不会消失
         notification.flags = Notification.FLAG_ONGOING_EVENT; // 点击清除按钮不会清除消息通知,可以用来表示在正在运行
         notification.flags = Notification.FLAG_AUTO_CANCEL; // 点击清除按钮或点击通知后会自动消失
         notification.flags = Notification.FLAG_INSISTENT; // 一直进行，比如音乐一直播放，知道用户响应
         */
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(100, notification); //第一个参数唯一的标识该Notification，第二个参数就是Notification对象。如果俩个为独立事件id不一致
    }


    private int getResId(Context context) {
        Resources r = context.getResources();
        int id = r.getIdentifier("app", "mipmap", "jpush.test.com.rxjavademo");
        return id;//0x7f0a0000

    }

    /**
     * 构建pendingIntent
     *
     * @param context
     * @return
     */
    private PendingIntent getPendingIntent(Context context) {
        //动态注册广播
        if (null == notificationReceiver) {
            notificationReceiver = new MyReceiver();
            IntentFilter filter = new IntentFilter(MY_ACTION);
            context.registerReceiver(notificationReceiver, filter);
        }
        /**
         flag:flags的取值有四个：
         PendingIntent.FLAG_ONE_SHOT：获取的PendingIntent只能使用一次
         PendingIntent.FLAG_NO_CREATE：利用FLAG_NO_CREAT获取的PendingIntent，若描述的Intent不存在则返回NULL值
         PendingIntent.FLAG_CANCEL_CURRENT：如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
         PendingIntent.FLAG_UPDATE_CURRENT：能够新new一个 Intent
         */

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(MY_ACTION), 0);
        return pendingIntent;
    }

    class MyReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            //点击处理相应逻辑
            Log.i("xupeng", "onReceive");
            showNotificationProgress(context);

        }
    }

    public void showNotificationProgress(Context context) {
        //进度条通知
        final NotificationCompat.Builder builderProgress = new NotificationCompat.Builder(context);
        builderProgress.setContentTitle("下载中");
        builderProgress.setSmallIcon(R.mipmap.ic_launcher);
        builderProgress.setTicker("进度条通知");
        builderProgress.setProgress(100, 0, false);
        final Notification notification = builderProgress.build();
        //发送一个通知
        manager.notify(100, notification);
        /**创建一个计时器,模拟下载进度**/
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int progress = 0;

            @Override
            public void run() {
                Log.i("progress", progress + "");
                while (progress <= 100) {
                    progress++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //更新进度条
                    builderProgress.setProgress(100, progress, false);
                    //再次通知
                    manager.notify(100, builderProgress.build());
                }
                //计时器退出
                this.cancel();
                //进度条退出
                manager.cancel(100);
                return;//结束方法
            }
        }, 0);
    }


}
