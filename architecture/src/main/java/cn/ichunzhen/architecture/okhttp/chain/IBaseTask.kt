package cn.ichunzhen.architecture.okhttp.chain

/**
 * @Author yuancz
 * @Date 2020/6/4-15:42
 * @Email ichunzhen6@gmail.com
 */
interface IBaseTask {
    fun doRunAction(isTask: String, task: IBaseTask)
}