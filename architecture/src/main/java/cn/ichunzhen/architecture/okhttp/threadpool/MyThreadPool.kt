package cn.ichunzhen.architecture.okhttp.threadpool

import java.util.concurrent.*

/**
 * @Author yuancz
 * @Date 2020/6/4-10:43
 * @Email ichunzhen6@gmail.com
 */
// Java 1.5 线程池 复用 线程池（线程，如何让这么多线程复用，线程管理工作）
// Executor
//  --- ExecutorService
//      --- AbstractExecutorService
//            ---- ThreadPoolExecutor
//Executors 辅助生成线程池
// ThreadPoolExecutor 学习此类

// 线程池里面 只有一个核心线程在跑任务
/**
 * todo 参数一：corePoolSize 核心线程数
 * todo 参数二：maximumPoolSize 线程池非核心线程数 线程池规定大小
 * todo 参数三/四：时间数值keepAliveTime， 单位：时分秒  60s
 *                正在执行的任务Runnable20 < corePoolSize --> 参数三/参数四 才会起作用
 *                作用：Runnable1执行完毕后 闲置60s，如果过了闲置60s,会回收掉Runnable1任务,，如果在闲置时间60s 复用此线程Runnable1
 *
 * todo 参数五：workQueue队列 ：会把超出的任务加入到队列中 缓存起来
 *
 */
//val executorService: ExecutorService =
//    ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, LinkedBlockingDeque<Runnable>());
//
//
//val executorService: ExecutorService =
//    ThreadPoolExecutor(5, 1, 60, TimeUnit.SECONDS, LinkedBlockingDeque<Runnable>());
//
//val executorService: ExecutorService =
//    ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, LinkedBlockingDeque<Runnable>());

// 想实现缓存 线程池方案
val executorService1: ExecutorService =
    ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, LinkedBlockingDeque<Runnable>());

var executorService: ExecutorService = ThreadPoolExecutor(
    0,
    Int.MAX_VALUE,
    60,
    TimeUnit.SECONDS,
    SynchronousQueue<Runnable>(),
    object : ThreadFactory {
        //创建线程的工厂
        override fun newThread(r: Runnable?): Thread? {
            val thread = Thread(r)
            thread.name = "MyOkHttp Dispatcher"
            thread.isDaemon = false // 不是守护线程
            return thread
        }
    })
// 》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》


// Java设计者 考虑到了不用使用线程池的参数配置，提供了API

var one = Executors.newCachedThreadPool(); // 缓存线程池方案


var two = Executors.newSingleThreadExecutor(); // 线程池里面只有一个 核心线程 最大线程数 也只有一个

// new ThreadPoolExecutor(5, 5, )
var three = Executors.newFixedThreadPool(5); // 指定固定大小线程池
fun main() {
    for (i in 1..20) {
        executorService.execute(Runnable {
            run {
                try {
                    Thread.sleep(1000)
                    println("${i}.当前线程，执行耗时任务，线程是：${Thread.currentThread().name}")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        })
    }
}