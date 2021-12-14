package me.ljh.app.learnandroidopengl.widget

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

private const val TAG = "MyGLRender"

class MyGLRender: GLSurfaceView.Renderer {
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(TAG, "onSurfaceCreated E")
        // set the RGBA value, which glClear will used.
        GLES30.glClearColor(1f, 1f, 1f, 1.0f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(TAG, "onSurfaceChanged E")
    }

    override fun onDrawFrame(gl: GL10?) {
        // draw background color, which was be set by glClearColor
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
    }
}