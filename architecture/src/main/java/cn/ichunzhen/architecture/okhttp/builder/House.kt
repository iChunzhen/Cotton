package cn.ichunzhen.architecture.okhttp.builder

/**
 * @Author yuancz
 * @Date 2020/6/4-15:02
 * @Email ichunzhen6@gmail.com
 */
data class House(var height: Double, var width: Double, var color: String) {
    override fun toString(): String {
        return "具体建造出来的房子>>>>：HouseParam{" +
                "height=" + height +
                ", width=" + width +
                ", color='" + color + '\'' +
                '}';
    }
}