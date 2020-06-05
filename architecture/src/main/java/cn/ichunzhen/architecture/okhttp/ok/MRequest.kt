package cn.ichunzhen.architecture.okhttp.ok

/**
 * @Author yuancz
 * @Date 2020/6/4-16:15
 * @Email ichunzhen6@gmail.com
 */
val GET = "GET"
val POST = "POST"

class MRequest {
    fun getUrl(): String = url

    internal constructor(builder: Builder) {
        url = builder.url
        requestMethod = builder.requestMethod
        mHeaderList
    }

    private var url: String = ""
    private var requestMethod = GET // 默认请求下是GET

    private var mHeaderList: Map<String, String> = HashMap() // 请求头 之请求集合


    class Builder {
        internal var url: String = ""
        internal var requestMethod: String = GET // 默认请求下是GET
        internal var mHeaderList: HashMap<String, String> = HashMap()
        internal var requestBody: MRequestBody? = null
        fun url(url: String) = apply {
            this@Builder.url = url
            return this
        }

        fun get() = apply {
            requestMethod = GET
            return this
        }

        fun post(requestBody2: MRequestBody) = apply {
            requestMethod = POST
            requestBody = requestBody2
            return this
        }


        fun addRequestHeader(key: String, value: String) = apply {
            mHeaderList.put(key, value)
            return this
        }

        fun build(): MRequest {
            return MRequest(this)
        }

    }
}