package cn.ichunzhen.ui.dispatchevent.listener;

import com.ichunzhen.dispatchevent.MotionEvent;
import com.ichunzhen.dispatchevent.View;

/**
 * @Author yuancz
 * @Date 2019/11/5-9:20
 * @Email ichunzhen6@gmail.com
 */
public interface OnTouchListener {
    boolean onTouchEvent(View view, MotionEvent motionEvent);
}
