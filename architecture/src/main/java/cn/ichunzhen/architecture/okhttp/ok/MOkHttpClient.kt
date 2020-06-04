package cn.ichunzhen.architecture.okhttp.ok

import okhttp3.Dispatcher


/**
 * @Author yuancz
 * @Date 2020/6/4-16:14
 * @Email ichunzhen6@gmail.com
 */
class MOkHttpClient constructor(builder: Builder) {
    val dispatcher: MDispatcher = builder.mDispatcher
    var isCanceled: Boolean = false

    class Builder() {
        var mDispatcher: MDispatcher = MDispatcher()
        var isCanceled: Boolean = false
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
    }
}