package cn.ichunzhen.architecture.okhttp.ok

import java.net.Socket

/**
 * @Author yuancz
 * @Date 2020/6/11-13:50
 * @Email ichunzhen6@gmail.com
 */
class HttpConnection(var host: String, var port: String) {
    fun closeSocket() {

    }

    var socket: Socket? = null

    // 连接对象最后使用时间
    var lastUseTime: Long = 0
}