package cn.ichunzhen.architecture.okhttp.ok.interceptor

import cn.ichunzhen.architecture.okhttp.ok.MResponse
import java.io.IOException

/**
 * 重试拦截器
 * @Author yuancz
 * @Date 2020/6/5-16:45
 * @Email ichunzhen6@gmail.com
 */
class ReRequestInterceptor : MInterceptor {

    @Throws(java.lang.Exception::class)
    override fun doNext(chain: MChain): MResponse {
        var chainManager = chain as ChainManager
        var call = chainManager.call
        var client = call.mOkHttpClient
        var ioException = IOException()
        //重试次数不为0
        if (client.recount != 0) {

            for (i in 0..6) {
                try {
                    System.out.println("我是重试拦截器，我要Return Response2了");
                    var response = chainManager.getMResponse(chainManager.getMRequest())
                    return response
                } catch (e: IOException) {
                    ioException = e
                }
            }
        }
        throw ioException
    }
}