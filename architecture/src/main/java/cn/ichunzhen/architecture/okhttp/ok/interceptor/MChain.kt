package cn.ichunzhen.architecture.okhttp.ok.interceptor

import cn.ichunzhen.architecture.okhttp.ok.MRequest
import cn.ichunzhen.architecture.okhttp.ok.MResponse

/**
 * @Author yuancz
 * @Date 2020/6/5-16:46
 * @Email ichunzhen6@gmail.com
 */
interface MChain {
    fun getMRequest(): MRequest
    fun getMResponse(request: MRequest): MResponse
}