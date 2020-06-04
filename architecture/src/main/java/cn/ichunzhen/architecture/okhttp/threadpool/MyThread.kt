package cn.ichunzhen.architecture.okhttp.threadpool

/**
 * @Author yuancz
 * @Date 2020/6/4-10:37
 * @Email ichunzhen6@gmail.com
 */
fun main() {
    val thread = Thread {
        run {
            while (true) {
                print("run")
            }
        }
    }
    thread.start()

}