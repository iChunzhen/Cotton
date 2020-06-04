package cn.ichunzhen.architecture.okhttp.chain

/**
 * @Author yuancz
 * @Date 2020/6/4-15:44
 * @Email ichunzhen6@gmail.com
 */
class Task2 : IBaseTask {
    override fun doRunAction(isTask: String, task: IBaseTask) {
        if ("2".equals(isTask)) {
            println("任务二 处理了事件")
            return
        } else {
            println("任务二 没有处理事件")
            task.doRunAction(isTask, task)
        }
    }
}