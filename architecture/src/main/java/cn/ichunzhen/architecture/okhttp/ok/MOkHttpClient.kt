package cn.ichunzhen.architecture.okhttp.ok


/**
 * @Author yuancz
 * @Date 2020/6/4-16:14
 * @Email ichunzhen6@gmail.com
 */
class MOkHttpClient constructor(builder: Builder) {


    private val dispatcher: MDispatcher = builder.mDispatcher
    private var isCanceled: Boolean = false
    private var recount: Int = 0

    class Builder() {
        internal var mDispatcher: MDispatcher = MDispatcher()
        internal var isCanceled: Boolean = false
        internal var recount: Int = 0

        internal constructor(okHttpClient: MOkHttpClient) : this() {
            this.mDispatcher = okHttpClient.dispatcher
            this.isCanceled = okHttpClient.isCanceled
            this.recount = okHttpClient.recount
        }

        fun build(): MOkHttpClient {
            return MOkHttpClient(this)
        }

        // apply适用于run函数的任何场景，一般用于初始化一个对象实例的时候，操作对象属性，并最终返回这个对象。
        fun dispatcher(dispatcher: MDispatcher) = apply {
            this.mDispatcher = dispatcher
        }

        fun canceled() = apply {
            this.isCanceled = true
        }

        fun setRetryCount(count: Int) = apply {
            this.recount = count
        }

    }

    fun isCanceled(): Boolean = isCanceled
    fun newCall(mRequest: MRequest): MCall = MRealCall(this, mRequest)
    fun dispatcher(): MDispatcher {
        return dispatcher
    }
}