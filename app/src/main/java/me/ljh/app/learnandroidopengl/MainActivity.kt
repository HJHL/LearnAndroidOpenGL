package me.ljh.app.learnandroidopengl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.ljh.app.learnandroidopengl.widget.MyGLSurfaceView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MyGLSurfaceView(this))
    }
}