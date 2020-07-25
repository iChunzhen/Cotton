package cn.ichunzhen.architecture.okhttp.ok

import okhttp3.internal.wait
import java.util.*
import java.util.concurrent.*

/**
 * 连接池
 * @Author yuancz
 * @Date 2020/6/11-13:49
 * @Email ichunzhen6@gmail.com
 */
class ConnectionPool {
    /**
     * 队列，专门去存放（连接对象）
     * 连接池 的存放容器
     */
    lateinit var httpConnectionDeque: Deque<HttpConnection>

    val cleanRunnableFlag = false

    /**
     * 检查的机制
     *
     * 每隔一分钟，就去检查，连接池 里面的连接是否可用，如果不可用，就会移除
     * 最大允许闲置时间
     *
     */
    var keepAlive: Long = 0

    init {
        httpConnectionDeque = ArrayDeque()
        //一分钟
        keepAlive = TimeUnit.MINUTES.toMillis(1)
    }

    /**
     * 开启一个线程 专门去检查 连接池里面的 （连接对象）
     *
     * 清理连接池里面的（连接对象）
     */
    private var cleanRunnable: Runnable? = Runnable {
        while (true) {
            // 当前的时间
            val nextCheckCleamTime: Long = clean(System.currentTimeMillis())
            if (-1L == nextCheckCleamTime) {
                return@Runnable  // while (true) 结束了
            }
            if (nextCheckCleamTime > 0) {
                // 等待一段时间后，再去检查 是否要去清理
                synchronized(this@ConnectionPool) {
                    try {
                        this@ConnectionPool.wait(nextCheckCleamTime)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }


    /**
     * 清理 那些 连接对象
     */
    private fun clean(now: Long): Long {
        var idleRecordSave = -1L;

        synchronized(this) {
            httpConnectionDeque.forEach continuing@{
                // TODO 我们添加了一个连接对象，超过了（最大闲置时间）就会移除这个连接对象

                // 计算出来的闲置时间
                var idleTime=now-it.lastUseTime

                if (idleTime > keepAlive) { // 大于最大允许的闲置时间了（一分钟）
                    // 移除
                    httpConnectionDeque.remove(it)
                    // 关闭Socket
                    it.closeSocket();
                    // 清理 那些 连接对象
                    return@continuing;
                }
                // 得到最长闲置时间 (计算)
                if (idleRecordSave < idleTime) {
                    idleRecordSave = idleTime; // idleRecordSave=10  idleRecordSave=20
                }

            }

            // 出来循环之后，idleRecordSave值计算完毕（闲置时间）
            // keepAlive=60s    idleRecordSave=30  60-30
            if (idleRecordSave >= 0) {
                return (keepAlive - idleRecordSave);
            }
        }
        return idleRecordSave
    }

    /**
     * 线程池
     *
     * 复用的决策
     *
     * 线程任务的（内部需要的）
     * LinkedBlockingQueue  链表方式处理的队列
     * SynchronousQueue     队列
     *
     */
    var threadPoolExecutor = ThreadPoolExecutor(
        0,
        Int.MAX_VALUE,
        60,
        TimeUnit.SECONDS,
        SynchronousQueue<Runnable>(),
        object : ThreadFactory {
            override fun newThread(r: Runnable?): Thread {
                var thread = Thread(r, "ConnectionPool")
                thread.isDaemon = true// 设置为守护线程
                return thread
            }

        }
    )

    /**
     * TODO 添加（连接对象）---》 连接池里面
     */

    /**
     * TODO 获取（连接对象）---》 连接池里面  可用 有效的 （复用）
     * String host, int port --> 查找有效的 连接对象 （复用）
     */
}