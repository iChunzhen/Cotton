package cn.ichunzhen.ui.path.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class PathView extends View {

    private Path mPath = new Path();
    private Path mPath2 = new Path();
    private Paint mPaint = new Paint();

    public PathView(Context context) {
        super(context);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPath.addCircle(200, 200, 100, Path.Direction.CW);
//        mPath2.addCircle(300, 300, 100, Path.Direction.CW);
//        mPath.op(mPath2,Path.Op.DIFFERENCE);//路径1-路径2
//        mPath.op(mPath2,Path.Op.INTERSECT);//路径1与路径2交集
//        mPath.op(mPath2,Path.Op.UNION);//路径1+路径2
//        mPath.op(mPath2,Path.Op.XOR);//路径1与路径2不相交的部分
//        mPath.op(mPath2,Path.Op.REVERSE_DIFFERENCE);//路径1与路径2不相交的部分
//        canvas.drawPath(mPath, mPaint);
//        canvas.drawPath(mPath2, mPaint);
//        mPath.addCircle(300, 300, 100, Path.Direction.CW);
//        mPath.setFillType(Path.FillType.EVEN_ODD);//路径1与路径2不相交的部分
//        mPath.setFillType(Path.FillType.INVERSE_WINDING);//反向绘制路径区域外
//        mPath.setFillType(Path.FillType.INVERSE_WINDING);//反向绘制路径区域外
//        mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);//绘制相交区域及外部区域
//        canvas.drawPath(mPath, mPaint);

//        mPath.moveTo(100, 100);//移动
//        mPath.rMoveTo(100, 100);
////        mPath.lineTo(200, 300);//连线
//        mPath.rLineTo(100, 100);
//        mPath.lineTo(100, 300);
//        mPath.close();//闭合
//        canvas.drawPath(mPath, mPaint);

        //添加子图形addXXX
//        mPath.addCircle(100, 100, 50, Path.Direction.CW);
//        mPath.addArc(200, 200, 300, 300, 0, -90);
//        mPath.arcTo(200, 200, 300, 300, 0, 90, true);
//        mPath.addOval(300, 300, 400, 450, Path.Direction.CW);
//        mPath.addRect(100, 400, 300, 500, Path.Direction.CW);
//        mPath.addRoundRect(100, 600, 300, 700, 20, 40, Path.Direction.CW);
        //二阶贝塞尔曲线
        mPath.moveTo(100, 100);
//        mPath.quadTo(400, 200, 10, 500);
        canvas.drawCircle(100, 100, 10, mPaint);
        canvas.drawCircle(400, 200, 10, mPaint);
        canvas.drawCircle(10, 500, 10, mPaint);
        canvas.drawCircle(300, 700, 10, mPaint);
//        mPath.rQuadTo(300, 100, -90, 400);
        //三阶贝塞尔曲线
        mPath.cubicTo(400, 200,10, 500,300, 700);
        canvas.drawPath(mPath, mPaint);

    }
}
