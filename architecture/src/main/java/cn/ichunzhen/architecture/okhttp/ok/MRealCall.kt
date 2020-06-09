package cn.ichunzhen.architecture.okhttp.ok

import cn.ichunzhen.architecture.okhttp.ok.interceptor.*
import java.io.IOException

/**
 * @Author yuancz
 * @Date 2020/6/4-16:16
 * @Email ichunzhen6@gmail.com
 */
class MRealCall(
    internal val mOkHttpClient: MOkHttpClient,
    internal val mRequest: MRequest
) : MCall {


    //    private var mOkHttpClient: MOkHttpClient? = null
//    private var mRequest: MRequest? = null
    private var executed = false


    fun getMOkHttpClient(): MOkHttpClient {
        return mOkHttpClient
    }

    fun getMRequest(): MRequest? {
        return mRequest
    }

    fun isExecuted(): Boolean {
        return executed
    }

    override fun enqueue(responseCallback: MCallback) {
        // 不能被重复的执行 enqueue
        synchronized(this) {
            if (executed) {
                executed = true;
                throw IllegalStateException("不能被重复的执行 enqueue Already Executed");
            }
        }
        mOkHttpClient.dispatcher().enqueue(MAsyncCall(responseCallback));
    }

    inner class MAsyncCall(var callback2: MCallback) : Runnable {
        fun getRequest(): MRequest {
            return mRequest
        }

        override fun run() {
            // 执行耗时操作
            var signalledCallback = false
            try {
                val response: MResponse = getResponseWithInterceptorChain()
                // 如果用户取消了请求，回调给用户，说失败了
                if (mOkHttpClient.isCanceled()) {
                    signalledCallback = true
                    callback2.onFailure(this@MRealCall, IOException("用户取消了 Canceled"))
                } else {
                    signalledCallback = true
                    callback2.onResponse(this@MRealCall, response)
                }
            } catch (e: IOException) {
                // 责任的划分
                if (signalledCallback) { // 如果等于true，回调给用户了，是用户操作的时候 报错
                    println("用户再使用过程中 出错了...")
                } else {
                    callback2.onFailure(
                        this@MRealCall,
                        IOException("OKHTTP getResponseWithInterceptorChain 错误... e:" + e.toString())
                    )
                }
            } finally {
                // 回收处理
                mOkHttpClient.dispatcher().finished(this)
            }
        }

        /**
         * 责任链模式设计的拦截器
         * @return
         * @throws IOException
         */
        private fun getResponseWithInterceptorChain(): MResponse {
            var interceptorList = ArrayList<MInterceptor>()
            interceptorList.add(ReRequestInterceptor())
            interceptorList.add(RequestHeaderInterceptor())
            interceptorList.add(ConnectionServerInterceptor())
            var chainManager = ChainManager(interceptorList, 0, mRequest, this@MRealCall)
            return chainManager.getMResponse(mRequest)
        }

    }
}