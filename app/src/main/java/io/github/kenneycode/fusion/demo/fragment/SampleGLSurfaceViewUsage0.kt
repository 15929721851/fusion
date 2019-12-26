package io.github.kenneycode.fusion.demo.fragment

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.kenneycode.fusion.common.DataKeys
import io.github.kenneycode.fusion.demo.R
import io.github.kenneycode.fusion.demo.Util
import io.github.kenneycode.fusion.framebuffer.FrameBufferPool
import io.github.kenneycode.fusion.process.RenderGraph
import io.github.kenneycode.fusion.renderer.DisplayRenderer
import io.github.kenneycode.fusion.renderer.SimpleRenderer
import io.github.kenneycode.fusion.texture.Texture
import io.github.kenneycode.fusion.texture.TexturePool
import io.github.kenneycode.fusion.util.GLUtil
import java.nio.ByteBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *
 * Coded by kenney
 *
 * http://www.github.com/kenneycode/fusion
 *
 * 基本GLSurfaceView用法0
 *
 */

class SampleGLSurfaceViewUsage0 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample_gl_surface_view, container,  false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<GLSurfaceView>(R.id.glSurfaceView).apply {

            setEGLContextClientVersion(3)
            setEGLConfigChooser(8, 8, 8, 8, 0, 0)
            setRenderer(object : GLSurfaceView.Renderer {

                private var surfaceWidth = 0
                private var surfaceHeight = 0
                private lateinit var renderGraph: RenderGraph


                override fun onDrawFrame(gl: GL10?) {
                    Log.e("debug", "onDrawFrame()")
                    // 执行渲染
                    renderGraph.update(mutableMapOf(
                            DataKeys.KEY_DISPLAY_WIDTH to surfaceWidth,
                            DataKeys.KEY_DISPLAY_HEIGHT to surfaceHeight
                    ))
                    renderGraph.render()

                }

                override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                    surfaceWidth = width
                    surfaceHeight = height
                }

                override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {

                    // 创建一个简单渲染器
                    val simpleRenderer = SimpleRenderer()

                    // 创建一个显示渲染器
                    val displayRenderer = DisplayRenderer().apply {
                        setFlip(false, true)
                    }

                    // 创建RenderGraph
                    renderGraph = RenderGraph(simpleRenderer).apply {
                        addNextRenderer(simpleRenderer, displayRenderer)
                        init()
                    }

                    // 创建图片输入源
                    val bitmap = Util.decodeBitmapFromAssets("test.png")!!
                    val imageTexture = GLUtil.createTexture()
                    GLUtil.bitmap2Texture(imageTexture, bitmap)
                    val buffer = ByteBuffer.allocate(bitmap.width * bitmap.height * 4)
                    bitmap.copyPixelsToBuffer(buffer)
                    buffer.position(0)
                    renderGraph.setInput(TexturePool.obtainTexture(bitmap.width, bitmap.height).apply {
                        retain = true
                        setData(buffer)
                    })

//                    val bitmap = Util.decodeBitmapFromAssets("test.png")!!
//                    val imageTexture = GLUtil.createTexture()
//                    GLUtil.bitmap2Texture(imageTexture, bitmap)
//                    val texture = Texture(bitmap.width, bitmap.height).apply {
//                        retain = true
//                        texture = imageTexture
//                    }
//                    renderGraph.setInput(texture)

                }

            })

            renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        }



    }

}
