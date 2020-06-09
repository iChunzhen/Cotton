package cn.ichunzhen.architecture.okhttp.ok

import java.net.MalformedURLException
import java.net.URL

class SocketRequestServer {
    private val K = " "
    private val VIERSION = "HTTP/1.1"
    private val GRGN = "\r\n"

    /**
     * 通过Request对象，寻找到域名HOST
     * @param request2
     * @return
     */
    fun getHost(request2: MRequest): String {
        try {
            // http://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2
            val url = URL(request2.url)
            return url.getHost() // restapi.amap.com
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 端口
     * @param request
     * @return
     */
    fun getPort(request: MRequest): Int {
        try {
            val url = URL(request.url)
            val port = url.port
            return if (port == -1) url.defaultPort else port
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return -1
    }

    /**
     * 获取请求头的所有信息
     * @param request2
     * @return
     */
    fun getRequestHeaderAll(request2: MRequest): String? {
        // 得到请求方式
        var url: URL? = null
        try {
            url = URL(request2.url)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        val file = url!!.file

        // 拼接 请求头 的 请求行  GET /v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n
        val stringBuffer = StringBuffer()
        stringBuffer.append(request2.requestMethod) // GET or POST
            .append(K)
            .append(file)
            .append(K)
            .append(VIERSION)
            .append(GRGN)

        //获取请求集 进行拼接
        /**
         * Content-Length: 48\r\n
         * Host: restapi.amap.com\r\n
         * Content-Type: application/x-www-form-urlencoded\r\n
         */
        if (!request2.mHeaderList.isEmpty()) {
            val mapList: Map<String, String> = request2.mHeaderList
            for ((key, value) in mapList) {
                stringBuffer.append(key)
                    .append(":").append(K)
                    .append(value)
                    .append(GRGN)
            }
            // 拼接空行，代表下面的POST，请求体了
            stringBuffer.append(GRGN)
        }

        // TODO POST请求才有 请求体的拼接
        if ("POST".equals(request2.requestMethod, ignoreCase = true)) {
            stringBuffer.append(request2.requestBody?.getBody()).append(GRGN)
        }
        return stringBuffer.toString()
    }


}
