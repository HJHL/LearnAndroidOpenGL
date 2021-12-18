package me.ljh.app.learnandroidopengl.widget

import android.opengl.GLES30
import android.util.Log
import me.ljh.app.learnandroidopengl.util.OpenGLUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer


private const val TAG = "Square"
private const val VERTEX_SHADER_CODE = "attribute vec4 vPosition;" +
        "void main() {" +
        "  gl_Position = vPosition;" +
        "}"

@Suppress("SpellCheckingInspection")
private const val FRAGMENT_SHADER_CODE = "precision mediump float;" +
        "uniform vec4 vColor;" +
        "void main() {" +
        "  gl_FragColor = vColor;" +
        "}"

@Suppress("SpellCheckingInspection")
private const val COORDS_PER_VERTEX = 3

@Suppress("SpellCheckingInspection")
private val SQUARE_COORDS = floatArrayOf(
    -0.5f, 0.5f, 0.0f,
    -0.5f, -0.5f, 0.0f,
    0.5f, -0.5f, 0.0f,
    0.5f, 0.5f, 0.0f,
)
private val DRAW_ORDER = shortArrayOf(
    0, 1, 2,
    0, 3, 2,
)
private val COLOR = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
private const val VERTEX_STRIDE = COORDS_PER_VERTEX * 4
private val VERTEX_COUNT = SQUARE_COORDS.size / COORDS_PER_VERTEX

class Square : IShape {
    private var mProgram: Int
    private var mPosition: Int = 0
    private var mColor: Int = 0

    private val mVertexBuffer: FloatBuffer =
        ByteBuffer.allocateDirect(SQUARE_COORDS.size * 4).order(
            ByteOrder.nativeOrder()
        ).asFloatBuffer().apply {
            put(SQUARE_COORDS)
            position(0)
        }
    private val mDrawListBuffer: ShortBuffer =
        ByteBuffer.allocateDirect(DRAW_ORDER.size * 2).order(
            ByteOrder.nativeOrder()
        ).asShortBuffer().apply {
            put(DRAW_ORDER)
            position(0)
        }

    init {
        val vertexShaderHandle = OpenGLUtils.loadShader(GLES30.GL_VERTEX_SHADER, VERTEX_SHADER_CODE)
        val fragmentShaderHandle =
            OpenGLUtils.loadShader(GLES30.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE)
        mProgram = GLES30.glCreateProgram().also {
            // be careful with glAttachShader usage
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
        mPosition = GLES30.glGetAttribLocation(mProgram, "vPosition").also {
            GLES30.glEnableVertexAttribArray(it)
            GLES30.glVertexAttribPointer(
                it,
                COORDS_PER_VERTEX,
                GLES30.GL_FLOAT,
                false,
                VERTEX_STRIDE,
                mVertexBuffer
            )
            mColor = GLES30.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->
                GLES30.glUniform4fv(colorHandle, 1, COLOR, 0)
            }

            Log.d(TAG, "position $mPosition color $mColor")

            GLES30.glDrawElements(
                GLES30.GL_TRIANGLES,
                DRAW_ORDER.size,
                GLES30.GL_UNSIGNED_SHORT,
                mDrawListBuffer
            )

            GLES30.glDisableVertexAttribArray(it)
        }
    }
}