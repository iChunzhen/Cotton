package cn.ichunzhen.ui.dispatchevent;

public class Activity {
    public static void main(String[] args) {
        ViewGroup viewGroup = new ViewGroup(0, 0, 1080, 1920);
        viewGroup.setName("顶级容器");

        ViewGroup viewGroup1 = new ViewGroup(0, 0, 500, 500);
        viewGroup1.setName("第二级容器");


        View view = new View(0, 0, 200, 200);
        viewGroup1.addView(view);
        viewGroup.addView(viewGroup1);

        MotionEvent motionEvent = new MotionEvent(100, 100);
        motionEvent.setActionMasked(MotionEvent.ACTION_DOWN);

//        顶级容器分发
        viewGroup.dispatchTouchEvent(motionEvent);
    }
}
