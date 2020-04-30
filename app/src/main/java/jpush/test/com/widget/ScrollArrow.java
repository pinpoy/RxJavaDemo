package jpush.test.com.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jpush.test.com.R;

public class ScrollArrow extends View {

    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作
    private Paint mDeafultPaint;

    public ScrollArrow(Context context) {
        super(context);
    }

    public ScrollArrow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow, options);
        mDeafultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDeafultPaint.setStyle(Paint.Style.STROKE); //描边加填充
        mDeafultPaint.setStrokeWidth(8);
        mMatrix = new Matrix();


    }

    public ScrollArrow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);      // 平移坐标系

        Path path = new Path();                                 // 创建 Path

        path.addCircle(0, 0, 20, Path.Direction.CW);           // 添加一个圆形
//
//        PathMeasure measure = new PathMeasure(path, false);     // 创建 PathMeasure
//
//        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
//        if (currentValue >= 1) {
//            currentValue = 0;
//        }
//
//        measure.getPosTan(measure.getLength() * currentValue, pos, tan);        // 获取当前位置的坐标以及趋势
//
//        mMatrix.reset();                                                        // 重置Matrix
//        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度
//
//        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
//        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合
//
        canvas.drawPath(path, mDeafultPaint);                                   // 绘制 Path
//        canvas.drawBitmap(mBitmap, mMatrix, mDeafultPaint);                     // 绘制箭头
//
//        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("ScrollArrow", "ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d("ScrollArrow", "ACTION_MOVE");
                return false;
            case MotionEvent.ACTION_UP:
                Log.d("ScrollArrow", "ACTION_UP");
                return false;
            case MotionEvent.ACTION_CANCEL:
                Log.d("ScrollArrow", "ACTION_CANCEL");
                return false;


        }
        return super.onTouchEvent(event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
}









