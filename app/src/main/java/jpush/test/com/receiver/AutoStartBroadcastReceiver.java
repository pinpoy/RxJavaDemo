package jpush.test.com.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import jpush.test.com.rxjavademo.MainActivity;

/**
 * 非root情况下利用开机广播启动mainactivity
 * Created by jesgoo on 2019/1/11.
 */
public class AutoStartBroadcastReceiver extends BroadcastReceiver {
    static final String action_boot = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(action_boot)) {
            Intent sayHelloIntent = new Intent(context, MainActivity.class);
            sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(sayHelloIntent);
            Log.i("xupeng","AutoStartBroadcastReceiver");


        }
    }
}

