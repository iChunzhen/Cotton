package cn.ichunzhen.ui.path.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BezierView extends View {
    private Paint mPaint;
    private Paint mLinePointPaint;
    private Path mPath;
    private List<PointF> mControlPoints;//控制点集合（包含数据点）

    public BezierView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mLinePointPaint = new Paint();
        mLinePointPaint.setAntiAlias(true);
        mLinePointPaint.setStrokeWidth(4);
        mLinePointPaint.setStyle(Paint.Style.STROKE);
        mLinePointPaint.setColor(Color.GRAY);
        mPath = new Path();
        mControlPoints = new ArrayList<>();
        init();
    }

    private void init() {
        mControlPoints.clear();
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            int x = random.nextInt(800) + 200;
            int y = random.nextInt(800) + 200;
            PointF pointF = new PointF(x, y);
            mControlPoints.add(pointF);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //控制点及控制点的连线
        int size = mControlPoints.size();
        PointF pointF;
        for (int i = 0; i < size; i++) {
            pointF = mControlPoints.get(i);
            if (i > 0) {
                mLinePointPaint.setColor(Color.GRAY);
                //控制点连线
                canvas.drawLine(mControlPoints.get(i - 1).x, mControlPoints.get(i - 1).y, pointF.x, pointF.y, mLinePointPaint);
            }
            //起点、终点换颜色
            if (i == 0) {
                mLinePointPaint.setColor(Color.RED);
            } else if (i == size - 1) {
                mLinePointPaint.setColor(Color.BLUE);
            }
            canvas.drawCircle(pointF.x, pointF.y, 10, mLinePointPaint);
        }
        //曲线连接
//        buildBezierPoints();
        calculate();
        canvas.drawPath(mPath, mPaint);
    }

    private ArrayList<PointF> calculate() {
        mPath.reset();
        //控制点个数(number-1阶)
        int number = mControlPoints.size();
        //小于2阶省略
        if (number < 2) {
            return null;
        }
        ArrayList<PointF> points = new ArrayList<>();

        //计算杨辉三角
        int[] mi = new int[number];
        mi[0] = mi[1] = 1;//第二层（一阶常数项）
        for (int i = 3; i <= number; i++) {
            //得到上一层的数据
            int[] t = new int[i - 1];
            for (int j = 0; j < t.length; j++) {
                t[j] = mi[j];
            }
            //计算当前行的数据
            mi[0] = mi[i - 1] = 1;
            for (int j = 0; j < i - 2; j++) {
                mi[j + 1] = t[j] + t[j + 1];
            }
        }
        //计算坐标点
        for (int i = 0; i < 1000; i++) {
            float t = (float) i / 1000;
            //分别计算x,y坐标
            //计算各项和(𝑛¦𝑖) 𝑃_𝑖 〖(1−𝑡)〗^(𝑛−i) 𝑡^𝑖
            PointF pointF = new PointF();
            for (int j = 0; j < number; j++) {
                pointF.x += mi[j] * mControlPoints.get(j).x * Math.pow(1 - t, number - 1 - j) * Math.pow(t, j);
                pointF.y += mi[j] * mControlPoints.get(j).y * Math.pow(1 - t, number - 1 - j) * Math.pow(t, j);
            }
            points.add(pointF);
            //0 moveTo
            if (i == 0) {
                mPath.moveTo(pointF.x, pointF.y);
            } else {
                mPath.lineTo(pointF.x, pointF.y);
            }
        }
        return points;
    }

    private ArrayList<PointF> buildBezierPoints() {
        mPath.reset();
        ArrayList<PointF> points = new ArrayList<>();
        int order = mControlPoints.size() - 1;//阶数
        //份数
        float delta = 1.0f / 1000;
        for (float t = 0; t <= 1; t += delta) {
            //bezier点集
            PointF pointF = new PointF(deCastelJau(order, 0, t, true), deCastelJau(order, 0, t, false));//计算在曲线上色位置
            points.add(pointF);
            if (points.size() == 1) {
                mPath.moveTo(points.get(0).x, points.get(0).y);
            } else {
                mPath.lineTo(pointF.x, pointF.y);
            }
        }
        return points;
    }
    /**
     * 通用计算公式
     *
     * @param positions 贝塞尔曲线控制点坐标
     * @param precision 精度，需要计算的该条贝塞尔曲线上的点的数目
     * @return 该条贝塞尔曲线上的点（二维坐标）
     */
    public float[][] calculate(float[][] positions, int precision) {
        //维度，坐标轴数（二维坐标，三维坐标...）
        int dimension = positions[0].length;

        //贝塞尔曲线控制点数（number-1阶数）
        int number = positions.length;

        //控制点数不小于 2 ，至少为二维坐标系
        if (number < 2 || dimension < 2)
            return null;

        float[][] result = new float[precision][dimension];

        //计算杨辉三角
        int[] mi = new int[number];
        mi[0] = mi[1] = 1;//第二层（一阶时常数项）
        for (int i = 3; i <= number; i++) {
            //得到上一层的数据
            int[] t = new int[i - 1];
            for (int j = 0; j < t.length; j++) {
                t[j] = mi[j];
            }
            //计算当前行的数据
            mi[0] = mi[i - 1] = 1;
            for (int j = 0; j < i - 2; j++) {
                mi[j + 1] = t[j] + t[j + 1];
            }
        }

        //计算坐标点
        for (int i = 0; i < precision; i++) {
            float t = (float) i / precision;
            //分别计算各轴上的坐标
            for (int j = 0; j < dimension; j++) {
                //计算各项和(𝑛¦𝑖) 𝑃_𝑖 〖(1−𝑡)〗^(𝑛−i) 𝑡^𝑖
                float temp = 0.0f;
                for (int k = 0; k < number; k++) {
                    temp += mi[k] * positions[k][j] * Math.pow(1 - t, number - 1 - k) * Math.pow(t, k);
                }
                result[i][j] = temp;
            }
        }
        return result;
    }
    /**
     * p(i,j) =  (1-t) * p(i-1,j)  +  t * p(i-1,j+1);
     *
     * @param i          阶数
     * @param j          控制点
     * @param t          时间
     * @param calculateX 计算哪个坐标值 true=x
     * @return
     */
    private float deCastelJau(int i, int j, float t, boolean calculateX) {
        if (i == 1) {
            return calculateX ? (1 - t) * mControlPoints.get(j).x + t * mControlPoints.get(j + 1).x :
                    (1 - t) * mControlPoints.get(j).y + t * mControlPoints.get(j + 1).y;
        } else {
            return (1 - t) * deCastelJau(i - 1, j, t, calculateX) + t * deCastelJau(i - 1, j + 1, t, calculateX);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            init();
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
