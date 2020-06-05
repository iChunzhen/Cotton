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
            val url = URL(request2.getUrl())
            return url.getHost() // restapi.amap.com
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return ""
    }


}
