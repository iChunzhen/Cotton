package cn.ichunzhen.architecture.okhttp.builder

/**
 * @Author yuancz
 * @Date 2020/6/4-15:06
 * @Email ichunzhen6@gmail.com
 */
class HouseParams constructor(var height: Double=1.1, var width: Double=1.2, var color: String = "白") {

    override fun toString(): String {
        return "画出来的图纸：HouseParam{" +
                "height=$height, " +
                "width= $width, " +
                "color='$color }'"
    }
}