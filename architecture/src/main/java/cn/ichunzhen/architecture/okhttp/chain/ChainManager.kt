package cn.ichunzhen.architecture.okhttp.chain

/**
 * @Author yuancz
 * @Date 2020/6/4-15:50
 * @Email ichunzhen6@gmail.com
 */
fun main() {
    var chainManager = ChainManager()
    chainManager.addTask(Task1())
    chainManager.addTask(Task2())
    chainManager.addTask(Task3())
    chainManager.doRunAction("3", chainManager)
}

class ChainManager : IBaseTask {
    var chainList = ArrayList<IBaseTask>()
    var index = 0
    fun addTask(task: IBaseTask) {
        chainList.add(task)
    }

    override fun doRunAction(isTask: String, task: IBaseTask) {
        if (chainList.isNullOrEmpty()) {
            return
        }
        if (index >= chainList.size) {
            return
        }
        var iBaseTaskResult = chainList.get(index)// index 0 t1,    index 1 t2      index 2 t3
        index++
        // iBaseTaskResult本质==Task1，   iBaseTaskResult本质==Task2      iBaseTaskResult本质==Task3
        iBaseTaskResult.doRunAction(isTask, task)


    }
}