package cn.ichunzhen.architecture.okhttp.ok

import java.io.IOException

/**
 * @Author yuancz
 * @Date 2020/6/4-16:15
 * @Email ichunzhen6@gmail.com
 */
interface MCallback  {
    fun onFailure(call: MCall?, e: IOException?)

    @Throws(IOException::class)
    fun onResponse(call: MCall, response: MResponse)
}