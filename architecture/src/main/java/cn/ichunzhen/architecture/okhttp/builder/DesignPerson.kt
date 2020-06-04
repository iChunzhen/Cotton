package cn.ichunzhen.architecture.okhttp.builder

/**
 * @Author yuancz
 * @Date 2020/6/4-15:15
 * @Email ichunzhen6@gmail.com
 */

fun main() {
    var house = DesignPerson().addColor("红色")?.addHeight(10.0)?.addWidth(100.0)?.build()
    println(house)
}


class DesignPerson {
    var params: HouseParams
    var worker: Worker

    init {
        params = HouseParams()
        worker = Worker()
    }

    /**
     * 增加楼层 -- 画图纸的过程
     */
    fun addHeight(height: Double): DesignPerson? {
        params.height = height
        return this
    }

    /**
     * 增加面积 -- 画图纸的过程
     */
    fun addWidth(width: Double): DesignPerson? {
        params.width = width
        return this
    }

    /**
     * 增加颜色 -- 画图纸的过程
     */
    fun addColor(color: String): DesignPerson? {
        params.color = color
        return this
    }

    fun build(): House {
        worker.setParams(params)
        return worker.buildHouse()
    }

}
