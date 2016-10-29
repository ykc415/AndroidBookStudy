package com.sunghyun.android.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YKC on 2016. 10. 29..
 */

public class BoxDrawingView extends View {

    private static final String TAG = "BoxDrawingView";

    private Box mCurrentBox;
    private List<Box> mBoxen = new ArrayList<>();
    private Paint mBoxPaint;
    private Paint mBackgroundPaint;

    // 코드에서 뷰를 생성할 때 사용한다
    public BoxDrawingView(Context context) {
        this(context, null);
    }

    // XML로부터 뷰를 인플레이트 할 때 사용한다.
    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 반투명의 붉은색으로 박스를 그린다
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        // 배경을 황백색으로 칠한다.
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 사용자의 손가락이 화면을 터치함
                action = "ACTION_DOWN";
                // 그리는 상태 정보를 리셋한다
                mCurrentBox = new Box(current);
                mBoxen.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE: // 사용자가 손가락을 화면 위에서 움직임
                action = "ACTION_MOVE";
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(current);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP: // 사용자가 화면에서 손가락을 뗌
                action = "ACTION_UP";
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL: // 부모 뷰가 터치 이벤트를 가로챔
                action = "ACTION_CANCEL";
                mCurrentBox = null;
                break;
        }

        Log.i(TAG, action + " at x=" + current.x + ", y=" + current.y);
        return true;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 배경을 채운다.
        canvas.drawPaint(mBackgroundPaint);

        for (Box box : mBoxen) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }
    }
}
