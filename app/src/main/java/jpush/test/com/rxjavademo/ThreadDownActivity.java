package jpush.test.com.rxjavademo;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class ThreadDownActivity extends AppCompatActivity {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.pb)
    ProgressBar pb;
    @Bind(R.id.tv)
    TextView tv;


    static int ThreadCount = 3;   //线程的个数
    static int finishedThread = 0;   //初始化下载完成的线程的个数
    private String fileName = "hsjj2ghgzh.txt";
    private String path = "http://dzs.qisuu.com/txt/%E6%AD%A6%E6%9E%81%E5%AE%97%E5%B8%88.txt";  //确定下载地址

    int CurrentProgress;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //防止溢出设置成long
            tv.setText((long) pb.getProgress() * 100 / pb.getMax() + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_down);
        ButterKnife.bind(this);
        //openPeimission();
    }

    private void openPeimission() {
        //RxBinding和RxPermissions联合使用,开启读写外部储存的权限
        RxView.clicks(button)
                .compose(new RxPermissions(this).ensure(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            startDown();
                        } else {
                            // 未获取权限
                            Toast.makeText(ThreadDownActivity.this, "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @OnClick(R.id.button)
    void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.button:   //开始下载
                startDown();
                break;
        }
    }

    private void startDown() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        //获取到请求资源的文件长度
                        int length = conn.getContentLength();
                        pb.setMax(length);

                        File file = new File(Environment.getExternalStorageDirectory(), fileName);
                        //创建随机储存文件
                        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                        //设置临时文件的大小
                        raf.setLength(length);
                        //关闭raf
                        raf.close();

                        //计算出每一个线程下载多少字节
                        int size = length / ThreadCount;

                        for (int i = 0; i < ThreadCount; i++) {
                            //startIndex,endIndex分別代表线程开始和结束的位置
                            int startIndex = i * size;
                            int endindex = (i + 1) * size;
                            if (i == ThreadCount - 1) {
                                //如果是最后一个线程,那么将结束位置写死
                                endindex = length - 1;
                            }
                            new DownLoadThread(startIndex, endindex, i).start();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    class DownLoadThread extends Thread {
        private int startIndex;
        private int endIndex;
        private int threadId;

        public DownLoadThread(int startIndex, int endIndex, int threadId) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            //使用http请求下载安装包文件
            URL url;
            try {
                File fileProgress = new File(Environment.getExternalStorageDirectory(), threadId + ".txt");

                //判断储存下载进度的临时文件是否存在
                if (fileProgress.exists()) {

                    FileInputStream fis = new FileInputStream(fileProgress);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                    //从下载进度的临时文件读取上一次下载的总进度,然后和原来文本的开始进度开始位置想家,得到新的下载位置
                    if (null != br.readLine()) {
                        int lastProgress = Integer.parseInt(br.readLine());
                        startIndex += lastProgress;

                        //把上次下载的进度显示到进度条,更新进度条
                        CurrentProgress += lastProgress;
                    }


                    //主线程刷新文本进度
                    handler.sendEmptyMessage(1);
                    fis.close();

                    System.out.print("线程" + threadId + "下载区间是" + startIndex + "====" + endIndex);
                    url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    //设置请求数据的区间
                    conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
                    //请求部分数据的响应码是206
                    if (conn.getResponseCode() == 206) {
                        //获取一部分数据的读取
                        InputStream is = conn.getInputStream();
                        byte[] b = new byte[1024];
                        int len = 0;
                        int total = 0;
                        //拿到临时文件的引用
                        File file = new File(Environment.getExternalStorageDirectory(), fileName);
                        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                        //更新文件的写入位置,startIndex
                        raf.seek(startIndex);
                        while ((len = is.read(b)) != -1) {
                            //每次读取流里面的数据,同步吧数据写入临时文件
                            raf.write(b, 0, len);
                            total += len;
                            System.out.println("线程" + threadId + "下载了" + total);

                            //每次读取的数据的长度加到进度条，并显示
                            CurrentProgress += len;
                            pb.setProgress(CurrentProgress);
                            //主线程刷新文本进度
                            handler.sendEmptyMessage(1);

                            RandomAccessFile fileProgressraf = new RandomAccessFile(fileProgress, "rwd");
                            //每一次读取流里面的数据以后，把当前线程下载的总进度写入临时文件中
                            fileProgressraf.write((total + "").getBytes());
                            fileProgressraf.close();
                        }
                        System.out.println("线程" + threadId + "下载过程结束===========================");
                        raf.close();
                        //条线程下载完成以后，清理临时文件
                        finishedThread++;
                        //线程安全
                        synchronized (path) {
                            if (finishedThread == ThreadCount) {
                                for (int i = 0; i < ThreadCount; i++) {
                                    File filefinish = new File(Environment.getExternalStorageDirectory(), i + ".txt");
                                    filefinish.delete();

                                }
                                finishedThread = 0;
                            }
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
