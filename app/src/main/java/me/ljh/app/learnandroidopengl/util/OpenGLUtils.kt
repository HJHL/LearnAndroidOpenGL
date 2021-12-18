package me.ljh.app.learnandroidopengl.util

import android.opengl.GLES30

object OpenGLUtils {
    fun loadShader(type: Int, code: String): Int = GLES30.glCreateShader(type).also {
        GLES30.glShaderSource(it, code)
        GLES30.glCompileShader(it)
    }
}