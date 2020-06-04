package cn.ichunzhen.architecture.okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.ichunzhen.architecture.R
import okhttp3.*
import java.io.IOException

class MainActivityOk : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ok)
    }

    fun clickOne(view: View) {
        var url = "https://www.baidu.com"
        val client = OkHttpClient()
        var request = Request.Builder()
            .url(url)
            .build()
        var call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                System.out.println("请求失败.. E:${e.toString()}");
            }

            override fun onResponse(call: Call, response: Response) {
                System.out.println("请求成功.. response:${response.toString()}");
            }
        })
    }
}