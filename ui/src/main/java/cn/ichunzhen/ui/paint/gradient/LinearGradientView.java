package cn.ichunzhen.ui.paint.gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import cn.ichunzhen.ui.CommonUtil;


/**
 * @Author yuancz
 * @Date 2019/10/18-14:02
 * @Email ichunzhen6@gmail.com
 */
public class LinearGradientView extends View {
    private Paint mPaint;
    private LinearGradient mLinearGradient;

    private int mWidthNum = 3;
    private int mHeightNum = 3;
    private int[] colorThree;
    private int[] colorTwo;
    private int mSpace;
    private float mWidth = 0;
    private float mRectRadius = 5;
    private String mContentText = "非淡泊无以明智，非宁静无以致远";


    public LinearGradientView(Context context) {
        super(context);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(CommonUtil.sp2px(16, getContext()));
        mRectRadius = CommonUtil.dp2px((int) mRectRadius, getContext());
        colorThree = new int[]{
                Color.parseColor("#ff0000"),
                Color.parseColor("#00ff00"),
                Color.parseColor("#0000ff")};
        colorTwo = new int[]{Color.parseColor("#ff0000"),
                Color.parseColor("#0000ff")};
        mSpace = (int) CommonUtil.dp2px(2, getContext());
        mPaint.setColor(Color.parseColor("#AAAAAA"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLinearRound(canvas);

    }

    public void drawLinearRound(Canvas canvas) {
        //1 绘制两色渐变
        int rectHeight = CommonUtil.dp2px(20, getContext());
        int space = 10;
        //水平clamp
        mLinearGradient = new LinearGradient(0, 0, getMeasuredWidth() / 2,
                0, colorTwo[0], colorTwo[1], Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
        canvas.drawRect(0, 0 + space, getMeasuredWidth(), rectHeight + space, mPaint);
        // 水平mirror
        mLinearGradient = new LinearGradient(0, 0, getMeasuredWidth() / 2,
                0, colorTwo[0], colorTwo[1], Shader.TileMode.MIRROR);
        mPaint.setShader(mLinearGradient);
        canvas.drawRect(0, rectHeight + space * 2, getMeasuredWidth(),
                (rectHeight << 1) + space * 2, mPaint);
        //水平repeat
        mLinearGradient = new LinearGradient(0, 0, getMeasuredWidth() / 2,
                0, colorTwo[0], colorTwo[1], Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        canvas.drawRect(0, (rectHeight << 1) + space * 3, getMeasuredWidth(),
                (rectHeight << 1) + rectHeight + space * 3, mPaint);
        //对角线mirror渐变
        mLinearGradient = new LinearGradient(0, 0, 20, 20,
                colorTwo[0], colorTwo[1], Shader.TileMode.MIRROR);
        mPaint.setShader(mLinearGradient);
        canvas.drawRect(0, (rectHeight << 1) + rectHeight + space * 4, getMeasuredWidth(),
                (rectHeight << 2) + space * 4, mPaint);

        //计算
        if (mWidth < 5) {
            mWidth = (getMeasuredWidth() - (mWidthNum + 1) * mSpace) / mWidthNum;
            mHeightNum = (int) (getMeasuredHeight() - CommonUtil.dp2px(120, getContext()) / mWidth);
        }
        //2 绘制三色渐变
        int count = 0;
        RectF rectF = new RectF();
        for (int i = 0; i < mHeightNum; i++) {
            rectF.top = i * mWidth + (i + 1) * mSpace + (int) CommonUtil.dp2px(120, getContext());
            rectF.bottom = rectF.top + mWidth;
            for (int j = 0; j < mWidthNum; j++) {
                rectF.left = j * mWidth + (j + 1) * mSpace;
                rectF.right = rectF.left + mWidth;
                count++;
                if (count > 9) {
                    break;
                }
                switch (count) {
                    case 1:
                        mLinearGradient = new LinearGradient(rectF.left, rectF.top,
                                rectF.left + (rectF.right - rectF.left) / 2, rectF.top + (rectF.bottom - rectF.top) / 2, colorThree,
                                new float[]{0.0f, 0.66f, 1.0f}, Shader.TileMode.REPEAT);
                        mPaint.setShader(mLinearGradient);
                        break;
                    case 2:
                        mLinearGradient = new LinearGradient(rectF.left, rectF.top,
                                rectF.left + (rectF.right - rectF.left) / 2, rectF.top + (rectF.bottom - rectF.top) / 2, colorThree,
                                new float[]{0.0f, 0.66f, 1.0f}, Shader.TileMode.MIRROR);
                        mPaint.setShader(mLinearGradient);
                        break;
                    case 3:
                        mLinearGradient = new LinearGradient(rectF.left, rectF.top,
                                rectF.left + (rectF.right - rectF.left) / 2, rectF.top + (rectF.bottom - rectF.top) / 2, colorThree,
                                new float[]{0.0f, 0.66f, 1.0f}, Shader.TileMode.CLAMP);
                        mPaint.setShader(mLinearGradient);
                        break;
                    case 4:
                        mLinearGradient = new LinearGradient(rectF.left, rectF.top,
                                rectF.left + (rectF.right - rectF.left) / 2, rectF.top, colorThree,
                                new float[]{0.0f, 0.66f, 1.0f}, Shader.TileMode.REPEAT);
                        mPaint.setShader(mLinearGradient);
                        break;
                    case 5:
                        mLinearGradient = new LinearGradient(rectF.left, rectF.top,
                                rectF.left + (rectF.right - rectF.left) / 2, rectF.top, colorThree,
                                new float[]{0.0f, 0.66f, 1.0f}, Shader.TileMode.MIRROR);
                        mPaint.setShader(mLinearGradient);
                        break;
                    case 6:
                        mLinearGradient = new LinearGradient(rectF.left, rectF.top,
                                rectF.left + (rectF.right - rectF.left) / 2, rectF.top, colorThree,
                                new float[]{0.0f, 0.66f, 1.0f}, Shader.TileMode.CLAMP);
                        mPaint.setShader(mLinearGradient);
                        break;
                    case 7:
                        mLinearGradient = new LinearGradient(rectF.left, rectF.top,
                                rectF.right, rectF.bottom, colorThree,
                                new float[]{0.0f, 0.66f, 1.0f}, Shader.TileMode.REPEAT);
                        mPaint.setShader(mLinearGradient);
                        break;
                    case 8:
                        mLinearGradient = new LinearGradient(rectF.left, rectF.bottom,
                                rectF.right, rectF.bottom, colorThree,
                                new float[]{0.0f, 0.66f, 1.0f}, Shader.TileMode.REPEAT);
                        mPaint.setShader(mLinearGradient);
                        break;
                    case 9:
                        mLinearGradient = new LinearGradient(rectF.left, rectF.bottom,
                                rectF.right, rectF.bottom, colorThree,
                                new float[]{0.5f, 0.75f, 1.0f}, Shader.TileMode.REPEAT);
                        mPaint.setShader(mLinearGradient);
                        break;
                    default:
                        break;
                }
                canvas.drawRoundRect(rectF, mRectRadius, mRectRadius, mPaint);

            }
        }
    }
}
