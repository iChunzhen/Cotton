package cn.ichunzhen.architecture.okhttp.ok

import java.util.*
import java.util.concurrent.*
import kotlin.collections.ArrayDeque


class MDispatcher {
    internal val maxRequests = 64 // 同时访问任务，最大限制64个

    internal val maxRequestsPerHost = 5 // 同时访问同一个服务器域名，最大限制5个

    internal val runningAsyncCalls: Deque<MRealCall.MAsyncCall> = ArrayDeque() // 存储运行的队列

    internal val readyAsyncCalls: Deque<MRealCall.MAsyncCall> = ArrayDeque() // 存储等待的队列

    fun enqueue(call: MRealCall.MAsyncCall) {
        // 同时运行的队列数 必须小于 配置的64   && 同时访问同一个服务器域名 不能超过5个
        if (runningAsyncCalls.size < maxRequests && runningCallsForHost(call) < maxRequestsPerHost) {
            runningAsyncCalls.add(call); // 先把任务加入到 运行队列中
            executorService().execute(call); // 然后再执行....
        } else {
            readyAsyncCalls.add(call); // 加入到等待队列中
        }
    }

    /**
     * 缓存方案
     * @return
     */
    private fun executorService(): ExecutorService {
        return ThreadPoolExecutor(0, Int.MAX_VALUE, 60L, TimeUnit.SECONDS,
            SynchronousQueue<Runnable>(),
            object : ThreadFactory {
                override fun newThread(r: Runnable?): Thread {
                    var thread = Thread(r, "自己创建线程")
                    thread.isDaemon = false
                    return thread
                }
            })

    }

    /**
     * 判断AsyncCall2中的Host，在运行的队列中，计数，然后放回
     * 参数AsyncCall2.Request.Host  == runningAsyncCalls.for{AsyncCall2.Request.Host} +1
     * @param call
     * @return
     */
    private fun runningCallsForHost(call: MRealCall.MAsyncCall): Int {
        var count = 0
        if (runningAsyncCalls.isNullOrEmpty()) {
            return 0
        }
        val srs = SocketRequestServer()
        /**
         * 遍历运行队列里面的所有任务，取出任务host == call.host +1
         */
        for (runningAsyncCall in runningAsyncCalls) {
            // 取出任务host == call.host
            if (srs.getHost(runningAsyncCall.getRequest()).equals(call.getRequest())) {
                count++
            }
        }
        return count

    }

    /**
     * 1.移除运行完成的任务
     * 2.把等待队列里面所有的任务取出来【执行】  AsyncCall2.run finished
     * @param call
     */
    fun finished(call: MRealCall.MAsyncCall) {
        // 当前运行完成的任务 给回收
        runningAsyncCalls.remove(call)
        // 考虑等待队列里面是否有任务，如果有任务是需要执行的
        if (readyAsyncCalls.isEmpty()) {
            return;
        }
        // 把等待队列中的任务给 移动 运行队列
        for (c in readyAsyncCalls) {
            readyAsyncCalls.remove(c)// 删除等待队列的任务
            runningAsyncCalls.add(c) // 把刚刚删除的等待队列任务 加入到 运行队列
            executorService().execute(c)// 开始执行任务
        }
    }

}
