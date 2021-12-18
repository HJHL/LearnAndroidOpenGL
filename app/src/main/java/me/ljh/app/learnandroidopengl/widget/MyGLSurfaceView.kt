package me.ljh.app.learnandroidopengl.widget

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {
    private val renderer: MyGLRender = MyGLRender()

    init {
        setEGLContextClientVersion(3)
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }
}


class MyGLRender : GLSurfaceView.Renderer {

    companion object {
        private const val TAG = "MyGLRender"
    }

    private lateinit var mShape: IShape

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(TAG, "onSurfaceCreated E")
        // set the background to white
        GLES30.glClearColor(1f, 1f, 1f, 1.0f)
        mShape = Square()
        Log.d(TAG, "onSurfaceCreated X")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(TAG, "onSurfaceChanged E")
        GLES30.glViewport(0, 0, width, height)
        Log.d(TAG, "onSurfaceChanged X")
    }

    override fun onDrawFrame(gl: GL10?) {
        Log.d(TAG, "onDrawFrame E")
        // draw background color, which was be set by glClearColor
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        mShape.draw()
        Log.d(TAG, "onDrawFrame X")
    }
}