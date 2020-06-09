package cn.ichunzhen.architecture.okhttp.ok.interceptor

import cn.ichunzhen.architecture.okhttp.ok.MResponse
import cn.ichunzhen.architecture.okhttp.ok.SocketRequestServer


/**
 * 请求头拦截器处理
 * @Author yuancz
 * @Date 2020/6/8-15:19
 * @Email ichunzhen6@gmail.com
 */
class RequestHeaderInterceptor : MInterceptor {
    override fun doNext(chain: MChain): MResponse {
        var request = chain.getMRequest()
        val mHeaderList: MutableMap<String, String> =
            request.mHeaderList as MutableMap<String, String>

        // get post  hostName    Host: restapi.amap.com

        // get post  hostName    Host: restapi.amap.com
        mHeaderList["Host"] = SocketRequestServer().getHost(chain.getMRequest())

        if ("POST".equals(request.requestMethod, ignoreCase = true)) {
            // 请求体   type lan
            /**
             * Content-Length: 48
             * Content-Type: application/x-www-form-urlencoded
             */
            mHeaderList["Content-Length"] = request.requestBody?.getBody()?.length.toString() + ""
            mHeaderList["Content-Type"] = request.requestBody!!.TYPE
        }
//        request.
        return chain.getMResponse(request)
    }
}