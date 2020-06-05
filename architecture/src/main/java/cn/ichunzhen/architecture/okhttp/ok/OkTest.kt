package cn.ichunzhen.architecture.okhttp.ok

import java.io.IOException

/**
 * @Author yuancz
 * @Date 2020/6/5-14:13
 * @Email ichunzhen6@gmail.com
 */
fun main() {
    var mHttpClient = MOkHttpClient.Builder().build()
    var mRequest = MRequest.Builder()
        .url("")
        .get()
        .addRequestHeader("", "")
        .build()
    var mCall = mHttpClient.newCall(mRequest)
    mCall.enqueue(object : MCallback {
        override fun onFailure(call: MCall?, e: IOException?) {
            TODO("Not yet implemented")
        }

        override fun onResponse(call: MCall, response: MResponse) {
            TODO("Not yet implemented")
        }
    })
}