package io.github.kenneycode.fusion.process

import io.github.kenneycode.fusion.context.GLContext
import io.github.kenneycode.fusion.renderer.Renderer
import io.github.kenneycode.fusion.texture.Texture
import io.github.kenneycode.fusion.util.Util

/**
 *
 * Coded by kenney
 *
 * http://www.github.com/kenneycode/fusion
 *
 * 渲染过程管理类，将Renderer按单链的方式连接，并执行渲染过程
 *
 */

open class RenderChain protected constructor(): Renderer {

    private val renderGraph = RenderGraph.create()
    private var tailRendererId: String? = null

    companion object {

        fun create(): RenderChain {
            return RenderChain()
        }

    }

    /**
     *
     * 初始化，会对chain中所有Node都调用其初始化方法
     *
     */
    override fun init() {
        renderGraph.init()
    }

    /**
     *
     * 更新数据，会对chain中所有Node都调用其更新数据的方法
     *
     * @param data 数据
     *
     * @return 是否需要执行当前渲染
     *
     */
    override fun update(data: MutableMap<String, Any>): Boolean {
        return renderGraph.update(data)
    }

    /**
     *
     * 添加一个Renderer
     *
     * @param renderer Renderer
     *
     * @return 返回此RenderChain
     *
     */
    fun addRenderer(renderer: Renderer, id: String = Util.genId(renderer)): RenderChain {
        renderGraph.addRenderer(renderer, id)
        tailRendererId?.let {
            renderGraph.connectRenderer(it, id)
        }
        tailRendererId = id
        return this
    }

    /**
     *
     * 设置输入
     *
     * @param frameBuffer 输入FrameBuffer
     *
     */
    override fun setInput(texture: Texture) {
        renderGraph.setInput(texture)
    }

    /**
     *
     * 设置输入
     *
     * @param frameBuffers 输入FrameBuffer
     */
    override fun setInput(textures: List<Texture>) {
        renderGraph.setInput(textures)
    }

    override fun getOutput(): Texture? {
        return renderGraph.getOutput()
    }

    /**
     *
     * 设置输出
     *
     * @param frameBuffer 输出FrameBuffer
     *
     */
    override fun setOutput(texture: Texture?) {
        renderGraph.setOutput(texture)
    }

    /**
     *
     * 执行渲染
     *
     * @return 输出FrameBuffer
     */
    override fun render() {
        return renderGraph.render()
    }

    fun getInput(): List<Texture> {
        return renderGraph.getInput()
    }

    /**
     *
     * 释放资源
     *
     */
    override fun release() {
        renderGraph.release()
    }

}