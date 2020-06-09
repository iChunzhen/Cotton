package cn.ichunzhen.architecture.okhttp.ok.interceptor

import cn.ichunzhen.architecture.okhttp.ok.MRealCall
import cn.ichunzhen.architecture.okhttp.ok.MRequest
import cn.ichunzhen.architecture.okhttp.ok.MResponse
import java.io.IOException

/**
 * @Author yuancz
 * @Date 2020/6/5-16:48
 * @Email ichunzhen6@gmail.com
 */
class ChainManager(
    val interceptors: List<MInterceptor>,
    var index: Int,
    val request: MRequest,
    var call: MRealCall
) : MChain {
    override fun getMRequest(): MRequest {
        return request
    }

    override fun getMResponse(request2: MRequest): MResponse {
        // 判断index++计数  不能大于 size 不能等于
        if (index >= interceptors.size) throw AssertionError()

        if (interceptors.isEmpty()) {
            throw IOException("interceptors is empty")
        }
        var interceptor = interceptors.get(index)
        var manager = ChainManager(interceptors, index + 1, request, call)
        var response = interceptor.doNext(manager)
        return response
    }
}