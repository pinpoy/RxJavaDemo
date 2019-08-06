package jpush.test.com.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by jesgoo on 2019/4/10.
 */

public class TelephonyUtils {


    public static String readLocalFile() {
        String imei = "";
        String path = "/storage/emulated/0/imei.txt";
        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            int length = fileInputStream.available();
            byte[] buffer = new byte[length];
            fileInputStream.read(buffer);
            String fileContent = new String(buffer, "UTF-8").trim();
            fileInputStream.close();

            String[] split = fileContent.split(";");    //切割字符串
            imei = split[1];
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("",e.toString());
        }
        return imei;

    }
}
