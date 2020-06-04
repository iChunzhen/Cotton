package cn.ichunzhen.architecture.okhttp.builder

class Worker() {
    private var params: HouseParams? = null
    fun setParams(params: HouseParams) {
        this.params = params
    }

    fun buildHouse(): House {
        if (params == null) {
            params = HouseParams()
        }
        return House(params!!.height, params!!.width, params!!.color)
    }
}
