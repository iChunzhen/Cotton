package cn.ichunzhen.architecture.okhttp.ok.interceptor

import cn.ichunzhen.architecture.okhttp.ok.MResponse

/**
 * @Author yuancz
 * @Date 2020/6/5-16:44
 * @Email ichunzhen6@gmail.com
 */
interface MInterceptor {
    fun doNext(chain: MChain):MResponse
}