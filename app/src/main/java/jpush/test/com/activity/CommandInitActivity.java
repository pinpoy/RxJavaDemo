package jpush.test.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import jpush.test.com.R;
import jpush.test.com.utils.VirtualTerminal;


public class CommandInitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_init);

        Log.i("xupeng", "CommandInitActivity-----onCreate");


        //需要手机root的操作
        if (exeCommand("am start -n it.colucciweb.sstpvpnclient/it.colucciweb.sstpvpnclient.MainActivity")) {
            SystemClock.sleep(3 * 1000);
            exeCommand("input tap 654 252");    //exeCommand("input tap 925 325"); nuxus设备
            SystemClock.sleep(3 * 1000);
//            exeCommand("input tap 660 244");    //exeCommand("input tap 996 324");  nuxus设备
            SystemClock.sleep(15 * 1000);
            //开启webview的页面
//            exeCommand("am start -n jpush.test.com.rxjavademo.webview/jpush.test.com.activity.WebviewActivity");

            startActivity(new Intent(this, WebviewActivity.class));
            finish();

        }

    }


    private static boolean exeCommand(String command) {
        boolean ret = false;
        try {
            VirtualTerminal vt;
            vt = new VirtualTerminal("su");
            VirtualTerminal.VTCommandResult r = vt.runCommand(command);
            ret = r.success();
            vt.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("xupeng", "CommandInitActivity-----onDestroy");

    }
}
