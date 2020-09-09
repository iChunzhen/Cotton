package cn.ichunzhen.ui.dispatchevent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuancz
 * @Date 2019/11/5-9:17
 * @Email ichunzhen6@gmail.com
 */
public class ViewGroup extends View {

    public ViewGroup(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    private String name;
    List<View> childList = new ArrayList<>();
    private View[] mChildren = new View[0];
    private TouchTarget mFirstTouchTarget;

    public void addView(View view) {
        if (view == null) {
            return;
        }
        childList.add(view);
        mChildren = childList.toArray(new View[mChildren.length]);
    }

    /**
     * @param motionEvent 事件分发入口
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        System.out.println(name + " dispatchTouchEvent ");
        boolean handled = false;//是否处理事件
        boolean intercepted = onInterceptTouchEvent(motionEvent);//是否拦截
        //TouchTarget模式 内存缓存
        TouchTarget newTouchTarget = null;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != MotionEvent.ACTION_CANCEL && !intercepted) {
            if (actionMasked == MotionEvent.ACTION_DOWN) {
                final View[] children = mChildren;
                for (int i = children.length - 1; i >= 0; i--) {
                    View child = mChildren[i];
                    if (!child.isContainer(motionEvent.getX(), motionEvent.getY())) {
                        continue;
                    }
                    if (dispatchTransformedTouchEvent(child, motionEvent)) {
                        handled = true;
                        newTouchTarget = addTouchTarget(child);
                        break;
                    }
                }
                if (mFirstTouchTarget == null) {
                    handled = dispatchTransformedTouchEvent(null, motionEvent);
                }
            }
        } else {
            handled = true;
        }
        return handled;
    }

    private TouchTarget addTouchTarget(View child) {
        TouchTarget target = TouchTarget.obtain(child);
        target.next = mFirstTouchTarget;
        mFirstTouchTarget = target;
        return mFirstTouchTarget;
    }

    private boolean dispatchTransformedTouchEvent(View child, MotionEvent motionEvent) {
        boolean handled = false;
        if (child == null) {
            handled = super.dispatchTouchEvent(motionEvent);
        } else {
            handled = child.dispatchTouchEvent(motionEvent);

        }
        return handled;

    }

    private boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    /**
     * 回收池 一个对象实现回收池
     */
    private static final class TouchTarget {
        public View child;//当前缓存的view
        private static TouchTarget sRecycleBin;
        private static final Object sRecyleLock = new Object[0];
        private TouchTarget next;
        private static int sRecycleCount;

        public static TouchTarget obtain(View view) {
            TouchTarget target;
            synchronized (sRecyleLock) {
                if (sRecycleBin == null) {
                    target = new TouchTarget();
                } else {
                    target = sRecycleBin;
                    sRecycleBin = target.next;
                    sRecycleCount--;
                    target.next = null;
                }
            }
            target.child = view;
            return target;
        }

        public void recycle() {
            if (child == null) {
                throw new IllegalStateException("已经被回收过了");
            }
            synchronized (sRecyleLock) {
                if (sRecycleCount <= 32) {
                    next = sRecycleBin;
                    sRecycleCount += 1;
                    sRecycleBin = this;
                }
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }
}
