package jpush.test.com.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import jpush.test.com.R;


public class EventBusSecondActivity extends Activity {

    private Button mButton2;
    private TextView mText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_second);
        mButton2 = (Button) findViewById(R.id.mButton2);
        mText2 = (TextView) findViewById(R.id.mText2);
        EventBus.getDefault().register(this);

        jumpActivity();
    }


    private void jumpActivity() {
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("欢迎大家浏览我写的博客"));
                finish();
            }
        });
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void logitech2(String pppp) {
        mText2.setText(pppp);
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void logitech1(String pppp) {
        mText2.setText(pppp);
    }

}
