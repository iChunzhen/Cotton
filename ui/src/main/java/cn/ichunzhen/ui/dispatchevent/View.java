package cn.ichunzhen.ui.dispatchevent;


import com.ichunzhen.dispatchevent.listener.OnClickListener;
import com.ichunzhen.dispatchevent.listener.OnTouchListener;

/**
 * @Author yuancz
 * @Date 2019/11/5-9:17
 * @Email ichunzhen6@gmail.com
 */
public class View {
    private int left;
    private int top;
    private int right;
    private int bottom;
    private OnTouchListener mOnTouchListener;
    private OnClickListener onClickListener;

    public void setOnTouchListener(OnTouchListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public View(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean isContainer(int x, int y) {
        if (x >= left && x < right && y >= top && y < bottom) {
            return true;
        }
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        System.out.println(" view  dispatchTouchEvent ");
        boolean handled = false;
        if (mOnTouchListener != null && mOnTouchListener.onTouchEvent(this, motionEvent)) {
            handled = true;
        }
        if (!handled && onTouchEvent(motionEvent)) {
            handled = true;
        }
        return handled;
    }

    private boolean onTouchEvent(MotionEvent motionEvent) {
        if (onClickListener != null) {
            onClickListener.onClick(this);
            return true;
        }
        return false;
    }
}
