package cn.ichunzhen.ui.paint.gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Author yuancz
 * @Date 2019/10/18-14:40
 * @Email ichunzhen6@gmail.com
 */
public class RadialGradientView extends View {
    private Paint mPaint;
    private Shader mShader;
    private int[] mColorTwo;
    private int[] mColorThree;


    public RadialGradientView(Context context) {
        super(context);
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorThree = new int[]{
                Color.parseColor("#ff0000"),
                Color.parseColor("#00ff00"),
                Color.parseColor("#0000ff")};
        mColorTwo = new int[]{Color.parseColor("#ff0000"),
                Color.parseColor("#0000ff")};

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //第一排 两色
        mShader = new RadialGradient(200, 200, 40, mColorTwo[0], mColorTwo[1], Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);
        canvas.drawCircle(200, 200, 150, mPaint);

        mShader = new RadialGradient(500, 200, 40, mColorTwo[0], mColorTwo[1], Shader.TileMode.CLAMP);
        mPaint.setShader(mShader);
        canvas.drawCircle(500, 200, 150, mPaint);

        mShader = new RadialGradient(800, 200, 40, mColorTwo[0], mColorTwo[1], Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);
        canvas.drawCircle(800, 200, 150, mPaint);

        //第二排 三色
        mShader = new RadialGradient(200, 500, 40,
                mColorThree,new float[]{0.0f,0.5f,1.0f},Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);
        canvas.drawCircle(200, 500, 150, mPaint);

        mShader = new RadialGradient(500, 500, 40,
                mColorThree,new float[]{0.0f,0.5f,1.0f},Shader.TileMode.CLAMP);
        mPaint.setShader(mShader);
        canvas.drawCircle(500, 500, 150, mPaint);

        mShader = new RadialGradient(800, 500, 40,
                mColorThree,new float[]{0.0f,0.5f,1.0f},Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);
        canvas.drawCircle(800, 500, 150, mPaint);

        //第三排 三色改变渲染色起点位置
        mShader = new RadialGradient(200, 800, 40,
                mColorThree,new float[]{0.5f,0.75f,1.0f},Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);
        canvas.drawCircle(200, 800, 150, mPaint);

        mShader = new RadialGradient(500, 800, 40,
                mColorThree,new float[]{0.5f,0.75f,1.0f},Shader.TileMode.CLAMP);
        mPaint.setShader(mShader);
        canvas.drawCircle(500, 800, 150, mPaint);

        mShader = new RadialGradient(800, 800, 40,
                mColorThree,new float[]{0.5f,0.75f,1.0f},Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);
        canvas.drawCircle(800, 800, 150, mPaint);
        //第四排 三色改变，修改渲染坐标
        mShader = new RadialGradient(50, 1100, 40,
                mColorThree,new float[]{0.0f,0.5f,1.0f},Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);
        canvas.drawCircle(200, 1100, 150, mPaint);

        mShader = new RadialGradient(500, 950, 40,
                mColorThree,new float[]{0.0f,0.5f,1.0f},Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);
        canvas.drawCircle(500, 1100, 150, mPaint);

        mShader = new RadialGradient(950, 1100, 40,
                mColorThree,new float[]{0.0f,0.5f,1.0f}, Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);
        canvas.drawCircle(800, 1100, 150, mPaint);

    }

}
