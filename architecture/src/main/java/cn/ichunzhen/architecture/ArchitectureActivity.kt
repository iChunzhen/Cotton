package cn.ichunzhen.architecture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.ichunzhen.architecture.okhttp.MainActivityOk

class ArchitectureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_architecture)
    }

    fun jump(view: View) {
        startActivity(Intent(this,MainActivityOk::class.java))
    }
}
