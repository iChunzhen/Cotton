package cn.ichunzhen.architecture.okhttp.chain

/**
 * @Author yuancz
 * @Date 2020/6/4-15:44
 * @Email ichunzhen6@gmail.com
 */
class Task3 : IBaseTask {
    override fun doRunAction(isTask: String, task: IBaseTask) {
        if ("3".equals(isTask)) {
            println("任务三 处理了事件")
            return
        } else {
            println("任务三 没有处理事件")
            task.doRunAction(isTask, task)
        }
    }
}