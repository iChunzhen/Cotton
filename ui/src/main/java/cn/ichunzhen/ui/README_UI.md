# ui



## UI原理与高级绘制

### UI绘制流程
### activity启动流程，源码分析
### UI绘制流程（测量， 布局， 绘制）
#### Paint
    重点
                  mPaint.setShader(new SweepGradient(200, 200, Color.BLUE, Color.RED)); //设置环形渲染器 着色器 ***！！！
                  mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN)); //设置图层混合模式 ***！！！ 18种
                  mPaint.setColorFilter(new LightingColorFilter(0x00ffff, 0x000000)); //设置颜色过滤器   滤镜    ColorFilter
    Paint mPaint = new Paint(); //初始化
          mPaint.setColor(Color.RED);// 设置颜色
          mPaint.setARGB(255, 255, 255, 0); // 设置 Paint对象颜色,范围为0~255
          mPaint.setAlpha(200); // 设置alpha不透明度,范围为0~255
          mPaint.setAntiAlias(true); // 抗锯齿
          mPaint.setStyle(Paint.Style.FILL); //描边效果 还填充效果
          mPaint.setStrokeWidth(4);//描边宽度
             BUTT    (0)
             ROUND   (1)
             SQUARE  
          mPaint.setStrokeCap(Paint.Cap.ROUND); //圆角效果
            MITER   (0)
            ROUND   (1)
            BEVEL   (2);
          mPaint.setStrokeJoin(Paint.Join.MITER);//拐角风格 三个
          
          mPaint.setShader(new SweepGradient(200, 200, Color.BLUE, Color.RED)); //设置环形渲染器 着色器 ***！！！
          
          mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN)); //设置图层混合模式 ***！！！
          
          mPaint.setColorFilter(new LightingColorFilter(0x00ffff, 0x000000)); //设置颜色过滤器
          mPaint.setFilterBitmap(true); //设置双线性过滤 有无马赛克效果 是否平滑
          mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));//设置画笔遮罩滤镜 ,传入度数和样式
          mPaint.setTextScaleX(2);// 设置文本缩放倍数
          mPaint.setTextSize(38);// 设置字体大小
          mPaint.setTextAlign(Paint.Align.LEFT);//对齐方式
          mPaint.setUnderlineText(true);// 设置下划线

          String str = "Android高级工程师";
          Rect rect = new Rect();
          mPaint.getTextBounds(str, 0, str.length(), rect); //测量文本大小，将文本大小信息存放在rect中  rect矩形区域
          mPaint.measureText(str); //获取文本的宽
          mPaint.getFontMetrics(); //获取字体度量对象  各种基准线 用于计算字体高度等数据
#####     mPaint.setShader()设置环形渲染器 着色器  
            定义了平铺的3种模式： 
            static final Shader.TileMode CLAMP: 边缘拉伸.
            
            static final Shader.TileMode MIRROR：在水平方向和垂直方向交替景象, 两个相邻图像间没有缝隙.渐变
                     
            Static final Shader.TillMode REPETA：在水平方向和垂直方向重复摆放,两个相邻图像间有缝隙缝隙.不渐变

        
         /**
         * 1.线性渲染,LinearGradient(float x0, float y0, float x1, float y1, @NonNull @ColorInt int colors[], @Nullable float positions[], @NonNull TileMode tile)
         * (x0,y0)：渐变起始点坐标
         * (x1,y1):渐变结束点坐标
         * color0:渐变开始点颜色,16进制的颜色表示，必须要带有透明度
         * color1:渐变结束颜色
         * colors:渐变数组
         * positions:位置数组，position的取值范围[0,1],作用是指定某个位置的颜色值，如果传null，渐变就线性变化。 数组和颜色数组对应，设置各个颜色的渲染起始位置。可以为null（颜色均分）。
         * tile:用于指定控件区域大于指定的渐变区域时，空白区域的颜色填充方法
         **/
        mShader = new LinearGradient(0, 0, 500, 500, new int[]{Color.RED, Color.BLUE, Color.GREEN}, new float[]{0.f,0.7f,1}, Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);

                /**
                 * 环形渲染，RadialGradient(float centerX, float centerY, float radius, @ColorInt int colors[], @Nullable float stops[], TileMode tileMode)
                 * centerX ,centerY：shader的中心坐标，开始渐变的坐标
                 * radius:渐变的半径
                 * centerColor,edgeColor:中心点渐变颜色，边界的渐变颜色
                 * colors:渐变颜色数组
                 * stoops:渐变位置数组，类似扫描渐变的positions数组，取值[0,1],中心点为0，半径到达位置为1.0f
                 * tileMode:shader未覆盖以外的填充模式。
                 */
                mShader = new RadialGradient(250, 250, 250, new int[]{Color.GREEN, Color.YELLOW, Color.RED}, null, Shader.TileMode.CLAMP);
                mPaint.setShader(mShader);
                canvas.drawCircle(250, 250, 250, mPaint);
                
                     /**
                     * 扫描渲染，SweepGradient(float cx, float cy, @ColorInt int color0,int color1)
                     * cx,cy 渐变中心坐标
                     * color0,color1：渐变开始结束颜色
                     * colors，positions：类似LinearGradient,用于多颜色渐变,positions为null时，根据颜色线性渐变
                     */
                    mShader = new SweepGradient(250, 250, Color.RED, Color.GREEN);
                    mPaint.setShader(mShader);
                    canvas.drawCircle(250, 250, 250, mPaint);
                    
                         /** 
                         * 位图渲染，BitmapShader(@NonNull Bitmap bitmap, @NonNull TileMode tileX, @NonNull TileMode tileY)
                         * Bitmap:构造shader使用的bitmap
                         * tileX：X轴方向的TileMode
                         * tileY:Y轴方向的TileMode
                         *  REPEAT, 绘制区域超过渲染区域的部分，重复排版
                         *  CLAMP， 绘制区域超过渲染区域的部分，会以最后一个像素拉伸排版
                         *  MIRROR, 绘制区域超过渲染区域的部分，镜像翻转排版
                         */
                        mShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
                        mPaint.setShader(mShader);
                        canvas.drawRect(0, 0, 500, 500, mPaint);
                        
                                /**
                                 * 组合渲染，
                                 * ComposeShader(@NonNull Shader shaderA, @NonNull Shader shaderB, Xfermode mode)
                                 * ComposeShader(@NonNull Shader shaderA, @NonNull Shader shaderB, PorterDuff.Mode mode)
                                 * shaderA,shaderB:要混合的两种shader
                                 * Xfermode mode： 组合两种shader颜色的模式
                                 * PorterDuff.Mode mode: 组合两种shader颜色的模式
                                 */
                                BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                                LinearGradient linearGradient = new LinearGradient(0, 0, 1000, 1600, new int[]{Color.RED, Color.GREEN, Color.BLUE}, null, Shader.TileMode.CLAMP);
                                mShader = new ComposeShader(bitmapShader, linearGradient, PorterDuff.Mode.MULTIPLY);
                                mPaint.setShader(mShader);
                                canvas.drawCircle(250, 250, 250, mPaint);
                                
#####        图层混合模式      PorterDuff.Mode.    18种              
                                src 原图像
                                dst 目标图像
                                //1.ComposeShader
                                //2.画笔Paint.setXfermode()
                                //3.PorterDuffColorFilter
                                
                                先禁止硬件加速，高版本Android系统不支持
#####    mPaint.setColorFilter(new LightingColorFilter(0x00ffff, 0x000000)); //设置颜色过滤器   滤镜    ColorFilter
       
       LightingColorFilter(@ColorInt int mul, @ColorInt int add)
        /**
         * R' = R * mul.R / 0xff + add.R
         * G' = G * mul.G / 0xff + add.G
         * B' = B * mul.B / 0xff + add.B
         */
        //红色去除掉   
        LightingColorFilter lighting = new LightingColorFilter(0x00ffff,0x000000);
        mPaint.setColorFilter(lighting);
        canvas.drawBitmap(mBitmap, 10,10, mPaint);
                
                 PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
                 mPaint.setColorFilter(porterDuffColorFilter);
                 canvas.drawBitmap(mBitmap, 100, 0, mPaint);


#### Cavans
    Cavans变换/状态保持/粒子效果
        变换
            canvas.translate(50,50);
        保持  canvas.save();  canvas.restore();   .restoreToCount(layerId);
         1.canvas内部对于状态的保存存放在栈中
         2.可以多次调用save保存canvas的状态，并且可以通过getSaveCount方法获取保存的状态个数
         3.可以通过restore方法返回最近一次save前的状态，也可以通过restoreToCount返回指定save状态。指定save状态之后的状态全部被清除
         4.saveLayer可以创建新的图层，之后的绘制都会在这个图层之上绘制，直到调用restore方法
         注意：绘制的坐标系不能超过图层的范围， saveLayerAlpha对图层增加了透明度信息

####贝塞尔曲线及应用    BezierView
        二阶  mPath.rQuadTo(300, 100, -90, 400);
        三阶  mPath.cubicTo(400, 200,10, 500,300, 700);
#### path
    1.Path path = new Path(); path.lineTo(0,200);
    canvas.drawPath(mPath, mPaint);

###  事件传递机制	dispatchevent
###  属性动画	animator	通过反射设置view的属性产生动画效果
	ObjectAnimator	 实现VSYNCManager接收刷新信号 提供设置方法设置属性插值器等 开始结束方法
                     接收vsync信号后通过FloatPropertyValuesHolder修改view属性
	FloatPropertyValuesHolder	修改view属性的实现类 通过反射获取对应属性 并set属性值
	MyFloatKeyframe	 关键帧保存数据的实体类
	MyKeyframeSet	 1提供ofFloat方法	通过插值器和设置的动画属性计算关键帧数据数组	2根据插值器fraction获取关键帧之间哎i俺的某一个具体帧
	TimeInterpolator 插值器 提供getInterpolation方法计算执行进度fraction
			
	
	ObjectAnimator objectAnimator = ObjectAnimator.
	ofFloat(button, "scaleX", 2f);
	objectAnimator.setDuration(3000);
	objectAnimator.start();


屏幕适配
    限定符适配   dimens 增加大小 
    百分比适配  抛弃dp px 用系统的presentLayout  不方便自定义控件
    代码动态适配  UiUtils

  全面屏适配

  toolbar   actionBar升级版


