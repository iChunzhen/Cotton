## Cotton
**安卓学习笔记	1Byte = 8bit	bit代表二进制数位 **
## 性能优化
### 多维度分析性能优化
##### app启动
	Application中进行优化 懒加载分为主线程和异步线程 减少代码执行时间，将执行的代码能放到异步线程 处理好依赖关系
##### 黑白屏解决方案
	SplashActivity设置默认主题给一个默认背景
##### 代码优化
	抽取	简化	复用
##### UI渲染流程及优化
	布局层级 尽量减少层级
	绘制优化 只绘制显示部分 层叠部分减少绘制
##### Java虚拟机垃圾回收机制内存泄漏
##### 内存优化（泄漏，抖动），bitmap内存管理
	handler使用强弱虚引用
	Profiler 生成内存片段 根据对象有无引用关系判断是否为内存泄漏 进行处理
	内存频繁的分配和回收，频繁的gc会导致UI卡顿，严重的时候导致oom
##### 大图加载和哈夫曼算法打造无损压缩技术
	NeBigView	内存复用	手势识别	滚动类	缩放手势识别
	哈夫曼算法	通过哈夫曼树减少色值所占字节数 色值使用次数多的位数越少 减少图片的内存占用
##### Android系统耗电统计与分析
### 高级应用安全技术
##### 防反编译 混淆打包并加固
	Apktoo	反编译DEX为smali文件	支持重打包
	dex2jar	JD-GU
	加固原理 修改application入口代理Application内存加载原有加密后DEX：加载原Application	ClassLoader设置Application引用替换
##### https防抓包
	证书中有公钥私钥等用于加解密
	fiddler等中间对两端伪造证书 可验证证书host防止抓包
##### APP网络传输安全
	使用https
	设置信任证书	自定义信任策略使用自己的证书
### 3 性能优化
##### 热修复
	//获取系统ClassLoader的pathList对象
	Object pathList = Reflect.on(loader).field("pathList").get();
	// 调用makePathElements构造补丁的dexElements
	ArrayList<IOException> suppressedExceptions = new ArrayList<>();
	Object[] patchDexElements = makePathElements(pathList, extraDexFiles,
	FileUtil.getDexOptDir(context), suppressedExceptions);
	//将补丁Dex注入到系统ClassLoader的pathList对象的dexElements的最前面
	expandElementsArray(pathList, patchDexElements);
##### Apk瘦身
	图片压缩WebP转换方式	PNG压缩	Jpeg图片压缩
	纯色图片代码实现	能用代码实现的信息尽量采用代码实现
	减少图片资源放置份数
	检测删除无用资源	布局String 色值等
	减少枚举使用
	减少语言包种类
	去除重复库
	压缩媒体资源
	so动态下发加载
##### Protocol Buffer