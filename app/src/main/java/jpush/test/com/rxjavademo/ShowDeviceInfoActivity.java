package jpush.test.com.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jpush.test.com.R;
import jpush.test.com.utils.SystemUtil;

public class ShowDeviceInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_device_info);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.tv_imei, R.id.tv_imsi, R.id.tv_brand, R.id.tv_model})
    void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_imei:
                String imei = SystemUtil.getIMEI(this);
                Toast.makeText(this, imei, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_imsi:
                String imsi = SystemUtil.getIMSI(this);
                Toast.makeText(this, imsi, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_brand:
                String deviceBrand = SystemUtil.getDeviceBrand();
                Toast.makeText(this, deviceBrand, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_model:
                String systemModel = SystemUtil.getSystemModel();
                Toast.makeText(this, systemModel, Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
