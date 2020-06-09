package cn.ichunzhen.architecture.okhttp.ok.interceptor

import cn.ichunzhen.architecture.okhttp.ok.MResponse
import cn.ichunzhen.architecture.okhttp.ok.SocketRequestServer
import java.io.*
import java.net.Socket


/**
 * 连接服务器的拦截器
 * @Author yuancz
 * @Date 2020/6/8-15:52
 * @Email ichunzhen6@gmail.com
 */
class ConnectionServerInterceptor : MInterceptor {
    override fun doNext(chain: MChain): MResponse {
        val srs = SocketRequestServer()
        var request = chain.getMRequest()
        var socket = Socket(srs.getHost(request), srs.getPort(request))
        // todo  output
        var os = socket.getOutputStream()
        val bufferedWriter = BufferedWriter(OutputStreamWriter(os))
        val requestAll = srs.getRequestHeaderAll(request)
        // Log.d(TAG, "requestAll:" + requestAll);
        // Log.d(TAG, "requestAll:" + requestAll);
        println("requestAll:$requestAll")
        bufferedWriter.write(requestAll) // 给服务器发送请求 --- 请求头信息 所有的
        bufferedWriter.flush() // 真正的写出去...

        // todo 响应
        val bufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()));
//        object : Thread() {
//            override fun run() {
//                super.run()
//                var readerLine: String? = null
//                while (true) {
//                    try {
//                        if (bufferedReader.readLine().also { readerLine = it } != null) {
//                            // Log.d(TAG, "服务器响应的:" + readerLine);
//                            println("服务器响应的:$readerLine")
//                        } else {
//                            return
//                        }
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                }
//            }
//        }.start()
        val response2 = MResponse("", 0)

        // todo 取出 响应码
        val readLine = bufferedReader.readLine() // 读取第一行 响应头信息
        // 服务器响应的:HTTP/1.1 200 OK
        val strings = readLine.split(" ".toRegex()).toTypedArray()
        response2.statusCode = (strings[1].toInt())
        // todo 取出响应体，只要是空行下面的，就是响应体
        var readerLine: String? = null
        try {
            while (bufferedReader.readLine().also { readerLine = it } != null) {
                if ("" == readerLine) {
                    // 读到空行了，就代表下面就是 响应体了
                    response2.body = (bufferedReader.readLine())
                    break
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response2

    }

}