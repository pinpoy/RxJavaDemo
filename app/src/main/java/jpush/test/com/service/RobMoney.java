package jpush.test.com.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import java.util.Calendar;

/**
 * Created by jesgoo on 2019/1/7.
 */

public class RobMoney extends AccessibilityService {

    private static final String TAG = "dxj";
    private Calendar c = Calendar.getInstance();
    int flag = 0;

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        Log.i(TAG, "onKeyEvent");
        int key = event.getKeyCode();

        switch (key) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:


                Intent downintent = new Intent("com.exmaple.broadcaster.KEYDOWN");
                downintent.putExtra("dtime", System.currentTimeMillis());
                if (flag == 0) {
                    sendBroadcast(downintent);
                    flag += 1;
                } else if (flag == 1) {
                    flag = 0;
                }
                Log.i(TAG, "KEYCODE_VOLUME_DOWN");
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:

                Intent upintent = new Intent("com.exmaple.broadcaster.KEYUP");
                upintent.putExtra("utime", System.currentTimeMillis());
                if (flag == 0) {
                    sendBroadcast(upintent);
                    flag += 1;
                } else if (flag == 1) {
                    flag = 0;
                }

                Log.i(TAG, "KEYCODE_VOLUME_UP");

                break;
            default:
                break;
        }
        return super.onKeyEvent(event);
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onCreate() {
        Log.i(TAG, "RobMoney::onCreate");
        super.onCreate();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // TODO Auto-generated method stub
        final int eventType = event.getEventType();

        Log.d(TAG, "事件---->" + event);

    }

}
