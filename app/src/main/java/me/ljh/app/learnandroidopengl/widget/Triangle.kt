package me.ljh.app.learnandroidopengl.widget

import android.opengl.GLES30
import android.util.Log
import me.ljh.app.learnandroidopengl.util.OpenGLUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

private const val TAG = "Triangle"
private const val VERTEX_SHADE_CODE =
    "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = vPosition;" +
            "}"
@Suppress("SpellCheckingInspection")
private const val FRAGMENT_SHADER_CODE =
    "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}"
private const val COORDS_PER_VERTEX = 3
private val COLOR = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
private val TRIANGLE_COORDS = floatArrayOf(
    0.0f, 0.622008459f, 0.0f,
    -0.5f, -0.311004243f, 0.0f,
    0.5f, -0.311004243f, 0.0f
)
private val VERTEX_COUNT: Int = TRIANGLE_COORDS.size / COORDS_PER_VERTEX
@Suppress("SpellCheckingInspection")
private val VERTEX_STRIDE: Int = COORDS_PER_VERTEX * 4

@Suppress("Unused")
class Triangle : IShape {
    private var mProgram: Int
    private var mPositionHandle: Int = 0
    private var mColorHandle: Int = 0

    private val vertexBuffer: FloatBuffer =
        ByteBuffer.allocateDirect(TRIANGLE_COORDS.size * 4).order(ByteOrder.nativeOrder())
            .asFloatBuffer().apply {
                put(TRIANGLE_COORDS)
                position(0)
            }

    init {
        val vertexShaderHandle = OpenGLUtils.loadShader(GLES30.GL_VERTEX_SHADER, VERTEX_SHADE_CODE)
        val fragmentShaderHandle =
            OpenGLUtils.loadShader(GLES30.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE)
        mProgram = GLES30.glCreateProgram().also {
            GLES30.glAttachShader(it, vertexShaderHandle)
            GLES30.glAttachShader(it, fragmentShaderHandle)
            GLES30.glLinkProgram(it)
        }
        Log.d(TAG, "vertex $vertexShaderHandle fragment $fragmentShaderHandle program $mProgram")
        GLES30.glDeleteShader(vertexShaderHandle)
        GLES30.glDeleteShader(fragmentShaderHandle)
    }

    override fun draw() {
        GLES30.glUseProgram(mProgram)
        mPositionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition").also {
            GLES30.glEnableVertexAttribArray(it)
            GLES30.glVertexAttribPointer(
                it,
                COORDS_PER_VERTEX,
                GLES30.GL_FLOAT,
                false,
                VERTEX_STRIDE,
                vertexBuffer
            )

            mColorHandle = GLES30.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->
                GLES30.glUniform4fv(colorHandle, 1, COLOR, 0)
            }

            Log.d(TAG, "position $mPositionHandle color $mColorHandle")
            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, VERTEX_COUNT)

            GLES30.glDisableVertexAttribArray(it)
        }
    }
}