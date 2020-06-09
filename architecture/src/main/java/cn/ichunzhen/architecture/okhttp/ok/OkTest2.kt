package cn.ichunzhen.architecture.okhttp.ok

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket
import java.net.URL
import javax.net.ssl.SSLSocketFactory

/**
 * @Author yuancz
 * @Date 2020/6/9-10:50
 * @Email ichunzhen6@gmail.com
 */
fun main() {
    println("请输入网址，然后回车...") // www.baidu.com
    val br =
        BufferedReader(InputStreamReader(System.`in`))
    val inputPath = br.readLine()
    val url = URL("https://$inputPath")
    val hostName: String = url.getHost() // 域名 主机
    var socket: Socket? = null
    var port = 0
    // HTTP  HTTS
    if ("HTTP".equals(url.getProtocol(), ignoreCase = true)) {
        port = 80
        socket = Socket(hostName, port)
    } else if ("HTTPS".equals(url.getProtocol(), ignoreCase = true)) {
        port = 443
        socket = SSLSocketFactory.getDefault().createSocket(hostName, port)
    }
    if (socket == null) {
        println("error")
        return
    }

    // TODO 写出去  请求
    val bw = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
    /**
     * GET / HTTP/1.1
     * Host: www.baud.com
     */
    bw.write("GET / HTTP/1.1\r\n")
    bw.write("Host: $hostName\r\n\r\n")
    bw.flush()

    // TODO 读取数据 响应
    val bufferedReader =
        BufferedReader(InputStreamReader(socket.getInputStream()))
    while (true) {
        var readLine: String? = null
        if (bufferedReader.readLine().also { readLine = it } != null) {
            println("响应的数据：$readLine")
        } else {
            break
        }
    }
}