package cn.ichunzhen.architecture.okhttp.ok

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * @Author yuancz
 * @Date 2020/6/5-15:03
 * @Email ichunzhen6@gmail.com
 */
class MRequestBody{
    // 表单提交Type application/x-www-form-urlencoded
    val TYPE = "application/x-www-form-urlencoded"

    private val ENC = "utf-8"

    // 请求体集合  a=123&b=666
    var bodys: MutableMap<String, String> = HashMap()

    /**
     * 添加请求体信息
     * @param key
     * @param value
     */
    fun addBody(key: String?, value: String?) {
        // 需要URL编码
        try {
            bodys[URLEncoder.encode(key, ENC)] = URLEncoder.encode(value, ENC)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    /**
     * 得到请求体信息
     */
    fun getBody(): String? {
        val stringBuffer = StringBuffer()
        for ((key, value) in bodys) {
            // a=123&b=666&
            stringBuffer.append(key)
                .append("=")
                .append(value)
                .append("&")
        }
        // a=123&b=666& 删除&
        if (stringBuffer.length != 0) {
            stringBuffer.deleteCharAt(stringBuffer.length - 1)
        }
        return stringBuffer.toString()
    }
}