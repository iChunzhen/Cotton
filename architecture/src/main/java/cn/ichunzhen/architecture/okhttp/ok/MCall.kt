package cn.ichunzhen.architecture.okhttp.ok

/**
 * @Author yuancz
 * @Date 2020/6/4-16:15
 * @Email ichunzhen6@gmail.com
 */
interface MCall {
    fun enqueue(responseCallback: MCallback)
}