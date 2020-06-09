package cn.ichunzhen.architecture.okhttp.ok


class MResponse(
    var body: String, var statusCode: Int

) {
    override fun toString(): String {
        return "${statusCode}:  ${body}"
    }
}
