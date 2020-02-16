package io.github.kenneycode.fusion.context

/**
 *
 * Coded by kenney
 *
 * http://www.github.com/kenneycode/fusion
 *
 * OpenGL Context 接口
 *
 */

interface GLContext {

    /**
     *
     * 将task在此OpenGL Context中执行
     *
     * @param task 要执行的task
     *
     */
    fun runOnGLContext(task: () -> Unit)

    fun release()

}
