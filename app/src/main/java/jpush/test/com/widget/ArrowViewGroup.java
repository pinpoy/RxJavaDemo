package jpush.test.com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class ArrowViewGroup extends RelativeLayout {


    public ArrowViewGroup(Context context) {
        super(context);
    }

    public ArrowViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArrowViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d("onTouchEvent", "ACTION_MOVE");
                return false;
            case MotionEvent.ACTION_UP:
                Log.d("onTouchEvent", "ACTION_UP");
                return false;
            case MotionEvent.ACTION_CANCEL:
                Log.d("onTouchEvent", "ACTION_CANCEL");
                return false;


        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                Log.d("onTouchEvent", "ACTION_MOVE");
//                return true;
//            case MotionEvent.ACTION_UP:
//                Log.d("onTouchEvent", "ACTION_UP");
//                return false;
//            case MotionEvent.ACTION_CANCEL:
//                Log.d("onTouchEvent", "ACTION_CANCEL");
//                return false;
//
//
//        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.d("ArrowViewGroup", "ACTION_MOVE");
                return false;
            case MotionEvent.ACTION_UP:
                Log.d("ArrowViewGroup", "ACTION_UP");
                return false;
            case MotionEvent.ACTION_CANCEL:
                Log.d("ArrowViewGroup", "ACTION_CANCEL");
                return false;


        }
        return super.dispatchTouchEvent(ev);
    }
}
