package jpush.test.com.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jesgoo on 2019/3/12.
 */

public class VirtualTerminal {
    private static final String TAG = "VirtualTerminal";

    private final Object mReadLock = new Object();
    private final Object mWriteLock = new Object();
    private Process mProcess = null;

    private DataOutputStream mOutputStream;
    private ByteArrayOutputStream mInputBuffer = new ByteArrayOutputStream();
    private ByteArrayOutputStream mErrBuffer = new ByteArrayOutputStream();
    private InputReaderThread mInputReaderThread;
    private InputReaderThread mErrReaderThread;

    public VirtualTerminal(String shell) throws IOException, InterruptedException {

        mProcess = Runtime.getRuntime().exec(shell);

        mOutputStream = new DataOutputStream(mProcess.getOutputStream());

        mInputReaderThread = new InputReaderThread(mProcess.getInputStream(), mInputBuffer);
        mErrReaderThread = new InputReaderThread(mProcess.getErrorStream(), mErrBuffer);
        Thread.sleep(50);
        mInputReaderThread.start();
        mErrReaderThread.start();
    }

    public VTCommandResult runCommand(String command) throws Exception {
        synchronized (mWriteLock) {
            mInputBuffer.reset();
            mErrBuffer.reset();
        }

        // $? 表示最后运行的命令的结束代码（返回值）
        mOutputStream.writeBytes(command + "\necho :RET=$?\n");
        mOutputStream.flush();

        while (true) {
            synchronized (mReadLock) {
                boolean doWait = false;
                synchronized (mWriteLock) {
                    byte[] inpbyte = mInputBuffer.toByteArray();
                    String inp = new String(inpbyte);
                    doWait = !inp.contains(":RET=");
                }
                if (doWait) {
                    mReadLock.wait();
                }
            }

            synchronized (mWriteLock) {
                byte[] inpbyte = mInputBuffer.toByteArray();
                byte[] errbyte = mErrBuffer.toByteArray();
                String inp = new String(inpbyte);
                String err = new String(errbyte);

                //Please keep log statement or else it will dead loop
                if (inp.contains(":RET=")) {
                    if (inp.contains(":RET=EOF") || err.contains(":RET=EOF")) {
                        Log.w(TAG, "exec:[eof]" + inp);
                    }

                    if (inp.contains(":RET=0")) {
                        Log.w(TAG, "exec:[ok]" + inp);
                        return new VTCommandResult(0, inp, err);
                    } else {
                        Log.w(TAG, "exec:[err]" + inp);
                        return new VTCommandResult(1, inp, err);
                    }
                }
            }
        }
    }

    public void shutdown() {
        mInputReaderThread.interrupt();
        mErrReaderThread.interrupt();
        mProcess.destroy();
    }

    /**
     * A thread class helps to read/write data
     */
    public class InputReaderThread extends Thread {
        private InputStream mInputStream;
        private ByteArrayOutputStream mByteArrayOutputStream;

        public InputReaderThread(InputStream in, ByteArrayOutputStream out) {
            mInputStream = in;
            mByteArrayOutputStream = out;
        }

        @Override
        public void run() {
            if (mInputStream != null) {
                try {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int read = mInputStream.read(buffer);
                        if (read < 0) {
                            synchronized(mWriteLock) {
                                String eof = ":RET=EOF";
                                mByteArrayOutputStream.write(eof.getBytes());
                            }
                            synchronized (mReadLock) {
                                mReadLock.notifyAll();
                            }

                            break;
                        } else if (read > 0) {
                            synchronized(mWriteLock) {
                                mByteArrayOutputStream.write(buffer, 0, read);
                            }
                            synchronized (mReadLock) {
                                mReadLock.notifyAll();
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * A result wrapper for exec()
     */
    public class VTCommandResult {
        public final String mStdout;
        public final String mStderr;
        public final Integer mExitValue;

        VTCommandResult(Integer exit_value_in, String stdout_in, String stderr_in) {
            mExitValue = exit_value_in;
            mStdout = stdout_in;
            mStderr = stderr_in;
        }

        VTCommandResult(Integer exit_value_in) {
            this(exit_value_in, null, null);
        }

        public boolean success() {
            return mExitValue != null && mExitValue == 0;
        }
    }
}
