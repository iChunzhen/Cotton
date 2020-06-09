package cn.ichunzhen.architecture.okhttp.ok

import java.io.*
import java.net.MalformedURLException
import java.net.Socket
import java.net.URL
import javax.net.ssl.SSLSocketFactory

/**
 * @Author yuancz
 * @Date 2020/6/5-14:13
 * @Email ichunzhen6@gmail.com
 */
var PATH =
    "http://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2"

fun main() {
    /**自定义okhttp请求*/
//    mOkRequest()
    /**url操作*/
//    urlAction()
    /**https请求*/
//    socketHTTPS();
    /**http请求*/
//    socketHTTP();
    /**http请求*/
    socketHTTPPost();

}

fun mOkRequest() {
    var mHttpClient = MOkHttpClient.Builder().build()
    var mRequest = MRequest.Builder()
        .url(PATH)
        .get()
        .addRequestHeader("123", "zzz")
        .build()
    var mCall = mHttpClient.newCall(mRequest)
    mCall.enqueue(object : MCallback {
        override fun onFailure(call: MCall?, e: IOException?) {
            println("请求失败")
        }

        override fun onResponse(call: MCall, response: MResponse) {
            println(response.toString())
        }
    })
}

// get request https
fun socketHTTPS() {
    try {
        // Socket socket = new Socket("www.baidu.com", 443); //  http:80

        // SSL 握手   访问HTTPS的socket客户端
        val socket: Socket =
            SSLSocketFactory.getDefault().createSocket("www.baidu.com", 443)

        // TODO 写出去  请求
        val bw = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
        /**
         * GET / HTTP/1.1
         * Host: www.baud.com
         */
        bw.write("GET / HTTP/1.1\r\n")
        bw.write("Host: www.baidu.com\r\n\r\n")
        bw.flush()

        // TODO 读取数据 响应
        val br = BufferedReader(InputStreamReader(socket.getInputStream()))
        while (true) {
            var readLine: String? = null
            if (br.readLine().also { readLine = it } != null) {
                println("响应的数据：$readLine")
            } else {
                break
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun socketHTTP() {
    /**
     * GET /v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2 HTTP/1.1
     * Host: restapi.amap.com
     */

    // HttpURLConnection --->
    try {
        val socket = Socket("restapi.amap.com", 80) //  http:80

        // TODO 写出去  请求
        val bw = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
        bw.write("GET /v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n")
        bw.write("Host: restapi.amap.com\r\n\r\n")
        bw.flush()

        // TODO 读取数据 响应
        val br = BufferedReader(InputStreamReader(socket.getInputStream()))
        while (true) {
            var readLine: String? = null
            if (br.readLine().also({ readLine = it }) != null) {
                println("响应的数据：$readLine")
            } else {
                break
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun socketHTTPPost() {
    /**
     * POST /v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2 HTTP/1.1
     * Content-Length: 48
     * Host: restapi.amap.com
     * Content-Type: application/x-www-form-urlencoded
     *
     * city=110101&key=13cb58f5884f9749287abbead9c658f2
     */

    // HttpURLConnection --->
    try {
        val socket = Socket("restapi.amap.com", 80) //  http:80

        // TODO 写出去  请求
        val bw = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
        bw.write("POST /v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n")
        bw.write("Content-Length: 48\r\n")
        bw.write("Content-Type: application/x-www-form-urlencoded\r\n")
        bw.write("Host: restapi.amap.com\r\n\r\n")

        //下面是 POST请求体
        bw.write("city=110101&key=13cb58f5884f9749287abbead9c658f2\r\n")
        bw.flush()

        // TODO 读取数据 响应
        val br = BufferedReader(InputStreamReader(socket.getInputStream()))
        while (true) {
            var readLine: String? = null
            if (br.readLine().also { readLine = it } != null) {
                println("响应的数据：$readLine")
            } else {
                break
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun urlAction() {
    try {
        val url = URL(PATH)
        System.out.println("" + url.getProtocol())
        System.out.println("" + url.getHost())
        System.out.println("" + url.getFile())
        System.out.println("" + url.getQuery())
        println(url.getPort().toString() + " ---- " + url.getDefaultPort())
    } catch (e: MalformedURLException) {
        e.printStackTrace()
    }
}



